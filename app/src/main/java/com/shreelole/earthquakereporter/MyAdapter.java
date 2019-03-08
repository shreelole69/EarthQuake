package com.shreelole.earthquakereporter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyAdapter extends ArrayAdapter
{
    public MyAdapter(@NonNull Context context, List<My_Data> resource) {
        super(context,0, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.adapter_layout, parent, false);
        }
        final My_Data current = (My_Data) getItem(position);
        TextView magTextView = (TextView) listItemView.findViewById(R.id.adapter_textview_mag);
        TextView location_TextView_one = (TextView) listItemView.findViewById(R.id.adapter_textview_one);
        TextView location_TextView_two = (TextView) listItemView.findViewById(R.id.adapter_textview_second);
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.adapter_textview_date);
        TextView time = (TextView) listItemView.findViewById(R.id.adapter_textview_time);
        magTextView.setText(current.getMag());
        location_TextView_one.setText(current.getLocation_one().toString());
        location_TextView_two.setText(current.getLocation_two().toString());
        time.setText(current.getTime());
        dateTextView.setText(current.getDate());
        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(ContextCompat.getColor(getContext() , current.Color()));

        return listItemView;
    }
}
