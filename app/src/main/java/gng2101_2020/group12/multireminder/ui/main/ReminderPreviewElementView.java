package gng2101_2020.group12.multireminder.ui.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import gng2101_2020.group12.multireminder.R;
import gng2101_2020.group12.multireminder.reminders.Reminder;


public class ReminderPreviewElementView extends LinearLayout {

    public ReminderPreviewElementView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ReminderPreviewElementView,
                0, 0);


        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.reminder_view_element, this);

        String colorString = a.getString(R.styleable.ReminderPreviewElementView_color);
        if (colorString != null) {
            int color = Color.parseColor(colorString);
            LinearLayout layout = findViewById(R.id.rootLayout);
            layout.setBackgroundColor(color);
        }

        String reminderText = a.getString(R.styleable.ReminderPreviewElementView_reminderText);
        if (reminderText != null) {
            CheckBox layout = findViewById(R.id.enableReminder);
            layout.setText(reminderText);
        }

        int priority = a.getInt(R.styleable.ReminderPreviewElementView_priority, 3);
        TextView priorityTextView = findViewById(R.id.reminderPriority);
        priorityTextView.setText(getPriorityString(priority));

        String reminderTime = a.getString(R.styleable.ReminderPreviewElementView_reminderTime);
        if (reminderTime != null) {
            TextView layout = findViewById(R.id.reminderTime);
            layout.setText(reminderTime);
        }

        String delayTime = a.getString(R.styleable.ReminderPreviewElementView_delayTime);
        if (delayTime != null) {
            TextView layout = findViewById(R.id.delayTime);
            layout.setText(delayTime);
        }
    }

    public ReminderPreviewElementView(Context context, Reminder reminder) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.reminder_view_element, this);

//        String colorString = a.getString(R.styleable.ReminderPreviewElementView_color);
//        if (colorString != null) {
//            int color = Color.parseColor(colorString);
//            LinearLayout layout = findViewById(R.id.rootLayout);
//            layout.setBackgroundColor(color);
//        }
        String categoryText = reminder.getCategory();
        String colorString;
        if (categoryText.equals("Medicine")) {
            colorString = "#FF9AA2";
        } else if (categoryText.equals("Food")) {
            colorString = "#FFDAC1";
        } else if (categoryText.equals("Travel")) {
            colorString = "#BFEAD7";
        } else {
            colorString = "#C7CEEA";
        }
        if (colorString != null) {
            int color = Color.parseColor(colorString);
            LinearLayout layout = (LinearLayout) findViewById(R.id.rootLayout);
            layout.setBackgroundColor(color);
        }

        String reminderText = reminder.getName();
        if (reminderText != null) {
            CheckBox layout = findViewById(R.id.enableReminder);
            layout.setText(reminderText);
        }

        int priority = reminder.getPriority();
        TextView priorityTextView = findViewById(R.id.reminderPriority);
        priorityTextView.setText(getPriorityString(priority));

        String reminderTime = reminder.getReminderTime();
        if (reminderTime != null) {
            TextView layout = findViewById(R.id.reminderTime);
            layout.setText(reminderTime);
        }

        String delayTime = reminder.getReminderDelay();
        if (delayTime != null) {
            TextView layout = findViewById(R.id.delayTime);
            layout.setText(delayTime);
        }
    }

    public String getPriorityString(int priority) {
        switch (priority) {
            case 0:
                return "";
            case 1:
                return "!";
            case 2:
                return "!!";
            case 3:
                return "!!!";
            default:
                return "";
        }
    }
}
