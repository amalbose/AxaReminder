package com.axatrikx.axareminder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.axatrikx.axareminder.R;
import com.axatrikx.axareminder.adapters.ReminderAdapter;
import com.axatrikx.axareminder.model.Reminder;
import com.axatrikx.axareminder.model.ReminderDataSource;

import java.util.List;

/**
 *
 */
public class AllRemindersFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<Reminder> data;

    public AllRemindersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_all_reminders, container, false);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.remDrawerList);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<Reminder> data = getData();
        mAdapter = new ReminderAdapter(getActivity(), data);
        mRecyclerView.setAdapter(mAdapter);

        return layout;
    }

    public List<Reminder> getData() {
        ReminderDataSource dataSource = new ReminderDataSource(getActivity());
        dataSource.open();
        List<Reminder> data = dataSource.getAllReminders();
        dataSource.close();
        return data;
    }
}
