package com.example.cardiocare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver broadcastReceiver1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        broadcastReceiver1 = new Broadcaster();
        registerReceiver(broadcastReceiver1, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
}