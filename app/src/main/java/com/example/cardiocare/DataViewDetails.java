package com.example.cardiocare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
/**
 * This activity displays the details of a data view.
 */
public class DataViewDetails extends AppCompatActivity {
    private TextView dateid,timeid,sytolicid,diastolicid,heartid,comid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dateid=findViewById(R.id.dateid);
        timeid=findViewById(R.id.timeid);
        sytolicid=findViewById(R.id.sytolicid);
        diastolicid=findViewById(R.id.diastolicid);
        heartid=findViewById(R.id.heartid);
        comid=findViewById(R.id.comid);
        Intent intent = getIntent();
        if (intent != null) {
            String date = intent.getStringExtra("date");
            String time = intent.getStringExtra("time");
            String systolic = intent.getStringExtra("systolic");
            String diastolic = intent.getStringExtra("diastolic");
            String heartRate = intent.getStringExtra("heartRate");
            String comment = intent.getStringExtra("comment");

            if (comid.getText() == "Normal Pressure") {
                comid.setTextColor(getResources().getColor(R.color.green));
            } else if (comid.getText() == "High Pressure") {
                comid.setTextColor(getResources().getColor(R.color.red));
            } else if (comid.getText() == "Low Pressure") {
                comid.setTextColor(getResources().getColor(R.color.blue));
            } else if (comid.getText() == "High Pressure :)") {
                comid.setTextColor(getResources().getColor(R.color.red));
            }
            // Set the data to the TextViews
            dateid.setText(date);
            timeid.setText(time);
            sytolicid.setText(systolic);
            diastolicid.setText(diastolic);
            heartid.setText(heartRate);
            comid.setText(comment);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}