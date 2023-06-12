package com.example.cardiocare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver broadcastReceiver1;
    private Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        broadcastReceiver1 = new Broadcaster();
        registerReceiver(broadcastReceiver1, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        b1=findViewById(R.id.go1);
        b2=findViewById(R.id.go2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,UserProfileShow.class);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent1 = new Intent(getApplicationContext(), UserLogin.class);
                startActivity(intent1);
            }
        });
    }
}