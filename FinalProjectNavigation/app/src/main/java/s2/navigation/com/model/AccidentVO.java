package s2.navigation.com.model;

import android.content.Context;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;

public class AccidentVO {
	private int num;
	private String acciNum;
	private String addr;
	private double latitude;
	private double longitude;
	private String status;
	private String atime;

	public String getAddr() {
		return addr;
	}
	public void setAddr(String address){
		this.addr = address;
	}
	public String convertAddr(double latitude, double longitude, Context context) {
		Geocoder gc = new Geocoder(context);
		String addr="";
		try {
			if(!gc.getFromLocation(latitude,longitude,1).isEmpty()){
				addr = gc.getFromLocation(latitude,longitude,1).get(0).getAddressLine(0).toString();
			}

		} catch (IOException e) {
			e.printStackTrace();
			Log.e("AccidentVO","Can not found adress");
		}
		return addr;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getAcciNum() {
		return acciNum;
	}
	public void setAcciNum(String acciNumNum) {
		this.acciNum = acciNumNum;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
