package com.example.cardiocare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserMeasurementShowAdapter extends RecyclerView.Adapter<UserMeasurementShowAdapter.MyViewHolder> {
    Context context;
    ArrayList<UserMeasurementDetails> list;

    public UserMeasurementShowAdapter(Context context, ArrayList<UserMeasurementDetails> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.user_ticketshow_sample, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserMeasurementDetails userTicketDetails=list.get(position);
        holder.date.setText(userTicketDetails.getDate());
        holder.time.setText(userTicketDetails.getTimne());
        holder.systolic.setText(userTicketDetails.getSystolic());
        holder.diastolic.setText(userTicketDetails.getDayastolic());
        holder.heartrate.setText(userTicketDetails.getHeartrate());
        holder.comment.setText(userTicketDetails.getComment());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date, time, systolic, diastolic,heartrate,comment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateid);
            time = itemView.findViewById(R.id.timeid);
            systolic = itemView.findViewById(R.id.sytolicid);
            diastolic = itemView.findViewById(R.id.diastolicid);
            heartrate=itemView.findViewById(R.id.heartid);
            comment=itemView.findViewById(R.id.comid);
        }
    }

}
