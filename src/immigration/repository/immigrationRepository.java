package immigration.repository;


import java.util.List;
import java.util.Map;

import immigration.dao.Country;
import immigration.dao.Embassy;
import immigration.dao.Programs;
import immigration.interfaces.ImmigrationRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

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
		Programs prog= new Programs();
		if(cr==null){
			System.out.println("Wrong CountryID");
		}else if(properties.get("name")==""){
			System.out.println("Wrong program name");
		}
		else{
			prog.setProperties(properties);
			List list=getProgramFromQuery(CountryID,prog.getName());
			if(list.size()==0){
				prog.setCountry(cr);
				em.persist(prog);
				list=getProgramFromQuery(CountryID,prog.getName());
			}
		}
		return prog;
	}
	
	private List getProgramFromQuery(int countryID, String name) {
		Query query=em.createQuery("SELECT prog FROM Programs prog WHERE country_CountryId=:ID AND name=:ProgName");
		query.setParameter("ID", countryID);
		query.setParameter("ProgName", name);
		return query.getResultList();
	}

	@Override
	@Transactional(readOnly = false)
	public Programs editProgram(Map<String, String>  properties,int id) {
		Programs pr=em.find(Programs.class, id);
		List list=getProgramFromQuery(pr.getCountry().getCountryId(),properties.get("name"));
		if(list.size()==0){
			pr.setProperties(properties);
			em.merge(pr);
			return pr;
		}else
			return null;
		
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
		Country country=new Country();
		String countryName=properties.get("name");
		List list=chekingCountryName(countryName);
		if(list.size()==0&&countryName!=""){
			country.setProperties(properties);
			em.persist(country);
		}else{
			System.out.println("This name already exist");
		}
		return country;
	}

	private List chekingCountryName(String name) {
		Query query=em.createQuery("SELECT country FROM Country country WHERE name=:CountryName");
		query.setParameter("CountryName", name);
		return query.getResultList();
	}

	@Override
	@Transactional(readOnly = false)
	public Country editCountry(Map<String, String> properties, int countryId) {
		int ind=chekingCountryName(properties.get("name")).size();
		if(ind==0){
			Country country=em.find(Country.class, countryId);
			country.setProperties(properties);
			em.merge(country);
			return country;
		}
		return null;
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
		Country location=em.find(Country.class, properties.get("location"));
		Embassy emb= new Embassy();
		if(cr==null){
			System.out.println("Wrong CountryID");
		}else if(properties.get("phone")==""){
			System.out.println("Wrong phone name");
		}
		else{
			emb.setProperties(properties);
			List list=getEmbassyFromQuery(CountryID,emb.getPhone());
			if(list.size()==0&&location!=null){
				emb.setCountry(cr);
				emb.setLocation(location);
				em.persist(emb);
			}
		}
		return emb;
	}

	private List getEmbassyFromQuery(int countryID, String phone) {
		Query query=em.createQuery("SELECT emb FROM Embassy emb WHERE country_CountryId=:ID AND phone=:phoneEmb");
		query.setParameter("ID", countryID);
		query.setParameter("phoneEmb", phone);
		return query.getResultList();
	}

	@Override
	@Transactional(readOnly = false)
	public Embassy editEmbassy(Map<String, String> properties, int EmbassyID) {
		Embassy emb=em.find(Embassy.class, EmbassyID);
		Country location=em.find(Country.class, properties.get("location"));
		List list=getEmbassyFromQuery(emb.getCountry().getCountryId(),properties.get("phone"));
		if(list.size()==0){
			emb.setProperties(properties);
			if(location!=null)
			emb.setLocation(location);
			em.merge(emb);
			return emb;
		}else
			return null;
	}



	
	
}
