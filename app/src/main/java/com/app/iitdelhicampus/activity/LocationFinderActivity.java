package com.app.iitdelhicampus.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.LocationsListAdapter;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.location.LocationTracker;
import com.app.iitdelhicampus.model.CreateEventModel;
import com.app.iitdelhicampus.model.FourSquareNearbyPlacesResponseModel;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class LocationFinderActivity extends BaseActivity
        implements OnMapReadyCallback, GoogleMap.OnMapClickListener,
        AdapterView.OnItemClickListener, TextWatcher, LocationTracker.LocationChangeListener, View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final String BASE_URL_FOURSQAURE = "https://api.foursquare.com/v2/venues/search";
    private double mCurrentLatitude;
    private double mCurrentLongitude;

    private String mLocality;
    private TextView txvShowLocation;
    private TextView txvCurrentLocation;
    private ListView listviewLocations;
    private boolean mIsViewHidden = true;
    private float density;
    private int end;
    private int start;
    private LocationsListAdapter adapter;
    private LocationTracker locationTracker;
    private boolean isMapActivated;
    private RelativeLayout rlMain;
    private RelativeLayout rlSearchItem;
    private RelativeLayout rtlSearch;
    private EditText etSearchCountry;
    private ImageView ivSearchIcon;
    private ImageView imgBackSearch;
    private Location location;
    //    private EditText etEventAddress;
    private String lattitude, longitude;
    String locationAddres = "";
    private Circle circle;

    private PlaceAutocompleteFragment autocompleteFragment;
    private String TAG = PlaceAutocompleteActivity.class.getSimpleName();
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private Intent intent;
    //    private Geocoder geocoder;
//    private List<Address> addresses;
    private String address;
    private ImageView ivLocationButton;
    private TextView tvDone;
    private String city_new;
    private String address_short_new;


    @Override
    public void onTextChanged(CharSequence chars, int arg1, int arg2, int arg3) {

    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        fetchNearByPlaces(editable.toString());
    }

    @Override
    protected void onCreate(Bundle args) {
        super.onCreate(args);
        setContentView(R.layout.activity_location_finder);
        density = getResources().getDisplayMetrics().density;
        etSearchCountry = (EditText) findViewById(R.id.etSearchCountry);
        listviewLocations = (ListView) findViewById(R.id.listviewLocations);
        txvShowLocation = (TextView) findViewById(R.id.txvShowLocation);
        txvCurrentLocation = (TextView) findViewById(R.id.txvCurrentLocation);
        rlMain = (RelativeLayout) findViewById(R.id.rlMain);
        rlSearchItem = (RelativeLayout) findViewById(R.id.rlSearchItem);
        rtlSearch = (RelativeLayout) findViewById(R.id.rlSearchBox);
        imgBackSearch = (ImageView) findViewById(R.id.imgBackSearch);
        ivSearchIcon = (ImageView) findViewById(R.id.ivSearchIcon);
        ivLocationButton = (ImageView) findViewById(R.id.ivLocationButton);
        tvDone = (TextView) findViewById(R.id.tvDone);
        tvDone.setOnClickListener(this);
        ivLocationButton.setOnClickListener(this);
//        etEventAddress = (EditText) findViewById(R.id.etEventAddress);
        etSearchCountry.setCursorVisible(true);
        findViewById(R.id.imgBack).setOnClickListener(this);
        txvShowLocation.setOnClickListener(this);
        txvCurrentLocation.setOnClickListener(this);
        rtlSearch.setOnClickListener(this);
        imgBackSearch.setOnClickListener(this);
        ivSearchIcon.setOnClickListener(this);
        requestAppPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0, 0, this);


