package immigration.controller.immigration.controller;


import java.util.LinkedHashMap;

import immigration.dao.Country;
import immigration.dao.Embassy;
import immigration.dao.Programs;
import immigration.interfaces.ImmigrationRepository;
import immigration.constants.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping({"/"})
public class ProgramsImmigrationController {
	@Autowired
	ImmigrationRepository Hibernate;
	
	@RequestMapping
	(value=immigration.constants.Constants.COUNTRIES,method=RequestMethod.GET)
	String getAllCountrys(Model model){
		return "ListCountrys";
	}
	
	@RequestMapping
	(value=Constants.LIST_COUNTRIES,method=RequestMethod.GET)
	@ResponseBody Iterable <Country> GetListCountries(){
		return Hibernate.getAllCountry();
	}
	
	@RequestMapping
	(value=Constants.COUNTRY,method=RequestMethod.POST)
	@ResponseBody Country addCountry(@RequestBody LinkedHashMap map){
		Country error=new Country();
		error.setName("Error");
		Country cr=Hibernate.addCountry(map);
		if(cr!=null)
			return cr;
		else
			return error;
	}
	
	@RequestMapping
	(value=Constants.COUNTRY_EDIT,method=RequestMethod.POST)
	@ResponseBody Country editCountry(@RequestBody LinkedHashMap map){
		Country error=new Country();
		error.setName("Error");
		Country cr=Hibernate.editCountry(map,Integer.parseInt(map.get("countryId").toString()));
		if(cr!=null)
			return cr;
		else
			return error;
	}

	@RequestMapping
	(value=Constants.PROGRAMS,method=RequestMethod.GET)
	String getProgramsByCountryId(int countryId,Model model){
		Country ctr=Hibernate.getCountryById(countryId);
		if (ctr==null)
			return this.getAllCountrys(model);
		model.addAttribute("results", Hibernate.getCountryById(countryId));
		return "ListPrograms";
	}
	@RequestMapping
	(value=Constants.LIST_PROGRAMS,method=RequestMethod.GET)
	@ResponseBody Iterable <Programs> GetListPrograms(int countryId){
		return Hibernate.getProgramsByCountry(countryId);
	}
	
	@RequestMapping
	(value=Constants.PROGRAM,method=RequestMethod.POST)
	@ResponseBody Programs addProgram(@RequestBody LinkedHashMap map){
		Programs error=new Programs();
		error.setName("Error");
		Programs pr=Hibernate.addProgram(map,Integer.parseInt(map.get("countryId").toString()));
		if(pr!=null)
			return pr;
		else
			return error;
	}
	
	@RequestMapping
	(value=Constants.PROGRAM_EDIT,method=RequestMethod.POST)
	@ResponseBody Programs editProgram(@RequestBody LinkedHashMap map){
		Programs error=new Programs();
		error.setName("Error");
		int Id=Integer.parseInt(map.get("programId").toString());
		Programs pr=Hibernate.editProgram(map,Id);
		if(pr!=null)
			return pr;
		else
			return error;
	}
	@RequestMapping
	(value=Constants.LIST_EMBASSIES,method=RequestMethod.GET)
	@ResponseBody Iterable<Embassy> GetListEmbassies(int countryId){
		return Hibernate.getEmbassyByCountry(countryId);
	}
	
	@RequestMapping
	(value=Constants.EMBASSIES,method=RequestMethod.GET)
	String getEmbassiesByCountryId(int countryId,Model model){
		Country ctr=Hibernate.getCountryById(countryId);
		if (ctr==null)
			return this.getAllCountrys(model);	
		model.addAttribute("results", Hibernate.getCountryById(countryId));
		return "ListEmbassies";
	}
	
	@RequestMapping
	(value=Constants.EMBASSY,method=RequestMethod.POST)
	@ResponseBody Embassy addEmbassy(@RequestBody LinkedHashMap map){
		Embassy error=new Embassy();
		error.setPhone("Error");
		int CountryId=Integer.parseInt(map.get("countryId").toString());
		Embassy emb=Hibernate.addEmbassy(map, CountryId);
		if(emb!=null)
			return emb;
		return error;
	}
	
	@RequestMapping
	(value=Constants.EMBASSY_EDIT,method=RequestMethod.POST)
	@ResponseBody Embassy editEmbassy(@RequestBody LinkedHashMap map){
		Embassy error=new Embassy();
		error.setPhone("Error");
		Embassy emb=Hibernate.editEmbassy(map, Integer.parseInt(map.get("embassyID").toString()));
		if(emb!=null)
			return emb;
		return error;
	}
	
	
}