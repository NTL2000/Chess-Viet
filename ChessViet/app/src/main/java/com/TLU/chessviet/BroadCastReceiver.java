package com.TLU.chessviet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

public class BroadCastReceiver extends BroadcastReceiver {
    private int label=0;

    @Override
    public void onReceive(Context context, Intent intent) {
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction()))
        {
            if(isNetWorkAvailable(context)){
                if(label==1)
                    Toast.makeText(context,"Đã kết nối wifi",Toast.LENGTH_LONG).show();
                label=1;
            }
            else {
                if (label==1)
                    Toast.makeText(context,"Mất kết nối wifi",Toast.LENGTH_LONG).show();
                label=1;
            }
        }
    }

    private boolean isNetWorkAvailable(Context context) {
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager==null){
            return false;
        }
        if(Build.VERSION.SDK_INT>=24){
            Network network=connectivityManager.getActiveNetwork();
            if(network==null){
                return false;
            }
            NetworkCapabilities capabilities=connectivityManager.getNetworkCapabilities(network);
            return capabilities!=null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
        }
        else {
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            return networkInfo!=null && networkInfo.isConnected();
        }
    }
}