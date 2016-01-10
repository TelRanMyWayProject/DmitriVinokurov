package immigration.dao;


import javax.persistence.*;


@Entity
public class PersonCustomData {
	
	String value;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int PersonDataid;
	
	@ManyToOne
	PersonData personData;

	public PersonCustomData(String value, PersonData personData) {
		super();
		this.value = value;
		this.personData = personData;
	}

	public PersonCustomData() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "PersonCustomData [value=" + value + ", PersonDataid="
				+ PersonDataid + ", personData=" + personData + "]";
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getPersonDataid() {
		return PersonDataid;
	}

	public PersonData getPersonData() {
		return personData;
	}

	public void setPersonData(PersonData personData) {
		this.personData = personData;
	}
	
	
}
