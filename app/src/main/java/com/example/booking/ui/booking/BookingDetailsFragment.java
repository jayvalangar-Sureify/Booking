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

import com.example.booking.Utils;
import com.example.booking.adapter.BookingDetailsAdapter;
import com.example.booking.databinding.FragmentBookingDetailsBinding;
import com.example.booking.interfaces.OnHistoryDataChangedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class BookingDetailsFragment extends Fragment implements OnHistoryDataChangedListener {

    LinkedHashMap<String, LinkedHashMap<String, String>> historyData_double_linkedhashmap;

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
        //------------------------------------------------------------------------------------
       for(int i = 0; i < documentSnapshot.size(); i++) {
           String owner_id_with_date_string = documentSnapshot.get(i).getId();

           String[] parts = owner_id_with_date_string.split("_");

           String owner_id_from_Server_string = parts[0]; // "15GeAx8pikQFJwFZ5q4GJIXLuMW2"
           String owner_booking_date = parts[1]; // "05 Apr 2023"

           String get_type_of_login = Utils.get_SharedPreference_type_of_login(getActivity());

           if(get_type_of_login.contains(Utils.owner)){
               //-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=
               String current_owner_id = user_Id_string;

               if(current_owner_id.contains(owner_id_from_Server_string)) {
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

                           // Get the nested booking data
                           Map<String, Object> booking = (Map<String, Object>) bookingDetailsEntry.getValue();

                               loggedIn_user_data_linked_hashmap.put(timeSlot, booking);

                       }
                   }
               }
               //-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=
           }else{
               //-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=
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

                       // Get the nested booking data
                       Map<String, Object> booking = (Map<String, Object>) bookingDetailsEntry.getValue();

                       if (user_Id_string.contains(bookingId)) {
                           loggedIn_user_data_linked_hashmap.put(timeSlot, booking);
                       }
                   }
               }
               //-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=
           }



       }

        //------------------------------------------------------------------------------------------
        if(loggedIn_user_data_linked_hashmap.size() != 0) {
            binding.tvBookingDetailsNoUpcoming.setVisibility(View.GONE);
            BookingDetailsAdapter adapter = new BookingDetailsAdapter(loggedIn_user_data_linked_hashmap, this);
            binding.bookingDetailsRecycleView.setAdapter(adapter);
            binding.bookingDetailsRecycleView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
// Call notifyDataSetChanged() on the adapter to refresh the data displayed in the RecyclerView
            adapter.notifyDataSetChanged();
// Call invalidate() on the RecyclerView to force it to redraw itself
            binding.bookingDetailsRecycleView.invalidate();
        }else{
            binding.tvBookingDetailsNoUpcoming.setVisibility(View.VISIBLE);
        }
        //------------------------------------------------------------------------------------------


        binding.rlProgressbarBookingDetailsFragment.setVisibility(View.GONE);
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