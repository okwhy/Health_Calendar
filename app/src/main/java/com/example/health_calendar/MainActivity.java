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
import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        final int[] days = new int[31];
        final int[] months = new int[12];
        final int[] years = new int[10];

        final TextView textHeight = findViewById(R.id.textHeight);
        final EditText textInputHeight = findViewById(R.id.textInputHeight);

        final TextView textWeight = findViewById(R.id.textWeight);
        final EditText textInputWeight = findViewById(R.id.textInputWeight);

        final TextView textPulse = findViewById(R.id.textPulse);
        final EditText textInputPulse = findViewById(R.id.textInputPulse);

        final TextView textPressure = findViewById(R.id.textPressure);
        final EditText textInputPressure = findViewById(R.id.textInputPressure);

        final TextView textAppetite = findViewById(R.id.textAppetite);
        final EditText textInputAppetite = findViewById(R.id.textInputAppetite);

        final TextView textSlepping = findViewById(R.id.textSlepping);
        final EditText textInputSlepping = findViewById(R.id.textInputSlepping);

        final TextView textExercise = findViewById(R.id.textExercise);
        final EditText textInputExercise = findViewById(R.id.textInputExercise);

        final TextView textHealth = findViewById(R.id.textHealth);
        final EditText textInputHealth = findViewById(R.id.textInputHealth);

        final View dayInfo = findViewById(R.id.dayInfo);
        final View dayHeight = findViewById(R.id.dayHeight);
        final View dayWeight = findViewById(R.id.dayWeight);
        final View dayPulse = findViewById(R.id.dayPulse);
        final View dayPressure = findViewById(R.id.dayPressure);
        final View dayAppetite = findViewById(R.id.dayAppetite);
        final View daySlepping = findViewById(R.id.daySlepping);
        final View dayExercise = findViewById(R.id.dayExercise);
        final View dayHealth = findViewById(R.id.dayHealth);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                currentYear = year;
                currentMonth = month;
                currentDay = dayOfMonth;

                if (dayInfo.getVisibility() == View.GONE &&
                        dayHeight.getVisibility() == View.GONE &&
                        dayWeight.getVisibility() == View.GONE &&
                        dayPulse.getVisibility() == View.GONE &&
                        dayPressure.getVisibility() == View.GONE &&
                        dayAppetite.getVisibility() == View.GONE &&
                        daySlepping.getVisibility() == View.GONE &&
                        dayExercise.getVisibility() == View.GONE &&
                        dayHealth.getVisibility() == View.GONE
                ) {
                    dayInfo.setVisibility(View.VISIBLE);
                    dayHeight.setVisibility(View.VISIBLE);
                    dayWeight.setVisibility(View.VISIBLE);
                    dayPulse.setVisibility(View.VISIBLE);
                    dayPressure.setVisibility(View.VISIBLE);
                    dayAppetite.setVisibility(View.VISIBLE);
                    daySlepping.setVisibility(View.VISIBLE);
                    dayExercise.setVisibility(View.VISIBLE);
                    dayHealth.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < 30; i++) {
                    if (days[i] == currentDay) {
                        for (int j = 0; j < 12; j++) {
                            if (months[j] == currentMonth) {
                                for (int k = 0; k < 10; k++) {
                                    if (years[k] == currentYear) {
                                        calendarView.setBackgroundColor(10);
                                        textInputHeight.setText(calendarStrings.get(i));
                                        textInputWeight.setText(calendarStrings.get(i + 1));
                                        textInputPulse.setText(calendarStrings.get(i + 2));
                                        textInputPressure.setText(calendarStrings.get(i + 3));
                                        textInputAppetite.setText(calendarStrings.get(i + 4));
                                        textInputSlepping.setText(calendarStrings.get(i + 5));
                                        textInputExercise.setText(calendarStrings.get(i + 6));
                                        textInputHealth.setText(calendarStrings.get(i + 7));
                                        return;
                                    }
                                }
                            }
                        }
                    }
                }
                textInputHeight.setText("");
                textInputWeight.setText("");
                textInputPulse.setText("");
                textInputPressure.setText("");
                textInputAppetite.setText("");
                textInputSlepping.setText("");
                textInputExercise.setText("");
                textInputHealth.setText("");
            }
        });


        final Button saveTextButton = findViewById(R.id.saveTextButton);



        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days[daysIndex] = currentDay;
                months[monthsIndex] = currentMonth;
                years[yearIndex] = currentYear;
                calendarStrings.add(daysIndex, textInputHealth.getText().toString());
                calendarStrings.add(daysIndex, textInputExercise.getText().toString());
                calendarStrings.add(daysIndex, textInputSlepping.getText().toString());
                calendarStrings.add(daysIndex, textInputAppetite.getText().toString());
                calendarStrings.add(daysIndex, textInputPressure.getText().toString());
                calendarStrings.add(daysIndex, textInputPulse.getText().toString());
                calendarStrings.add(daysIndex, textInputWeight.getText().toString());
                calendarStrings.add(daysIndex, textInputHeight.getText().toString());
                daysIndex++;
                monthsIndex++;
                yearIndex++;
                textInputHeight.setText("");
                textInputWeight.setText("");
                textInputPulse.setText("");
                textInputPressure.setText("");
                textInputAppetite.setText("");
                textInputSlepping.setText("");
                textInputExercise.setText("");
                textInputHealth.setText("");
            }
        });




    }
}