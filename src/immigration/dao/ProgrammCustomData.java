package immigration.dao;


import javax.persistence.*;

@Entity
public class ProgrammCustomData {
	String value;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int ProgramDataid;
	
	@ManyToOne
	Programs reqData;

	public ProgrammCustomData(String value) {
		super();
		this.value = value;
	}

	public ProgrammCustomData() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ProgrammCustomData [value=" + value + ", ProgramDataid="
				+ ProgramDataid + ", reqData=" + reqData + "]";
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getProgramDataid() {
		return ProgramDataid;
	}

	public Programs getReqData() {
		return reqData;
	}

	public void setReqData(Programs reqData) {
		this.reqData = reqData;
	} 
	
	
	
}