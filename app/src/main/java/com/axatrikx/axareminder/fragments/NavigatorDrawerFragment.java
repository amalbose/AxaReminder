package com.axatrikx.axareminder.fragments;


import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.axatrikx.axareminder.R;
import com.axatrikx.axareminder.common.Utils;
import com.axatrikx.axareminder.model.Reminder;
import com.axatrikx.axareminder.adapters.NavDrawerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigatorDrawerFragment extends Fragment implements NavDrawerAdapter.ClickListener {

    private static final String USER_LEARNED_PREF_KEY = "user_learned_pref";

    private RecyclerView recyclerView;
    private NavDrawerAdapter drawerAdapter;

    private ActionBarDrawerToggle drawerToggle;
    private DrawerLayout drawerLayout;

    private boolean userLearnedDrawer;
    private boolean fromSavedInstance;

    private View containerView;

    public NavigatorDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userLearnedDrawer = Boolean.valueOf(Utils.readPref(getActivity(), USER_LEARNED_PREF_KEY, "false"));
        if (savedInstanceState != null) {
            fromSavedInstance = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigator_drawer, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.drawerList);
        drawerAdapter = new NavDrawerAdapter(getActivity(), getAppDrawerData());
        drawerAdapter.setClickListener(this);
        recyclerView.setAdapter(drawerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }

    public void setUp(int fragment_id, DrawerLayout drwrLayout, final Toolbar toolbar) {
        containerView = getActivity().findViewById(fragment_id);
        drawerLayout = drwrLayout;
        drawerToggle = new ActionBarDrawerToggle(getActivity(), drwrLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (userLearnedDrawer) {
                    userLearnedDrawer = true;
                    Utils.saveToPref(getActivity(), USER_LEARNED_PREF_KEY, String.valueOf(userLearnedDrawer));
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

        };

        if (!userLearnedDrawer && !fromSavedInstance) {
            drawerLayout.openDrawer(containerView);
        }

        drawerLayout.setDrawerListener(drawerToggle);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                drawerToggle.syncState();
            }
        });
    }

    public static List<Reminder> getAppDrawerData() {
        List<Reminder> data = new ArrayList<Reminder>();
        String[] titles = {"Home", "All Reminders", "Birthdays", "Settings"};
        Reminder rem;
        for (String title : titles) {
            rem = new Reminder();
            rem.setIconId(R.drawable.ic_launcher);
            rem.setReminderName(title);
            data.add(rem);
        }
        return data;
    }

    @Override
    public void itemClicked(View view, int position) {
        Log.d("ABC", "Clicked" + position);
//        startActivity(new Intent(getActivity(), CreateReminder.class));
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new AllRemindersFragment();
                break;
            default:
                fragment = new UpcomingReminders();
                break;
        }


//        Bundle args = new Bundle();
//        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
//        fragment.setArguments(args);

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_fragment, fragment)
                .commit();

        Fragment fgmt = fragmentManager.findFragmentById(R.id.content_fragment);
        if (fgmt != null && fgmt.getView() != null)
            fgmt.getView().setBackgroundColor(Color.WHITE);

        drawerLayout.closeDrawers();
    }
}
