package s2.navigation.com.view;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import s2.navigation.com.BroadCastProximity;
import s2.navigation.com.BroadCastProximity2;
import s2.navigation.com.R;
import s2.navigation.com.SoundPoolController;
import s2.navigation.com.model.AccidentVO;
import s2.navigation.com.model.RequestToServerDAO;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static GoogleMap mMap;
    private static LocationManager mLocMgr;
    private Location loc;
    private Marker mNowMarker;
    private Circle m200Range;
    private Circle m100Range;
    private PendingIntent mPending100;
    private PendingIntent mPending200;
    private Thread mThread;
    private final double sensingRadius200 = 200;
    private final double sensingRadius100 = 100;
    private static final int REQUEST_FINE_ACCESS = 123;
    private static final int REQUEST_COARSE_LOCATION = 234;
    private SoundPoolController pool;
    private RequestToServerDAO reqDAO = new RequestToServerDAO();
    private List<Marker> makerList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.i("MapsActivity>>", "onCreate()");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); //onMapReady() Async로실행

        pool = new SoundPoolController(getApplicationContext());
        //접근할때 호출할 브로드캐스트등록
        Intent intent = new Intent(MapsActivity.this, BroadCastProximity.class);
        mPending100 = PendingIntent.getBroadcast(MapsActivity.this, 0, intent, 0);
        Intent intent1 = new Intent(this, BroadCastProximity2.class);
        mPending200 = PendingIntent.getBroadcast(this,0,intent1,0);

        mLocMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE); //로케이션 메니져 등록
        mThread = new Thread(new ThreadSocket());
        mThread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MapsActivity>>", "onResume()");
        String gpsProvider = LocationManager.GPS_PROVIDER;
        String netProvider = LocationManager.NETWORK_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_ACCESS);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION);
        } //권한 얻기
        //위치정보 최신화 리스너 등록
        mLocMgr.requestLocationUpdates(gpsProvider, (long) 1000, 10, mLocListener);

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.overridePendingTransition(R.anim.anim_slide_out_top,R.anim.anim_nomove);
        mLocMgr.removeUpdates(mLocListener);    //리스너 해제
        mLocMgr.removeProximityAlert(mPending100); //접근 알람 해제
        mLocMgr.removeProximityAlert(mPending200); //접근 알람 해제
//        mThread.stop();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {   //맵이 사용준비가 됬을때 호출
        Log.i("MapsActivity>>", "onMapReady()");
        mMap = googleMap;
        //UI 세팅
        UiSettings settings = mMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setCompassEnabled(true);
//        mMap.setMyLocationEnabled(true);
        if (mLocMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            loc = mLocMgr.getLastKnownLocation(mLocMgr.GPS_PROVIDER); //마지막 GPS정보 얻기
        } else {
            loc = mLocMgr.getLastKnownLocation(mLocMgr.NETWORK_PROVIDER); //마지막 GPS정보 얻기
        }

        LatLng pos;
        if (loc != null) {
            pos = new LatLng(loc.getLatitude(), loc.getLongitude());
        } else {
            pos = new LatLng(37.402970, 127.109501);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 16)); //해당위치로(위치,줌)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_ACCESS);
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_LOCATION);
            return;
        } //권한 얻기

        //내차 마커세팅
        MarkerOptions myCar = new MarkerOptions();
        myCar.icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder)); //아이콘 이미지
        myCar.position(pos); //마커위치

        mNowMarker = mMap.addMarker(myCar); //마커를 맵에 add

        m100Range = mMap.addCircle(new CircleOptions().center(pos) //사고 인지범위 100
                .radius(sensingRadius100)
                .fillColor(Color.parseColor("#22ff0000"))
                .strokeWidth(0f));
        m200Range = mMap.addCircle(new CircleOptions().center(pos) //사고 인지범위 200
                .radius(sensingRadius200)
                .fillColor(Color.parseColor("#220000ff"))
                .strokeWidth(0f));

        new Thread(new GetJSONRunnable()).start();
    }

    //위치 리스너 함수
    LocationListener mLocListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LatLng position = new LatLng(location.getLatitude(), location.getLongitude());//위치값을 가져온다
            mNowMarker.setPosition(position); //자동차 지금위치를 set
            m200Range.setCenter(position); //200 반경 범위 지금위치로 set
            m100Range.setCenter(position);//100 반경 범위 지금위치로 set
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position)); // 카메라도 지금위치로 move
            Log.i("moveto", location.getLatitude() + " : " + location.getLongitude());
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
    };


    private class ThreadSocket implements Runnable { //최신화를 알려주는 Socket Thread
        String mRead = "";

        @Override
        public void run() {
            try {
                Log.i("SocketRunnable", "thread start>>");
                Socket socket = new Socket("52.231.26.49", 5000);
                BufferedReader bfReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    if ((mRead = bfReader.readLine()) != null) { //소켓서버에서 메시지를 받으면
                        Log.i("inputStream>>", mRead);
                        if (mRead.equals("placeAccident")) {
                            pool.playSound(pool.SOUND_ALAERT);
                        }
                        new Thread(new GetJSONRunnable()).start();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//end run()
    }//end class SocketRunable

    private class GetJSONRunnable implements Runnable {
        @Override
        public void run() {
          getJSON();
        }
    }

    public void getJSON() {
        ArrayList<AccidentVO> listVO = new ArrayList<>();
        Log.i("MapsActivity>>", "getJSONStart");
        try {
            listVO = reqDAO.getAccidentJson(); //네트워크에서 json을 가져와 리스트에  담음
            if(listVO!=null){
                Message msg = new Message();
                msg.obj = listVO;
                mHandler.sendMessage(msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }//end getJSON

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            addMarkerAlert((ArrayList<AccidentVO>) msg.obj);
        }
    };

    private void addMarkerAlert(ArrayList<AccidentVO> listVO){
        Log.i("MapsActivity>>", "addMakerAlertStart");
        Iterator<AccidentVO> iterator = listVO.iterator();

//      사고 지역 : 접근 알람 등록, 마커등록
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_FINE_ACCESS);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_COARSE_LOCATION);
        } //권한추가
        //이전 정보들 지우기
        if(mLocMgr !=null) {
            mLocMgr.removeProximityAlert(mPending100);
        }//접근 알람 지우기
        for(int i=0; i<makerList.size();i++){
            if(makerList.get(i) != null){
                makerList.get(i).remove(); //마커지우기
            }
        }//마커 지우기
        makerList.clear();

        //새로등록
        while(iterator.hasNext()) {
            AccidentVO tempVO = iterator.next();
            iterator.remove();
//          접근알람 등록
            mLocMgr.addProximityAlert(tempVO.getLatitude(), tempVO.getLongitude(), (float)sensingRadius100,-1,mPending100);
            mLocMgr.addProximityAlert(tempVO.getLatitude(), tempVO.getLongitude(), (float)sensingRadius200,-1,mPending200);
            LatLng position = new LatLng(tempVO.getLatitude(), tempVO.getLongitude());
            makerList.add(mMap.addMarker(new MarkerOptions().position(position).title("사고 지역").icon(BitmapDescriptorFactory.fromResource(R.drawable.warning2))));
            makerList.get(tempVO.getNum()).showInfoWindow();
        }//end while
    }//end addMarkerAlert()

}
