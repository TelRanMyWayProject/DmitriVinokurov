package immigration.repository;

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
		String name = step.getName();
		if (name != null && name.length() > 0) {
			Query query = entityManager.createQuery("SELECT step FROM Step step WHERE name = ?1");
			query.setParameter(1, name);
			if (query.getResultList().isEmpty()) {
				try {
					entityManager.persist(step);
					entityManager.flush();
					return step.getId();
				}
				catch (Exception e) {
				}
			}
		}
		return -1;
	}

	@Transactional(readOnly = false)
	public int addStep(String name, String description) {
		if (name != null && name.length() > 0) {
			Query query = entityManager.createQuery("SELECT step FROM Step step WHERE name = ?1");
			query.setParameter(1, name);
			if (query.getResultList().isEmpty()) {
				Step step = new Step(name, description);
				try {
					entityManager.persist(step);
					entityManager.flush();
					return step.getId();
				}
				catch (Exception e) {
				}
			}
		}
		return -1;
	}

	@Transactional(readOnly = false)
	public boolean editStep(int id, String name, String description) {
		boolean res = false;
		if (id > 0 && name != null && name.length() > 0) {
			Step step = entityManager.find(Step.class, id);
			if (step != null) {
				Query query = entityManager.createQuery("SELECT step FROM Step step WHERE name = ?1");
				query.setParameter(1, name);
				@SuppressWarnings("unchecked")
				List<Step> queryList = query.getResultList();
				if (queryList.isEmpty() || queryList.contains(step)) {
					step.setName(name);
					step.setDescription(description);
					try {
						entityManager.merge(step);
						entityManager.flush();
						res = true;
					}
					catch (Exception e) {
					}
				}
				else {
				}
			}
		}
		return res;
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

	@SuppressWarnings("unchecked")
	public Iterable<Step> getAllSteps() {
		try {
			Query query = entityManager.createQuery("SELECT step FROM Step step");
			return query.getResultList();
		}
		catch (Exception e) {
		}
		return null;
	}

//	public Iterable<Step> getAllStepsInProgram(Program program) {
//		if (program != null) {
//			try {
//				Query query = entityManager.createQuery("SELECT programStep FROM ProgramStep programStep WHERE id_program = ?1 order by stepOrder");
//				query.setParameter(1, program.getId());
//				List<ProgramStep> resList = query.getResultList();
//				List<Step> stepList = new ArrayList<Step>();
//				for (ProgramStep ps : resList) {
//					stepList.add(ps.getStep());
//				}
//				return stepList;
//			}
//			catch (Exception e) {
//			}
//		}
//		return null;
//	}

	// ProgramStep
	@Transactional(readOnly = false)
	public int addProgramStep(ProgramStep programStep) {
		Programs program = programStep.getProgram();
		Step step = programStep.getStep();
		if (program != null && step != null) {
			Query query = entityManager.createQuery("SELECT programStep FROM ProgramStep programStep WHERE id_program = ?1 AND id_step = ?2");
			query.setParameter(1, program.getProgramId());
			query.setParameter(2, step.getId());
			if (query.getResultList().isEmpty()) {
				try {
					entityManager.persist(programStep);
					entityManager.flush();
					return programStep.getId();
				}
				catch (Exception e) {
				}
			}
		}
		return -1;
	}

	@Transactional(readOnly = false)
	public int addProgramStep(Programs program, Step step, int stepOrder, String description) {
		if (program != null && step != null) {
			Query query = entityManager.createQuery("SELECT programStep FROM ProgramStep programStep WHERE id_program = ?1 AND id_step = ?2");
			query.setParameter(1, program.getProgramId());
			query.setParameter(2, step.getId());
			if (query.getResultList().isEmpty()) {
				try {
					ProgramStep programStep = new ProgramStep(program, step);
					if (stepOrder > 0) {
						programStep.setStepOrder(stepOrder);
					}
					programStep.setDescription(description);
					entityManager.persist(programStep);
					entityManager.flush();
					return programStep.getId();
				}
				catch (Exception e) {
				}
			}
		}
		return -1;
	}

//	@Transactional(readOnly = false)
//	public boolean editProgramStep(int id, Program program, Step step, int stepOrder, String description) {
//		boolean res = false;
//		if (id > 0) {
//			ProgramStep programStep = entityManager.find(ProgramStep.class, id);
//			if (programStep != null) {
//				if (program != null) {
//					programStep.setProgram(program);
//				}
//				if (step != null) {
//					programStep.setStep(step);
//				}
//				if (stepOrder > 0) {
//					programStep.setStepOrder(stepOrder);
//				}
//				programStep.setDescription(description);
//				try {
//					entityManager.merge(programStep);
//					entityManager.flush();
//					res = true;
//				}
//				catch (Exception e) {
//				}
//			}
//		}
//		return res;
//	}

	@Transactional(readOnly = false)
	public int deleteProgramStep(ProgramStep programStep) {
		if (programStep != null) {
			int res = programStep.getId();
			try {
				entityManager.remove(programStep);
				entityManager.flush();
				return res;
			}
			catch (Exception e) {
			}
		}
		return -1;
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
			// Query query = entityManager.createQuery("DELETE programStep FROM ProgramStep programStep WHERE id_program = ?1");
			// query.setParameter(1, program.getId());
			// result = query.executeUpdate();
		}
		return result;
	}

	public ProgramStep getProgramStep(int id) {
		ProgramStep programStep = null;
		if (id > 0) {
			try {
				programStep = entityManager.find(ProgramStep.class, id);
			}
			catch (Exception e) {
			}
		}
		return programStep;
	}

	public Iterable<ProgramStep> getAllProgramStepsInProgram(Programs program) {
		if (program != null) {
			try {
				Query query = entityManager.createQuery("SELECT programStep FROM ProgramStep programStep WHERE id_program = ?1 order by stepOrder");
				query.setParameter(1, program.getProgramId());
				@SuppressWarnings("unchecked")
				List<ProgramStep> resList = query.getResultList();
				return resList;
			}
			catch (Exception e) {
			}
		}
		return null;
	}

	// FieldNames
	@Transactional(readOnly = false)
	public int addFieldNames(FieldNames fieldNames) {
		String name = fieldNames.getName();
		if (name != null && name.length() > 0) {
			Query query = entityManager.createQuery("SELECT fieldNames FROM FieldNames fieldNames WHERE name = ?1");
			query.setParameter(1, name);
			if (query.getResultList().isEmpty()) {
				try {
					entityManager.persist(fieldNames);
					entityManager.flush();
					return fieldNames.getId();
				}
				catch (Exception e) {
				}
			}
		}
		return -1;
	}

	@Transactional(readOnly = false)
	public int addFieldNames(String name, String possibleValues) {
		if (name != null && name.length() > 0) {
			Query query = entityManager.createQuery("SELECT fieldNames FROM FieldNames fieldNames WHERE name = ?1");
			query.setParameter(1, name);
			if (query.getResultList().isEmpty()) {
				FieldNames fieldNames = new FieldNames(name, "");
				if (possibleValues != null) {
					fieldNames.setPossibleValues(possibleValues);
				}
				try {
					entityManager.persist(fieldNames);
					entityManager.flush();
					return fieldNames.getId();
				}
				catch (Exception e) {
				}
			}
		}
		return -1;
	}

	@Transactional(readOnly = false)
	public boolean editFieldNames(int id, String name, String possibleValues) {
		boolean res = false;
		if (name != null && name.length() > 0) {
			FieldNames fieldNames = entityManager.find(FieldNames.class, id);
			if (fieldNames != null) {
				if (name != null && name.length() > 0) {
					Query query = entityManager.createQuery("SELECT fieldNames FROM FieldNames fieldNames WHERE name = ?1");
					query.setParameter(1, name);
					@SuppressWarnings("unchecked")
					List<FieldNames> queryList = query.getResultList();
					if (queryList.isEmpty() || queryList.contains(fieldNames)) {
						fieldNames.setName(name);
						fieldNames.setPossibleValues(possibleValues);
						try {
							entityManager.merge(fieldNames);
							entityManager.flush();
							res = true;
						}
						catch (Exception e) {
						}
					}
					else {
					}
				}
			}
		}
		return res;
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

	@SuppressWarnings("unchecked")
	public Iterable<FieldNames> getAllFieldNames() {
		try {
			Query query = entityManager.createQuery("SELECT fieldNames FROM FieldNames fieldNames");
			return query.getResultList();
		}
		catch (Exception e) {
		}
		return null;
	}

	// ProgramCustomData
	@Transactional(readOnly = false)
	public int addProgramCustomData(ProgramCustomData programCustomData) {
		Programs program = programCustomData.getProgram();
		FieldNames fieldNames = programCustomData.getFieldNames();
		if (program != null && fieldNames != null) {
			Query query = entityManager.createQuery("SELECT programCustomData FROM ProgramCustomData programCustomData WHERE id_program = ?1 AND id_fieldnames = ?2");
			query.setParameter(1, program.getProgramId());
			query.setParameter(2, fieldNames.getId());
			if (query.getResultList().isEmpty()) {
				try {
					entityManager.persist(programCustomData);
					entityManager.flush();
					return programCustomData.getId();
				}
				catch (Exception e) {
				}
			}
		}
		return -1;
	}

	@Transactional(readOnly = false)
	public int addProgramCustomData(Programs program, FieldNames fieldNames, String value) {
		if (value != null && value != "" && program != null && fieldNames != null) {
			Query query = entityManager.createQuery("SELECT programCustomData FROM ProgramCustomData programCustomData WHERE id_program = ?1 AND id_fieldnames = ?2");
			query.setParameter(1, program.getProgramId());
			query.setParameter(2, fieldNames.getId());
			if (query.getResultList().isEmpty()) {
				try {
					ProgramCustomData programCustomData = new ProgramCustomData(program, fieldNames, value);
					entityManager.persist(programCustomData);
					entityManager.flush();
					return programCustomData.getId();
				}
				catch (Exception e) {
				}
			}
		}
		return -1;
	}

	public ProgramCustomData getProgramCustomData(int id) {
		ProgramCustomData programCustomData = null;
		if (id > 0) {
			try {
				programCustomData = entityManager.find(ProgramCustomData.class, id);
			}
			catch (Exception e) {
			}
		}
		return programCustomData;
	}

	@SuppressWarnings("unchecked")
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
		if (programCustomData != null) {
			try {
				entityManager.remove(programCustomData);
				entityManager.flush();
				return programCustomData.getId();
			}
			catch (Exception e) {
			}
		}
		return -1;
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

	@Override
	public Iterable<Country> getAllCountry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Country getCountryById(int countryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Country addCountry(Map<String, String> properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Country editCountry(Map<String, String> properties, int countryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Programs> getProgramsByCountry(int CountryID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Programs getProgramById(int ProgId) {
		Programs program = null;
		if (ProgId > 0) {
			try {
				program = entityManager.find(Programs.class, ProgId);
			}
			catch (Exception e) {
			}
		}
		return program;
	}

	@Override
	public Programs addProgram(Map<String, String> properties, int CountryID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Programs editProgram(Map<String, String> properties, int idProgram) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Embassy> getEmbassyByCountry(int EmbassyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Embassy getEmbassyById(int EmbassyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Embassy addEmbassy(Map<String, String> properties, int CountryID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Embassy editEmbassy(Map<String, String> properties, int EmbassyId) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
