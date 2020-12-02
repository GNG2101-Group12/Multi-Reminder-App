package gng2101_2020.group12.multireminder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import gng2101_2020.group12.multireminder.reminders.Reminder;

public class ActiveReminderHolder {
    public static Queue<Reminder> reminderQueue = new LinkedList<>();
}
