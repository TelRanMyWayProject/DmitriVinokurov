package immigration.dao;

import java.util.List;

import javax.persistence.*;

@Entity
public class FieldsName {
	String name;
	String posibleValues;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int FieldId;
	
	@OneToMany
	@JoinTable(name="PersonCustomData",joinColumns={@JoinColumn(name="FieldId")},inverseJoinColumns={@JoinColumn(name="PersonDataid")})
	List <PersonCustomData> personValues;
	
	@OneToMany
	@JoinTable(name="ProgrammCustomData",joinColumns={@JoinColumn(name="FieldId")},inverseJoinColumns={@JoinColumn(name="ProgramDataid")})
	List <ProgrammCustomData> programValues;

	public FieldsName(String name, String posibleValues) {
		super();
		this.name = name;
		this.posibleValues = posibleValues;
	}

	public FieldsName() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	@Override
	public String toString() {
		return "FieldsName [name=" + name + ", posibleValues=" + posibleValues
				+ ", FieldId=" + FieldId + ", personValues=" + personValues
				+ ", programValues=" + programValues + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosibleValues() {
		return posibleValues;
	}

	public void setPosibleValues(String posibleValues) {
		this.posibleValues = posibleValues;
	}

	public int getFieldId() {
		return FieldId;
	}

	public List<PersonCustomData> getPersonValues() {
		return personValues;
	}

	public void setPersonValues(List<PersonCustomData> personValues) {
		this.personValues = personValues;
	}

	public List<ProgrammCustomData> getProgramValues() {
		return programValues;
	}

	public void setProgramValues(List<ProgrammCustomData> programValues) {
		this.programValues = programValues;
	}

	

}
