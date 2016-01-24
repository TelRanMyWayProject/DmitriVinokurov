package immigration.interfaces;

import java.util.Map;

import org.springframework.context.annotation.Scope;

import immigration.dao.*;

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

	static final String FIELDNAMES = "/fieldnames"; // JSP for Dima
	static final String FIELDNAMES_ADD = "/fieldnamesadd";
	static final String FIELDNAMES_EDIT = "/fieldnamesedit";

	static final String PROG_STEPS = "/progsteps"; // JSP for Dima
	static final String STEPS_REST = "/stepsrest";
	static final String PROGRAMSTEPS_PROGRAM = "/programstepsofprogram";
	static final String PROGRAMSTEPS_PROGRAM_SAVE = "/programstepsofprogramsave";
	static final String PROGRAMSTEPS_PROGRAM_DELETE = "/programstepsofprogramdelete";

	static final String PROG_CUSTDATA = "/progcustdata"; // JSP for Dima
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
	int addStep(String name, String description);
	boolean editStep(int id, String name, String description);
	Step getStep(int id);
	Iterable<Step> getAllSteps();

	int addProgramStep(ProgramStep programStep);
	int addProgramStep(Programs program, Step step, int stepOrder, String description);
	int deleteAllProgramStepsInProgram(Programs program);
	ProgramStep getProgramStep(int id);
	Iterable<ProgramStep> getAllProgramStepsInProgram(Programs program);

	int addFieldNames(FieldNames fieldNames);
	int addFieldNames(String name, String possibleValues);
	boolean editFieldNames(int id, String name, String possibleValues);
	FieldNames getFieldNames(int id);
	Iterable<FieldNames> getAllFieldNames();

	int addProgramCustomData(ProgramCustomData programCustomData);
	int addProgramCustomData(Programs program, FieldNames fieldNames, String value);
	int deleteAllProgramCustomDataInProgram(Programs program);
	ProgramCustomData getProgramCustomData(int id);
	Iterable<ProgramCustomData> getAllProgramCustomDataInProgram(Programs program);
	boolean checkAdminLogin(String login, String password);

	
}
