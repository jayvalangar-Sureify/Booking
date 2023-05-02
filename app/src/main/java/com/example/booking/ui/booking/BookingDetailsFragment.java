package com.example.booking.ui.booking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.booking.FirebaseProductionSingletonClass;
import com.example.booking.Utils;
import com.example.booking.adapter.BookingDetailsAdapter;
import com.example.booking.databinding.FragmentBookingDetailsBinding;
import com.example.booking.interfaces.OnHistoryDataChangedListener;
import com.example.booking.model.BookingDetailsModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BookingDetailsFragment extends Fragment implements OnHistoryDataChangedListener {

    LinkedHashMap<String, LinkedHashMap<String, String>> historyData_double_linkedhashmap;


    //--------------------------------------------------
    ArrayList<String> date_arrayList;
    ArrayList<String> timeslotsandprice_arraylist;
    ArrayList<String> indetailBookingDetails_arraylist;
    //--------------------------------------------------

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
        // Initialize
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
        if(Utils.get_SharedPreference_staging_or_production_enviorment(getActivity()).contains(Utils.value_production)){
            firebaseAuth = FirebaseAuth.getInstance(FirebaseProductionSingletonClass.getInstance(getActivity()));
            firebaseFirestore = FirebaseFirestore.getInstance(FirebaseProductionSingletonClass.getInstance(getActivity()));

        }else{
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseFirestore = FirebaseFirestore.getInstance();
        }
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=

        user_Id_string = firebaseAuth.getCurrentUser().getUid();
        //-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=


        binding.rlProgressbarBookingDetailsFragment.setVisibility(View.VISIBLE);

        firebaseFirestore.collection(Utils.key_place_booking_firestore)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentSnapshot> documentSnapshots = task.getResult().getDocuments();
                            getAllBookingIdData(documentSnapshots);

                        } else {
                            Log.i("test_response", "Error getting documents: ", task.getException());
                        }
                    }
                });


        //-=-=-=-=--=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=
        binding.btnBookingDetailsShowHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookingHistoryActivity.class);
                intent.putExtra("historyData", loggedIn_user_data_linked_hashmap);
                startActivity(intent);
            }
        });
        //-=-=-=-=-=--=-=-==-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-

            return root;
    }




    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    private void getAllBookingIdData(List<DocumentSnapshot> documentSnapshot) {
        CollectionReference collectionRef = firebaseFirestore.collection(Utils.key_place_booking_firestore);
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    date_arrayList = new ArrayList<>();
                    timeslotsandprice_arraylist = new ArrayList<>();
                    indetailBookingDetails_arraylist = new ArrayList<>();

                    QuerySnapshot querySnapshot = task.getResult();
                    List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                    for (int i = 0; i < documents.size(); i++) {
                        DocumentSnapshot document = documents.get(i);

                        String[] parts = document.getId().split("_");
                        String owner_id_from_server_String = parts[0]; // "bHbwpPf7xFhJXX42vC9pEomm8Ey2"
                        String date_from_Server_string = parts[1]; // "14 Apr 2023"

                        Utils.printLongLog("test_new_data", "owner_id_from_server_String : "+owner_id_from_server_String);
                        Utils.printLongLog("test_new_data", "date_from_Server_string : "+date_from_Server_string);

                        Utils.printLongLog("test_new_data", "======================================================");

                        String get_type_of_login = Utils.get_SharedPreference_type_of_login(getActivity());

                        if (get_type_of_login.contains(Utils.owner)) {

                            Map<String, Object> bookingData = document.getData();

                            for (Map.Entry<String, Object> entry : bookingData.entrySet()) {
                                String list_number_key = entry.getKey();

                                Map<String, Object> value = (Map<String, Object>) entry.getValue();

                                for (Map.Entry<String, Object> innerEntry : value.entrySet()) {
                                    String timeSlot = innerEntry.getKey();
                                    innerEntry.getValue();
//
                                }
                            }

                        } else {
                            // It's User

                            Utils.printLongLog("test_new_data", "It's USER");


                            Map<String, Object> bookingData = document.getData();

                            for (Map.Entry<String, Object> entry : bookingData.entrySet()) {
                                String key_1_ground_net_number = entry.getKey();
                                Map<String, Object> value_1 = (Map<String, Object>) entry.getValue();


                                for (Map.Entry<String, Object> innerEntry : value_1.entrySet()) {
                                    String key_2_time_price_combo = innerEntry.getKey();
                                    Map<String, Object> value_2_all_booking_details = (Map<String, Object>) entry.getValue();


                                    // Take USer Id from Value object
                                    //--------------------------------------------------------------------------------------
                                    Pattern pattern = Pattern.compile("\\{([a-zA-Z0-9]+)=\\{");
                                    Matcher matcher = pattern.matcher(value_2_all_booking_details.toString());
                                    String userid_from_Server = "";
                                    while (matcher.find()) {
                                        userid_from_Server = matcher.group(1);
                                    }
                                    //--------------------------------------------------------------------------------------


                                    if (user_Id_string.contains(userid_from_Server)) {
                                        Utils.printLongLog("test_new_data", "***** USERID match Current USer *****");

                                        //=============================================================
                                        String date_timeSlot_String = date_from_Server_string+"_"+key_2_time_price_combo;
                                        String[] date_timeSlot_parts = date_timeSlot_String.split("_");
                                        String date = parts[0];
                                        String time = parts[1];

                                        // Add date, time and Details
                                        date_arrayList.add(date_from_Server_string);
                                        timeslotsandprice_arraylist.add(key_2_time_price_combo);
                                        indetailBookingDetails_arraylist.add(value_2_all_booking_details.get(key_2_time_price_combo).toString());
                                        //=============================================================

                                    }


                                }
                            }


                            //-=-=-=-=-=-=-=-=-=-=--=-=-=--=-=-=--=-=-=--=-=-=-=--=-=-=-=-=--=-=-=-=
                            // Create a list of Booking objects
                            List<BookingDetailsModel> bookings = new ArrayList<>();
                            for (int i1 = 0; i1 < date_arrayList.size(); i1++) {
                                String date = date_arrayList.get(i1);
                                String timePrice = timeslotsandprice_arraylist.get(i1);
                                String[] splitTimePrice = timePrice.split(" = ");
                                String time = splitTimePrice[0];
                                int price = Integer.parseInt(splitTimePrice[1]);
                                Map<String, Map<String, String>> details = new HashMap<>();
                                details.put(indetailBookingDetails_arraylist.get(i1), new HashMap<>());
                                BookingDetailsModel booking = new BookingDetailsModel(date, time, price, details);
                                bookings.add(booking);
                            }

                            // Sort the list by date and time
                            Collections.sort(bookings, new Comparator<BookingDetailsModel>() {
                                public int compare(BookingDetailsModel b1, BookingDetailsModel b2) {
                                    int dateCompare = b1.getDate().compareTo(b2.getDate());
                                    if (dateCompare != 0) {
                                        return dateCompare;
                                    }
                                    return b1.getTime().compareTo(b2.getTime());
                                }
                            });

                            // Update the original arraylists based on the sorted Booking objects
                            for (int i2 = 0; i2 < bookings.size(); i2++) {
                                date_arrayList.set(i2, bookings.get(i2).getDate());
                                String time = bookings.get(i2).getTime() + " = " + bookings.get(i2).getPrice();
                                timeslotsandprice_arraylist.set(i2, time);
                                indetailBookingDetails_arraylist.set(i2, bookings.get(i2).getDetails().keySet().iterator().next());
                            }

                            //-=-=--=-=-=--=-=-=-=--=-=-=--=-=-=-=-=--=-=-=-=-=--=-=-=-=-=-=-=--==-=

                            Utils.printLongLog("test_new_data_only", "Date : \n" + date_arrayList.toString());
                            Utils.printLongLog("test_new_data_only", "Time : \n" + timeslotsandprice_arraylist.toString());
                            Utils.printLongLog("test_new_data_only", "Indetails : \n" + indetailBookingDetails_arraylist.toString());



                        }

                        Utils.printLongLog("test_new_data", "======================================================");
                    }
                    // END : 1st loop for number of selected timeslots ---------------------------------------------------------------------------------


                //********************************************************************************************
                    if (date_arrayList.size() != 0) {
                        binding.tvBookingDetailsNoUpcoming.setVisibility(View.GONE);
                        BookingDetailsAdapter adapter = new BookingDetailsAdapter(date_arrayList, timeslotsandprice_arraylist, indetailBookingDetails_arraylist, BookingDetailsFragment.this);
                        binding.bookingDetailsRecycleView.setAdapter(adapter);
                        binding.bookingDetailsRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                        adapter.notifyDataSetChanged();
                        binding.bookingDetailsRecycleView.invalidate();
                    } else {
                        binding.tvBookingDetailsNoUpcoming.setVisibility(View.VISIBLE);
                    }

                    //********************************************************************************************

                }
            }

        });





        //------------------------------------------------------------------------------------
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-


    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
    @Override
    public void onHistoryDataChanged(LinkedHashMap<String, LinkedHashMap<String, String>> historyData) {
        // Handle the changed history data here
        historyData_double_linkedhashmap = historyData;
    }
    //=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}