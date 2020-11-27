package gng2101_2020.group12.multireminder.reminders;

import android.os.Bundle;

public class Reminder {
    private String name;
    private String category;
    private String reminderTime;
    private String reminderDelay;
    private int numberOfSnoozes;
    private int snoozesOccurred;
    private boolean completed;
    private String frequency;
    private String frequencyParameters;
    private int priority;

    public int getNumberOfSnoozes() {
        return numberOfSnoozes;
    }

    public void setNumberOfSnoozes(int numberOfSnoozes) {
        this.numberOfSnoozes = numberOfSnoozes;
    }

    public int getSnoozesOccurred() {
        return snoozesOccurred;
    }

    public void setSnoozesOccurred(int snoozesOccurred) {
        this.snoozesOccurred = snoozesOccurred;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFrequencyParameters() {
        return frequencyParameters;
    }

    public void setFrequencyParameters(String frequencyParameters) {
        this.frequencyParameters = frequencyParameters;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Reminder(String name, String category) {
        this.name = name;
        this.category = category;
    }

    protected Reminder(Bundle bundle) {
        this(bundle.getString("name"), bundle.getString("category"));

        setReminderTime(bundle.getString("reminderTime"));
        setReminderDelay(bundle.getString("reminderDelay"));
        setNumberOfSnoozes(bundle.getInt("numberOfSnoozes"));
        setSnoozesOccurred(bundle.getInt("snoozesOccurred"));
        setCompleted(bundle.getBoolean("completed"));
        setFrequency(bundle.getString("frequency"));
        setFrequencyParameters(bundle.getString("frequencyParameters"));
        setPriority(bundle.getInt("priority"));
    }

    public Reminder() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public String getReminderDelay() {
        return reminderDelay;
    }

    public void setReminderDelay(String reminderDelay) {
        this.reminderDelay = reminderDelay;
    }

    public void write(Bundle bundle) {
        bundle.putString("name", name);
        bundle.putString("category", category);
        bundle.putString("reminderTime", reminderTime);
        bundle.putString("reminderDelay", reminderDelay);
        bundle.putInt("numberOfSnoozes", numberOfSnoozes);
        bundle.putInt("snoozesOccurred", snoozesOccurred);
        bundle.putBoolean("completed", completed);
        bundle.putString("frequency", frequency);
        bundle.putString("frequencyParameters", frequencyParameters);
        bundle.putInt("priority", priority);
    }
}
