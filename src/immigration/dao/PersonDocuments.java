package immigration.dao;

import java.util.Date;

import javax.persistence.*;


@Entity
public class PersonDocuments {
String documentType;
String linkToImage;
String language;
String translation;
boolean isTemporary;
Date expirationDate;

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
@Column(name = "PersonDocumentId")
int PersonDocId;



public PersonDocuments() {
	super();
}

public PersonDocuments(String documentType, String linkToImage,
		String language, String translation, boolean isTemporary,
		Date expirationDate, int personDocId) {
	super();
	this.documentType = documentType;
	this.linkToImage = linkToImage;
	this.language = language;
	this.translation = translation;
	this.isTemporary = isTemporary;
	this.expirationDate = expirationDate;
}



@Override
public String toString() {
	return "PersonDocuments [documentType=" + documentType + ", linkToImage="
			+ linkToImage + ", language=" + language + ", translation="
			+ translation + ", isTemporary=" + isTemporary
			+ ", expirationDate=" + expirationDate + ", PersonDocId="
			+ PersonDocId + "]";
}

public String getDocumentType() {
	return documentType;
}

public void setDocumentType(String documentType) {
	this.documentType = documentType;
}

public String getLinkToImage() {
	return linkToImage;
}

public void setLinkToImage(String linkToImage) {
	this.linkToImage = linkToImage;
}

public String getLanguage() {
	return language;
}

public void setLanguage(String language) {
	this.language = language;
}

public String getTranslation() {
	return translation;
}

public void setTranslation(String translation) {
	this.translation = translation;
}

public boolean isTemporary() {
	return isTemporary;
}

public void setTemporary(boolean isTemporary) {
	this.isTemporary = isTemporary;
}

public Date getExpirationDate() {
	return expirationDate;
}

public void setExpirationDate(Date expirationDate) {
	this.expirationDate = expirationDate;
}

public int getPersonDocId() {
	return PersonDocId;
}

	
}
