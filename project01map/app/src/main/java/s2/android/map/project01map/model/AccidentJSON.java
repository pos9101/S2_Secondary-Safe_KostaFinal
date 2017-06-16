package s2.android.map.project01map.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017-05-29.
 */

public class AccidentJSON {

    private JSONArray jArray;

    public AccidentJSON(){
        jArray = new JSONArray();
    }
    //37.402970, 127.109501
    //37.402968, 127.112440
    private JSONArray getjArray(){
        JSONObject jObject = new JSONObject();
        try {
            jObject.put("lati","37.402970");
            jObject.put("longi","127.109501");
            jArray.put(jObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jObject = new JSONObject();
        try {
            jObject.put("lati","37.402968");
            jObject.put("longi","127.112440");
            jArray.put(jObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jArray;
    }

    public double[][] getCoordinates() {
        int length  = getjArray().length();
        Log.i("Jarray length>>>",String.valueOf(length));
        int i;

        double[][] coordinates = new double[length][2];
        for ( i =0; i<length; i++){
            try {
                JSONObject tempJson = getjArray().getJSONObject(i);
                Log.i("Jobj>>>",tempJson.toString());
                Log.i("Jobj>>>",tempJson.toString().length()+"");
                Log.i("Jobj>>>",tempJson.getString("longi"));
                Log.i("Jobj>>>",tempJson.getString("lati"));
                coordinates[i][0] = Double.parseDouble(tempJson.getString("lati"));
                coordinates[i][1] = Double.parseDouble(tempJson.getString("longi"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return coordinates;
    }
}
