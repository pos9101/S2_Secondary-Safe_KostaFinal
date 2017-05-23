package s2.navigation.com;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import s2.navigation.com.socket.SocketNetwork;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Marker nowLocation;
    LocationManager mLocMgr;
    private final int REQUEST_FINE_ACCESS = 123;
    private final int REQUEST_COARSE_LOCATION = 234;
    Location loc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mLocMgr = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_FINE_ACCESS);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_COARSE_LOCATION);
        }
        loc = mLocMgr.getLastKnownLocation(mLocMgr.GPS_PROVIDER);


    }

    @Override
    protected void onResume() {
        super.onResume();
        String locProv = LocationManager.GPS_PROVIDER;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_FINE_ACCESS);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},REQUEST_COARSE_LOCATION);
        }
            mLocMgr.requestLocationUpdates(locProv, (long) 3000, 3.f, mLocListener);

    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocMgr.removeUpdates(mLocListener);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        loc.getLongitude();
        LatLng pos = new LatLng(loc.getLatitude(), loc.getLongitude());
        //LatLng pos = new LatLng(37.4980, 127.027);

        mMap.addMarker(new MarkerOptions().position(pos).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos,17));

        MarkerOptions myCar = new MarkerOptions();
        myCar.position(pos);
        myCar.icon(BitmapDescriptorFactory.fromResource(R.drawable.carmarker));
        nowLocation = mMap.addMarker(myCar);
    }

    LocationListener mLocListener= new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            LatLng position = new LatLng(location.getLatitude(),location.getLongitude());//위치값을 가져온다
            nowLocation.setPosition(position);
            mMap.moveCamera( CameraUpdateFactory.newLatLng(position) );
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    };


}
