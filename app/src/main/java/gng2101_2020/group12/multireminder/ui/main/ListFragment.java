package gng2101_2020.group12.multireminder.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import gng2101_2020.group12.multireminder.R;
import gng2101_2020.group12.multireminder.reminders.Reminder;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListFragment extends Fragment {


    public static ListFragment newInstance() {
        ListFragment fragment = new ListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    ArrayList<Reminder> arrayList;
    ArrayAdapter<Reminder> arrayAdapter;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
                View root = inflater.inflate(R.layout.list_fragment, container, false);

//                DatabaseHandler db = new DatabaseHandler(this.getActivity().getBaseContext());
//                List<Reminder> reminders = db.readAll();
//
//                LinearLayout reminderList = (LinearLayout)root.findViewById(R.id.reminderlist);
//                for (Reminder reminder : reminders) {
//                    ReminderPreviewElementView reminderElement = new ReminderPreviewElementView(getActivity().getBaseContext(), reminder);
//                    reminderList.addView(reminderElement);
//                }


                // TODO: Display reminders from database
                //ArrayAdapter<String[]> allRemindersAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), R.layout.reminder_view_element, R.id.rootLayout, reminderList);
                //listItems.setAdapter(allRemindersAdapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        View root = getView();

        DatabaseHandler db = new DatabaseHandler(this.getActivity().getBaseContext());
        List<Reminder> reminders = db.readAll();

        LinearLayout reminderList = (LinearLayout)root.findViewById(R.id.reminderlist);
        reminderList.removeAllViews();
        for (Reminder reminder : reminders) {
            ReminderPreviewElementView reminderElement = new ReminderPreviewElementView(getActivity().getBaseContext(), reminder);
            reminderList.addView(reminderElement);
        }
    }
}