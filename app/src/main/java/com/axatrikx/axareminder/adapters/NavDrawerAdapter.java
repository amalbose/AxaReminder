package com.axatrikx.axareminder.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.axatrikx.axareminder.R;
import com.axatrikx.axareminder.model.Reminder;

import java.util.Collections;
import java.util.List;

/**
 * Reminder Nav Drawer Adapter
 * Created by amalbose on 3/21/2015.
 */
public class NavDrawerAdapter extends RecyclerView.Adapter<NavDrawerAdapter.AxaRemViewHolder> {

    private LayoutInflater inflator;

    List<Reminder> data = Collections.emptyList();
    private Context context;
    private ClickListener clickListner;

    public NavDrawerAdapter(Context context, List<Reminder> data) {
        inflator = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public AxaRemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.nav_drawer_row, parent, false);
        return new AxaRemViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(AxaRemViewHolder axaRemViewHolder, int position) {
        Reminder curRem = data.get(position);
        axaRemViewHolder.title.setText(curRem.getReminderName());
        axaRemViewHolder.icon.setImageResource(curRem.getIconId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListner = clickListener;
    }

    class AxaRemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView icon;
        Context context;

        public AxaRemViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            title = (TextView) itemView.findViewById(R.id.nav_drawer_txt);
            icon = (ImageView) itemView.findViewById(R.id.nav_drawer_img);
            itemView.findViewById(R.id.nav_drawer_row).setOnClickListener(this);
//            title.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListner != null) {
                clickListner.itemClicked(v, getPosition());
            }
        }

    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
    }
}
