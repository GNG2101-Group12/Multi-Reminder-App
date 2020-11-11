package gng2101_2020.group12.multireminder.ui.create_reminder;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.function.Function;

import gng2101_2020.group12.multireminder.Helpers;
import gng2101_2020.group12.multireminder.MainActivity;
import gng2101_2020.group12.multireminder.R;
import gng2101_2020.group12.multireminder.reminders.Reminder;
import gng2101_2020.group12.multireminder.reminders.ReminderCreator;
import gng2101_2020.group12.multireminder.ui.main.DatabaseHandler;
import gng2101_2020.group12.multireminder.ui.main.SectionsPagerAdapter;
import mobi.upod.timedurationpicker.TimeDurationPicker;
import mobi.upod.timedurationpicker.TimeDurationPickerDialog;

public class CreateReminderActivity extends AppCompatActivity {

    EditText taskTitle;
    EditText chooseTime;
    Spinner categorySpinner;

    ReminderCreator reminderCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);

        reminderCreator = new ReminderCreator(this);

        taskTitle = findViewById(R.id.taskTitle);
        chooseTime = findViewById(R.id.choose_time);
        categorySpinner = findViewById(R.id.categorySpinner);

        EditText chooseTime = findViewById(R.id.choose_time);
        chooseTime.setOnClickListener((view) -> openTimePicker(view));
        chooseTime.setOnFocusChangeListener((view, b) -> {if (b) openTimePicker(view);});

        EditText snoozeDuration = findViewById(R.id.snoozeDuration);
        snoozeDuration.setOnClickListener((view) -> openDurationPicker(view));
        snoozeDuration.setOnFocusChangeListener((view, b) -> {if (b) openDurationPicker(view);});

        ImageButton submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener((view) -> {
            String[] chosenTime = chooseTime.getText().toString().split(":");
            if (chosenTime.length != 2) {
                Toast.makeText(this, "Time is invalid", Toast.LENGTH_SHORT).show();
                return;
            }
            int minute = 0;
            int hour = 0;
            try {
                minute = Integer.parseInt(chosenTime[1]);
                hour = Integer.parseInt(chosenTime[0]);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Time is invalid", Toast.LENGTH_SHORT).show();
                return;
            }

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.HOUR, hour);

            System.out.println(System.currentTimeMillis() - calendar.getTimeInMillis());
//
            // TODO: Implement repeating reminders
//            calendar.add(Calendar.DAY_OF_MONTH, 1);

            Reminder reminder = new Reminder(taskTitle.getText().toString(), categorySpinner.getSelectedItem().toString());
            reminderCreator.scheduleReminder(calendar, reminder);

            // Save Data in SQL Database
            String item = categorySpinner.getSelectedItem().toString();
            reminder.setCategory(item);
            reminder.setReminderDelay(snoozeDuration.getText().toString());

            DatabaseHandler dbHandler = new DatabaseHandler(this);
            dbHandler.addReminder(reminder);

            finish();
        });

        Spinner spinner = (Spinner) findViewById(R.id.frequencySpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);
                LinearLayout weeklyLinearLayout = (LinearLayout) findViewById(R.id.WeeklyView);
                Spinner monthlySpinner = (Spinner) findViewById(R.id.MonthlyView);
                LinearLayout yearlyLinearLayout = (LinearLayout) findViewById(R.id.YearlyView);
                switch (item) {
                    case "Weekly":
                        weeklyLinearLayout.setVisibility(View.VISIBLE);
                        monthlySpinner.setVisibility(View.GONE);
                        yearlyLinearLayout.setVisibility(View.GONE);
                        break;
                    case "Monthly":
                        weeklyLinearLayout.setVisibility(View.GONE);
                        monthlySpinner.setVisibility(View.VISIBLE);
                        yearlyLinearLayout.setVisibility(View.GONE);
                        break;
                    case "Yearly":
                        weeklyLinearLayout.setVisibility(View.GONE);
                        monthlySpinner.setVisibility(View.GONE);
                        yearlyLinearLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void openTimePicker(View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(CreateReminderActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay,
                                          int minute) {
                        ((EditText) view).setText(Helpers.formatDate(hourOfDay, minute));
                    }
                }, mcurrentTime.get(Calendar.HOUR_OF_DAY),
                mcurrentTime.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    public void openDurationPicker(View view) {
        TimeDurationPickerDialog timePickerDialog = new TimeDurationPickerDialog(CreateReminderActivity.this,
                new TimeDurationPickerDialog.OnDurationSetListener() {
                    @Override
                    public void onDurationSet(TimeDurationPicker v, long duration) {
                        ((EditText) view).setText(Helpers.formatDuration(duration));
                    }
                }, 600000);
        timePickerDialog.show();
    }
}