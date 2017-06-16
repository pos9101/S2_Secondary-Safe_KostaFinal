package s2.navigation.com.model;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class RequestToServerDAO {

	private String URL_DATAS = "http://192.168.0.127:8090/web/json/datas";
    private String URL_CALL = "http://192.168.0.127:8090/web/arduino.in";
	private String URL_POST = "http://192.168.0.127:8090/web/manage.do";
	private final String URL_UPDATE = "http://192.168.0.118:8090/project02movieweb/update_json.do";
	private final String URL_DELETE = "http://192.168.0.118:8090/project02movieweb/delete_json.do";
	private final String URL_LOGIN = "http://192.168.0.118:8090/project02movieweb/login_json.do";
	

	private HttpURLConnection conn = null;
	
	
	public ArrayList<AccidentVO> getAccidentJson() throws JSONException {
		ArrayList<AccidentVO> voList =new ArrayList<>();
//		String str =requestQuery(URL_DATAS);
		String str= "{\"totalAccidents\":[{\"serialnum\":\"SF17061056\",\"latitude\":37.402129,\"longitude\":127.107474,\"atime\":\"2017.06.10/16:10\",\"status\":\"occured\"},{\"serialnum\":\"SF2017061000\",\"latitude\":37.401913,\"longitude\":127.109340,\"atime\":\"2017.06.10/13:54\",\"status\":\"occured\"}]}";
		Log.i("ReqDaoActivity>>","search");
		Log.i("ReqDaoActivity>>",str);
		 JSONObject jobj = new JSONObject(str);
		Log.i("ReqDaoActivity>>",jobj.toString());
		JSONArray jArr = jobj.getJSONArray("totalAccidents");
		Log.i("JReqDaoActivity>>",jArr.toString());
		for (int i = 0 ;  i < jArr.length() ; i++){
			JSONObject tempJobj = jArr.getJSONObject(i);
			AccidentVO acciVO = new AccidentVO();
			acciVO.setAcciNum(tempJobj.getString("serialnum"));;
			acciVO.setLatitude(tempJobj.getDouble("latitude"));
			acciVO.setLongitude(tempJobj.getDouble("longitude"));
			acciVO.setAtime(tempJobj.getString("atime"));
			acciVO.setStatus(tempJobj.getString("status"));
			Log.i("ReqDaoActivity>>","Lati:"+acciVO.getLatitude());
			Log.i("ReqDaoActivity>>","Longi:"+acciVO.getLongitude());
			voList.add(acciVO);
		}

		return voList;
	}

	public void postData(String serialnum, String status) {
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(URL_POST);

		try {
			// Dependancy 추가: org.jbundle.util.osgi.wrapped:org.jbundle.util.osgi.wrapped.org.apache.http.client:4.1.2
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("serialnum", serialnum));
			nameValuePairs.add(new BasicNameValuePair("status", status));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			//HTTP Post 요청 실행
			HttpResponse response = httpclient.execute(httppost);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void call(String serialnum, double latitude, double longitude){
		Log.i("RQA>>","insert in...");
		String jstr =requestQuery(URL_CALL+"?latitude="+ String.valueOf(latitude) + "&longitude="+ String.valueOf(longitude)
				+ "&serialnum="+ serialnum);
//		return Boolean.parseBoolean(jstr);
	}
	
	public boolean update(SignVO vo){
		Log.i("RQA>>","update in...");
		Log.i("RQA>>","Name:"+vo.getName());
		Log.i("RQA>>","Id:"+vo.getId());
		Log.i("RQA>>","Email:"+vo.getEmail());
		Log.i("RQA>>","Tel:"+vo.getTel());
		String jstr =requestQuery(URL_UPDATE+"?id="+ vo.getId() + "&tel="+ vo.getTel()
				+ "&email="+ vo.getEmail() + "&pw="+ vo.getPw());
		return Boolean.parseBoolean(jstr);
	}
	
	public boolean delete(SignVO vo){
		String jstr =requestQuery(URL_DELETE+"?id="+vo.getId());
		return Boolean.parseBoolean(jstr);
	}
	
	public boolean loginCheck(SignVO vo){
		Log.i("LocinCheck>>",URL_LOGIN+"?id="+vo.getId()+"&pw="+ vo.getPw());
		String jstr =requestQuery(URL_LOGIN+"?id="+vo.getId()+"&pw="+ vo.getPw());
		Log.i("LocinCheck>>","result"+ jstr);
		return Boolean.parseBoolean(jstr);
	}
	
	private String requestQuery(String serverURL) {
		String lr ="";
		try {
			// settings
			URL url = new URL(serverURL);
			conn = (HttpURLConnection) url.openConnection();
			Log.i("con>>",conn.getResponseCode()+"");
//			conn.setRequestMethod("GET");
//			conn.setDoInput(true);
//			conn.setDoOutput(true);
//			conn.setConnectTimeout(60);
			
//			// request
//			OutputStream os = conn.getOutputStream();
//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//			writer.write("?id=" + vo.getId() + "&name=" + vo.getName() + "&tel=" + vo.getTel() + "&email="
//					+ vo.getEmail() + "&pw=" + vo.getPw());
//
//			writer.flush();
//			writer.close();
//			os.close();
			
			// get result
			conn.connect();
			lr = lineRead();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
			if (conn != null)
				conn.disconnect();
		} // end finally

		return lr;
	}// end search

	private String lineRead() {
		StringBuilder sb=null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = null;
			sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				if (sb.length() > 0) {
					sb.append("\n");
				}
				sb.append(line);
				System.out.println("response:" + sb.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}//end line Read()
	
	
}// end class
