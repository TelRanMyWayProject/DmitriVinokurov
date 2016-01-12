package immigration.controller;

import immigration.dao.*;
import immigration.interfaces.ImmigrationRepository;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


@Controller
@RequestMapping({ "/" })
public class ControllerAdmin {

	private static final String RESULT = "result";

	@Autowired
	ImmigrationRepository hibernateWeb;

	public Gson createMyGsonWithDateFormat() {
		return new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
	}

//	public Gson createMyGsonWithDateFormat(String format) {
//		Gson gson = null;
//		if (format != null && format.length() > 0) {
//			try {
//				gson = new GsonBuilder().setDateFormat(format).create();
//			}
//			catch (Exception e) {
//				gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//			}
//		}
//		else
//			gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
//		return gson;
//	}


	// AdminFirst.JSP
	@RequestMapping("/")
	public String getAdminFirstController() {
		return "AdminFirst";
	}


	// Steps.JSP
	@RequestMapping(value = ImmigrationRepository.STEPS, method = RequestMethod.GET)
	public String getAllStepsController(Model model) {
		Iterable<Step> res = hibernateWeb.getAllSteps();
		Iterator<Step> iterator = res.iterator();
		String resJson = "[{}]";
		if (iterator.hasNext() == true) {
			// String resJson = "{\"steps\":" + new Gson().toJson(res) + "}";
			resJson = new Gson().toJson(res);
		}
		else {
			// Steps List is empty !!!
		}
		model.addAttribute(RESULT, resJson);
		return "Steps";
	}

