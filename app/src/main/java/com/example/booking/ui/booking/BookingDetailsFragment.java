package com.example.booking.ui.booking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.booking.Utils;
import com.example.booking.adapter.BookingDetailsAdapter;
import com.example.booking.databinding.FragmentBookingDetailsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class BookingDetailsFragment extends Fragment {

    boolean is_data_taken_from_server = false;
    boolean is_data_filtering_done = false;

    LinkedHashMap<String, Object> loggedIn_user_data_linked_hashmap = new LinkedHashMap<>();

    private FragmentBookingDetailsBinding binding;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String user_Id_string;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBookingDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        // Initialize
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user_Id_string = firebaseAuth.getCurrentUser().getUid();
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=



        firebaseFirestore.collection(Utils.key_place_booking_firestore)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                            getAllBookingIdData(documentSnapshots);

                            int lastIndex = task.getResult().size() - 1;
//                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
//
//                                String booking_id = documentSnapshot.getId();
//
////                                getAllBookingIdData(documentSnapshot);
//
//                                Log.i("test_index_response", ""+documentSnapshots.indexOf(documentSnapshot));
//
//
//                                // Check if this is the last iteration
//                                if (documentSnapshots.indexOf(documentSnapshot) == lastIndex) {
//                                    is_data_taken_from_server = true;
//                                }
//
//                            }

                        } else {
                            Log.i("test_response", "Error getting documents: ", task.getException());
                        }
                    }
                });


//        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-==-=-=
//        // Check owner already added place or not
//        firebaseFirestore.collection(Utils.key_place_booking_firestore)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//
//
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                String booking_id = document.getId();
//
//                                // Access the nested HashMap
//                                HashMap<String, HashMap<String, HashMap<String, String>>> bookings_data_hashmap = (HashMap<String, HashMap<String, HashMap<String, String>>>) document.getData().values().iterator().next();
//
//                                Log.i("test_response", "SIZE : "+bookings_data_hashmap.size());
//                                for (String time_slots_key : bookings_data_hashmap.keySet()) {
//
//                                    bookings_data_hashmap.get(time_slots_key);
//
//                                    String first_hashmap_key = time_slots_key;
//
//                                    Log.i("test_response", "first_hashmap_key : "+first_hashmap_key);
//
//
//                                    // Get the second-level HashMap for this key
//                                    HashMap<String, HashMap<String, String>> time_slots_hashmap = bookings_data_hashmap.get(time_slots_key);
//
//                                    // Iterate over each key in the second-level HashMap
//                                    for (String userid_as_a_key : time_slots_hashmap.keySet()) {
////                                        // Get the innermost HashMap for this key
////                                        HashMap<String, String> innerMap = time_slots_hashmap.get(userid_as_a_key);
////
////                                        // Iterate over each key in the innermost HashMap
////                                        for (String innerKey : innerMap.keySet()) {
////                                            // Get the value for this key
////                                            String all_details_value = innerMap.get(innerKey);
////
////                                            // Do something with the key and value
////                                            System.out.println(time_slots_key + ", " + userid_as_a_key + ", " + innerKey + ", " + all_details_value);
////
////
////
////
//                                        Log.i("test_response", "userid_as_a_key : "+userid_as_a_key);
//                                        Log.i("test_response", "userid_value : "+time_slots_hashmap.get(userid_as_a_key));
////                                        Log.i("test_response", "innerKey : "+innerKey);
////                                        Log.i("test_response", "all_details_value : "+all_details_value);
//                                    }
//                                }
//
//                            }
//                        } else {
//                            Log.i("test_response", "Error getting documents: ", task.getException());
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.i("test_response", "Error getting documents: "+e.getMessage());
//                    }
//                });
//        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-=-=-=-=-==-=-=

            return root;
    }



    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    private void getAllBookingIdData(List<DocumentSnapshot> documentSnapshot) {
        //------------------------------------------------------------------------------------
       for(int i = 0; i < documentSnapshot.size(); i++) {
           Map<String, Object> bookingData = documentSnapshot.get(i).getData();
           for (Map.Entry<String, Object> bookingEntry : bookingData.entrySet()) {
               // Get the booking time slot
               String timeSlot = bookingEntry.getKey();
               Log.d("TAG", "Time slot: " + timeSlot);

               // Get the nested booking data
               Map<String, Object> bookingDetails = (Map<String, Object>) bookingEntry.getValue();
               for (Map.Entry<String, Object> bookingDetailsEntry : bookingDetails.entrySet()) {
                   // Get the booking ID
                   String bookingId = bookingDetailsEntry.getKey();
                   Log.d("test_special_response", "USERRRRRR_IDDDDDDDDDDDD :- " + bookingId);

                   // Get the nested booking data
                   Map<String, Object> booking = (Map<String, Object>) bookingDetailsEntry.getValue();


                   Log.d("sajahfsal", "user_Id_string = " + user_Id_string);
                   Log.d("sajahfsal", "bookingId = " + bookingId);

                   if (user_Id_string.contains(bookingId)) {
                       loggedIn_user_data_linked_hashmap.put(timeSlot, booking);
                   }


                   // Get the booking fields
                   String userId = (String) booking.get("key_booking_user_id");
                   String placeName = (String) booking.get("key_booking_place_name");
                   Double latitude = Double.valueOf((String) booking.get("key_booking_place_latitude"));
                   Double longitude = Double.valueOf((String) booking.get("key_booking_place_longitude"));
                   String staffNumber = (String) booking.get("key_booking_staff_number");
                   String placeAddress = (String) booking.get("key_booking_place_address");
                   String bookingDate = (String) booking.get("key_booking_date");


                   Log.d("test_response", user_Id_string + " == " + userId);

                   // Log the booking fields
                   Log.d("TAG", "User ID: " + userId);
                   Log.d("TAG", "Place Name: " + placeName);
                   Log.d("TAG", "Latitude: " + latitude);
                   Log.d("TAG", "Longitude: " + longitude);
                   Log.d("TAG", "Staff Number: " + staffNumber);
                   Log.d("TAG", "Place Address: " + placeAddress);
                   Log.d("TAG", "Booking Date: " + bookingDate);
               }
           }
       }

        //------------------------------------------------------------------------------------------
        BookingDetailsAdapter adapter = new BookingDetailsAdapter(loggedIn_user_data_linked_hashmap);
        binding.bookingDetailsRecycleView.setAdapter(adapter);
        binding.bookingDetailsRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
// Call notifyDataSetChanged() on the adapter to refresh the data displayed in the RecyclerView
        adapter.notifyDataSetChanged();
// Call invalidate() on the RecyclerView to force it to redraw itself
        binding.bookingDetailsRecycleView.invalidate();
        //------------------------------------------------------------------------------------------



        //------------------------------------------------------------------------------------
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}