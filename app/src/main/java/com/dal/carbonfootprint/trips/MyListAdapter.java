package com.dal.carbonfootprint.trips;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.dal.carbonfootprint.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private ArrayList<Trip> listdata;

    // RecyclerView recyclerView;
    public MyListAdapter(ArrayList<Trip> listdata) {
        this.listdata = listdata;
    }
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_trips, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Trip myListData = listdata.get(position);
        holder.Name.setText(listdata.get(position).name);
        holder.Date.setText(listdata.get(position).date);
        holder.Distance.setText(listdata.get(position).distance);
        String uri = "@drawable/myresource";  // where myresource (without the extension) is the file

        if(position%3==0)
        holder.image.setImageResource(R.drawable.ic_car2);
        else if(position%3==1) holder.image.setImageResource(R.drawable.ic_car3);
        else if(position%3==2) holder.image.setImageResource(R.drawable.ic_car4);

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Name;
        public TextView Date;
        public TextView Distance;
        public ImageView image;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.Name = (TextView) itemView.findViewById(R.id.name);
            this.Date = (TextView) itemView.findViewById(R.id.date);
            this.Distance= (TextView) itemView.findViewById(R.id.message);
            this.image=(ImageView) itemView.findViewById(R.id.carimage);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.usersRecycler);
        }
    }
}
