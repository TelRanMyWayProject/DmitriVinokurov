package immigration.dao;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.*;

@Entity
public class Embassy{
	
private static final String PHONE = "phone";
private static final String LINK = "link";
private static final String EMBASSYID = "EmbassyID";
private static final String ADDRESS = "address";
String phone;
String link;
String address;

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
int EmbassyID;


@ManyToOne
Country country;

@ManyToOne
Country location;

public Embassy(String phone, String link) {
	super();
	this.phone = phone;
	this.link = link;
}

public Embassy() {
	super();
}
public void setProperties(Map<String, String> properties) throws NumberFormatException{
	{
		String property=null;
		property=properties.get(PHONE);
		if(property != null&&property!="")
			phone=property;
		property=properties.get(LINK);
		if(property != null&&property!="")
			link=property;
		property=properties.get(ADDRESS);
		if(property != null&&property!="")
			address=property;
		property=properties.get(EMBASSYID);
		if(property != null&&property!="")
			EmbassyID=Integer.parseInt(property);
	}
}
public Map<String, Object> getProperties(){
	Map<String, Object> res=new HashMap<String, Object>();
	res.put(PHONE, phone);
	res.put(LINK, link);
	res.put(ADDRESS, address);
	res.put(EMBASSYID, EmbassyID);
	return res;
}	


@Override
public String toString() {
	String json=String.format("{\"phone\":\"%s\",\"link\":\"%s\",\"address\":\"%s\",\"EmbassyID\":\"%d\",\"location\":\"%d\"}", 
			phone,link,address,EmbassyID,location.CountryId);
	return json;
}


public Country getLocation() {
	return location;
}

public String getAddress() {
	return address;
}

public void setAddress(String address) {
	this.address = address;
}

public void setLocation(Country location) {
	this.location = location;
}

public Country getCountry() {
	return country;
}

public void setCountry(Country country) {
	this.country = country;
}

public void setEmbassyID(int EmbassyID) {
	this.EmbassyID = EmbassyID;
}

public String getPhone() {
	return phone;
}

public void setPhone(String phone) {
	this.phone = phone;
}

public String getLink() {
	return link;
}

public void setLink(String link) {
	this.link = link;
}

public int getEmbassyID() {
	return EmbassyID;
}


}
