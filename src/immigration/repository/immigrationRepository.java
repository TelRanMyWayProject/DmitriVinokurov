package immigration.repository;


import java.util.Date;
import java.util.List;
import java.util.Map;

import immigration.dao.*;
import immigration.interfaces.ImmigrationRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;
@SuppressWarnings("unchecked")
public class immigrationRepository implements ImmigrationRepository {
	@PersistenceContext(unitName="springHibernate")
	EntityManager em;
	
	
	public immigrationRepository() {
		super();
	}
	
	
	public Iterable <Country> getAllCountry(){
		return em.createQuery("SELECT country FROM Country country").getResultList();
	}
	@Override
	public Iterable <Programs> getProgramsByCountry(int CountryID){
		Query query=em.createQuery("SELECT prog FROM Programs prog WHERE country_CountryId=:ID");
		query.setParameter("ID", CountryID);
		return query.getResultList();
	}
	@Override
	@Transactional(readOnly = false)
	public Programs addProgram(Map<String, String>  properties,int CountryID) {
		Country cr=em.find(Country.class, CountryID);
		Programs prog=new Programs();
		List<?> list=getProgramFromQuery(CountryID,properties.get("name"));
		if(list.size()==0){
			prog.setProperties(properties);
			prog.setStartProgram(new Date());
			prog.setModified(new Date());
			prog.setCountry(cr);
			em.persist(prog);
		}else{
			Programs prg=(Programs)list.get(0);
			prog.setName("Error");
			prog.setEnabled((boolean) prg.getProperties().get(Programs.ENABLED));
		}
		return prog;
	}
	
	private List<?> getProgramFromQuery(int countryID, String name) {
		Query query=em.createQuery("SELECT prog FROM Programs prog WHERE country_CountryId=:ID AND name=:ProgName");
		query.setParameter("ID", countryID);
		query.setParameter("ProgName", name);
		return query.getResultList();
	}

	@Override
	@Transactional(readOnly = false)
	public Programs editProgram(Map<String, String>  properties,int id) {
		Programs pr=em.find(Programs.class, id);
		List<?> list=getProgramFromQueryEdit(pr.getCountry().getCountryId(),properties.get("name"),properties.get("programId"));
		if(list.size()==0){
			pr.setProperties(properties);
			pr.setModified(new Date());
			em.merge(pr);
			return pr;
		}else{
			Programs error=new Programs();
			Programs prg=(Programs)list.get(0);
			error.setName("Error");
			error.setEnabled((boolean) prg.getProperties().get(Programs.ENABLED));
			return error;
		}
		
	}
	
	private List<?> getProgramFromQueryEdit(int countryId, String name,
			String programId) {
		Query query=em.createQuery("SELECT prog FROM Programs prog WHERE country_CountryId=:ID AND name=:ProgName AND ProgramId!=:ProgId");
		query.setParameter("ID", countryId);
		query.setParameter("ProgName", name);
		query.setParameter("ProgId", Integer.parseInt(programId));
		return query.getResultList();
	}

	@Override
	public Country getCountryById(int countryId) {
		return em.find(Country.class, countryId);
	}

	@Override
	public Programs getProgramById(int ProgId) {
		return em.find(Programs.class, ProgId);
	}

	
	@Override
	@Transactional(readOnly = false)
	public Country addCountry(Map<String, String> properties) {
		String countryName=properties.get("name");
		List<?> list=chekingCountryName(countryName);
		if(list.size()==0&&countryName!=""){
			Country country=new Country();
			country.setProperties(properties);
			em.persist(country);
			return country;
		}else
			return null;
	}

	private List<?> chekingCountryName(String name) {
		Query query=em.createQuery("SELECT country FROM Country country WHERE name=:CountryName");
		query.setParameter("CountryName", name);
		return query.getResultList();
	}

	@Override
	@Transactional(readOnly = false)
	public Country editCountry(Map<String, String> properties, int countryId) {
		int ind=chekingCountryNameEdit(properties.get("name"),countryId).size();
		if(ind==0){
			Country country=em.find(Country.class, countryId);
			country.setProperties(properties);
			em.merge(country);
			return country;
		}
		return null;
	}

