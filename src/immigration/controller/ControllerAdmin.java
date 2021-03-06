package immigration.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import immigration.dao.Country;
import immigration.dao.Embassy;
import immigration.dao.FieldNames;
import immigration.dao.ProgramCustomData;
import immigration.dao.ProgramStep;
import immigration.dao.Programs;
import immigration.dao.Step;
import immigration.interfaces.ImmigrationRepository;



@Controller
@Scope(value="session")
@RequestMapping({ "/" })
public class ControllerAdmin {

	private static final String RESULT = "result";
	private boolean authTrue=false;
	
	@Autowired
	ImmigrationRepository hibernateWeb;

	public Gson createMyGsonWithDateFormat() {
		return new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	}

	// AdminFirst.JSP
	@RequestMapping(value="/checkLogin", method = RequestMethod.POST)
	public String checkLogin(HttpServletRequest request, HttpServletResponse response) {
		Enumeration<String> parameters = request.getParameterNames();

		while (parameters.hasMoreElements()) {
			String parametr = parameters.nextElement(); 
			if(parametr.equals("name")){
				String getParametr = request.getParameter(parametr);
				/*hibernateWeb.;*/
				
			}
		}
			return ""/*home(model)*/;
	}

	// .JSP
		@RequestMapping("/")
		public String getAdminFirstController() {
		/*	if(authTrue){*/
				return "AdminFirst";
			/*}else{
				return "login";
			}*/
		}
	
	// AdminFirst.JSP
	@RequestMapping("/mainPage")
	public String getMainGageController() {
		return "AdminFirst";
	}

	// Steps.JSP
	@RequestMapping(value = ImmigrationRepository.STEPS, method = RequestMethod.GET)
	public String getAllStepsController(Model model) {
		Iterable<Step> res = hibernateWeb.getAllSteps();
		Iterator<Step> iterator = res.iterator();
		String resJson = "[]";
		if (iterator.hasNext() == true) {
			resJson = new Gson().toJson(res);
		}
		model.addAttribute(RESULT, resJson);
		return "Steps";
	}

