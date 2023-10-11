package com.example.ticketreservationsystem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketreservationsystem.models.Train;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    Context context;
    ArrayList<Train> list;

    public MyAdapter(Context context, ArrayList<Train> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Train trains = list.get(position);

        // Set the labels and data in the TextViews
        holder.trainID.setText("Train ID: " + trains.getTrainID());
        holder.deptTime.setText("Departure Time: " + trains.getDeptTime());
        holder.arrivalTime.setText("Arrival Time: " + trains.getArrivalTime());
        holder.startLoc.setText("Start Location: " + trains.getStartLoc());
        holder.destination.setText("Destination: " + trains.getDestination());

        // Handle button click
        holder.selectButton.setOnClickListener(view -> {
            // Launch CreateReservation and pass selected trainID
            Intent intent = new Intent(context, CreateReservation.class);
            intent.putExtra("trainID", trains.getTrainID());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView trainID, startLoc, destination, deptTime, arrivalTime;
        Button selectButton;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            trainID = itemView.findViewById(R.id.trainIDTextView);
            startLoc = itemView.findViewById(R.id.startLocTextView);
            destination = itemView.findViewById(R.id.destinationTextView);
            deptTime = itemView.findViewById(R.id.deptTimeTextView);
            arrivalTime = itemView.findViewById(R.id.arrivalTimeTextView);
            selectButton = itemView.findViewById(R.id.selectButton);
        }
    }
}
