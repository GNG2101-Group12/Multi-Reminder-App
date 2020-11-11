package gng2101_2020.group12.multireminder.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
                View root = inflater.inflate(R.layout.list_fragment, container, false);

                ListView listItems = (ListView)root.findViewById(R.id.listView1);

                DatabaseHandler db = new DatabaseHandler(this.getActivity().getBaseContext());
                ArrayList<String[]> reminderList = db.readAll();

                // TODO: Display reminders from database
                //ArrayAdapter<String[]> allRemindersAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), R.layout.reminder_view_element, R.id.rootLayout, reminderList);
                //listItems.setAdapter(allRemindersAdapter);

        return root;
    }
}