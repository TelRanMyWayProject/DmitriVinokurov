package immigration.controller;



import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import immigration.dao.Country;
import immigration.dao.Embassy;
import immigration.dao.Programs;
import immigration.interfaces.ImmigrationRepository;



@Controller
//@RequestMapping({"/"})
public class ProgramsImmigrationController {
	@Autowired
	ImmigrationRepository Hibernate;
	
	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.COUNTRIES,method=RequestMethod.GET)
	String getAllCountrys(Model model){
		return "ListCountrys";
	}
	
	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.LIST_COUNTRIES,method=RequestMethod.GET)
	@ResponseBody Iterable <Country> GetListCountries(){
		return Hibernate.getAllCountry();
	}
	
	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.COUNTRY,method=RequestMethod.POST)
	@ResponseBody Country addCountry(@RequestBody LinkedHashMap<String, String> map){
		Country error=new Country();
		error.setName("Error");
		Country cr=Hibernate.addCountry(map);
		if(cr!=null)
			return cr;
		else
			return error;
	}
	
	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.COUNTRY_EDIT,method=RequestMethod.POST)
	@ResponseBody Country editCountry(@RequestBody LinkedHashMap<String, String> map){
		Country error=new Country();
		error.setName("Error");
		Country cr=Hibernate.editCountry(map,Integer.parseInt(map.get("countryId").toString()));
		if(cr!=null)
			return cr;
		else
			return error;
	}

	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.PROGRAMS,method=RequestMethod.GET)
	String getProgramsByCountryId(int countryId,Model model){
		Country ctr=Hibernate.getCountryById(countryId);
		if (ctr==null)
			return this.getAllCountrys(model);
		model.addAttribute("results", Hibernate.getCountryById(countryId));
		return "ListPrograms";
	}
	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.LIST_PROGRAMS,method=RequestMethod.GET)
	@ResponseBody Iterable <Programs> GetListPrograms(int countryId){
		return Hibernate.getProgramsByCountry(countryId);
	}
	
	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.PROGRAM,method=RequestMethod.POST)
	@ResponseBody Programs addProgram(@RequestBody LinkedHashMap<String, String> map){
		return Hibernate.addProgram(map,Integer.parseInt(map.get("countryId").toString()));
		
	}
	
	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.PROGRAM_EDIT,method=RequestMethod.POST)
	@ResponseBody Programs editProgram(@RequestBody LinkedHashMap<String, String> map){
		int Id=Integer.parseInt(map.get("programId").toString());
		return Hibernate.editProgram(map,Id);
		
	}
	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.LIST_EMBASSIES,method=RequestMethod.GET)
	@ResponseBody Iterable<Embassy> GetListEmbassies(int countryId){
		return Hibernate.getEmbassyByCountry(countryId);
	}
	
	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.EMBASSIES,method=RequestMethod.GET)
	String getEmbassiesByCountryId(int countryId,Model model){
		Country ctr=Hibernate.getCountryById(countryId);
		if (ctr==null)
			return this.getAllCountrys(model);	
		model.addAttribute("results", Hibernate.getCountryById(countryId));
		return "ListEmbassies";
	}
	
	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.EMBASSY,method=RequestMethod.POST)
	@ResponseBody Embassy addEmbassy(@RequestBody LinkedHashMap<String, String> map){
		Embassy error=new Embassy();
		error.setPhone("Error");
		int CountryId=Integer.parseInt(map.get("countryId"));
		Embassy emb=Hibernate.addEmbassy(map, CountryId);
		if(emb!=null)
			return emb;
		return error;
	}
	
	@RequestMapping
	(value=immigration.interfaces.ImmigrationRepository.EMBASSY_EDIT,method=RequestMethod.POST)
	@ResponseBody Embassy editEmbassy(@RequestBody LinkedHashMap<String, String> map){
		Embassy error=new Embassy();
		error.setPhone("Error");
		Embassy emb=Hibernate.editEmbassy(map, Integer.parseInt(map.get("embassyID").toString()));
		if(emb!=null)
			return emb;
		return error;
	}
	
	
}