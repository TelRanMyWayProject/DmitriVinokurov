package immigration.interfaces;

import java.util.Map;

import immigration.dao.Country;
import immigration.dao.Embassy;
import immigration.dao.Programs;

public interface ImmigrationRepository {
	
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
	
}
