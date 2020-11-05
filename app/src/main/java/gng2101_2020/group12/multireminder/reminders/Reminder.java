package gng2101_2020.group12.multireminder.reminders;

import android.os.Bundle;
import android.os.Parcel;

public class Reminder {
    private String name;
    private String category;

    public Reminder(String name, String category) {
        this.name = name;
        this.category = category;
    }

    protected Reminder(Bundle bundle) {
        this(bundle.getString("name"), bundle.getString("category"));
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

    public void write(Bundle bundle) {
        bundle.putString("name", name);
        bundle.putString("category", category);
    }
}
