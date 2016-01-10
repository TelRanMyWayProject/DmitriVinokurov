package immigration.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity
public class Person {
String email;
String password;

@Temporal(TemporalType.DATE)
Date registration;

@Temporal(TemporalType.DATE)
Date lastLogin;

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@Column(name = "PersonId")
int PersonId;

@OneToOne
@JoinColumn(name="PersonDataId")
PersonData personData;

@OneToMany
@JoinTable(name="FamilyMember",joinColumns={@JoinColumn(name="PersonId")},inverseJoinColumns={@JoinColumn(name="MemberId")})
List <FamilyMember> familyMembers;




public Person(String email, String password, Date registration) {
	super();
	this.email = email;
	this.password = password;
	this.registration = registration;
}

@Override
public String toString() {
	return "Person [email=" + email + ", password=" + password
			+ ", registration=" + registration + ", lastLogin=" + lastLogin
			+ ", PersonId=" + PersonId + ", personData=" + personData
			+ ", familyMembers=" + familyMembers + "]";
}

public Person() {
}


public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public Date getRegistration() {
	return registration;
}

public void setRegistration(Date registration) {
	this.registration = registration;
}

public Date getLastLogin() {
	return lastLogin;
}

public void setLastLogin(Date lastLogin) {
	this.lastLogin = lastLogin;
}

public PersonData getPersonData() {
	return personData;
}

public void setPersonData(PersonData personData) {
	this.personData = personData;
}

public List<FamilyMember> getFamilyMembers() {
	return familyMembers;
}

public void setFamilyMembers(List<FamilyMember> familyMembers) {
	this.familyMembers = familyMembers;
}



}
