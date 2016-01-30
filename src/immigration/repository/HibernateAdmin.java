package immigration.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import immigration.dao.Country;
import immigration.dao.Embassy;
import immigration.dao.FieldNames;
import immigration.dao.ProgramCustomData;
import immigration.dao.ProgramStep;
import immigration.dao.Programs;
import immigration.dao.Step;
import immigration.interfaces.ImmigrationRepository;


public class HibernateAdmin implements ImmigrationRepository {

	@PersistenceContext(unitName = "springHibernate")
	EntityManager entityManager;

	
	// Step
	@Transactional(readOnly = false)
	public int addStep(Step step) {
		int result = -1;
		if (step != null) {
			try {
				entityManager.persist(step);
				entityManager.flush();
				result = step.getId();
			}
			catch (Exception e) {
			}
		}
		return result;
	}

	@Transactional(readOnly = false)
	public boolean editStep(Step step) {
		boolean result = false;
		if (step != null) {
			Step stepOrig = null;
			try {
				stepOrig = entityManager.find(Step.class, step.getId());
				if (stepOrig != null) {
					stepOrig.setName(step.getName());
					stepOrig.setDescription(step.getDescription());
					entityManager.merge(stepOrig);
					entityManager.flush();
					result = true;
				}
			}
			catch (Exception e) {
			}
		}
		return result;
	}

	public Step getStep(int id) {
		Step step = null;
		if (id > 0) {
			try {
				step = entityManager.find(Step.class, id);
			}
			catch (Exception e) {
			}
		}
		return step;
	}

	public Iterable<Step> getAllSteps() {
		try {
			Query query = entityManager.createQuery("SELECT step FROM Step step");
			return query.getResultList();
		}
		catch (Exception e) {
		}
		return null;
	}


	// FieldNames
	@Transactional(readOnly = false)
	public int addFieldNames(FieldNames fieldNames) {
		int result = -1;
		if (fieldNames != null) {
			try {
				entityManager.persist(fieldNames);
				entityManager.flush();
				result = fieldNames.getId();
			}
			catch (Exception e) {
			}
		}
		return result;
	}

	@Transactional(readOnly = false)
	public boolean editFieldNames(FieldNames fieldNames) {
		boolean result = false;
		if (fieldNames != null) {
			FieldNames fieldNamesOrig = null;
			try {
				fieldNamesOrig = entityManager.find(FieldNames.class, fieldNames.getId());
				if (fieldNamesOrig != null) {
					fieldNamesOrig.setName(fieldNames.getName());
					fieldNamesOrig.setPossibleValues(fieldNames.getPossibleValues());
					entityManager.merge(fieldNamesOrig);
					entityManager.flush();
					result = true;
				}
			}
			catch (Exception e) {
			}
		}
		return result;
	}

	public FieldNames getFieldNames(int id) {
		FieldNames fieldNames = null;
		if (id > 0) {
			try {
				fieldNames = entityManager.find(FieldNames.class, id);
			}
			catch (Exception e) {
			}
		}
		return fieldNames;
	}

	public Iterable<FieldNames> getAllFieldNames() {
		try {
			Query query = entityManager.createQuery("SELECT fieldNames FROM FieldNames fieldNames");
			return query.getResultList();
		}
		catch (Exception e) {
		}
		return null;
	}


	// ProgramStep
	@Transactional(readOnly = false)
	public int addProgramStep(ProgramStep programStep) {
		int result = -1;
		if (programStep != null) {
			try {
				entityManager.persist(programStep);
				entityManager.flush();
				result = programStep.getId();
			}
			catch (Exception e) {
			}
		}
		return result;
	}

	@Transactional(readOnly = false)
	public int deleteProgramStep(ProgramStep programStep) {
		int result = -1;
		if (programStep != null) {
			try {
				result = programStep.getId();
				entityManager.remove(programStep);
				entityManager.flush();
			}
			catch (Exception e) {
			}
		}
		return result;
	}

	@Transactional(readOnly = false)
	public int deleteAllProgramStepsInProgram(Programs program) {
		int result = -1;
		if (program != null) {
			Iterable<ProgramStep> programSteps = getAllProgramStepsInProgram(program);
			for (ProgramStep programStep : programSteps) {
				if (programStep.getId() == deleteProgramStep(programStep)) {
					if (result == -1)
						result = 1;
					else
						result++;
				}
			}
		}
		return result;
	}

