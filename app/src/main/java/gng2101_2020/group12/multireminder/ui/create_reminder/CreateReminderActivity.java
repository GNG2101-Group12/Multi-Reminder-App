package gng2101_2020.group12.multireminder.ui.create_reminder;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.function.Function;

import gng2101_2020.group12.multireminder.Helpers;
import gng2101_2020.group12.multireminder.R;
import gng2101_2020.group12.multireminder.ui.main.SectionsPagerAdapter;
import mobi.upod.timedurationpicker.TimeDurationPicker;
import mobi.upod.timedurationpicker.TimeDurationPickerDialog;

public class CreateReminderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reminder);

        EditText chooseTime = findViewById(R.id.choose_time);
        chooseTime.setOnClickListener((view) -> openTimePicker(view));
        chooseTime.setOnFocusChangeListener((view, b) -> {if (b) openTimePicker(view);});

        EditText snoozeDuration = findViewById(R.id.snoozeDuration);
        //TODO: Fix this to actually be minutes
        snoozeDuration.setOnClickListener((view) -> openDurationPicker(view));
        snoozeDuration.setOnFocusChangeListener((view, b) -> {if (b) openDurationPicker(view);});
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