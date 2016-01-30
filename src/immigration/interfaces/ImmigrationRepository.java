package immigration.interfaces;

import java.util.Map;

import immigration.dao.Country;
import immigration.dao.Embassy;
import immigration.dao.FieldNames;
import immigration.dao.ProgramCustomData;
import immigration.dao.ProgramStep;
import immigration.dao.Programs;
import immigration.dao.Step;


public interface ImmigrationRepository {
	static final String PROGRAM="/program"; //jsp
	static final String LIST_PROGRAMS="/list_programs";
	static final String PROGRAMS="/programs";
	static final String PROGRAM_EDIT="/program_edit";
	
	static final String COUNTRY="/country"; //jsp
	static final String LIST_COUNTRIES="/list_countries";
	static final String COUNTRIES="/countries";
	static final String COUNTRY_EDIT="/country_edit";
	
	static final String EMBASSY="/embassy"; //jsp
	static final String LIST_EMBASSIES="/list_embassies";
	static final String EMBASSIES="/Embassies";
	static final String EMBASSY_EDIT="/embassy_edit";
	
	static final String STEPS = "/steps"; // JSP
	static final String STEP_ADD = "/stepadd";
	static final String STEP_EDIT = "/stepedit";

	static final String FIELDNAMES = "/fieldnames"; // JSP
	static final String FIELDNAMES_ADD = "/fieldnamesadd";
	static final String FIELDNAMES_EDIT = "/fieldnamesedit";

	static final String PROG_STEPS = "/progsteps"; // JSP
	static final String STEPS_REST = "/stepsrest";
	static final String PROGRAMSTEPS_PROGRAM = "/programstepsofprogram";
	static final String PROGRAMSTEPS_PROGRAM_SAVE = "/programstepsofprogramsave";
	static final String PROGRAMSTEPS_PROGRAM_DELETE = "/programstepsofprogramdelete";

	static final String PROG_CUSTDATA = "/progcustdata"; // JSP
	static final String FIELDNAMES_REST = "/fieldnamesrest";
	static final String PROGRAMCUSTDATA_PROGRAM = "/programcustdatasofprogram";
	static final String PROGRAMCUSTDATA_PROGRAM_SAVE = "/programcustdataofprogramsave";
	static final String PROGRAMCUSTDATA_PROGRAM_DELETE = "/programcustdataofprogramdelete";
	
	public Iterable <Country> getAllCountry();
	public Country getCountryById(int countryId);
	public Country addCountry(Map<String, String>  properties);
	public Country editCountry(Map<String, String>  properties,int countryId);
	
	public Iterable <Programs> getProgramsByCountry(int CountryID);
	public Programs getProgramById(int ProgId);
	public Programs addProgram(Map<String, String>  properties,int CountryID);
	public Programs editProgram(Map<String, String>  properties,int idProgram);
	
	public Iterable <Embassy> getEmbassyByCountry(int EmbassyId);
	public Embassy getEmbassyById(int EmbassyId);
	public Embassy addEmbassy(Map<String, String>  properties,int CountryID);
	public Embassy editEmbassy(Map<String, String>  properties,int EmbassyId);
	
	int addStep(Step step);
	boolean editStep(Step step);
	Step getStep(int id);
	Iterable<Step> getAllSteps();

	int addFieldNames(FieldNames fieldNames);
	boolean editFieldNames(FieldNames fieldNames);
	FieldNames getFieldNames(int id);
	Iterable<FieldNames> getAllFieldNames();

	int addProgramStep(ProgramStep programStep);
	int deleteAllProgramStepsInProgram(Programs program);
	Iterable<ProgramStep> getAllProgramStepsInProgram(Programs program);

	int addProgramCustomData(ProgramCustomData programCustomData);
	int deleteAllProgramCustomDataInProgram(Programs program);
	Iterable<ProgramCustomData> getAllProgramCustomDataInProgram(Programs program);

	
}