	public Iterable<ProgramStep> getAllProgramStepsInProgram(Programs program) {
		if (program != null) {
			try {
				Query query = entityManager.createQuery("SELECT programStep FROM ProgramStep programStep WHERE id_program = ?1 order by stepOrder");
				query.setParameter(1, program.getProgramId());
				List<ProgramStep> resList = query.getResultList();
				return resList;
			}
			catch (Exception e) {
			}
		}
		return null;
	}


	// ProgramCustomData
	@Transactional(readOnly = false)
	public int addProgramCustomData(ProgramCustomData programCustomData) {
		int result = -1;
		if (programCustomData != null) {
			try {
				entityManager.persist(programCustomData);
				entityManager.flush();
				result = programCustomData.getId();
			}
			catch (Exception e) {
			}
		}
		return result;
	}

	public Iterable<ProgramCustomData> getAllProgramCustomDataInProgram(Programs program) {
		if (program != null) {
			try {
				Query query = entityManager.createQuery("SELECT programCustomData FROM ProgramCustomData programCustomData WHERE id_program = ?1");
				query.setParameter(1, program.getProgramId());
				return query.getResultList();
			}
			catch (Exception e) {
			}
		}
		return null;
	}

	@Transactional(readOnly = false)
	public int deleteProgramCustomData(ProgramCustomData programCustomData) {
		int result = -1;
		if (programCustomData != null) {
			try {
				result = programCustomData.getId();
				entityManager.remove(programCustomData);
				entityManager.flush();
			}
			catch (Exception e) {
			}
		}
		return result;
	}

