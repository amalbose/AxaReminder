package com.axatrikx.axareminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.axatrikx.axareminder.fragments.NavigatorDrawerFragment;
import com.software.shell.fab.ActionButton;


public class Home extends ActionBarActivity {

    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolBar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //fragment
        NavigatorDrawerFragment drawerFrag = (NavigatorDrawerFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_nav_drawer);

        drawerFrag.setUp(R.id.fragment_nav_drawer, (DrawerLayout) findViewById(R.id.drawerLayout),toolBar);
        initActionButtons();
        Log.d("ABC", "created");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Hello.. you selected " + item.getTitle(), Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initActionButtons() {
        ActionButton actionButton = (ActionButton) findViewById(R.id.action_button);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createReminderIntent = new Intent(v.getContext(),CreateReminder.class);
                startActivity(createReminderIntent);
            }
        });
    }

}