//        showHideListView();
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build();
        autocompleteFragment.setFilter(typeFilter);

        try {
            intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(this);
//            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    private void showLocationButton() {
        View locationButton = ((View) findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        // and next place it, on bottom right (as Google Maps app)
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                locationButton.getLayoutParams();
        // position on right bottom
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        layoutParams.setMargins(0, 0, 30, 30);

    }

//    private String getAddressFromLatLng(double lat, double lon) {
//
//        try {
//            addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//
//            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//            String city = addresses.get(0).getLocality();
//            String state = addresses.get(0).getAdminArea();
//            String country = addresses.get(0).getCountryName();
//            String postalCode = addresses.get(0).getPostalCode();
//            String knownName = addresses.get(0).getFeatureName();
//            address
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return locationAddres;
//    }

    private void searchLocation() {
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("KEY", "Event Location");
        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        ivSearchIcon.setClickable(false);

//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                // TODO: Get info about the selected place.
//                Log.i(TAG, "Place: " + place.getName());
//            }
//
//            @Override
//            public void onError(Status status) {
//                // TODO: Handle the error.
//                Log.i(TAG, "An error occurred: " + status);
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //autocompleteFragment.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                hideSoftKeyBoard();
                ivSearchIcon.setClickable(true);
                Place place = PlaceAutocomplete.getPlace(this, data);
//                Intent intent = new Intent();
//                intent.putExtra(Constants.Params.LATITUDE_LOCATION, place.getLatLng().latitude);
//                intent.putExtra(Constants.Params.LONGITUDE_LOCATION, place.getLatLng().longitude);
//                intent.putExtra(Constants.Params.NAME_LOCATION, place.getName().toString());
                lattitude = String.valueOf(place.getLatLng().latitude);
                longitude = String.valueOf(place.getLatLng().longitude);
                address = place.getName().toString();
                txvCurrentLocation.setText(address);
                if (map != null) {
                    map.clear();

                    BitmapDescriptor marker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                    map.addMarker(new MarkerOptions().position(place.getLatLng()).icon(marker));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(place.getLatLng())
                            .zoom(14)
                            .bearing(0)
                            .tilt(0)
                            .build();
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                }
//                setResult(RESULT_OK, intent);
//                Utility.removeActivity(2);
//                finish();

//                zoomCamera(place.getName().toString(),place.getLatLng().latitude, place.getLatLng().longitude);
//                Log.i(TAG, "Place:" + place.toString());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i(TAG, status.getStatusMessage());
            } else if (requestCode == RESULT_CANCELED) {

            }
        }
    }


    private void zoomCamera(final String placeAddress, double lat, double lon) {
        LatLng latLng = new LatLng(lat, lon);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
//        BitmapDescriptor icon = DataHelper.getEventIcon("default");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
        map.addMarker(markerOptions);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(14)
                .bearing(0)
                .tilt(0)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

//        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                Intent intent = new Intent();
//                intent.putExtra(Constants.Params.LATITUDE_LOCATION, mCurrentLatitude);
//                intent.putExtra(Constants.Params.LONGITUDE_LOCATION, mCurrentLongitude);
//                intent.putExtra(Constants.Params.NAME_LOCATION, placeAddress);
//                setResult(RESULT_OK, intent);
//                finish();
//                return false;
//            }
//        });
//        map.setPadding(0, 500, 0, 0);
    }


    @Override
    public void onStart() {
        super.onStart();
        locationTracker = new LocationTracker(this);
        adapter = new LocationsListAdapter(this);
        listviewLocations.setAdapter(adapter);
        listviewLocations.setOnItemClickListener(this);
        if (locationTracker.mGoogleApiClient != null) {
            locationTracker.mGoogleApiClient.connect();

        }

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.imgBack:
                finish();
                break;
//            case R.id.txvCurrentLocation:
            case R.id.tvDone:
                if (txvCurrentLocation.getText().toString().length() != 0) {
                    CreateEventModel createEventModel = CreateEventModel.getInstance(false);
                    createEventModel.setLatitude(lattitude);
                    createEventModel.setLongitude(longitude);
                    createEventModel.setLocation(address);
                    intent.putExtra(Constants.Params.LATITUDE_LOCATION, lattitude);
                    intent.putExtra("short", address_short_new);
                    intent.putExtra("city", city_new);
                    intent.putExtra(Constants.Params.LONGITUDE_LOCATION, longitude);
                    intent.putExtra(Constants.Params.NAME_LOCATION, address);
                    setResult(RESULT_OK, intent);
                }
                finish();
                break;
            case R.id.txvShowLocation:
                showHideListView();
                break;

//            case R.id.txvCurrentLocation:
            case R.id.ivSearchIcon:
                searchLocation();
                break;
            case R.id.imgBackSearch:
                rlSearchItem.setVisibility(View.GONE);
                rlMain.setVisibility(View.VISIBLE);
                break;
            case R.id.ivLocationButton:
                if (location == null)
                    return;
                getCurrentLocation(location.getLatitude(), location.getLongitude());
                onMapClick(new LatLng(location.getLatitude(), location.getLongitude()));
                break;

            default:
                break;
        }
    }

    private void showHideListView() {
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) listviewLocations.getLayoutParams();

        if (mIsViewHidden) {
            txvShowLocation.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.hide_places, 0, 0, 0);
            txvShowLocation.setText(getString(R.string.hide_locations));
            end = 250;
            start = 0;
        } else {
            txvShowLocation.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.show_places, 0, 0, 0);
            txvShowLocation.setText(getString(R.string.show_locations));
            end = 0;
            start = 250;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (start != end) {

                    if (mIsViewHidden) {
                        start = start + 5;
                    } else {
                        start = start - 5;
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            params.height = (int) (start * density);
                            listviewLocations.setLayoutParams(params);
                        }
                    });

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (start == end) {
                    mIsViewHidden = !mIsViewHidden;
                }
            }
        }).start();
    }

    private void sendLocation(double lat, double lng, String locality) {
        if (lat != 0 && lng != 0 && locality != null) {
            Intent intent = new Intent();
            intent.putExtra("EXTRA_CHAT_LOCATION", lat + "," + lng + "," + locality);
            setResult(RESULT_OK, intent);
        } else {
            setResult(RESULT_CANCELED);
        }

        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ivSearchIcon.setClickable(true);
        etSearchCountry.addTextChangedListener(this);

        Location location = locationTracker.getLocation();
        if (location != null) onLocationChanged(location);

        locationTracker.setLocationChangeListener(this);

        if (!ConnectivityUtils.isLocationEnabled(this)) {
            locationTracker.showSettingsAlert();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        etSearchCountry.removeTextChangedListener(this);
        locationTracker.setLocationChangeListener(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationTracker.stopLocationUpdates();
        if (locationTracker.mGoogleApiClient != null) {
            locationTracker.mGoogleApiClient.connect();
        }
        locationTracker.mGoogleApiClient.disconnect();
    }

    @Override
    public void onMapReady(GoogleMap map1) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.map = map1;
        map.setOnMapClickListener(this);
        map.setMyLocationEnabled(true);
        LatLng latLng = new LatLng(mCurrentLatitude, mCurrentLongitude);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));

        BitmapDescriptor marker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE) /*BitmapDescriptorFactory.fromResource(R.mipmap.location_pointer_icon)*/;
        map.addMarker(new MarkerOptions().position(latLng).icon(marker));
        map.setMyLocationEnabled(false);
        circle = map.addCircle(new CircleOptions()
                .center(latLng)
                .radius(1000)
                .strokeWidth(5)
                .strokeColor(Color.BLUE)
                .fillColor(Color.parseColor("#00000000"))
                .clickable(true));

        map.setOnCircleClickListener(new GoogleMap.OnCircleClickListener() {

            @Override
            public void onCircleClick(Circle circle) {
                // Flip the r, g and b components of the circle's
                // stroke color.
                int strokeColor = circle.getStrokeColor() ^ 0x00ffffff;
                circle.setStrokeColor(strokeColor);
            }
        });