	@Transactional(readOnly = false)
	public int deleteAllProgramCustomDataInProgram(Programs program) {
		int result = -1;
		if (program != null) {
			Iterable<ProgramCustomData> programCustomDatas = getAllProgramCustomDataInProgram(program);
			for (ProgramCustomData programCustomData : programCustomDatas) {
				if (programCustomData.getId() == deleteProgramCustomData(programCustomData)) {
					if (result == -1)
						result = 1;
					else
						result++;
				}
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public Iterable <Country> getAllCountry(){
		return entityManager.createQuery("SELECT country FROM Country country").getResultList();
	}
	@SuppressWarnings("unchecked")
	@Override
	public Iterable <Programs> getProgramsByCountry(int CountryID){
		Query query=entityManager.createQuery("SELECT prog FROM Programs prog WHERE country_CountryId=:ID");
		query.setParameter("ID", CountryID);
		return query.getResultList();
	}
	@Override
	@Transactional(readOnly = false)
	public Programs addProgram(Map<String, String>  properties,int CountryID) {
		Country cr=entityManager.find(Country.class, CountryID);
		Programs prog=new Programs();
		List<?> list=getProgramFromQuery(CountryID,properties.get("name"));
		if(list.size()==0){
			prog.setProperties(properties);
			prog.setStartProgram(new Date());
			prog.setModified(new Date());
			prog.setCountry(cr);
			entityManager.persist(prog);
		}else{
			Programs prg=(Programs)list.get(0);
			prog.setName("Error");
			prog.setEnabled((boolean) prg.getProperties().get(Programs.ENABLED));
		}
		return prog;
	}
	
	private List<?> getProgramFromQuery(int countryID, String name) {
		Query query=entityManager.createQuery("SELECT prog FROM Programs prog WHERE country_CountryId=:ID AND name=:ProgName");
		query.setParameter("ID", countryID);
		query.setParameter("ProgName", name);
		return query.getResultList();
	}

	@Override
	@Transactional(readOnly = false)
	public Programs editProgram(Map<String, String>  properties,int id) {
		Programs pr=entityManager.find(Programs.class, id);
		List<?> list=getProgramFromQueryEdit(pr.getCountry().getCountryId(),properties.get("name"),properties.get("programId"));
		if(list.size()==0){
			pr.setProperties(properties);
			pr.setModified(new Date());
			entityManager.merge(pr);
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
		Query query=entityManager.createQuery("SELECT prog FROM Programs prog WHERE country_CountryId=:ID AND name=:ProgName AND ProgramId!=:ProgId");
		query.setParameter("ID", countryId);
		query.setParameter("ProgName", name);
		query.setParameter("ProgId", Integer.parseInt(programId));
		return query.getResultList();
	}

	@Override
	public Country getCountryById(int countryId) {
		return entityManager.find(Country.class, countryId);
	}

	@Override
	public Programs getProgramById(int ProgId) {
		return entityManager.find(Programs.class, ProgId);
	}

	
	@Override
	@Transactional(readOnly = false)
	public Country addCountry(Map<String, String> properties) {
		Country error=new Country();
		error.setName("Error");
		String countryName=properties.get("name");
		List<?> list=chekingCountryName(countryName);
		if(list.size()==0&&countryName!=""){
			Country country=new Country();
			country.setProperties(properties);
			entityManager.persist(country);
			return country;
		}else
			return error;
	}

	private List<?> chekingCountryName(String name) {
		Query query=entityManager.createQuery("SELECT country FROM Country country WHERE name=:CountryName");
		query.setParameter("CountryName", name);
		return query.getResultList();
	}

	@Override
	@Transactional(readOnly = false)
	public Country editCountry(Map<String, String> properties, int countryId) {
		Country error=new Country();
		error.setName("Error");
		int ind=chekingCountryNameEdit(properties.get("name"),countryId).size();
		if(ind==0){
			Country country=entityManager.find(Country.class, countryId);
			country.setProperties(properties);
			entityManager.merge(country);
			return country;
		}
		return error;
	}

	private List<?> chekingCountryNameEdit(String name, int countryId) {
		Query query=entityManager.createQuery("SELECT country FROM Country country WHERE name=:CountryName AND CountryId!=:Id");
		query.setParameter("CountryName", name);
		query.setParameter("Id", countryId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Embassy> getEmbassyByCountry(int EmbassyId) {
		Query query=entityManager.createQuery("SELECT emb FROM Embassy emb WHERE country_CountryId=:ID");
		query.setParameter("ID", EmbassyId);
		return query.getResultList();
	}

	@Override
	public Embassy getEmbassyById(int EmbassyId) {
		return entityManager.find(Embassy.class, EmbassyId);
	}

	@Override
	@Transactional(readOnly = false)
	public Embassy addEmbassy(Map<String, String> properties,int CountryID) {
		Embassy error=new Embassy();
		error.setPhone("Error");
		Country cr=entityManager.find(Country.class, CountryID);
		Country location=entityManager.find(Country.class, Integer.parseInt(properties.get("location")));
		if(cr!=null&&properties.get("phone")!=""){
			Embassy emb= new Embassy();
			emb.setProperties(properties);
			List<?> list=getEmbassyFromQuery(CountryID,emb.getPhone());
			if(list.size()==0&&location!=null){
				emb.setCountry(cr);
				emb.setLocation(location);
				entityManager.persist(emb);
				return emb;
			}
		}
		return error;
			
		
	}

	private List<?> getEmbassyFromQuery(int countryID, String phone) {
		Query query=entityManager.createQuery("SELECT emb FROM Embassy emb WHERE country_CountryId=:ID AND phone=:phoneEmb");
		query.setParameter("ID", countryID);
		query.setParameter("phoneEmb", phone);
		return query.getResultList();
	}

	@Override
	@Transactional(readOnly = false)
	public Embassy editEmbassy(Map<String, String> properties, int EmbassyID) {
		Embassy error=new Embassy();
		error.setPhone("Error");
		Embassy emb=entityManager.find(Embassy.class, EmbassyID);
		Country location=entityManager.find(Country.class, Integer.parseInt(properties.get("location")));
		List<?> list=getEmbassyFromQueryEdit(emb.getCountry().getCountryId(),properties.get("phone"),EmbassyID);
		if(list.size()==0){
			emb.setProperties(properties);
			if(location!=null)
			emb.setLocation(location);
			entityManager.merge(emb);
			return emb;
		}else
			return error;
	}

	private List<?> getEmbassyFromQueryEdit(int countryId, String phone,
			int embassyID) {
		Query query=entityManager.createQuery("SELECT emb FROM Embassy emb WHERE country_CountryId=:ID AND phone=:phoneEmb AND EmbassyID!=:EmbId");
		query.setParameter("ID", countryId);
		query.setParameter("phoneEmb", phone);
		query.setParameter("EmbId", embassyID);
		return query.getResultList();
	}


}
