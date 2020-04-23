package com.example.a2017067_assignment3;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WifiAdaptor extends RecyclerView.Adapter<WifiViewHolder>{

    List<Wifi> items;

    public WifiAdaptor(List<Wifi> items){
        this.items = items;
    }

    @NonNull
    @Override
    public WifiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater infl = LayoutInflater.from(parent.getContext());
        return new WifiViewHolder(infl, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull WifiViewHolder holder, int position) {
        Wifi i = items.get(position);
        holder.bind(i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Wifi> items){
        this.items = items;
    }
}


class WifiViewHolder extends RecyclerView.ViewHolder{
    private Wifi currItem;
    private TextView timestamp;
    private TextView names;
    private TextView strengths;
    private ViewGroup temp;

    public WifiViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.wifi_layout, parent, false));

        timestamp = (TextView) itemView.findViewById(R.id.timeStampWifi);
        names = (TextView) itemView.findViewById(R.id.names);
        strengths = (TextView) itemView.findViewById(R.id.strengths);
        this.temp = parent;
    }

    public void bind(Wifi i){
        currItem = i;
        timestamp.setText(i.getTimestamp());
        names.setText(i.getAp_names());
        strengths.setText(i.getAp_strengths());
    }

}

