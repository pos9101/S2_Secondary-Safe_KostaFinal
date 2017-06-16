package s2.android.map.project01map;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;

import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

import java.net.URISyntaxException;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import s2.android.map.project01map.model.AcciMarkOveray;
import s2.android.map.project01map.model.AccidentJSON;

public class MainActivity extends MapActivity{
    MapView mMap;
    MyLocation myLocation;
    LocationManager mLocMgr;

    private final int REQUEST_FINE_ACCESS = 123;
    private final int REQUEST_COARSE_LOCATION = 234;
    Location loc;

    PendingIntent mPending;
    BroadCastProximity brReceiver;
//    static public Socket mSocket;

    AccidentJSON accidentJSON = new AccidentJSON();

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMap = (MapView) findViewById(R.id.mapView);

        //Meinifest에 등록하지 않고 리시버 등록
       brReceiver = new BroadCastProximity();
        IntentFilter filter = new IntentFilter("s2.android.map.project01map");
        registerReceiver(brReceiver,filter);
        //접근할때 호출할 인텐트 등록
        Intent intent = new Intent(this, BroadCastProximity.class);
        mPending = PendingIntent.getBroadcast(this,0,intent,0);

        //맵 기본 설정
        MapController mapControl  = mMap.getController();
        mapControl.setZoom(15);

        GeoPoint pt = new GeoPoint(37881311, 127709968);
        mapControl.setCenter(pt);

        myLocation = new MyLocation(this, mMap);
        //위험지역 표시
        Drawable warningMark = getResources().getDrawable(R.drawable.warning);
        warningMark.setBounds(0,0,warningMark.getIntrinsicWidth(),warningMark.getIntrinsicHeight());
        AcciMarkOveray warningOveray = new AcciMarkOveray(warningMark);
        List<Overlay> overlays = mMap.getOverlays();
        overlays.add(myLocation);
        overlays.add(warningOveray);

        //처음 조사된 위치로 이동
        myLocation.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                mMap.getController().animateTo(myLocation.getMyLocation());
            }
        });

        //소켓 스타트
//        startSocketNetwork();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //권한설정
        String locProv = LocationManager.GPS_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_FINE_ACCESS);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_COARSE_LOCATION);
        }
        //접근 알람 등록
        double[][] coordinates = accidentJSON.getCoordinates();
        for (int i = 0; i< coordinates.length; i++){
            mLocMgr.addProximityAlert(coordinates[i][0], coordinates[i][1], 1000,-1,mPending);
        }

//        //리스너 등록
//        mLocMgr.requestLocationUpdates(locProv, (long) 3000, 3.f, mLocListener);
//        myLocation.enableMyLocation();
//        myLocation.enableCompass();


    }

    @Override
    protected void onPause() {
        super.onPause();
        //접근 알람 해제
        mLocMgr.removeProximityAlert(mPending);
//        //리스너 해제
//        mLocMgr.removeUpdates(mLocListener);
//        myLocation.disableMyLocation();
//        myLocation.disableCompass();
    }

    public class MyLocation extends MyLocationOverlay {
        public MyLocation(Context context, MapView mapView) {
            super(context, mapView);
        }

        protected boolean dipatchTap(){
            Toast.makeText(MainActivity.this, "여기가 현재위치입니다.",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

//    //소켓 함수
//    public void startSocketNetwork(){
//        try {
//            mSocket = IO.socket("https://nodejs-jeongjiho.c9users.io:8080/");
//            mSocket.on(io.socket.client.Socket.EVENT_CONNECT, onConnect);
//            mSocket.on("smart", system2);
//            mSocket.connect();
//
//        } catch (URISyntaxException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    //값보낼때 사용
//    public static Emitter.Listener onConnect = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            System.out.println("Send listener");
//            // TODO Auto-generated method stub
//            String sendM="kyo1";
//            mSocket.emit("rint",sendM);
//            System.out.println("보낸값:"+sendM);
//        }
//    };//end emitter
//
//
//    //값받을 때 사용
//    public static Emitter.Listener system2 = new Emitter.Listener() {
//        @Override
//        public void call(Object... args) {
//            System.out.println(args[0]);
//        }//end call
//    };//end Listener



}
