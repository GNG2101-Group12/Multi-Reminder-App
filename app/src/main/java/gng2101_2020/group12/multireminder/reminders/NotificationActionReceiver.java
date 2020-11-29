package gng2101_2020.group12.multireminder.reminders;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import gng2101_2020.group12.multireminder.Helpers;

public class NotificationActionReceiver extends BroadcastReceiver {

    public static int COMPLETE = 0;
    public static int SNOOZE = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationID = intent.getIntExtra("notificationID", 0);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationID);

        Reminder reminder = new Reminder(intent.getBundleExtra("reminder"));

        ReminderCreator reminderCreator = new ReminderCreator(context);

        switch (intent.getIntExtra("action", -1)) {
            case 0:
                // TODO: Clear if from the DB

                break;
            case 1:
                // Send a new reminder delayed by the correct amount
                Calendar calendar = Calendar.getInstance();
                int delayTime = (int) Helpers.getMillisDuration(reminder.getReminderDelay());
                calendar.add(Calendar.MILLISECOND, delayTime);

                reminder.increaseSnoozesOccurred();

                reminderCreator.scheduleReminder(calendar, reminder);

                break;
            default:
                return;
        }
    }
}
