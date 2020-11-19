package gng2101_2020.group12.multireminder.ui.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import gng2101_2020.group12.multireminder.reminders.Reminder;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "reminderDB.db";
    public static final String TABLE_REMINDERS = "reminders";
    public static final String COLUMN_REMINDERNAME = "ReminderName";
    public static final String COLUMN_CATEGORY = "Category";
    public static final String COLUMN_REMINDERTIME = "ReminderTime";
    public static final String COLUMN_REMINDERDELAY = "ReminderDelay";
    public static final String COLUMN_NUMBEROFSNOOZES = "NumberOfSnoozes";
    public static final String COLUMN_SNOOZESOCCURRED = "SnoozesOccurred";
    public static final String COLUMN_COMPLETED = "Completed";
    public static final String COLUMN_FREQUENCY = "Frequency";
    public static final String COLUMN_FREQUENCYPARAMETERS = "FrequencyParameters";
    public static final String COLUMN_PRIORITY = "Priority";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("Hello", "I'm here");
//        String CREATE_REMINDERS_TABLE = "CREATE TABLE " + TABLE_REMINDERS + "(" + COLUMN_REMINDERNAME + " TEXT PRIMARY KEY ," + COLUMN_CATEGORY + " TEXT," +
//                COLUMN_REMINDERTIME + " TEXT," + COLUMN_REMINDERDELAY + " TEXT)";
        String CREATE_REMINDERS_TABLE = "CREATE TABLE " + TABLE_REMINDERS +
                "(" +
                COLUMN_REMINDERNAME + " TEXT PRIMARY KEY," +
                COLUMN_CATEGORY + " TEXT," +
                COLUMN_REMINDERTIME + " TEXT," +
                COLUMN_REMINDERDELAY + " TEXT," +
                COLUMN_NUMBEROFSNOOZES + " INTEGER," +
                COLUMN_SNOOZESOCCURRED + " INTEGER," +
                COLUMN_COMPLETED + " BIT," +
                COLUMN_FREQUENCY + " TEXT," +
                COLUMN_FREQUENCYPARAMETERS  + " TEXT," +
                COLUMN_PRIORITY + " INTEGER)";
        db.execSQL(CREATE_REMINDERS_TABLE); // YOUR QUERIES TO EXECUTE WHEN APPLICATION IS FIRST INSTALLED
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // YOUR QUERY WHEN DB VERSION CHANGES
    }

    public void addReminder(Reminder reminder) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_REMINDERNAME, reminder.getName());
        values.put(COLUMN_CATEGORY, reminder.getCategory());
        values.put(COLUMN_REMINDERTIME, reminder.getReminderTime());
        values.put(COLUMN_REMINDERDELAY, reminder.getReminderDelay());
        values.put(COLUMN_NUMBEROFSNOOZES, reminder.getNumberOfSnoozes());
        values.put(COLUMN_SNOOZESOCCURRED, reminder.getSnoozesOccurred());
        if (reminder.isCompleted()) {
            values.put(COLUMN_COMPLETED, 1);
        } else {
            values.put(COLUMN_COMPLETED, 0);
        }
        values.put(COLUMN_FREQUENCY, reminder.getFrequency());
        values.put(COLUMN_FREQUENCYPARAMETERS, reminder.getFrequencyParameters());
        values.put(COLUMN_PRIORITY, reminder.getPriority());

        db.insert(TABLE_REMINDERS, null, values);
        db.close();
    }

    public Reminder findReminder(String reminderName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_REMINDERS + " WHERE " + COLUMN_REMINDERNAME + " = \"" + reminderName + "\"";
        Cursor cursor = db.rawQuery(query, null);

        Reminder reminder = new Reminder();
        if (cursor.moveToFirst()) {
            reminder.setName(cursor.getString(0));
            reminder.setCategory(cursor.getString(1));
            reminder.setReminderTime(cursor.getString(2));
            reminder.setReminderDelay(cursor.getString(3));
        } else {
            reminder = null;
        }
        db.close();
        return reminder;
    }

    public boolean updateReminder(String reminderName) {
        boolean result = false;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        try {
            Reminder reminder = findReminder(reminderName);
            values.put(COLUMN_REMINDERNAME, reminder.getName());
            values.put(COLUMN_CATEGORY, reminder.getCategory());
            values.put(COLUMN_REMINDERTIME, reminder.getReminderTime());
            values.put(COLUMN_REMINDERDELAY, reminder.getReminderDelay());
            values.put(COLUMN_NUMBEROFSNOOZES, reminder.getNumberOfSnoozes());
            values.put(COLUMN_SNOOZESOCCURRED, reminder.getSnoozesOccurred()+1);
            if (reminder.isCompleted()) {
                values.put(COLUMN_COMPLETED, 1);
            } else {
                values.put(COLUMN_COMPLETED, 0);
            }
            values.put(COLUMN_FREQUENCY, reminder.getFrequency());
            values.put(COLUMN_FREQUENCYPARAMETERS, reminder.getFrequencyParameters());
            values.put(COLUMN_PRIORITY, reminder.getPriority());

            db.update(TABLE_REMINDERS, values, COLUMN_REMINDERNAME + "=" + reminderName, null);
            result = true;
        } catch(Exception e) {
            Log.e("Error", "This reminder could not be found in the database");
        }

        return result;
    }

    public boolean deleteReminder(String reminderName) {
        boolean result = false;

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_REMINDERS + " WHERE " + COLUMN_REMINDERNAME + " = \"" + reminderName + "\"";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            db.delete(TABLE_REMINDERS, COLUMN_REMINDERNAME + " = " + "\"" + reminderName + "\"", null);
            cursor.close();
            result = true;
        }

        db.close();
        return result;
    }

    public List<Reminder> readAll() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_REMINDERS;
        Cursor cursor = db.rawQuery(query, null);

        List<Reminder> reminderList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder();
                reminder.setName(cursor.getString(0));
                reminder.setCategory(cursor.getString(1));
                reminder.setReminderTime(cursor.getString(2));
                reminder.setReminderDelay(cursor.getString(3));
                reminder.setNumberOfSnoozes(Integer.parseInt(cursor.getString(4)));
                reminder.setSnoozesOccurred(Integer.parseInt(cursor.getString(5)));
                if (Integer.parseInt(cursor.getString(6)) == 0) {
                    reminder.setCompleted(false);
                } else {
                    reminder.setCompleted(true);
                }
                reminder.setFrequency(cursor.getString(7));
                reminder.setFrequencyParameters(cursor.getString(8));
                reminder.setPriority(Integer.parseInt(cursor.getString(9)));
                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }
        db.close();
        return reminderList;
    }
}
