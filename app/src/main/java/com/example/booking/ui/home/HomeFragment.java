package com.example.booking.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.booking.Utils;
import com.example.booking.databinding.FragmentHomeBinding;
import com.example.booking.ui.Owner_Add_Place_Details.OwnerAddPlaceDetails;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment implements OnMapReadyCallback,  GoogleMap.OnMapLoadedCallback {

    private FragmentHomeBinding binding;

    public MapView mapView;
    public GoogleMap googleMap;
    LocationManager locationManager;
    Criteria criteria;
    String provider;
    Location location;

    // For user to show places location
    public HashMap<Double, Double> owner_places_hashmap = new HashMap<>();
    public HashMap<String, String > owner_places_in_details_hashmap = new HashMap<>();

    String login_type_string;

    // Fetch Data
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String user_Id_string;
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Checking login type
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        login_type_string = Utils.get_SharedPreference_type_of_login(getActivity());
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        // Initialization
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user_Id_string = firebaseAuth.getCurrentUser().getUid();
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        // Check user permission
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        if (ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 20);

        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=



        // Initialisation
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        try {
            provider = locationManager.getBestProvider(criteria, false);
            location = locationManager.getLastKnownLocation(provider);
        }catch (Exception e){
            e.getMessage();
        }

        if(googleMap != null) { //prevent crashing if the map doesn't exist yet (eg. on starting activity)
            googleMap.clear();
            googleMap.setMyLocationEnabled(true);

        }
        return root;
    }


    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