	private List<?> chekingCountryNameEdit(String name, int countryId) {
		Query query=em.createQuery("SELECT country FROM Country country WHERE name=:CountryName AND CountryId!=:Id");
		query.setParameter("CountryName", name);
		query.setParameter("Id", countryId);
		return query.getResultList();
	}

	@Override
	public Iterable<Embassy> getEmbassyByCountry(int EmbassyId) {
		Query query=em.createQuery("SELECT emb FROM Embassy emb WHERE country_CountryId=:ID");
		query.setParameter("ID", EmbassyId);
		return query.getResultList();
	}

	@Override
	public Embassy getEmbassyById(int EmbassyId) {
		return em.find(Embassy.class, EmbassyId);
	}

	@Override
	@Transactional(readOnly = false)
	public Embassy addEmbassy(Map<String, String> properties,int CountryID) {
		Country cr=em.find(Country.class, CountryID);
		Country location=em.find(Country.class, Integer.parseInt(properties.get("location")));
		if(cr!=null&&properties.get("phone")!=""){
			Embassy emb= new Embassy();
			emb.setProperties(properties);
			List<?> list=getEmbassyFromQuery(CountryID,emb.getPhone());
			if(list.size()==0&&location!=null){
				emb.setCountry(cr);
				emb.setLocation(location);
				em.persist(emb);
				return emb;
			}
		}
		return null;
			
		
	}

	private List<?> getEmbassyFromQuery(int countryID, String phone) {
		Query query=em.createQuery("SELECT emb FROM Embassy emb WHERE country_CountryId=:ID AND phone=:phoneEmb");
		query.setParameter("ID", countryID);
		query.setParameter("phoneEmb", phone);
		return query.getResultList();
	}

	@Override
	@Transactional(readOnly = false)
	public Embassy editEmbassy(Map<String, String> properties, int EmbassyID) {
		Embassy emb=em.find(Embassy.class, EmbassyID);
		Country location=em.find(Country.class, Integer.parseInt(properties.get("location")));
		List<?> list=getEmbassyFromQueryEdit(emb.getCountry().getCountryId(),properties.get("phone"),EmbassyID);
		if(list.size()==0){
			emb.setProperties(properties);
			if(location!=null)
			emb.setLocation(location);
			em.merge(emb);
			return emb;
		}else
			return null;
	}

	private List<?> getEmbassyFromQueryEdit(int countryId, String phone,
			int embassyID) {
		Query query=em.createQuery("SELECT emb FROM Embassy emb WHERE country_CountryId=:ID AND phone=:phoneEmb AND EmbassyID!=:EmbId");
		query.setParameter("ID", countryId);
		query.setParameter("phoneEmb", phone);
		query.setParameter("EmbId", embassyID);
		return query.getResultList();
	}

	@Override
	public int addStep(Step step) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addStep(String name, String description) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean editStep(int id, String name, String description) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Step getStep(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Step> getAllSteps() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addProgramStep(ProgramStep programStep) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addProgramStep(Programs program, Step step, int stepOrder,
			String description) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAllProgramStepsInProgram(Programs program) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ProgramStep getProgramStep(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<ProgramStep> getAllProgramStepsInProgram(Programs program) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addFieldNames(FieldNames fieldNames) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addFieldNames(String name, String possibleValues) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean editFieldNames(int id, String name, String possibleValues) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FieldNames getFieldNames(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<FieldNames> getAllFieldNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addProgramCustomData(ProgramCustomData programCustomData) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addProgramCustomData(Programs program, FieldNames fieldNames,
			String value) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteAllProgramCustomDataInProgram(Programs program) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ProgramCustomData getProgramCustomData(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<ProgramCustomData> getAllProgramCustomDataInProgram(
			Programs program) {
		// TODO Auto-generated method stub
		return null;
	}



	
	
}
