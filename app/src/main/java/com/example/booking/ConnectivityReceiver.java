package com.example.booking;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class ConnectivityReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(final Context context, final Intent intent) {
        if(!Utils.isConnectedToInternet(context)){
            AlertDialog.Builder builder =  new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.no_internet_popup,null);
            builder.setView(view);

            Button retry = (Button) view.findViewById(R.id.btn_no_internet_retry);

            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);
            dialog.getWindow().setGravity(Gravity.CENTER);

            retry.setOnClickListener(view1 -> {
                dialog.dismiss();
                onReceive(context,intent);
            });
        }else {

        }
    }
}