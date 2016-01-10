package immigration.dao;

import javax.persistence.*;

@Entity
public class ProgramSteps {
	String stepType;
	String about; //JSON
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ProgramStepId")
	int ProgramStepId;

	@Embedded
	StepTypeCont conts;
	
	@ManyToOne
	Programs stepstoDo;
	
	public ProgramSteps() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProgramSteps(String stepType, String about) {
		super();
		this.stepType = stepType;
		this.about = about;
	}
	
	@Override
	public String toString() {
		return "ProgramSteps [stepType=" + stepType + ", about=" + about
				+ ", ProgramStepId=" + ProgramStepId + "]";
	}

	public Programs getStepstoDo() {
		return stepstoDo;
	}

	public void setStepstoDo(Programs stepstoDo) {
		this.stepstoDo = stepstoDo;
	}

	public StepTypeCont getConts() {
		return conts;
	}

	public void setConts(StepTypeCont conts) {
		this.conts = conts;
	}

	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public int getProgramStepId() {
		return ProgramStepId;
	}

	public void setProgramStepId(int programStepId) {
		ProgramStepId = programStepId;
	}
	
	
	
}
