package com.example.maps.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maps.interfaces.ApiInterface;
import com.example.maps.retrofit.ApiClient;
import com.example.maps.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FusedLocationProviderClient mFusedLocationProvider;
    private UiSettings mUiSettings;
    private GoogleMap mMap;
    private LatLng location1 , location2;
    private ApiClient mApiClient = new ApiClient();
    private Retrofit mRetrofit;
    private MarkerOptions marker1,marker2;
    private String polyLineValue;
    private TextView mTvDistance, mTvTime;
    private final int mMapPadding = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTvDistance = findViewById(R.id.tvHomeDistance);
        mTvTime = findViewById(R.id.tvHomeTime);
        mTvTime.setEnabled(false);
        mTvDistance.setEnabled(false);
        mFusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);
         marker2 = new MarkerOptions();
         marker1 = new MarkerOptions();

        if (ContextCompat.checkSelfPermission( this,android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        {
            showPermissionDialog();
        }
        else {
            showLocationOnMap();
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;
        mUiSettings = googleMap.getUiSettings();
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setZoomControlsEnabled(true);
        googleMap.setMyLocationEnabled(true);

        fetchLocation();

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                fetchLocation();
                location2 = latLng;
                marker2.position(latLng);
                googleMap.addMarker(marker2);
                getPath();
            }
        });
    }

    private void getPath(){

        mRetrofit = mApiClient.getRetrofit();
        Map <String,String > sendValues = new HashMap<>();
        sendValues.put("origin",location1.latitude + "," + location1.longitude);
        sendValues.put("destination",location2.latitude + "," + location2.longitude);
        sendValues.put("key","AIzaSyCGzRSRc83_FAuXRBwJlHHYnO6mSKLaeEs");
        ApiInterface apiInterface = mRetrofit.create(ApiInterface.class);
        Call<Map> call = apiInterface.getMap(sendValues);
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(@NonNull Call<Map> call,@NonNull Response<Map> response) {
                generatePolyLine(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Map> call,@NonNull Throwable t) {
                Log.i("response","fail");
            }
        });

    }

    private void generatePolyLine(Map<String,String> response){
        JSONObject mainObject = new JSONObject(response);
        try{
            String status = mainObject.getString("status");
            if(status.equals("OK")){
                JSONArray routeArray = mainObject.getJSONArray("routes");
                JSONObject object = routeArray.getJSONObject(0);
                JSONObject overViewPolyline = object.getJSONObject("overview_polyline");
                polyLineValue = overViewPolyline.getString("points");
                Log.i("routeValue",polyLineValue + "");
                List<LatLng> decodePolyList = PolyUtil.decode(polyLineValue);

                JSONArray legsArray = object.getJSONArray("legs");
                JSONObject legsObject = legsArray.getJSONObject(0);
                JSONObject distanceObject = legsObject.getJSONObject("distance");
                String totalDistance = distanceObject.getString("text");

                JSONObject durationObject = legsObject.getJSONObject("duration");
                String totalTime = durationObject.getString("text");

                mTvDistance.setText("Distance : " + totalDistance);
                mTvTime.setText("Time : " + totalTime);

//                LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                builder.include(new LatLng(location1.latitude,location1.longitude));
//                builder.include(new LatLng(location2.latitude,location2.longitude));
//                LatLngBounds bounds = builder.build();
//                final CameraUpdate cameraZoom = CameraUpdateFactory.newLatLngBounds(bounds, mMapPadding);
//                mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
//                    @Override
//                    public void onMapLoaded() {
//                        mMap.moveCamera(cameraZoom);
//                    }
//                });

                generatePolyList(decodePolyList);
            }
            Log.i("routeStatus",status + "");
        }catch(JSONException e){
            Log.i("routeArray","unable to fetch route");
        }

    }

    private void generatePolyList(List<LatLng> polylineList){
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        for(int i=0;i<polylineList.size();i++){
            options.add(polylineList.get(i));
        }
        mMap.addPolyline(options);
    }

    private void fetchLocation(){
        mFusedLocationProvider.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            location1 = myLocation;
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
                            marker1.position(myLocation);
                            marker1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            mMap.addMarker(marker1);

                        }
                        else{
                            Toast.makeText(MapActivity.this,getString(R.string.location_null),Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MapActivity.this,getString(R.string.location_fail),Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {

        if(requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showLocationOnMap();
            } else {
                showPermissionDialog();
            }
        }
    }

    private void showPermissionDialog(){
        ActivityCompat.requestPermissions(MapActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
    }

    private void showLocationOnMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
}
