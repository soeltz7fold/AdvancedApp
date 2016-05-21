package x7a.droid.advancedapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by DroiD on 12/05/2016.
 */
public class MapsPage extends FragmentActivity implements LocationListener{
    private GoogleMap gMap; // Might be null if Google Play services APK is not available.
    private LocationManager locationManager;
    private TextView tvLatitude, tvLongitude, tvAltitude;
    private String provider;
    Geocoder geocoder;
    Timer timer;

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_maps, container, false);
//
//
//        return view;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);

        tvLongitude = (TextView) findViewById(R.id.tvLongitude);
        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvAltitude = (TextView) findViewById(R.id.tvAltitude);

        setUpMapIfNeeded();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            Log.d("MyMap", "provider : " + provider + " selected");
            onLocationChanged(location);
            Toast.makeText(this, "location null", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("MyMap", "location ==null");
        }
        showCurrentLocation();
    }

    protected void showCurrentLocation() {
        geocoder = new Geocoder(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                1000,
                100,
                this
        );
        timer = new Timer();
        timer.schedule(new GetLastLocation(), 20000);

    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(provider, 100, 1, this);
        setUpMapIfNeeded();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }
    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #gMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (gMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            gMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (gMap != null) {
                setUpMap();
            }
        }
    }
    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #gMap} is not null.
     */
    private void setUpMap() {
//        setMarker(new LatLng(0, 0), "marker0");
//        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
//        mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Marker"));
    }


    @Override
    public void onLocationChanged(Location location) {
        tvLongitude.setText(location.getLongitude() + "");
        tvLatitude.setText(location.getLatitude() + "");
        tvAltitude.setText(location.getAltitude() + "");
//        mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("Marker"));
        setMarker(new LatLng(location.getLatitude(), location.getLongitude()), "marker");
    }
    public void setMarker(LatLng latlng, String title) {
        if (gMap != null) {
            Marker hamburg = gMap.addMarker(new MarkerOptions().position(latlng)
                    .title("Hello Maps"));
            gMap.setMyLocationEnabled(true);
            gMap.getUiSettings().setMyLocationButtonEnabled(true);
            gMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
            gMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }

    @Override
    public void onStatusChanged(String info, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String info) {

    }

    @Override
    public void onProviderDisabled(String info) {

    }

    class GetLastLocation extends TimerTask {

        @Override
        public void run() {
            timer.cancel();
            locationManager.removeUpdates(MapsPage.this);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            System.out.println("loc.." + location);
            if (location != null) {
                String message = String.format("Location \n Longitude: %1$s \n Latitude: %2$s",
                        location.getLongitude(), location.getLatitude());
//                Toast.makeText(MapsActivity.this, message,
//                        Toast.LENGTH_LONG).show();
                System.out.println("address .." + message);
                //acTextView.setText(message);
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 10); //<10>
                    for (Address address : addresses) {
                        System.out.println("my location .." + address.getAddressLine(0));
//                        acTextView.setText(address.getAddressLine(0));
                    }

                } catch (IOException e) {
                    Log.e("LocateMe", "Could not get Geocoder data", e);
                }
            } else {
                AlertDialog.Builder alertbox1 = new AlertDialog.Builder(MapsPage.this);
                alertbox1.setMessage("No Network Signal Received... Please Move On!");
                alertbox1.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                alertbox1.show();
            }
        }
    }
}