	@RequestMapping(value = ImmigrationRepository.STEP_ADD, method = RequestMethod.POST)
	public void addStepController(HttpServletRequest request, HttpServletResponse response) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		int id = -1;
		Step step = null;
		try {
			step = new Gson().fromJson(str2, Step.class);
			id = hibernateWeb.addStep(step);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		String resJson = "[]";
		if (id != -1)
			resJson = new Gson().toJson(hibernateWeb.getStep(id));
		try {
			response.getWriter().write(resJson);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	@RequestMapping(value = ImmigrationRepository.STEP_EDIT, method = RequestMethod.POST)
	public void editStepController(HttpServletRequest request, HttpServletResponse response) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		boolean res = false;
		Step step = null;
		try {
			step = new Gson().fromJson(str2, Step.class);
			res = hibernateWeb.editStep(step);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		String resJson = "";
		if (res == true)
			resJson = new Gson().toJson(step);
		else {
			if (step != null)
				resJson = new Gson().toJson(hibernateWeb.getStep(step.getId()));
		}
		try {
			response.getWriter().write(resJson);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}


	// FieldNames.JSP
	@RequestMapping(value = ImmigrationRepository.FIELDNAMES, method = RequestMethod.GET)
	public String getAllFieldNamesController(Model model) {
		Iterable<FieldNames> res = hibernateWeb.getAllFieldNames();
		Iterator<FieldNames> iterator = res.iterator();
		String resJson = "[]";
		if (iterator.hasNext() == true) {
			resJson = new Gson().toJson(res);
		}
		model.addAttribute(RESULT, resJson);
		return "FieldNames";
	}

	@RequestMapping(value = ImmigrationRepository.FIELDNAMES_ADD, method = RequestMethod.POST)
	public void addFieldNamesController(HttpServletRequest request, HttpServletResponse response) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		int id = -1;
		FieldNames fieldNames = null;
		try {
			fieldNames = new Gson().fromJson(str2, FieldNames.class);
			id = hibernateWeb.addFieldNames(fieldNames);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		String resJson = "[]";
		if (id != -1)
			resJson = new Gson().toJson(hibernateWeb.getFieldNames(id));
		try {
			response.getWriter().write(resJson);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	@RequestMapping(value = ImmigrationRepository.FIELDNAMES_EDIT, method = RequestMethod.POST)
	public void editFieldNamesController(HttpServletRequest request, HttpServletResponse response) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		boolean res = false;
		FieldNames fieldNames = null;
		try {
			fieldNames = new Gson().fromJson(str2, FieldNames.class);
			res = hibernateWeb.editFieldNames(fieldNames);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		String resJson = "";
		if (res == true)
			resJson = new Gson().toJson(fieldNames);
		else {
			if (fieldNames != null)
				resJson = new Gson().toJson(hibernateWeb.getFieldNames(fieldNames.getId()));
		}
		try {
			response.getWriter().write(resJson);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}


	// ProgSteps.JSP
	@RequestMapping(value = ImmigrationRepository.PROG_STEPS, method = RequestMethod.GET)
	String getAllProgramStepsInProgramControllerDima(int programId, Model model) {
		Programs program = hibernateWeb.getProgramById(programId);
		if (program != null) {
			Gson gson = createMyGsonWithDateFormat();
			String resJson1 = "{\"program\":" + gson.toJson(program) + ",";
			String resJson2 = resJson1 + "\"programsteps\":[]}";
			if (program != null) {
				Iterable<ProgramStep> res = hibernateWeb.getAllProgramStepsInProgram(program);
				Iterator<ProgramStep> iterator = res.iterator();
				if (iterator.hasNext() == true)
					resJson2 = resJson1 + "\"programsteps\":" + gson.toJson(res) + "}";
			}
			model.addAttribute(RESULT, resJson2);
		}
		else {
			model.addAttribute(RESULT, "Program Error !!!");
		}
		return "ProgSteps";
	}

	@RequestMapping(value = ImmigrationRepository.PROGRAMSTEPS_PROGRAM, method = RequestMethod.POST)
	public void getAllProgramStepsInProgramController(HttpServletRequest request, HttpServletResponse response) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		Gson gson = createMyGsonWithDateFormat();
		Programs program = null;
		String resJson = "[]";
		try {
			program = gson.fromJson(str2, Programs.class);
			Iterable<ProgramStep> res = hibernateWeb.getAllProgramStepsInProgram(program);
			Iterator<ProgramStep> iterator = res.iterator();
			if (iterator.hasNext() == true)
				resJson = gson.toJson(res);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		try {
			response.getWriter().write(resJson);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	@RequestMapping(value = ImmigrationRepository.STEPS_REST, method = RequestMethod.POST)
	public void getAllStepsForAddToProgramController(HttpServletResponse response) {
		Iterable<Step> res = hibernateWeb.getAllSteps();
		Iterator<Step> iterator = res.iterator();
		String resJson = "[]";
		if (iterator.hasNext() == true)
			resJson = new Gson().toJson(res);
		try {
			response.getWriter().write(resJson);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = ImmigrationRepository.PROGRAMSTEPS_PROGRAM_SAVE, method = RequestMethod.POST)
	public void saveProgramStepsInProgramController(HttpServletRequest request) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		Gson gson = createMyGsonWithDateFormat();
		Type datasetListType = new TypeToken<Collection<ProgramStep>>() {}.getType();
		try {
			List<ProgramStep> programSteps = gson.fromJson(str2, datasetListType);
			for (ProgramStep programStep : programSteps) {
				Programs program = programStep.getProgram();
				hibernateWeb.deleteAllProgramStepsInProgram(program);
				break;
			}
			for (ProgramStep programStep : programSteps) {
				// programStep.setId(0);
				hibernateWeb.addProgramStep(programStep);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

	@RequestMapping(value = ImmigrationRepository.PROGRAMSTEPS_PROGRAM_DELETE, method = RequestMethod.POST)
	public void deleteAllProgramStepsInProgramController(HttpServletRequest request) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		Gson gson = createMyGsonWithDateFormat();
		Programs program = null;
		try {
			program = gson.fromJson(str2, Programs.class);
			hibernateWeb.deleteAllProgramStepsInProgram(program);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}


	// ProgCustomData.JSP
	@RequestMapping(value = ImmigrationRepository.PROG_CUSTDATA, method = RequestMethod.GET)
	String getAllProgramCustomDataInProgramControllerDima(int programId, Model model) {
		Programs program = hibernateWeb.getProgramById(programId);
		if (program != null) {
			Gson gson = createMyGsonWithDateFormat();
			String resJson1 = "{\"program\":" + gson.toJson(program) + ",";
			String resJson2 = resJson1 + "\"programcustdata\":[]}";
			if (program != null) {
				Iterable<ProgramCustomData> res = hibernateWeb.getAllProgramCustomDataInProgram(program);
				Iterator<ProgramCustomData> iterator = res.iterator();
				if (iterator.hasNext() == true)
					resJson2 = resJson1 + "\"programcustdata\":" + gson.toJson(res) + "}";
			}
			model.addAttribute(RESULT, resJson2);
		}
		else {
			model.addAttribute(RESULT, "Program Error !!!");
		}
		return "ProgCustomData";
	}

	@RequestMapping(value = ImmigrationRepository.FIELDNAMES_REST, method = RequestMethod.POST)
	public void getAllFildNamesForAddToProgramController(HttpServletResponse response) {
		Iterable<FieldNames> res = hibernateWeb.getAllFieldNames();
		Iterator<FieldNames> iterator = res.iterator();
		String resJson = "[]";
		if (iterator.hasNext() == true)
			resJson = new Gson().toJson(res);
		try {
			response.getWriter().write(resJson);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	@RequestMapping(value = ImmigrationRepository.PROGRAMCUSTDATA_PROGRAM, method = RequestMethod.POST)
	public void getAllProgramCustomDataInProgramController(HttpServletRequest request, HttpServletResponse response) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		Gson gson = createMyGsonWithDateFormat();
		Programs program = null;
		String resJson = "[]";
		try {
			program = gson.fromJson(str2, Programs.class);
			Iterable<ProgramCustomData> res = hibernateWeb.getAllProgramCustomDataInProgram(program);
			Iterator<ProgramCustomData> iterator = res.iterator();
			if (iterator.hasNext() == true)
				resJson = gson.toJson(res);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		try {
			response.getWriter().write(resJson);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	@RequestMapping(value = ImmigrationRepository.PROGRAMCUSTDATA_PROGRAM_SAVE, method = RequestMethod.POST)
	public void saveProgramCustomDataInProgramController(HttpServletRequest request) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		Gson gson = createMyGsonWithDateFormat();
		Type datasetListType = new TypeToken<Collection<ProgramCustomData>>() {}.getType();
		try {
			List<ProgramCustomData> programCustomDatas = gson.fromJson(str2, datasetListType);
			for (ProgramCustomData programCustomData : programCustomDatas) {
				Programs program = programCustomData.getProgram();
				hibernateWeb.deleteAllProgramCustomDataInProgram(program);
				break;
			}
			for (ProgramCustomData programCustomData : programCustomDatas) {
				// programCustomData.setId(0);
				hibernateWeb.addProgramCustomData(programCustomData);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

	@RequestMapping(value = ImmigrationRepository.PROGRAMCUSTDATA_PROGRAM_DELETE, method = RequestMethod.POST)
	public void deleteAllProgramCustomDataInProgramController(HttpServletRequest request) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		Gson gson = createMyGsonWithDateFormat();
		Programs program = null;
		try {
			program = gson.fromJson(str2, Programs.class);
			hibernateWeb.deleteAllProgramCustomDataInProgram(program);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}


	//From Comtr#2

	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.COUNTRIES,method=RequestMethod.GET)
	String getAllCountrys(Model model){
		return "ListCountrys";
	}

	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.LIST_COUNTRIES,method=RequestMethod.GET)
	@ResponseBody Iterable <Country> GetListCountries(){
		return hibernateWeb.getAllCountry();
	}

	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.COUNTRY,method=RequestMethod.POST)
	@ResponseBody Country addCountry(@RequestBody LinkedHashMap<String, String> map){
		return hibernateWeb.addCountry(map);
	}

	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.COUNTRY_EDIT,method=RequestMethod.POST)
	@ResponseBody Country editCountry(@RequestBody LinkedHashMap<String, String> map){
		return hibernateWeb.editCountry(map,Integer.parseInt(map.get("countryId").toString()));
		
	}

	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.PROGRAMS,method=RequestMethod.GET)
	String getProgramsByCountryId(int countryId,Model model){
		Country ctr=hibernateWeb.getCountryById(countryId);
		if (ctr==null)
			return this.getAllCountrys(model);
		model.addAttribute("results", hibernateWeb.getCountryById(countryId));
		return "ListPrograms";
	}
	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.LIST_PROGRAMS,method=RequestMethod.GET)
	@ResponseBody Iterable <Programs> GetListPrograms(int countryId){
		return hibernateWeb.getProgramsByCountry(countryId);
	}

	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.PROGRAM,method=RequestMethod.POST)
	@ResponseBody Programs addProgram(@RequestBody LinkedHashMap<String, String> map){
		return hibernateWeb.addProgram(map,Integer.parseInt(map.get("countryId").toString()));

	}

	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.PROGRAM_EDIT,method=RequestMethod.POST)
	@ResponseBody Programs editProgram(@RequestBody LinkedHashMap<String, String> map){
		int Id=Integer.parseInt(map.get("programId").toString());
		return hibernateWeb.editProgram(map,Id);

	}
	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.LIST_EMBASSIES,method=RequestMethod.GET)
	@ResponseBody Iterable<Embassy> GetListEmbassies(int countryId){
		return hibernateWeb.getEmbassyByCountry(countryId);
	}

	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.EMBASSIES,method=RequestMethod.GET)
	String getEmbassiesByCountryId(int countryId,Model model){
		Country ctr=hibernateWeb.getCountryById(countryId);
		if (ctr==null)
			return this.getAllCountrys(model);	
		model.addAttribute("results", hibernateWeb.getCountryById(countryId));
		return "ListEmbassies";
	}

	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.EMBASSY,method=RequestMethod.POST)
	@ResponseBody Embassy addEmbassy(@RequestBody LinkedHashMap<String, String> map){
		return hibernateWeb.addEmbassy(map, Integer.parseInt(map.get("countryId")));
	}

	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.EMBASSY_EDIT,method=RequestMethod.POST)
	@ResponseBody Embassy editEmbassy(@RequestBody LinkedHashMap<String, String> map){
		return hibernateWeb.editEmbassy(map, Integer.parseInt(map.get("embassyID").toString()));
	}


}
