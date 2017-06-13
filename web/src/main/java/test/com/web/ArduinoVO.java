package test.com.web;

public class ArduinoVO {
	private String serialnum;
	private double latitude;
	private double longitude;
	private String atime;
	
	public ArduinoVO() {
		serialnum = "SF17060800";
		latitude = 37.401604;
		longitude = 127.107402;
		atime = "199101301330";
	}
	
	public ArduinoVO(String serialnum, double latitude, double longitude, String atime){

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
	
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAtime() {
		return atime;
	}
	public void setAtime(String atime) {
		this.atime = atime;
	}
	
	
}
