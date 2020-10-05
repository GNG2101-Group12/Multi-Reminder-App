package gng2101_2020.group12.multireminder.ui.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import gng2101_2020.group12.multireminder.R;


public class ReminderPreviewElementView extends LinearLayout {

    public ReminderPreviewElementView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.reminder_view_element, this);
    }
}
