package com.example.cardiocare;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A RecyclerView adapter that displays a list of UserMeasurementDetails objects.
 *
 * @author George
 */
public class UserMeasurementShowAdapter extends RecyclerView.Adapter<UserMeasurementShowAdapter.MyViewHolder> {
    Context context;
    AlertDialog insert;
    private static ClickListener clickListener;
    ArrayList<UserMeasurementDetails> list;
    DatabaseReference databaseReference;
    FirebaseAuth mauth;

    /**
     * Creates a new UserMeasurementShowAdapter.
     *
     * @param context The context to use to inflate the item layout.
     * @param list    The list of UserMeasurementDetails objects to be displayed.
     */
    public UserMeasurementShowAdapter(Context context, ArrayList<UserMeasurementDetails> list) {
        this.context = context;
        this.list = list;
        databaseReference = FirebaseDatabase.getInstance().getReference("userdata");
    }

    /**
     * Creates a new ViewHolder object.
     *
     * @param parent The item layout to inflate.
     * @return A new ViewHolder object.
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.user_datashow_sample, parent, false);

        return new MyViewHolder(view);
    }

    /**
     * Binds the data from a UserMeasurementDetails object to the views in the corresponding item layout.
     *
     * @param holder   The ViewHolder object to bind the data to.
     * @param position The position of the UserMeasurementDetails object in the list.
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserMeasurementDetails userTicketDetails = list.get(position);
        holder.date.setText(userTicketDetails.getDate());
        holder.time.setText(userTicketDetails.getTimne());
        holder.systolic.setText(userTicketDetails.getSystolic());
        holder.diastolic.setText(userTicketDetails.getDayastolic());
        holder.heartrate.setText(userTicketDetails.getHeartrate());
        holder.comment.setText(userTicketDetails.getComment());
        holder.deletedayta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alart = new AlertDialog.Builder(v.getContext());
                alart.setTitle("ALERT");
                alart.setMessage("Are you sure to delete?");
                alart.setIcon(R.drawable.interrogation);
                alart.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UserMeasurementDetails userMeasurementDetails = list.get(holder.getAdapterPosition());
                        String key = userMeasurementDetails.getKey();
                        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "data Deleted", Toast.LENGTH_SHORT).show();
                                deleteItem(holder.getAdapterPosition());

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "sorry", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                alart.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog al = alart.create();
                al.show();

            }
        });
        holder.editdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                View view = LayoutInflater.from(context).inflate(R.layout.edit_measurement_data, null);
                EditText syseditid, dyaeditid, hearteditid;
                TextView dateid, timeid, sytolicid, diastolicid, heartid, comid;
                Button dataeditid;
                dataeditid = view.findViewById(R.id.dataeditid);
                syseditid = view.findViewById(R.id.syseditid);
                dyaeditid = view.findViewById(R.id.dyaeditid);
                hearteditid = view.findViewById(R.id.hearteditid);
                dateid = view.findViewById(R.id.dateid);
                timeid = view.findViewById(R.id.timeid);
                sytolicid = view.findViewById(R.id.sytolicid);
                diastolicid = view.findViewById(R.id.diastolicid);
                heartid = view.findViewById(R.id.heartid);
                comid = view.findViewById(R.id.comid);
                UserMeasurementDetails userMeasurementDetails = list.get(holder.getAdapterPosition());
                dateid.setText(userMeasurementDetails.getDate());
                timeid.setText(userMeasurementDetails.getTimne());
                sytolicid.setText(userMeasurementDetails.getSystolic());
                diastolicid.setText(userMeasurementDetails.getDayastolic());
                heartid.setText(userMeasurementDetails.getHeartrate());
                comid.setText(userMeasurementDetails.getComment());
                dataeditid.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onClick(View view1) {
                        ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                        progressDialog.setTitle("Loading");
                        progressDialog.setMessage("Please Wait");
                        progressDialog.show();
                        String systolicdata = syseditid.getText().toString().trim();
                        String diastolicdata = dyaeditid.getText().toString().trim();
                        String hearteditdata = hearteditid.getText().toString().trim();
                        String key = userMeasurementDetails.getKey();
                        String dateString = userMeasurementDetails.getDate();
                        String timeString = userMeasurementDetails.getTimne();
                        if (systolicdata.isEmpty()) {
                            syseditid.setError("Data Required");
                            progressDialog.dismiss();
                            syseditid.requestFocus();
                            return;
                        } else if (diastolicdata.isEmpty()) {
                            dyaeditid.setError("Data Required");
                            progressDialog.dismiss();
                            dyaeditid.requestFocus();
                            return;
                        } else if (hearteditdata.isEmpty()) {
                            hearteditid.setError("Data Required");
                            progressDialog.dismiss();
                            hearteditid.requestFocus();
                            return;
                        } else if (!systolicdata.matches("\\d+")) {
                            syseditid.setError("Valid data required");
                            progressDialog.dismiss();
                            syseditid.requestFocus();
                            return;
                        } else if (!diastolicdata.matches("\\d+")) {
                            dyaeditid.setError("Valid data required");
                            progressDialog.dismiss();
                            dyaeditid.requestFocus();
                            return;
                        } else if (!hearteditdata.matches("\\d+")) {
                            hearteditid.setError("Valid data required");
                            progressDialog.dismiss();
                            hearteditid.requestFocus();
                            return;
                        } else {
                            String comments = null;
                            int s, d;
                            s = Integer.parseInt(systolicdata);
                            d = Integer.parseInt(diastolicdata);
                            if (s < d) {
                                dyaeditid.setError("Systolic must be greater than Diastolic");
                                dyaeditid.requestFocus();
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

                            } else if ((s >= 90 && s <= 140) && (d > 90 && d < s)) {
                                comments = "High Pressure";

                            } else {
                                comments = "High Pressure :)";

                            }
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("userdata").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(key);
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("date", dateString);
                            updates.put("time", timeString);
                            updates.put("systolic", systolicdata);
                            updates.put("dayastolic", diastolicdata);
                            updates.put("heartrate", hearteditdata);
                            updates.put("comment", comments);
                            updates.put("key", key);
                            String finalComments = comments;
                            databaseReference1.updateChildren(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(context, "Data Modified", Toast.LENGTH_SHORT).show();
                                    insert.dismiss();
                                    progressDialog.dismiss();
                                    userMeasurementDetails.setSystolic(systolicdata);
                                    userMeasurementDetails.setDayastolic(diastolicdata);
                                    userMeasurementDetails.setHeartrate(hearteditdata);
                                    userMeasurementDetails.setComment(finalComments);

                                    // Notify the adapter of the data change for the updated item
                                    notifyItemChanged(holder.getAdapterPosition());
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Sorry", Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    }
                });

                builder.setView(view);
                insert = builder.create();
                insert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * The ViewHolder class for the UserMeasurementShowAdapter.
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView date, time, systolic, diastolic, heartrate, comment;
        ImageButton editdata, deletedayta;

        /**
         * Creates a new ViewHolder object.
         *
         * @param itemView The item layout view.
         */
        @SuppressLint("ResourceAsColor")
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateid);
            time = itemView.findViewById(R.id.timeid);
            systolic = itemView.findViewById(R.id.sytolicid);
            diastolic = itemView.findViewById(R.id.diastolicid);
            heartrate = itemView.findViewById(R.id.heartid);
            comment = itemView.findViewById(R.id.comid);
            deletedayta = itemView.findViewById(R.id.deletedaytaid);
            editdata = itemView.findViewById(R.id.editdataid);
            itemView.setOnClickListener(this);
            if (comment.getText() == "Normal Pressure") {
                comment.setTextColor(R.color.green);
            } else if (comment.getText() == "Low Pressure") {
                comment.setTextColor(R.color.blue);
            } else if (comment.getText() == "High Pressure") {
                comment.setTextColor(R.color.red);
            } else if (comment.getText() == "High Pressure :)") {
                comment.setTextColor(R.color.red);
            }

        }

        /**
         * Handles clicks on the items in the list.
         *
         * @param v The View object that was clicked.
         */
        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public interface ClickListener {
        void onItemClick(int position, View view);
    }

    /**
     * Sets the listener that will be notified when an item in the list is clicked.
     *
     * @param clickListener The listener to set.
     */
    public void setOnItemClickListener(ClickListener clickListener) {
        UserMeasurementShowAdapter.clickListener = clickListener;
    }

    /**
     * Removes a UserMeasurementDetails object from the list and notifies the adapter that the data has changed.
     *
     * @param position The position of the UserMeasurementDetails object to remove.
     */
    public void deleteItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
    }

    /**
     * Updates the list of UserMeasurementDetails objects and notifies the adapter that the data has changed.
     *
     * @param newData The new list of UserMeasurementDetails objects.
     */
    public void updateData(ArrayList<UserMeasurementDetails> newData) {
        list.addAll(newData);
        notifyDataSetChanged();
    }
}
