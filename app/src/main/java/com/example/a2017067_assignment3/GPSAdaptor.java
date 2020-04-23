package com.example.a2017067_assignment3;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GPSAdaptor extends RecyclerView.Adapter<GPSViewHolder>{
    List<GPS> items;

    public GPSAdaptor(List<GPS> items){
        this.items = items;
    }


    @NonNull
    @Override
    public GPSViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater infl = LayoutInflater.from(parent.getContext());
        return new GPSViewHolder(infl, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull GPSViewHolder holder, int position) {
        GPS i = items.get(position);
        holder.bind(i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<GPS> items){
        this.items = items;
    }
}


class GPSViewHolder extends RecyclerView.ViewHolder{
    private GPS currItem;
    private TextView timestamp;
    private TextView latitude;
    private TextView longitude;
    private ViewGroup temp;

    public GPSViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.gps_layout, parent, false));

        timestamp = (TextView) itemView.findViewById(R.id.timeStampGPS);
        latitude = (TextView) itemView.findViewById(R.id.latitude);
        longitude = (TextView) itemView.findViewById(R.id.longitude);
        this.temp = parent;
    }

    public void bind(GPS i){
        currItem = i;
        timestamp.setText(i.getTimestamp());
        latitude.setText(i.getLatitude().substring(0,5));
        longitude.setText(i.getLongitude().substring(0,5));
    }

}
