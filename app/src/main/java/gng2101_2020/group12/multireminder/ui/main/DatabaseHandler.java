package gng2101_2020.group12.multireminder.ui.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import gng2101_2020.group12.multireminder.reminders.Reminder;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "reminderDB.db";
    public static final String TABLE_REMINDERS = "reminders";
    public static final String COLUMN_REMINDERNAME = "remindername";
    public static final String COLUMN_CATEGORY = "CATEGORY";
    public static final String COLUMN_REMINDERTIME = "remindertime";
    public static final String COLUMN_REMINDERDELAY = "reminderdelay";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("Hello", "I'm here");
        String CREATE_REMINDERS_TABLE = "CREATE TABLE " + TABLE_REMINDERS + "(" + COLUMN_REMINDERNAME + " TEXT PRIMARY KEY ," + COLUMN_CATEGORY + " TEXT," +
                COLUMN_REMINDERTIME + " TEXT," + COLUMN_REMINDERDELAY + " TEXT)";
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

    public ArrayList<String[]> readAll() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_REMINDERS;
        Cursor cursor = db.rawQuery(query, null);

        ArrayList<String[]> reminderList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String[] reminder = new String[4];
                reminder[0] = cursor.getString(0);
                reminder[1] = cursor.getString(1);
                reminder[2] = cursor.getString(2);
                reminder[3] = cursor.getString(3);
                reminderList.add(reminder);
            } while (cursor.moveToNext());
        }
        db.close();
        return reminderList;
    }
}
