package immigration.dao; 

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Programs {
	public static final String NAME = "name";
	public static final String LINK = "link";
	public static final String DESCRIPTION = "description";
	public static final String CATEGORY = "category";
	public static final String ENABLED = "enabled";
	public static final String EXPIRATION = "expiration";
	public static final String PROGRAMID = "ProgramId";
	
	String name;
	String link;
	String description;
	String category;
	boolean enabled;
	Date expiration;
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int ProgramId;
	
	@JsonIgnore
	@ManyToOne
	Country country;
	
	public void setProperties(Map<String, String> properties) throws NumberFormatException{
		{
			String property=null;
			property=properties.get(NAME);
			if(property != null&&property!="")
				name=property;
			property=properties.get(LINK);
			if(property != null&&property!="")
				link=property;
			property=properties.get(DESCRIPTION);
			if(property != null&&property!="")
				description=property;
			property=properties.get(CATEGORY);
			if(property != null&&property!="")
				category=property;
			property=properties.get(ENABLED);
			if(property == "false"||property!="true")
				enabled=Boolean.parseBoolean(property);
			property=properties.get(EXPIRATION);
			if(property != null&&property!=""){
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				try {
					expiration = format.parse(property);
				} catch (ParseException e) {
				}
			}
			property=properties.get(PROGRAMID);
			if(property != null&&property!="")
				ProgramId=Integer.parseInt(property);
		}
	}
	@JsonIgnore
	public Map<String, Object> getProperties(){
		Map<String, Object> res=new HashMap<String, Object>();
		res.put(NAME, name);
		res.put(LINK, link);
		res.put(DESCRIPTION, description);
		res.put(CATEGORY, category);
		res.put(ENABLED, enabled);
		res.put(EXPIRATION, expiration);
		res.put(PROGRAMID, ProgramId);
		return res;
	}
	
	
	
	@JsonIgnore
	public Country getCountry() {
		return country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}

	

	public Programs(String name, String link, String description,String category){
		super();
		this.name=name;
		this.link=link;
		this.description=description;
		this.category = category;
	}
	
	
	public Programs() {
		super();
	}
	
	@JsonIgnore
	@Override
	public String toString() {
		String json=String.format("{\"name\":\"%s\",\"link\":\"%s\",\"description\":\"%s\",\"category\":\"%s\""
				+ ",\"enabled\":\"%s\",\"expiration\":\"%td/%tm/%tY\",\"ProgramId\":\"%d\"}", 
				name,link,description,category,enabled,expiration,expiration,expiration,ProgramId);
		return json;
	}
	
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public void setProgramId(int programId) {
		ProgramId = programId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getProgramId() {
		return ProgramId;
	}


}
