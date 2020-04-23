package com.example.a2017067_assignment3;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AccelerometerAdaptor extends RecyclerView.Adapter<AccViewHolder>{

    List<Accelerometer> items;

    public AccelerometerAdaptor(List<Accelerometer> items){
        this.items = items;
    }


    @NonNull
    @Override
    public AccViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater infl = LayoutInflater.from(parent.getContext());
        return new AccViewHolder(infl, parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AccViewHolder holder, int position) {
        Accelerometer i = items.get(position);
        holder.bind(i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Accelerometer> items){
        this.items = items;
    }
}

class AccViewHolder extends RecyclerView.ViewHolder{
    private Accelerometer currItem;
    private TextView timestamp;
    private TextView xvalue;
    private TextView yvalue;
    private TextView zvalue;
    private ViewGroup temp;

    public AccViewHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.accelerometer_layout, parent, false));

        timestamp = (TextView) itemView.findViewById(R.id.timeStampAcc);
        xvalue = (TextView) itemView.findViewById(R.id.xvalue);
        yvalue = (TextView) itemView.findViewById(R.id.yvalue);
        zvalue = (TextView) itemView.findViewById(R.id.zvalue);
        this.temp = parent;
    }

    public void bind(Accelerometer i){
        currItem = i;
        timestamp.setText(i.getTimestamp());
        xvalue.setText(i.getXvalue());
        yvalue.setText(i.getYvalue());
        zvalue.setText(i.getZvalue());
    }

}