//        showLocationButton();
    }

    public String getLocality(LatLng location, Context context) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);
        } catch (IOException e) {
            Log.e("TAG", "getLocality", e);
        }

        if (addresses == null || addresses.size() == 0) return "";

        StringBuilder builder = new StringBuilder();
        Address address = addresses.get(0);

        int addressLineCount = address.getMaxAddressLineIndex();

        for (int i = 0; i < addressLineCount; i++) {
            builder.append(address.getAddressLine(i));
            if (i != (addressLineCount - 1)) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    private void fetchNearByPlaces(String queryString) {
        if (!ConnectivityUtils.isNetworkEnabled(this)) {
            Toast.makeText(this, "device_is_out_of_network_coverage", Toast.LENGTH_SHORT).show();
//            ToastUtils.showToast(this, getString(R.string.device_is_out_of_network_coverage));
            return;
        }

        new GetNearbyPlacesAsyncTask(queryString).execute();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapClick(LatLng latLng) {
//        if (map != null)
//            map.clear();
//        BitmapDescriptor marker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
//        map.addMarker(new MarkerOptions().position(latLng).icon(marker));
//        CameraPosition cameraPosition = new CameraPosition.Builder()
//                .target(latLng)
//                .zoom(14)
//                .bearing(0)
//                .tilt(0)
//                .build();
//        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//
//        getCurrentLocation(latLng.latitude, latLng.longitude);
//        txvCurrentLocation.setText(address);
    }

    class GetNearbyPlacesAsyncTask extends AsyncTask<Void, Void, String> {

        private final String query;

        protected GetNearbyPlacesAsyncTask(String queryString) {
            StringBuilder builder = new StringBuilder();
            builder.append(BASE_URL_FOURSQAURE)
                    .append("?ll=").append(mCurrentLatitude + "," + mCurrentLongitude)
                    .append("&client_id=").append("3RVCREYLWDSGLMTBHSKQRPZCCQU23VL2XVVAPDLM3CIIIEGN")
                    .append("&client_secret=").append("5H3BMGOM2ZDRJWZKL0ZJXMK522AZI4E4KMUKUEA1K3KRCVCB")
                    .append("&v=").append(millisNowToFormatted());

            if (!TextUtils.isEmpty(queryString)) {
                builder.append("&query=").append(queryString);
            }

            query = builder.toString();
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                URL url = new URL(query);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                StringBuilder builder = new StringBuilder();
                if (connection.getResponseCode() == 200) {
                    BufferedReader bufr = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String lineRead;

                    while ((lineRead = bufr.readLine()) != null) {
                        builder.append(lineRead);
                    }
                }

                connection.disconnect();
                return builder.toString();
            } catch (Exception e) {
                Log.e("TAG", "doInBackground: ", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            if (isFinishing() || json == null) return;
            FourSquareNearbyPlacesResponseModel model = new Gson().fromJson(json, FourSquareNearbyPlacesResponseModel.class);
            if (model != null && model.hasVenues()) {
                adapter.setListData(model.getVenues());
                adapter.notifyDataSetChanged();
            }
        }
    }

    private String millisNowToFormatted() {
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return sd.format(calendar.getTime());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LocationsListAdapter adapter = (LocationsListAdapter) parent.getAdapter();
        FourSquareNearbyPlacesResponseModel.Venues venues = adapter.getItem(position);
        Intent intent = new Intent();
        intent.putExtra(Constants.Params.LATITUDE_LOCATION, venues.getLocation().getLat());
        intent.putExtra(Constants.Params.LONGITUDE_LOCATION, venues.getLocation().getLng());
        intent.putExtra(Constants.Params.NAME_LOCATION, venues.getName());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        if (location.getLatitude() == mCurrentLatitude
                && location.getLongitude() == mCurrentLongitude) return;

        mCurrentLatitude = location.getLatitude();
        mCurrentLongitude = location.getLongitude();

        mLocality = getLocality(new LatLng(mCurrentLatitude, mCurrentLatitude), this);

        lattitude = String.valueOf(mCurrentLatitude);
        longitude = String.valueOf(mCurrentLongitude);

        if (!isMapActivated) {
            SupportMapFragment mapFragment = new SupportMapFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.map_frame, mapFragment).commit();
            mapFragment.getMapAsync(this);
            isMapActivated = true;
        }
        fetchNearByPlaces(null);
        locationTracker.disconnectClient();

        getCurrentLocation(location.getLatitude(), location.getLongitude());

    }


    //    sudo dmidecode -t system | grep Serial
    public void getCurrentLocation(double lat, double lon) {

        try {

            lattitude = String.valueOf(lat);
            longitude = String.valueOf(lon);

            Geocoder geocoder;
            List<Address> addresses = null;

            geocoder = new Geocoder(this, Locale.getDefault());
            addresses = geocoder.getFromLocation(lat, lon, 1);
            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            if (!addresses.isEmpty()) {
                String address_short = addresses.get(0).getAddressLine(0);

                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

                address = address_short + " " + city;
                address_short_new = knownName + " " + city;
                city_new = state + " " + country + " " + postalCode;
                txvCurrentLocation.setText(address);
                Log.e("address", knownName + " " + city + " " + state + " " + country + " " + postalCode);
            }
//            double latitude = addresses.get(0).getLatitude();
//            double longitude = addresses.get(0).getLongitude();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    protected void search(List<Address> addresses) {
//
//        Address address = (Address) addresses.get(0);
//       double home_long = address.getLongitude();
//        double home_lat = address.getLatitude();
//        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//
//      String  addressText = String.format(
//                "%s, %s",
//                address.getMaxAddressLineIndex() > 0 ? address
//                        .getAddressLine(0) : "", address.getCountryName());
//
//        MarkerOptions  markerOptions = new MarkerOptions();
//
//        markerOptions.position(latLng);
//        markerOptions.title(addressText);
//
//        map.clear();
//        map.addMarker(markerOptions);
//        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        map.animateCamera(CameraUpdateFactory.zoomTo(15));
////        locationTv.setText("Latitude:" + address.getLatitude() + ", Longitude:"
////                + address.getLongitude());
//
//    }

}
