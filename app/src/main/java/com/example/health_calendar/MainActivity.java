package com.example.health_calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.health_calendar.entites.Date;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int currentYear = 0;
    private int currentMonth = 0;
    private int currentDay = 0;

    private int daysIndex = 0;
    private int monthsIndex = 0;
    private int yearIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalendarView calendarView = findViewById(R.id.calendarView);

        final List<String> calendarStrings = new ArrayList<>();
        final int[] days= new int[31];

        CalendarView calendarView = findViewById(R.id.calendarView);

        final List<String> calendarStrings = new ArrayList<>();
        final int[] days= new int[30];

        final int[] months = new int[12];
        final int[] years = new int[10];

        final EditText textInput = findViewById(R.id.textInput);

        final View dayContent = findViewById(R.id.dayContent);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                currentYear = year;
                currentMonth = month;
                currentDay = dayOfMonth;
                if(dayContent.getVisibility() == View.GONE)
                {
                    dayContent.setVisibility(View.VISIBLE);
                }

                for(int i = 0; i < 30; i++)
                {
                    if(days[i] == currentDay)
                    {
                        for(int j = 0; j < 12; j++)
                        {
                            if(months[j] == currentMonth)
                            {
                                for(int k = 0; k < 10; k++)
                                {
                                    if(years[k] == currentYear)
                                    {
                                        textInput.setText(calendarStrings.get(i));
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
                textInput.setText("");
            }
        });


        final Button saveTextButton = findViewById(R.id.saveTextButton);



        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days[daysIndex] = currentDay;
                months[monthsIndex] = currentMonth;
                years[yearIndex] = currentYear;
                calendarStrings.add(daysIndex, textInput.getText().toString());
                daysIndex++;
                monthsIndex++;
                yearIndex++;
                textInput.setText("");

            }
        });
    }
}