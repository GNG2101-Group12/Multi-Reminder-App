package gng2101_2020.group12.multireminder.reminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Calendar;
import java.util.UUID;

public class ReminderCreator {
    Context context;

    public ReminderCreator(Context context) {
        this.context = context;
    }

    public void scheduleReminder(Calendar calendar, Reminder reminder) {
        // Based on https://stackoverflow.com/a/12208261/1985387
        Intent intent = new Intent(context, NotifyReceiver.class);
        Bundle bundle = new Bundle();
        reminder.write(bundle);
        intent.putExtra("reminder", bundle);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, UUID.randomUUID().hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
