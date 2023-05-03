package com.example.booking.ui.About;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.booking.BuildConfig;
import com.example.booking.R;
import com.example.booking.Utils;
import com.example.booking.databinding.FragmentAboutBinding;

public class AboutFragment extends Fragment {

    private FragmentAboutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAboutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        binding.tvAppVersion.setText(versionName + " ("+versionCode+")");

        if(Utils.get_SharedPreference_staging_or_production_enviorment(getActivity()).contains(Utils.value_production)){
            binding.tvAppName.setText(getString(R.string.app_name));
        }else{
            binding.tvAppName.setText(getString(R.string.app_name_staging));


        }


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}