	@RequestMapping(value = ImmigrationRepository.STEP_ADD, method = RequestMethod.POST)
	// @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void addStepController(HttpServletRequest request, HttpServletResponse response) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		int id = -1;
		Step step = new Gson().fromJson(str2, Step.class);
		if (step != null && step.getName().length() > 0)
			id = hibernateWeb.addStep(step.getName(), step.getDescription());
		String resJson = "[{}]";
		if (id != -1)
			resJson = new Gson().toJson(hibernateWeb.getStep(id));
		try {
			response.getWriter().write(resJson);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = ImmigrationRepository.STEP_EDIT, method = RequestMethod.POST)
	public void editStepController(HttpServletRequest request, HttpServletResponse response) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		boolean res = false;
		Step step = new Gson().fromJson(str2, Step.class);
		String name = step.getName();
		String description = step.getDescription();
		if (name != null && name != "")
			res = hibernateWeb.editStep(step.getId(), name, description);
		String resJson = "";
		if (res == true)
			resJson = new Gson().toJson(step);
		else
			resJson = new Gson().toJson(hibernateWeb.getStep(step.getId()));
		try {
			response.getWriter().write(resJson);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	// FieldNames.JSP
	@RequestMapping(value = ImmigrationRepository.FIELDNAMES, method = RequestMethod.GET)
	public String getAllFieldNamesController(Model model) {
		Iterable<FieldNames> res = hibernateWeb.getAllFieldNames();
		Iterator<FieldNames> iterator = res.iterator();
		if (iterator.hasNext() == true) {
			String resJson = new Gson().toJson(res);
			model.addAttribute(RESULT, resJson);
		}
		else {
			// FieldNames List is empty !!!
		}
		return "FieldNames";
	}

	@RequestMapping(value = ImmigrationRepository.FIELDNAMES_ADD, method = RequestMethod.POST)
	// @ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void addFieldNamesController(HttpServletRequest request, HttpServletResponse response) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		int id = -1;
		FieldNames fieldNames = new Gson().fromJson(str2, FieldNames.class);
		if (fieldNames != null && fieldNames.getName() != "")
			id = hibernateWeb.addFieldNames(fieldNames);
		String resJson = "[{}]";
		if (id != -1)
			resJson = new Gson().toJson(hibernateWeb.getFieldNames(id));
		try {
			response.getWriter().write(resJson);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = ImmigrationRepository.FIELDNAMES_EDIT, method = RequestMethod.POST)
	public void editFieldNamesController(HttpServletRequest request, HttpServletResponse response) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		boolean res = false;
		FieldNames fieldNames = new Gson().fromJson(str2, FieldNames.class);
		String name = fieldNames.getName();
		if (name != null && name != "")
			res = hibernateWeb.editFieldNames(fieldNames.getId(), name, fieldNames.getPossibleValues());
		String resJson = "";
		if (res == true)
			resJson = new Gson().toJson(fieldNames);
		else
			resJson = new Gson().toJson(hibernateWeb.getFieldNames(fieldNames.getId()));
		try {
			response.getWriter().write(resJson);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	// ProgSteps.JSP for Dima
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
		Programs program = gson.fromJson(str2, Programs.class);
		String resJson = "[]";
		if (program != null) {
			Iterable<ProgramStep> res = hibernateWeb.getAllProgramStepsInProgram(program);
			Iterator<ProgramStep> iterator = res.iterator();
			if (iterator.hasNext() == true)
				resJson = gson.toJson(res);
		}
		try {
			response.getWriter().write(resJson);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		List<ProgramStep> programSteps = gson.fromJson(str2, datasetListType);
		for (ProgramStep programStep : programSteps) {
			Programs program = programStep.getProgram();
			hibernateWeb.deleteAllProgramStepsInProgram(program);
			break;
		}
		for (ProgramStep programStep : programSteps) {
			programStep.setId(0);
			hibernateWeb.addProgramStep(programStep);
		}
	}

	@RequestMapping(value = ImmigrationRepository.PROGRAMSTEPS_PROGRAM_DELETE, method = RequestMethod.POST)
	public void deleteAllProgramStepsInProgramController(HttpServletRequest request) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		Gson gson = createMyGsonWithDateFormat();
		Programs program = gson.fromJson(str2, Programs.class);
		if (program != null) {
			hibernateWeb.deleteAllProgramStepsInProgram(program);
		}

	}


	// ProgCustomData.JSP for Dima
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
			e.printStackTrace();
		}
	}

	@RequestMapping(value = ImmigrationRepository.PROGRAMCUSTDATA_PROGRAM, method = RequestMethod.POST)
	public void getAllProgramCustomDataInProgramController(HttpServletRequest request, HttpServletResponse response) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		Gson gson = createMyGsonWithDateFormat();
		Programs program = gson.fromJson(str2, Programs.class);
		String resJson = "[]";
		if (program != null) {
			Iterable<ProgramCustomData> res = hibernateWeb.getAllProgramCustomDataInProgram(program);
			Iterator<ProgramCustomData> iterator = res.iterator();
			if (iterator.hasNext() == true)
				resJson = gson.toJson(res);
		}
		try {
			response.getWriter().write(resJson);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = ImmigrationRepository.PROGRAMCUSTDATA_PROGRAM_SAVE, method = RequestMethod.POST)
	public void saveProgramCustomDataInProgramController(HttpServletRequest request) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		Gson gson = createMyGsonWithDateFormat();
		Type datasetListType = new TypeToken<Collection<ProgramCustomData>>() {}.getType();
		List<ProgramCustomData> programCustomDatas = gson.fromJson(str2, datasetListType);
		for (ProgramCustomData programCustomData : programCustomDatas) {
			Programs program = programCustomData.getProgram();
			hibernateWeb.deleteAllProgramCustomDataInProgram(program);
			break;
		}
		for (ProgramCustomData programCustomData : programCustomDatas) {
			programCustomData.setId(0);
			hibernateWeb.addProgramCustomData(programCustomData);
		}
	}

	@RequestMapping(value = ImmigrationRepository.PROGRAMCUSTDATA_PROGRAM_DELETE, method = RequestMethod.POST)
	public void deleteAllProgramCustomDataInProgramController(HttpServletRequest request) {
		String str1 = request.getParameterMap().keySet().toString();
		String str2 = str1.substring(1, str1.length() - 1);
		Gson gson = createMyGsonWithDateFormat();
		Programs program = gson.fromJson(str2, Programs.class);
		if (program != null) {
			hibernateWeb.deleteAllProgramCustomDataInProgram(program);
		}

	}


	

}
