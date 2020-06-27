package com.example.parenting.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.parenting.R;

import java.util.Calendar;

public class CalendarFragment extends Fragment {

    private View CalendarView;
    private android.widget.CalendarView mCalendarView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CalendarView = inflater.inflate(R.layout.fragment_calendar, container, false);

        mCalendarView = (CalendarView) CalendarView.findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull android.widget.CalendarView calendarView, int year, int mounth, int day) {
                String date = day + "/" + (mounth + 1) + "/" + year;

            }
        });

        return CalendarView;
    }


}
