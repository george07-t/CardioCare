package com.example.cardiocare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver broadcastReceiver1;
    private FloatingActionButton adddataid;
    private RecyclerView recyclerView;
    UserMeasurementShowAdapter viewAdapter;
    ArrayList<UserMeasurementDetails> list;
    private FirebaseAuth mAuth;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    UserMeasurementDetails userMeasurementDetails;
    ActionBarDrawerToggle toggle;
    ImageView imageMenu;
    DatabaseReference databaseReference, databaseReference1, databaseReference2;
    private String userid;
    public String fullname;
    private TextView usershowinnavigation;
    AlertDialog insert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressDialog progressDialog1 = new ProgressDialog(MainActivity.this);
        progressDialog1.setTitle("Loading");
        progressDialog1.setMessage("Please Wait");
        progressDialog1.show();
        getSupportActionBar().hide();
        broadcastReceiver1 = new Broadcaster();
        mAuth = FirebaseAuth.getInstance();
        registerReceiver(broadcastReceiver1, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        adddataid = findViewById(R.id.adddataid);
        recyclerView = findViewById(R.id.data);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        viewAdapter = new UserMeasurementShowAdapter(MainActivity.this, list);
        recyclerView.setAdapter(viewAdapter);
        databaseReference1 = FirebaseDatabase.getInstance().getReference("userdata");
        //insertdata
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dataadd, null);
        EditText sysid, dyaid, heartid;
        sysid = view.findViewById(R.id.sysid);
        dyaid = view.findViewById(R.id.dyaid);
        heartid = view.findViewById(R.id.heartid);
        Button datainsert = view.findViewById(R.id.datainsertid);
        datainsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Please Wait");
                progressDialog.show();
                databaseReference2 = FirebaseDatabase.getInstance().getReference("userdata");
                String key = databaseReference2.push().getKey();
                String sysdata = sysid.getText().toString().trim();
                String dyadata = dyaid.getText().toString().trim();
                String heartdata = heartid.getText().toString().trim();
                Date currentDate = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
                String dateString = dateFormat.format(currentDate);
                String timeString = timeFormat.format(currentDate);
                if (sysdata.isEmpty()) {
                    sysid.setError("Data Required");
                    progressDialog.dismiss();
                    sysid.requestFocus();
                    return;
                } else if (dyadata.isEmpty()) {
                    dyaid.setError("Data Required");
                    progressDialog.dismiss();
                    dyaid.requestFocus();
                    return;
                } else if (heartdata.isEmpty()) {
                    heartid.setError("Data Required");
                    progressDialog.dismiss();
                    heartid.requestFocus();
                    return;
                } else {
                    String comments = null;
                    int s, d;
                    s = Integer.parseInt(sysdata);
                    d = Integer.parseInt(dyadata);
                    if (s < d) {
                        dyaid.setError("Systolic must be greater than Diastolic");
                        dyaid.requestFocus();
                        progressDialog.dismiss();
                        return;
                    } else if ((s >= 90 && s <= 140) && (d >= 60 && d <= 90)) {
                        comments = "Normal Pressure";
                    } else if (s < 90 && d < 60) {
                        comments = "Low Pressure";
                    } else if (s > 140 && d > 90) {
                        comments = "High Pressure";
                    } else if (s > 140 && d < 60) {
                        comments = "Low Pressure";
                    } else if (s > 140 && d > 60) {
                        comments = "High Pressure";
                    } else if ((s >= 90 && s <= 140) && d < 60) {
                        comments = "Low Pressure";
                    } else if ((d >= 60 && d <= 90) && s > 140) {
                        comments = "High Pressure";
                    } else {
                        comments = "No Comments :)";
                    }
                    userMeasurementDetails = new UserMeasurementDetails(dateString, timeString, sysdata, dyadata, heartdata, comments,key);
                    ArrayList<UserMeasurementDetails> updatedData = new ArrayList<>();
                    databaseReference2.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(key)
                            .setValue(userMeasurementDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this, "Measurement added", Toast.LENGTH_SHORT).show();
                                        sysid.getText().clear();
                                        dyaid.getText().clear();
                                        heartid.getText().clear();
                                        insert.dismiss();
                                        updatedData.add(userMeasurementDetails);
                                        //recreate();
                                        viewAdapter.updateData(updatedData);
                                        progressDialog.dismiss();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Unsuccessful ", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        insert.dismiss();
                                    }
                                }
                            });

                }
            }
        });
        builder.setView(view);
        insert = builder.create();
        //navigarion
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_View);
        View header = navigationView.getHeaderView(0);
        usershowinnavigation = (TextView) header.findViewById(R.id.usershow);
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("userprofile");
        if (firebaseUser != null) {
            userid = firebaseUser.getUid();
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.msignin).setVisible(false);
            nav_Menu.findItem(R.id.msignup).setVisible(false);
            usershowinnavigation.setVisibility(View.VISIBLE);
            databaseReference.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserProfile userProfile = snapshot.getValue(UserProfile.class);
                    if (userProfile != null) {
                        fullname = userProfile.name;
                        usershowinnavigation.setText("@" + fullname);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


        imageMenu = findViewById(R.id.imageMenu);

        toggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.mHome:
                        Toast.makeText(MainActivity.this, "You Are in Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.Profile:
                        Intent intentss = new Intent(MainActivity.this, UserProfileShow.class);
                        startActivity(intentss);
                        drawerLayout.closeDrawers();

                        break;
                    case R.id.msignout:
                        FirebaseAuth.getInstance().signOut();
                        finish();
                        Intent intent1 = new Intent(getApplicationContext(), UserLogin.class);
                        startActivity(intent1);
                        break;
                    case R.id.msignin:
                        Intent intent2 = new Intent(MainActivity.this, UserLogin.class);
                        startActivity(intent2);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.msignup:
                        Intent intent3 = new Intent(MainActivity.this, UserSignup.class);
                        startActivity(intent3);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.aboutus:
                        Toast.makeText(MainActivity.this, "About uS", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder alertBuilder;
                        alertBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertBuilder.setIcon(R.drawable.smartphone);
                        alertBuilder.setTitle(Html.fromHtml("<b>" + getString(R.string.app_name) + "</b>"));
                        alertBuilder.setMessage("Monitor, record, and improve your heart health." +
                                "ake control of your cardiovascular well-being.\n\n" +
                                "Developd by:\n" +
                                "George Tonmoy Roy (1907114) \n" +
                                "                      &\n" +
                                "Masudur Rahman Rabby (1907113)");
                        alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = alertBuilder.create();
                        alertDialog.show();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.shareapp:
                        Intent intents = new Intent(Intent.ACTION_SEND);
                        intents.setType("text/plain");
                        String subject = "CardioCare";
                        String body = "https://drive.google.com/drive/u/0/folders/1KNuP217XIOJixc6bRryJncjvZMmjoxqP";
                        intents.putExtra(Intent.EXTRA_SUBJECT, subject);
                        intents.putExtra(Intent.EXTRA_TEXT, body);
                        startActivity(Intent.createChooser(intents, "Share With"));

                        drawerLayout.closeDrawers();
                        break;
                    case R.id.feedback:
                        Toast.makeText(MainActivity.this, "Feedback", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/email");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"georgetonmoy07@gmail.com", "roy1907114@kstud.kuet.ac.bd"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "FEEDBACK FORM THE USER");
                        intent.putExtra(Intent.EXTRA_TEXT, "Name= " + fullname + "\n Feedback= ");
                        startActivity(Intent.createChooser(intent, "Feedback With"));
                        drawerLayout.closeDrawers();

                        break;
                }

                return false;
            }
        });

        imageMenu = findViewById(R.id.imageMenu);
        imageMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        databaseReference1.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String dataid = dataSnapshot.getKey();

                    UserMeasurementDetails userTicketDetails = dataSnapshot.getValue(UserMeasurementDetails.class);
                    userTicketDetails.setDataid(dataid);
                    list.add(userTicketDetails);

                }
                progressDialog1.dismiss();
                viewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        viewAdapter.setOnItemClickListener(new UserMeasurementShowAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                UserMeasurementDetails userMeasurementDetails1 = list.get(position);
                String date = userMeasurementDetails1.getDate();
                String time = userMeasurementDetails1.getTimne();
                String systolic = userMeasurementDetails1.getSystolic();
                String diastolic = userMeasurementDetails1.getDayastolic();
                String heartRate = userMeasurementDetails1.getHeartrate();
                String comment = userMeasurementDetails1.getComment();

                Intent intent = new Intent(MainActivity.this, DataViewDetails.class);
                intent.putExtra("date", date);
                intent.putExtra("time", time);
                intent.putExtra("systolic", systolic);
                intent.putExtra("diastolic", diastolic);
                intent.putExtra("heartRate", heartRate);
                intent.putExtra("comment", comment);
                startActivity(intent);
            }
        });
        adddataid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert.show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alart = new AlertDialog.Builder(MainActivity.this);
        alart.setTitle("ALERT");
        alart.setMessage("Are you sure exit?");
        alart.setIcon(R.drawable.interrogation);
        alart.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alart.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "App not exit yet", Toast.LENGTH_SHORT).show();
            }
        });
        alart.setNeutralButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Continue your work", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog al = alart.create();
        al.show();


    }
}