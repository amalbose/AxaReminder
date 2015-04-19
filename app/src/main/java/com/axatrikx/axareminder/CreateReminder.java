package com.axatrikx.axareminder;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.android.datetimepicker.time.RadialPickerLayout;
import com.android.datetimepicker.time.TimePickerDialog;
import com.axatrikx.axareminder.model.Reminder;
import com.axatrikx.axareminder.model.ReminderDataSource;
import com.doomonafireball.betterpickers.recurrencepicker.EventRecurrence;
import com.doomonafireball.betterpickers.recurrencepicker.EventRecurrenceFormatter;
import com.doomonafireball.betterpickers.recurrencepicker.RecurrencePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CreateReminder extends FragmentActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener,
        RecurrencePickerDialog.OnRecurrenceSetListener, AdapterView.OnItemSelectedListener {

    private static final String TIME_PATTERN = "HH:mm";

    private TextView newReminderDate;
    private TextView newReminderTime;
    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;

    private TextView repeatText;

    private EventRecurrence mEventRecurrence = new EventRecurrence();

    private static final String FRAG_TAG_RECUR_PICKER = "recurrencePickerDialogFragment";

    private String mRrule;

    private ReminderDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);

        dataSource = new ReminderDataSource(this);
        dataSource.open();

        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        newReminderDate = (TextView) findViewById(R.id.newReminderDate);
        newReminderDate.setFocusable(false);
        newReminderDate.setClickable(true);
        newReminderTime = (TextView) findViewById(R.id.newReminderTime);
        newReminderTime.setFocusable(false);
        newReminderTime.setClickable(true);

        update();

        addRecPicker();

        addTypeSpinner();
        addAlarmTypeSpinner();
    }

    private void addAlarmTypeSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.newReminderAlarmType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.alarm_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void addTypeSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.remTypespinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.reminder_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void addRecPicker() {
        ImageButton repeatButton;
        repeatText = (TextView) findViewById(R.id.newReminderRecurrence);
        repeatButton = (ImageButton) findViewById(R.id.repeatButton);

        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Bundle b = new Bundle();
                Time t = new Time();
                t.setToNow();
                b.putLong(RecurrencePickerDialog.BUNDLE_START_TIME_MILLIS, t.toMillis(false));
                b.putString(RecurrencePickerDialog.BUNDLE_TIME_ZONE, t.timezone);

                // may be more efficient to serialize and pass in EventRecurrence
                b.putString(RecurrencePickerDialog.BUNDLE_RRULE, mRrule);

                RecurrencePickerDialog rpd = (RecurrencePickerDialog) fm.findFragmentByTag(
                        FRAG_TAG_RECUR_PICKER);
                if (rpd != null) {
                    rpd.dismiss();
                }
                rpd = new RecurrencePickerDialog();
                rpd.setArguments(b);
                rpd.setOnRecurrenceSetListener(CreateReminder.this);
                rpd.show(fm, FRAG_TAG_RECUR_PICKER);
            }
        });
    }

    @Override
    public void onRecurrenceSet(String rrule) {
        mRrule = rrule;
        if (mRrule != null) {
            mEventRecurrence.parse(mRrule);
        }
        populateRepeats();
    }

    @Override
    public void onResume() {
        // Example of reattaching to the fragment
        super.onResume();
        RecurrencePickerDialog rpd = (RecurrencePickerDialog) getSupportFragmentManager().findFragmentByTag(
                FRAG_TAG_RECUR_PICKER);
        if (rpd != null) {
            rpd.setOnRecurrenceSetListener(this);
        }
    }

    private void populateRepeats() {

        String repeatString = "";
        if (!TextUtils.isEmpty(mRrule)) {
            repeatString = EventRecurrenceFormatter.getRepeatString(this, getResources(), mEventRecurrence, true);
        }
        repeatText.setText(repeatString);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_reminder, menu);
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
            return true;
        }

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.newReminderDate:
                DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
                break;
            case R.id.newReminderTime:
                TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show(getFragmentManager(), "timePicker");
                break;
            case R.id.createButton:
                Reminder rem = new Reminder();
                rem.setReminderName(((EditText) findViewById(R.id.newReminderTitle)).getText().toString());
                rem.setDate(((EditText) findViewById(R.id.newReminderDate)).getText().toString());
                rem.setTime(((EditText) findViewById(R.id.newReminderTime)).getText().toString());
                rem.setRecurrence(((TextView) findViewById(R.id.newReminderRecurrence)).getText().toString());
                rem.setType(((Spinner) findViewById(R.id.remTypespinner)).getSelectedItem().toString());
                rem.setNote(((EditText) findViewById(R.id.newReminderNote)).getText().toString());
                rem.setAlarmType(((Spinner) findViewById(R.id.newReminderAlarmType)).getSelectedItem().toString());

                Reminder newRem = dataSource.createReminder(rem);

                if (newRem != null) {
                    finish();
                }
                else
                    Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();

                break;
        }
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        update();
    }


    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        update();
    }

    private void update() {
        newReminderDate.setText(dateFormat.format(calendar.getTime()));
        newReminderTime.setText(timeFormat.format(calendar.getTime()));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(this, "Hello.. you selected " + view.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
