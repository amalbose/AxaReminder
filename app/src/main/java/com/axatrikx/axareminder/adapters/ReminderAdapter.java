package com.axatrikx.axareminder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.axatrikx.axareminder.R;
import com.axatrikx.axareminder.model.Reminder;

import java.util.Collections;
import java.util.List;

/**
 * Created by amalbose on 4/4/2015.
 */
public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.AxaRemViewHolder> {

    private LayoutInflater inflator;

    List<Reminder> data = Collections.emptyList();
    private Context context;
    private ClickListener clickListner;

    public ReminderAdapter(Context context, List<Reminder> data) {
        inflator = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public ReminderAdapter.AxaRemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflator.inflate(R.layout.reminder_row, parent, false);
        return new AxaRemViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(ReminderAdapter.AxaRemViewHolder axaRemViewHolder, int position) {
        Reminder curRem = data.get(position);
        axaRemViewHolder.name.setText(curRem.getReminderName());
        axaRemViewHolder.date.setText(curRem.getDate());
        axaRemViewHolder.time.setText(curRem.getTime());
        axaRemViewHolder.recurrence.setText(curRem.getRecurrence());
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListner = clickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class AxaRemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView date;
        TextView time;
        TextView recurrence;

        Context context;

        public AxaRemViewHolder(Context context, View itemView) {
            super(itemView);
            this.context = context;
            name = (TextView) itemView.findViewById(R.id.reminder_name);
            date = (TextView) itemView.findViewById(R.id.reminder_date);
            time = (TextView) itemView.findViewById(R.id.reminder_time);
            recurrence = (TextView) itemView.findViewById(R.id.reminder_recc);
            itemView.findViewById(R.id.reminder_row).setOnClickListener(this);
            itemView.findViewById(R.id.row_detail).setOnClickListener(this);
//            title.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListner != null) {
                if(v.getId() == R.id.row_detail)
                    clickListner.itemOpened(v, getPosition());
                else
                    clickListner.itemClicked(v, getPosition());
            }
        }

    }

    public interface ClickListener {
        public void itemClicked(View view, int position);
        public void itemOpened(View view, int position);
    }
}