package s2.navigation.com.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONException;

import s2.navigation.com.R;
import s2.navigation.com.SoundPoolController;
import s2.navigation.com.model.RequestToServerDAO;

public class CallActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {
    private Button callbtn;
    private ImageView callOn, callOff;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private boolean bool = false;
    private Thread callThread;
    private Thread cancelThread;
    private SoundPoolController pool;
    private RequestToServerDAO reqDAO;
    private Thread reqThread;
    private EditText serialEdit;
    Location location;
    LocationManager mLocMng;

    @Override
    protected void onStart() {
        super.onStart();
        pool = new SoundPoolController(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        pool.releasePool();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        callbtn = (Button) findViewById(R.id.callchange_btn);
        callOff = (ImageView) findViewById(R.id.img_calloff);
        callOn = (ImageView) findViewById(R.id.img_callon);
        serialEdit = (EditText) findViewById(R.id.serialNum_edit);
        sp = getSharedPreferences("call", MODE_PRIVATE);
        editor = sp.edit();
        callThread = new Thread(new CallCenter());
        cancelThread = new Thread(new CancelCenter());

        reqDAO = new RequestToServerDAO();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},123);
            return;
        }
        mLocMng = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        mLocMng.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                2000, 1, this);
        String provider= mLocMng.getBestProvider(new Criteria(),false);
        location = mLocMng.getLastKnownLocation(provider);
        if(location != null){
            onLocationChanged(location);
            Log.i("now Location>>",location.getLatitude()+" "+location.getLongitude());
        }


        bool = sp.getBoolean("status", false);
        if(bool==true){
            callbtn.setText(R.string.callon);
            callOff.setVisibility(View.GONE);
            callOn.setVisibility(View.VISIBLE);
        }else{
            callbtn.setText(R.string.calloff);
            callOff.setVisibility(View.VISIBLE);
            callOn.setVisibility(View.GONE);
        }

        callbtn.setOnClickListener(this);
        callOn.setOnClickListener(this);
        callOff.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        pool.playSound(pool.SOUND_BBOK);
        if(bool==false){
            callDialog();
        }else{
            cancelDialog();
        }
    }

    private void callDialog() {
            new AlertDialog.Builder(CallActivity.this)
                    .setIconAttribute(android.R.attr.alertDialogIcon)
                    .setTitle("신고하기")
                    .setMessage("신고접수를 하시겠습니까?")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Toast.makeText(CallActivity.this.getApplicationContext(), "신고접수 중 입니다.", Toast.LENGTH_SHORT).show();
                            bool=true;
                            callOff.setVisibility(View.GONE);
                            callOn.setVisibility(View.VISIBLE);
                            callbtn.setText(R.string.callon);
                            editor.putBoolean("status",bool);
                            editor.commit();
                            callThread.start();

                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Log.i("DelLog", "cancel");
                            bool=false;
                            callOff.setVisibility(View.VISIBLE);
                            callOn.setVisibility(View.GONE);
                            callbtn.setText(R.string.calloff);
                            editor.putBoolean("status",bool);
                            editor.commit();
                        }
                    })
                    .show();
    }

    private void cancelDialog(){
        new AlertDialog.Builder(CallActivity.this)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle("신고하기")
                .setMessage("신고접수를 취소 하시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Toast.makeText(CallActivity.this.getApplicationContext(), "신고접수를 취소 중 입니다.", Toast.LENGTH_SHORT).show();
                        bool=false;
                        callOff.setVisibility(View.VISIBLE);
                        callOn.setVisibility(View.GONE);
                        callbtn.setText(R.string.calloff);
                        editor.putBoolean("status",bool);
                        editor.commit();
                        cancelThread.start();
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        bool=true;
                        callOff.setVisibility(View.GONE);
                        callOn.setVisibility(View.VISIBLE);
                        callbtn.setText(R.string.callon);
                        editor.putBoolean("status",bool);
                        editor.commit();
                    }
                })
                .show();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location= location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    class CallCenter implements Runnable{
        @Override
        public void run() {
            String serial = serialEdit.getText().toString();
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            Log.i("CallActivity>>",lat +":"+lng);
            reqDAO.call(serial,lat,lng);
        }
    }

    class CancelCenter implements Runnable{

        @Override
        public void run() {

        }
    }



}
