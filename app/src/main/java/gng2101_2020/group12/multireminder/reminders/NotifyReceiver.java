package gng2101_2020.group12.multireminder.reminders;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.UUID;

import gng2101_2020.group12.multireminder.ActiveReminderHolder;
import gng2101_2020.group12.multireminder.MainActivity;
import gng2101_2020.group12.multireminder.R;

public class NotifyReceiver extends BroadcastReceiver {

    private final String LOW_PRIORITY_REMINDER = "LOW_PRIORITY_REMINDER";
    private final String MEDIUM_PRIORITY_REMINDER = "MEDIUM_PRIORITY_REMINDER";
    private final String HIGH_PRIORITY_REMINDER = "HIGH_PRIORITY_REMINDER";

    private Context context;
    private Reminder reminder;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        Bundle reminderData = intent.getBundleExtra("reminder");
        if (reminderData == null) return;

        reminder = new Reminder(reminderData);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        StatusBarNotification[] activeNotifications = notificationManager.getActiveNotifications();
        if (activeNotifications.length > 0) {
            // Add this reminder to the queue so it can be shown next
            ActiveReminderHolder.reminderQueue.add(reminder);
            return;
        }

        createNotificationChannels();

        int notificationID = UUID.randomUUID().hashCode();

        Intent completeIntent = new Intent(context, NotificationActionReceiver.class);
        completeIntent.putExtra("notificationID", notificationID);
        completeIntent.putExtra("action", NotificationActionReceiver.COMPLETE);
        completeIntent.putExtra("reminder", intent.getBundleExtra("reminder"));
        PendingIntent completePendingIntent = PendingIntent.getBroadcast(context, UUID.randomUUID().hashCode(), completeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action complete = new NotificationCompat.Action.Builder(R.drawable.ic_baseline_timer_24,
                context.getString(R.string.complete), completePendingIntent)
                .build();

        Intent snoozeIntent = new Intent(context, NotificationActionReceiver.class);
        snoozeIntent.putExtra("notificationID", notificationID);
        snoozeIntent.putExtra("action", NotificationActionReceiver.SNOOZE);
        snoozeIntent.putExtra("reminder", intent.getBundleExtra("reminder"));
        PendingIntent snoozePendingIntent = PendingIntent.getBroadcast(context, UUID.randomUUID().hashCode(), snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action snooze = new NotificationCompat.Action.Builder(R.drawable.ic_baseline_timer_24,
                context.getString(R.string.snooze), snoozePendingIntent)
                .build();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, LOW_PRIORITY_REMINDER)
                .setSmallIcon(R.drawable.ic_baseline_check_24)
                .setContentTitle(reminder.getName())
                .setContentText("Category: " + reminder.getCategory())
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .addAction(complete)
                .setOngoing(true);

        if (reminder.getNumberOfSnoozes() > reminder.getSnoozesOccurred()) {
            notificationBuilder.addAction(snooze);
        }

        Notification notification = notificationBuilder.build();

        NotificationManagerCompat notificationCompatManager = NotificationManagerCompat.from(context);
        notificationCompatManager.notify(notificationID, notification);
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(LOW_PRIORITY_REMINDER, context.getString(R.string.low_priority_reminder), NotificationManager.IMPORTANCE_HIGH, true);
            createNotificationChannel(MEDIUM_PRIORITY_REMINDER, context.getString(R.string.medium_priority_reminder), NotificationManager.IMPORTANCE_HIGH, true);
            createNotificationChannel(HIGH_PRIORITY_REMINDER, context.getString(R.string.high_priority_reminder), NotificationManager.IMPORTANCE_MAX, true);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelID, String name, int importance, boolean enableVibration) {
        NotificationChannel channel = new NotificationChannel(channelID, name, importance);
        channel.enableVibration(enableVibration);
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }
}
