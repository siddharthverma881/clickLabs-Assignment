package com.example.maps.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maps.interfaces.ApiInterface;
import com.example.maps.interfaces.ApiKeys;
import com.example.maps.retrofit.ApiClient;
import com.example.maps.R;
import com.example.maps.util.Constants;
import com.example.maps.util.Utils;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback , ApiKeys {

    private FusedLocationProviderClient mFusedLocationProvider;
    private UiSettings mUiSettings;
    private GoogleMap mMap;
    private LatLng mLocation1 , mLocation2;
    private Retrofit mRetrofit;
    private MarkerOptions mMarkerOptions1,mMarkerOptions2;
    private TextView mTvDistance, mTvTime;
    private Marker mLiveMarker,mMarker;
    private CameraUpdate mCameraZoom;
    private boolean doubleBackToExitPressedOnce = false;
    private ConnectivityManager mConnManager;
    private AlertDialog mDialog;
    private Utils mUtils = new Utils();
    private Constants mConstants = new Constants();
    private ApiClient mApiClient = new ApiClient();
    private HashMap<String,Marker> markerMap = new HashMap<>();
    private ArrayList<Polyline> mPolyLineList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTvDistance = findViewById(R.id.tvHomeDistance);
        mTvTime = findViewById(R.id.tvHomeTime);
        mFusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);
        mMarkerOptions2 = new MarkerOptions();
        mMarkerOptions1 = new MarkerOptions();

        if (ContextCompat.checkSelfPermission( this,android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        {
            showPermissionDialog();
        }
        else {
            checkInternet();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if (ContextCompat.checkSelfPermission( this,android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED )
        {
            showPermissionDialog();
        }
        else {
            checkInternet();
        }
    }

    @Override
    public void onMapReady( GoogleMap googleMap) {

        mMap = googleMap;
        mUiSettings = googleMap.getUiSettings();
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setZoomControlsEnabled(true);
        googleMap.setMyLocationEnabled(true);

        fetchLocation();

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                int hashMapSize = markerMap.size();
                if(hashMapSize == mConstants.MAX_NO_OF_MARKERS){
                    for(Map.Entry<String,Marker> entryMap : markerMap.entrySet()){
                        Marker marker = entryMap.getValue();
                        marker.remove();
                    }
                    for(Polyline polyline : mPolyLineList){
                        polyline.remove();
                    }
                    markerMap.clear();
                }
                mLocation2 = latLng;
                mMarkerOptions2.position(latLng);
                if(hashMapSize == mConstants.FIRST_MARKER){
                    mMarkerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                }
                else if(hashMapSize == mConstants.SECOND_MARKER ){
                    mMarkerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                }
                else if(hashMapSize == mConstants.THIRD_MARKER){
                    mMarkerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
                }
                mMarker= mMap.addMarker(mMarkerOptions2);
                markerMap.put(mMarker.getId(),mMarker);
                mMarkerOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                getPath();
            }
        });
    }

    // setting base url and getting the response
    private void getPath(){
        mRetrofit = mApiClient.getRetrofit();
        Map <String,String > sendValues = new HashMap<>();
        sendValues.put(mConstants.URL_KEY_ORIGIN,mLocation1.latitude + "," + mLocation1.longitude);
        sendValues.put(mConstants.URL_KEY_DESTINATION,mLocation2.latitude + "," + mLocation2.longitude);
        sendValues.put(mConstants.URL_KEY,mConstants.MAP_KEY);
        ApiInterface apiInterface = mRetrofit.create(ApiInterface.class);
        Call<Map> call = apiInterface.getMap(sendValues);
        call.enqueue(new Callback<Map>() {
            @Override
            public void onResponse(@NonNull Call<Map> call,@NonNull Response<Map> response) {
                generatePolyLine(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<Map> call,@NonNull Throwable t) {
                mUtils.showToast(MapActivity.this,t.getMessage());
            }
        });
    }

    /**
     * @param response is the map that is returned from the api
     */
    private void generatePolyLine(Map<String,String> response){
        JSONObject mainObject = new JSONObject(response);
        try{
            String status = mainObject.getString(STATUS);
            if(status.equals(mConstants.RESULT_OK)){
                JSONArray routeArray = mainObject.getJSONArray(ROUTES);
                JSONObject object = routeArray.getJSONObject(0);
                JSONObject overViewPolyline = object.getJSONObject(OVERVIEW_POLYLINE);
                String polyLineValue = overViewPolyline.getString(POINTS);
                List<LatLng> decodePolyList = PolyUtil.decode(polyLineValue);

                JSONArray legsArray = object.getJSONArray(LEGS);
                JSONObject legsObject = legsArray.getJSONObject(0);
                JSONObject distanceObject = legsObject.getJSONObject(DISTANCE);
                String totalDistance = distanceObject.getString(TEXT);

                JSONObject durationObject = legsObject.getJSONObject(DURATION);
                String totalTime = durationObject.getString(TEXT);

                mTvDistance.setText(getString(R.string.tv_distance) + totalDistance);
                mTvTime.setText(getString(R.string.tv_time) + totalTime);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(new LatLng(mLocation1.latitude,mLocation1.longitude));
                builder.include(new LatLng(mLocation2.latitude,mLocation2.longitude));
                LatLngBounds bounds = builder.build();
                mCameraZoom = CameraUpdateFactory.newLatLngBounds(bounds, mConstants.MAP_PADDING);
                mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        mMap.moveCamera(mCameraZoom);
                        mMap.animateCamera(mCameraZoom,mConstants.MAP_ANIMATE,null);
                    }
                });

                generatePolyList(decodePolyList);
            }
        }catch(JSONException e){
            mUtils.showToast(MapActivity.this,getString(R.string.route_fail));
        }

    }

    /**
     * @param polylineList is the list containing all the latlng points in the route
     */
    private void generatePolyList(List<LatLng> polylineList){
        PolylineOptions options = new PolylineOptions().width(mConstants.POLYLINE_WIDTH).color(Color.BLUE).geodesic(true);
        for(int i=0;i<polylineList.size();i++){
            options.add(polylineList.get(i));
        }
        Polyline polyline = mMap.addPolyline(options);
        mPolyLineList.add(polyline);
    }

    //getting the current location of the user
    private void fetchLocation(){
        mFusedLocationProvider.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            mLocation1 = myLocation;
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, mConstants.MAP_CAMERA_ZOOM));
                            mMarkerOptions1.position(myLocation);
                            mMarkerOptions1.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            mLiveMarker = mMap.addMarker(mMarkerOptions1);
                        }
                        else{
                            mUtils.showToast(MapActivity.this,getString(R.string.location_null));
                        }
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        mUtils.showToast(MapActivity.this,getString(R.string.location_fail));
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {

        if(requestCode == mConstants.PERMISSION_LOCATION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkInternet();
            } else {
                showPermissionDialog();
            }
        }
    }

    //requesting location permission from user
    private void showPermissionDialog(){
        ActivityCompat.requestPermissions(MapActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION}, mConstants.PERMISSION_LOCATION);
    }

    //plotting the map
    private void showLocationOnMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    //if the user taps back button twice within 2 secs, then only he can exit the app
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        mUtils.showToast(MapActivity.this,getString(R.string.toast_exit_app));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, mConstants.BACK_PRESS_DELAY);
    }

    //for showing dialog if user's internet if off
    public void checkInternet(){
        if(!isOnline(MapActivity.this)){
            AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this,R.style.AlertDialog);
            builder.setTitle(getString(R.string.no_internet_title));
            builder.setMessage(getString(R.string.no_internet_message));
            builder.setPositiveButton(getString(R.string.no_internet_btn),new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    checkInternet();
                }
            });
            mDialog = builder.create();
            mDialog.setCanceledOnTouchOutside(false);

            mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg) {
                    mDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                }
            });
            mDialog.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);
            mDialog.show();
        }
        else{
            showLocationOnMap();
        }
    }

    //for checking if internet is on or not
    public boolean isOnline(Context context) {
        boolean result = false;
        if (context != null) {
            mConnManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnManager != null) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    result = mConnManager.isDefaultNetworkActive();
                }
            }
            else{
                mUtils.showToast(MapActivity.this,getString(R.string.toast_unable_connection));
            }
        }
        return result;
    }
}