package com.axatrikx.axareminder.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.axatrikx.axareminder.CreateReminder;
import com.axatrikx.axareminder.R;
import com.axatrikx.axareminder.adapters.ReminderAdapter;
import com.axatrikx.axareminder.extlib.SwipeableRecyclerViewTouchListener;
import com.axatrikx.axareminder.model.Reminder;
import com.axatrikx.axareminder.model.ReminderDataSource;
import com.software.shell.fab.ActionButton;

import java.util.List;

/**
 *
 */
public class AllRemindersFragment extends Fragment implements ReminderAdapter.ClickListener {

    private static final int CREATE_REMINDER_REQUEST = 1;
    private RecyclerView mRecyclerView;
    private ReminderAdapter mAdapter;
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

        getData();

        mAdapter = new ReminderAdapter(getActivity(), data);
        mAdapter.setClickListener(this);

        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(mRecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipe(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
//                                for (int position : reverseSortedPositions) {
//                                    data.remove(position);
//                                    mAdapter.notifyItemRemoved(position);
//                                }
//                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    data.remove(position);
                                    mAdapter.notifyItemRemoved(position);
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        });

        mRecyclerView.addOnItemTouchListener(swipeTouchListener);

        mRecyclerView.setAdapter(mAdapter);

        ActionButton actionButton = (ActionButton) layout.findViewById(R.id.action_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createReminderIntent = new Intent(v.getContext(), CreateReminder.class);
                startActivityForResult(createReminderIntent, CREATE_REMINDER_REQUEST);
            }
        });

        return layout;
    }

    public void getData() {
        ReminderDataSource dataSource = new ReminderDataSource(getActivity());
        dataSource.open();
        data = dataSource.getAllReminders();
        System.out.println(data);
        dataSource.close();
    }

    @Override
    public void itemClicked(View view, int position) {

        if (view.findViewById(R.id.reminder_date).getVisibility() == View.GONE) {
            view.findViewById(R.id.reminder_date).setVisibility(View.VISIBLE);
            view.findViewById(R.id.reminder_time).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.reminder_date).setVisibility(View.GONE);
            view.findViewById(R.id.reminder_time).setVisibility(View.GONE);
        }
        Toast.makeText(getActivity(), "Clicked " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void itemOpened(View view, int position) {
        Toast.makeText(getActivity(), "Selected " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_REMINDER_REQUEST) {
            // TODO return Reminder object
            // Make sure the request was successful
//            if (resultCode == RESULT_OK) {
//                // The user picked a contact.
//                // The Intent's data Uri identifies which contact was selected.
//
//                // Do something with the contact here (bigger example below)
//            }
            getData();
            mAdapter.notifyItemInserted(this.data.size());
            mAdapter.notifyDataSetChanged();

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this);
            ft.attach(this);
            ft.commit();
        }
    }
}
