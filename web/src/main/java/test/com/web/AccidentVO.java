package test.com.web;

public class AccidentVO {
	private int num;
	private String status;
	private String serialnum;
	
	public AccidentVO() {
		num = 0;
		status = "solved";
		serialnum = "AC12676668";
	}

	public AccidentVO(int num, String status, String serialnum){
		this.num = num;
		this.status = status;
		this.serialnum = serialnum;
	}
	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSerialnum() {
		return serialnum;
	}

	public void setSerialnum(String serialnum) {
		this.serialnum = serialnum;
	}
	

	
	
	
}
