package gng2101_2020.group12.multireminder.ui.main;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import gng2101_2020.group12.multireminder.R;


public class ReminderPreviewElementView extends LinearLayout {

    public ReminderPreviewElementView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ReminderPreviewElementView,
                0, 0);


        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.reminder_view_element, this);

        String colorString = a.getString(R.styleable.ReminderPreviewElementView_color);
        System.out.println(colorString);
        if (colorString != null) {
            int color = Color.parseColor(colorString);
            LinearLayout layout = findViewById(R.id.rootLayout);
            layout.setBackgroundColor(color);
        }
    }
}