// Register the OnMapLoadedCallback
        googleMap.setOnMapLoadedCallback(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

       // View visible and Gone based on Login Type
       //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        if(login_type_string.equals(Utils.user)){
            binding.llBottomLatitudeLongitude.setVisibility(View.GONE);
        }

        if(login_type_string.equals(Utils.owner)){

            // Owner already added place
            if(Utils.get_SharedPreference_owner_completed_add_placele_procedure(getActivity()).equals("1")){
                binding.llBottomLatitudeLongitude.setVisibility(View.GONE);
                binding.rlTop.setVisibility(View.GONE);
            }else {
                binding.llBottomLatitudeLongitude.setVisibility(View.VISIBLE);
                binding.rlTop.setVisibility(View.VISIBLE);
            }
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        // Adding places from database :  in hashmap
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        if(login_type_string.equals(Utils.user)){
            firebaseFirestore.collection(Utils.key_ownerplace_firestore)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Adding latitude and longitude to one hasmap
                                    Double owner_place_latitude = (Double) document.getData().get(Utils.map_key_owner_place_latitude);
                                    Double owner_place_longitude = (Double) document.getData().get(Utils.map_key_owner_place_longitude);
                                    owner_places_hashmap.put(owner_place_latitude, owner_place_longitude);

                                    // Adding other details
                                    String owner_place_name_string = (String) document.getData().get(Utils.map_key_owner_place_name);
                                    String owner_place_full_address_string = (String) document.getData().get(Utils.map_key_owner_place_full_address);
                                    String owner_place_staff_number_string = (String) document.getData().get(Utils.map_key_owner_place_ground_staff_number);
                                    String owner_place_total_nets_string = (String) document.getData().get(Utils.map_key_owner_place_total_nets);
                                    owner_places_in_details_hashmap.put(
                                            ""+owner_place_latitude+","+owner_place_longitude,

                                            "\n Place Name : "+owner_place_name_string
                                            +"\n Address : "+owner_place_full_address_string
                                            + "\n Owner Staff : " + owner_place_staff_number_string
                                            + "\n Total Nets : " + owner_place_total_nets_string);
                                }
                            } else {
                                Log.i("test_response", "Error getting documents: ", task.getException());
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("test_response", "Error getting documents: "+e.getMessage());
                        }
                    });
            //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        }


    }


    @Override
    public void onMapLoaded() {
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=




        // Check user permission
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        if (ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 20);

        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        if(googleMap != null) { //prevent crashing if the map doesn't exist yet (eg. on starting activity)
            googleMap.clear();
            googleMap.setMyLocationEnabled(true);


            // USer : Add owners places (Set All Location Mark)
            if(login_type_string.equals(Utils.user)) {
                // Database Part : Add owner dummy location
                //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
//                owner_places_hashmap.put(21.170240, 72.831062);
//                owner_places_hashmap.put(21.216820, 72.830269);
//                owner_places_hashmap.put(21.227830, 72.860000);
//                owner_places_hashmap.put(21.238887, 72.850289);
//                owner_places_hashmap.put(21.248828, 72.840899);
                //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


                // User : Add custom places on map
                //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                if (location != null) {
                    LatLng current_my_location = new LatLng(location.getLatitude(), location.getLongitude());

                    Log.i("test_response", "owner_places_hashmap : "+owner_places_hashmap.size());
                owner_places_in_details_hashmap.entrySet().forEach(entry -> {
                        // seperating latitude and longitude from key
                           String latitude_longitude_string = entry.getKey();
                    List<String> individual_latitude_longitude_list = Arrays.asList(latitude_longitude_string.split(","));


                        LatLng set_owner_places_location = new LatLng(Double.parseDouble(individual_latitude_longitude_list.get(0)), Double.parseDouble(individual_latitude_longitude_list.get(1)));
                        googleMap.addMarker(new MarkerOptions().position(set_owner_places_location).title(""+entry.getValue())).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(set_owner_places_location, 11));
                    });

                    googleMap.addMarker(new MarkerOptions().position(current_my_location).title("My Location"));
                }
                //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

                // USer : Clicked on location mark for place details
                //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                try {
                    googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                        @Override
                        public boolean onMarkerClick(Marker arg0) {
                            if (arg0 != null) ; // if marker  source is clicked

                            new AlertDialog.Builder(getActivity())
                                    .setTitle("")
                                    .setMessage(""+arg0.getTitle())

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Continue with delete operation
                                            dialog.dismiss();
                                        }
                                    })

                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setNegativeButton(android.R.string.no, null)
                                    .setIcon(android.R.drawable.ic_menu_mylocation)
                                    .show();
                            return true;
                        }

                    });
                }catch (Exception e){
                    e.getMessage();
                }
                //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


            }


            if(login_type_string.equals(Utils.owner)) {

                // Show current location
                //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                if (location != null) {
                    double owner_current_latitude = location.getLatitude();
                    double owner_current_longitude = location.getLongitude();
                    LatLng current_my_location = new LatLng(owner_current_latitude, owner_current_longitude);
                    googleMap.addMarker(new MarkerOptions().position(current_my_location).title("My Current Location"));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(current_my_location, 11));

                    // Owner selected latitude and longitude
                    binding.etHomeLatitude.setText(""+owner_current_latitude);
                    binding.etHomeLongitude.setText(""+owner_current_longitude);

                    binding.btnHomeAddOwnerPlace.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            add_latitude_and_longitude_to_firestore(owner_current_latitude, owner_current_longitude);
                        }
                    });
                }
                //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


                // OWNER : Select place for add your place
                //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
                try {
                    googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng latLng) {
                            // Add a marker at the clicked location
                            googleMap.clear();
                            googleMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location")).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                            // Do something with the latitude and longitude values
                            double latitude = latLng.latitude;
                            double longitude = latLng.longitude;


                            // Owner selected latitude and longitude
                            binding.etHomeLatitude.setText(""+latitude);
                            binding.etHomeLongitude.setText(""+longitude);

                            binding.btnHomeAddOwnerPlace.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    add_latitude_and_longitude_to_firestore(latitude, longitude);
                                     }
                            });
                            Log.i("test_response", "latitude : " + latitude + " longitude : " + longitude);
                        }
                    });
                } catch (Exception e) {
                    e.getMessage();
                }
                //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

            }



        }

        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    }


    // Add latitude and longitude into firestore
    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
    public void add_latitude_and_longitude_to_firestore(Double latitude, Double longitude){
        // Add location to owner table
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        DocumentReference docRef = firebaseFirestore.collection(Utils.key_owner_firestore).document(user_Id_string);
        // Update the document with a new field
        Map<String, Object> updates = new HashMap<>();
        updates.put(Utils.map_key_owner_Location, latitude+","+longitude);

        docRef.update(updates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("test_response", "location added successfully");
                        Intent intent = new Intent(getActivity(), OwnerAddPlaceDetails.class);
                        intent.putExtra(Utils.key_latitude,latitude);
                        intent.putExtra(Utils.key_longitude,longitude);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("test_response", "Error updating document", e);
                    }
                });
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    }

    //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}