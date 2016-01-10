package immigration.dao;

import javax.persistence.*;

@Entity
public class WaySteps {
	boolean isDone;
	String information;//JSON
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "WayStepsId")
	int WayStepsId;

	@ManyToOne
	ProgramSteps ProgSteps;
	
	
	public WaySteps() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public WaySteps(boolean isDone, String information) {
		super();
		this.isDone = isDone;
		this.information = information;
	}

	public boolean isDone() {
		return isDone;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	public int getWayStepsId() {
		return WayStepsId;
	}

	public ProgramSteps getProgSteps() {
		return ProgSteps;
	}

	public void setProgSteps(ProgramSteps progSteps) {
		ProgSteps = progSteps;
	}
	
	
	
	
}
