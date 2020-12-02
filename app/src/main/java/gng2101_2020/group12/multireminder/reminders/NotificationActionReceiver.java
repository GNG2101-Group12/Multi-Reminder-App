package gng2101_2020.group12.multireminder.reminders;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Calendar;

import gng2101_2020.group12.multireminder.ActiveReminderHolder;
import gng2101_2020.group12.multireminder.Helpers;

public class NotificationActionReceiver extends BroadcastReceiver {

    public static final int COMPLETE = 0;
    public static final int SNOOZE = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationID = intent.getIntExtra("notificationID", 0);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationID);

        Reminder reminder = new Reminder(intent.getBundleExtra("reminder"));

        ReminderCreator reminderCreator = new ReminderCreator(context);

        // Trigger NotifyReceiver for next notification in the queue
        if (!ActiveReminderHolder.reminderQueue.isEmpty()) {
            Reminder nextReminder = ActiveReminderHolder.reminderQueue.remove();

            Intent nextReminderIntent = new Intent(context, NotifyReceiver.class);
            Bundle bundle = new Bundle();
            nextReminder.write(bundle);
            nextReminderIntent.putExtra("reminder", bundle);

            context.sendBroadcast(nextReminderIntent);
        }

        switch (intent.getIntExtra("action", -1)) {
            case COMPLETE:
                // TODO: Clear if from the DB

                break;
            case SNOOZE:
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
