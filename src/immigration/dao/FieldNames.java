package immigration.dao;

import javax.persistence.*;

@Entity
public class FieldNames {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name", unique = true, nullable = false)
	private String name;

	private String possibleValues;

	public FieldNames() {
	}

	public FieldNames(String name, String possibleValues) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPossibleValues() {
		return possibleValues;
	}

	public void setPossibleValues(String possibleValues) {
		this.possibleValues = possibleValues;
	}

	@Override
	public String toString() {
		return "FieldNames [id=" + id + ", name=" + name + ", possibleValues=" + possibleValues + "]";
	}
	

}
