package test.com.web;

public class ArduinoVO {
	private String serialnum;
	private String latitude;
	private String longitude;
	private String atime;
	
	public ArduinoVO() {
		serialnum = "AC12676668";
		latitude ="37335887";
		longitude = "126584063";
		atime = "199101301330";
	}
	
	public ArduinoVO(String serialnum, String latitude, String longitude, String atime){
		this.serialnum = serialnum;
		this.latitude = latitude;
		this.longitude = longitude;
		this.atime = atime;
	}
	
	
	public String getSerialnum() {
		return serialnum;
	}
	public void setSerialnum(String serialnum) {
		this.serialnum = serialnum;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getAtime() {
		return atime;
	}
	public void setAtime(String atime) {
		this.atime = atime;
	}
	
	
}
