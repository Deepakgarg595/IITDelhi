//// Copyright 2020 Google LLC
////
//// Licensed under the Apache License, Version 2.0 (the "License");
//// you may not use this file except in compliance with the License.
//// You may obtain a copy of the License at
////
////      http://www.apache.org/licenses/LICENSE-2.0
////
//// Unless required by applicable law or agreed to in writing, software
//// distributed under the License is distributed on an "AS IS" BASIS,
//// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//// See the License for the specific language governing permissions and
//// limitations under the License.
//
//package com.app.orionsecure.activity;
//
//import android.Manifest;
//import android.annotation.SuppressLint;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import com.app.orionsecure.BuildConfig;
//import com.app.orionsecure.R;
//import com.app.orionsecure.utility.ToastUtils;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.libraries.places.api.Places;
//import com.google.android.libraries.places.api.model.Place;
//import com.google.android.libraries.places.api.model.PlaceLikelihood;
//import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
//import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
//import com.google.android.libraries.places.api.net.PlacesClient;
//import com.google.android.material.snackbar.Snackbar;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * An activity that displays a map showing the place at the device's current location.
// */
//public class MapsActivityCurrentPlace extends AppCompatActivity
//        implements OnMapReadyCallback {
//
//    private static final String TAG = MapsActivityCurrentPlace.class.getSimpleName();
//    private static final int DEFAULT_ZOOM = 15;
//    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
//    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
//    // Keys for storing activity state.
//    // [START maps_current_place_state_keys]
//    private static final String KEY_CAMERA_POSITION = "camera_position";
//    private static final String KEY_LOCATION = "location";
//    // Used for selecting the current place.
//    private static final int M_MAX_ENTRIES = 5;
//    // A default location (Sydney, Australia) and default zoom to use when location permission is
//    // not granted.
//    private final LatLng defaultLocation = new LatLng(28.628454, 77.376945);
//    protected Location mLastLocation;
//    private GoogleMap map;
//    private CameraPosition cameraPosition;
//    // The entry point to the Places API.
//    private PlacesClient placesClient;
//    // The entry point to the Fused Location Provider.
//    private FusedLocationProviderClient fusedLocationProviderClient;
//    // [END maps_current_place_state_keys]
//    private boolean locationPermissionGranted;
//    // The geographical location where the device is currently located. That is, the last-known
//    // location retrieved by the Fused Location Provider.
//    private Location lastKnownLocation;
//    private String[] likelyPlaceNames;
//    private String[] likelyPlaceAddresses;
//    private List[] likelyPlaceAttributions;
//    private LatLng[] likelyPlaceLatLngs;
//    private FusedLocationProviderClient mFusedLocationClient;
//    private View tvSnack;
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        if (!checkPermissions()) {
//            requestPermissions();
//        } else {
//            getLastLocation();
//        }
//    }
//
//    @SuppressWarnings("MissingPermission")
//    private void getLastLocation() {
//        mFusedLocationClient.getLastLocation()
//                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        if (task.isSuccessful() && task.getResult() != null) {
//                            mLastLocation = task.getResult();
//                            ToastUtils.showToast(MapsActivityCurrentPlace.this, "Lat: " + mLastLocation.getLatitude() + "Lon: " + mLastLocation.getLongitude());
//                        } else {
//                            Log.w(TAG, "getLastLocation:exception", task.getException());
//                        }
//                    }
//                });
//    }
//
//
//    private boolean checkPermissions() {
//        int permissionState = ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION);
//        return permissionState == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void startLocationPermissionRequest() {
//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
//                REQUEST_PERMISSIONS_REQUEST_CODE);
//    }
//
//    private void requestPermissions() {
//        boolean shouldProvideRationale =
//                ActivityCompat.shouldShowRequestPermissionRationale(this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION);
//
//        // Provide an additional rationale to the user. This would happen if the user denied the
//        // request previously, but didn't check the "Don't ask again" checkbox.
//        if (shouldProvideRationale) {
//            Log.i(TAG, "Displaying permission rationale to provide additional context.");
//
//            showSnackbar(R.string.permissions_action_not_performed_missing_permissions, android.R.string.ok,
//                    new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            // Request permission
//                            startLocationPermissionRequest();
//                        }
//                    });
//
//        } else {
//            Log.i(TAG, "Requesting permission");
//            // Request permission. It's possible this can be auto answered if device policy
//            // sets the permission in a given state or the user denied the permission
//            // previously and checked "Never ask again".
//            startLocationPermissionRequest();
//        }
//    }
//
//    /**
//     * Callback received when a permissions request has been completed.
//     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        Log.i(TAG, "onRequestPermissionResult");
//        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
//            if (grantResults.length <= 0) {
//                // If user interaction was interrupted, the permission request is cancelled and you
//                // receive empty arrays.
//                Log.i(TAG, "User interaction was cancelled.");
//            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted.
//                getLastLocation();
//            } else {
//                // Permission denied.
//
//                // Notify the user via a SnackBar that they have rejected a core permission for the
//                // app, which makes the Activity useless. In a real app, core permissions would
//                // typically be best requested during a welcome-screen flow.
//
//                // Additionally, it is important to remember that a permission might have been
//                // rejected without asking the user for permission (device policy or "Never ask
//                // again" prompts). Therefore, a user interface affordance is typically implemented
//                // when permissions are denied. Otherwise, your app could appear unresponsive to
//                // touches or interactions which have required permissions.
//                showSnackbar(R.id.tvSnack, R.string.settings,
//                        new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                // Build intent that displays the App settings screen.
//                                Intent intent = new Intent();
//                                intent.setAction(
//                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                Uri uri = Uri.fromParts("package",
//                                        BuildConfig.APPLICATION_ID, null);
//                                intent.setData(uri);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(intent);
//                            }
//                        });
//            }
//        } else if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
//            locationPermissionGranted = false;
//            // If request is cancelled, the result arrays are empty.
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                locationPermissionGranted = true;
//            }
//
//            updateLocationUI();
//        }
//    }
//
//    private void showSnackbar(final String text) {
//        View container = findViewById(R.id.tvSnack);
//        if (container != null) {
//            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
//        }
//    }
//
//    private void showSnackbar(final int mainTextStringId, final int actionStringId,
//                              View.OnClickListener listener) {
//        Snackbar.make(findViewById(android.R.id.content),
//                getString(mainTextStringId),
//                Snackbar.LENGTH_INDEFINITE)
//                .setAction(getString(actionStringId), listener).show();
//    }
//
//
//    // [START maps_current_place_on_create]
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        tvSnack = (View) findViewById(R.id.tvSnack);
//
//        // [START_EXCLUDE silent]
//        // [START maps_current_place_on_create_save_instance_state]
//        // Retrieve location and camera position from saved instance state.
//        if (savedInstanceState != null) {
//            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
//            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
//        }
//        // [END maps_current_place_on_create_save_instance_state]
//        // [END_EXCLUDE]
//
//        // Retrieve the content view that renders the map.
//        setContentView(R.layout.activity_maps);
//
//        // [START_EXCLUDE silent]
//        // Construct a PlacesClient
//        Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
//        placesClient = Places.createClient(this);
//
//        // Construct a FusedLocationProviderClient.
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//
//        // Build the map.
//        // [START maps_current_place_map_fragment]
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        // [END maps_current_place_map_fragment]
//        // [END_EXCLUDE]
//        showCurrentPlace();
//    }
//    // [END maps_current_place_on_create]
//
//    /**
//     * Saves the state of the map when the activity is paused.
//     */
//    // [START maps_current_place_on_save_instance_state]
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        if (map != null) {
//            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
//            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
//        }
//        super.onSaveInstanceState(outState);
//    }
//    // [END maps_current_place_on_save_instance_state]
//
//    /**
//     * Sets up the options menu.
//     *
//     * @param menu The options menu.
//     * @return Boolean.
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.current_place_menu, menu);
//        return true;
//    }
//
//    /**
//     * Handles a click on the menu option to get a place.
//     *
//     * @param item The menu item to handle.
//     * @return Boolean.
//     */
//    // [START maps_current_place_on_options_item_selected]
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.option_get_place) {
//            showCurrentPlace();
//        }
//        return true;
//    }
//    // [END maps_current_place_on_options_item_selected]
//
//    /**
//     * Manipulates the map when it's available.
//     * This callback is triggered when the map is ready to be used.
//     */
//    // [START maps_current_place_on_map_ready]
//    @Override
//    public void onMapReady(GoogleMap map) {
//        this.map = map;
//
//        // [START_EXCLUDE]
//        // [START map_current_place_set_info_window_adapter]
//        // Use a custom info window adapter to handle multiple lines of text in the
//        // info window contents.
//        this.map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//
//            @Override
//            // Return null here, so that getInfoContents() is called next.
//            public View getInfoWindow(Marker arg0) {
//                return null;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                // Inflate the layouts for the info window, title and snippet.
//                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents,
//                        (FrameLayout) findViewById(R.id.map), false);
//
//                TextView title = infoWindow.findViewById(R.id.title);
//                title.setText(marker.getTitle());
//
//                TextView snippet = infoWindow.findViewById(R.id.snippet);
//                snippet.setText(marker.getSnippet());
//
//                return infoWindow;
//            }
//        });
//        // [END map_current_place_set_info_window_adapter]
//
//        // Prompt the user for permission.
//        getLocationPermission();
//        // [END_EXCLUDE]
//
//        // Turn on the My Location layer and the related control on the map.
//        updateLocationUI();
//
//        // Get the current location of the device and set the position of the map.
//        getDeviceLocation();
//    }
//    // [END maps_current_place_on_map_ready]
//
//    /**
//     * Gets the current location of the device, and positions the map's camera.
//     */
//    // [START maps_current_place_get_device_location]
//    private void getDeviceLocation() {
//        /*
//         * Get the best and most recent location of the device, which may be null in rare
//         * cases when a location is not available.
//         */
//        try {
//            if (locationPermissionGranted) {
//                @SuppressLint("MissingPermission") Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
//                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Location> task) {
//                        if (task.isSuccessful()) {
//                            // Set the map's camera position to the current location of the device.
//                            lastKnownLocation = task.getResult();
//                            if (lastKnownLocation != null) {
//                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                        new LatLng(lastKnownLocation.getLatitude(),
//                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
//                            }
//                        } else {
//                            Log.d(TAG, "Current location is null. Using defaults.");
//                            Log.e(TAG, "Exception: %s", task.getException());
//                            map.moveCamera(CameraUpdateFactory
//                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
//                            map.getUiSettings().setMyLocationButtonEnabled(false);
//                        }
//                    }
//                });
//            }
//        } catch (SecurityException e) {
//            Log.e("Exception: %s", e.getMessage(), e);
//        }
//    }
//    // [END maps_current_place_get_device_location]
//
//    /**
//     * Prompts the user for permission to use the device location.
//     */
//    // [START maps_current_place_location_permission]
//    private void getLocationPermission() {
//        /*
//         * Request location permission, so that we can get the location of the
//         * device. The result of the permission request is handled by a callback,
//         * onRequestPermissionsResult.
//         */
//        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            locationPermissionGranted = true;
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//        }
//    }
//    // [END maps_current_place_location_permission]
//
//    /**
//     * Handles the result of the request for location permissions.
//     */
//    // [START maps_current_place_on_request_permissions_result]
////    @Override
////    public void onRequestPermissionsResult(int requestCode,
////                                           @NonNull String[] permissions,
////                                           @NonNull int[] grantResults) {
////        locationPermissionGranted = false;
////        switch (requestCode) {
////            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
////                // If request is cancelled, the result arrays are empty.
////                if (grantResults.length > 0
////                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                    locationPermissionGranted = true;
////                }
////            }
////            break;
////        }
////        updateLocationUI();
////    }
//    // [END maps_current_place_on_request_permissions_result]
//
//    /**
//     * Prompts the user to select the current place from a list of likely places, and shows the
//     * current place on the map - provided the user has granted location permission.
//     */
//    // [START maps_current_place_show_current_place]
//    private void showCurrentPlace() {
//        if (map == null) {
//            return;
//        }
//
//        if (locationPermissionGranted) {
//            // Use fields to define the data types to return.
//            List<Place.Field> placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS,
//                    Place.Field.LAT_LNG);
//
//            // Use the builder to create a FindCurrentPlaceRequest.
//            FindCurrentPlaceRequest request =
//                    FindCurrentPlaceRequest.newInstance(placeFields);
//
//            // Get the likely places - that is, the businesses and other points of interest that
//            // are the best match for the device's current location.
//            @SuppressWarnings("MissingPermission") final Task<FindCurrentPlaceResponse> placeResult =
//                    placesClient.findCurrentPlace(request);
//            placeResult.addOnCompleteListener(new OnCompleteListener<FindCurrentPlaceResponse>() {
//                @Override
//                public void onComplete(@NonNull Task<FindCurrentPlaceResponse> task) {
//                    if (task.isSuccessful() && task.getResult() != null) {
//                        FindCurrentPlaceResponse likelyPlaces = task.getResult();
//
//                        // Set the count, handling cases where less than 5 entries are returned.
//                        int count;
//                        if (likelyPlaces.getPlaceLikelihoods().size() < M_MAX_ENTRIES) {
//                            count = likelyPlaces.getPlaceLikelihoods().size();
//                        } else {
//                            count = M_MAX_ENTRIES;
//                        }
//
//                        int i = 0;
//                        likelyPlaceNames = new String[count];
//                        likelyPlaceAddresses = new String[count];
//                        likelyPlaceAttributions = new List[count];
//                        likelyPlaceLatLngs = new LatLng[count];
//
//                        for (PlaceLikelihood placeLikelihood : likelyPlaces.getPlaceLikelihoods()) {
//                            // Build a list of likely places to show the user.
//                            likelyPlaceNames[i] = placeLikelihood.getPlace().getName();
//                            likelyPlaceAddresses[i] = placeLikelihood.getPlace().getAddress();
//                            likelyPlaceAttributions[i] = placeLikelihood.getPlace()
//                                    .getAttributions();
//                            likelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();
//
//                            i++;
//                            if (i > (count - 1)) {
//                                break;
//                            }
//                        }
//
//                        // Show a dialog offering the user the list of likely places, and add a
//                        // marker at the selected place.
//                        MapsActivityCurrentPlace.this.openPlacesDialog();
//                    } else {
//                        Log.e(TAG, "Exception: %s", task.getException());
//                    }
//                }
//            });
//        } else {
//            // The user has not granted permission.
//            Log.i(TAG, "The user did not grant location permission.");
//
//            // Add a default marker, because the user hasn't selected a place.
//            map.addMarker(new MarkerOptions()
//                    .title("T1")
//                    .position(defaultLocation)
//                    .snippet("T2"));
//
//            // Prompt the user for permission.
//            getLocationPermission();
//        }
//    }
//    // [END maps_current_place_show_current_place]
//
//    /**
//     * Displays a form allowing the user to select a place from a list of likely places.
//     */
//    // [START maps_current_place_open_places_dialog]
//    private void openPlacesDialog() {
//        // Ask the user to choose the place where they are now.
//        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // The "which" argument contains the position of the selected item.
//                LatLng markerLatLng = likelyPlaceLatLngs[which];
//                String markerSnippet = likelyPlaceAddresses[which];
//                if (likelyPlaceAttributions[which] != null) {
//                    markerSnippet = markerSnippet + "\n" + likelyPlaceAttributions[which];
//                }
//
//                // Add a marker for the selected place, with an info window
//                // showing information about that place.
//                map.addMarker(new MarkerOptions()
//                        .title(likelyPlaceNames[which])
//                        .position(markerLatLng)
//                        .snippet(markerSnippet));
//
//                // Position the map's camera at the location of the marker.
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng,
//                        DEFAULT_ZOOM));
//            }
//        };
//
//        // Display the dialog.
//        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setTitle("Line421")
//                .setItems(likelyPlaceNames, listener)
//                .show();
//    }
//    // [END maps_current_place_open_places_dialog]
//
//    /**
//     * Updates the map's UI settings based on whether the user has granted location permission.
//     */
//    // [START maps_current_place_update_location_ui]
//    @SuppressLint("MissingPermission")
//    private void updateLocationUI() {
//        if (map == null) {
//            return;
//        }
//        try {
//            if (locationPermissionGranted) {
//                map.setMyLocationEnabled(true);
//                map.getUiSettings().setMyLocationButtonEnabled(true);
//            } else {
//                map.setMyLocationEnabled(false);
//                map.getUiSettings().setMyLocationButtonEnabled(false);
//                lastKnownLocation = null;
//                getLocationPermission();
//            }
//        } catch (SecurityException e) {
//            Log.e("Exception: %s", e.getMessage());
//        }
//    }
//    // [END maps_current_place_update_location_ui]
//}