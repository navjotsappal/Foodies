package com.realraghavgupta.foodies;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
// https://developers.google.com/maps/documentation/android-api/current-place-tutorial


public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private CameraPosition campos;

    // The entry points to the Places API.
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient placeDetection;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProvider;


    private final LatLng mDefaultLocation = new LatLng(-63.582687, 44.651070);
    private static final int DZOOM = 10;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean permissionGranted;

    String[] stores;

    private Location lastLocation;

    private static final String KEY_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";


    private Marker mwalmart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //stores array
        Resources rsrcs = getResources();
        stores = rsrcs.getStringArray(R.array.stores);



        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            campos = savedInstanceState.getParcelable(KEY_POSITION);
        }

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        placeDetection = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);

        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastLocation);
            super.onSaveInstanceState(outState);
        }
    }



    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        for (String store : stores
                ) {
            String title = store.split(",")[0];
            double lat = Double.parseDouble(store.split(",")[1]);
            double log = Double.parseDouble(store.split(",")[2]);

            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, log))
                    .title(title));


            mMap.moveCamera(CameraUpdateFactory
                    .newLatLngZoom(new LatLng(44.642342,-63.575439), DZOOM));
            mMap.setOnMarkerClickListener(this);

            // Prompt the user for permission.
            getLocationPermission();

            // Turn on the My Location layer and the related control on the map.
            updateLocation();

            // Get the current location of the device and set the position of the map.
            getDeviceLocation();
        }
    }


    private void getDeviceLocation() {

        try {
            if (permissionGranted) {
                Task<Location> locationResult = fusedLocationProvider.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {

                            lastLocation = task.getResult();
                            if (lastLocation != null)
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastLocation.getLatitude(),
                                                lastLocation.getLongitude()), DZOOM));
                        } else {
                            Log.d(TAG, " No Current location .");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }



    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            permissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        permissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionGranted = true;
                }
            }
        }
        updateLocation();
    }


    private void updateLocation() {
        if (mMap == null) {
            return;
        }
        try {
            if (permissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public boolean onMarkerClick(final Marker mkr) {

        // Retrieve the data from the marker.
        Integer Count = (Integer) mkr.getTag();

        // Check if a click count was set, then display the click count.
        if (Count != null) {
            Count = Count + 1;
            mkr.setTag(Count);
            Toast.makeText(this,
                    mkr.getTitle() +
                            " is clicked " + Count + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}
