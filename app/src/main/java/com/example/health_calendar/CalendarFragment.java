package com.example.health_calendar;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.health_calendar.entites.DateSQL;
import com.example.health_calendar.entites.DateWithNotes;
import com.example.health_calendar.entites.Note;
import com.example.health_calendar.services.DataService;

public class CalendarFragment extends Fragment{

    public CalendarFragment(){
        // require a empty public constructor
    }
    private LocalDate curdate =null;
    private Calendar calendar;
    private int currentYear =0 ;
    private int currentMonth = 0;
    private int currentDay = 0;

    private int daysIndex = 0;
    private int monthsIndex = 0;
    private int yearIndex = 0;

    private DataService dataService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.calendar_page, container, false);
        calendar=Calendar.getInstance();
        curdate = LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).toLocalDate();
        currentDay=curdate.getDayOfMonth();
        currentMonth=curdate.getMonthValue();
        currentYear=curdate.getYear();
        CalendarView calendarView = view.findViewById(R.id.calendarView_1);
        dataService=DataService.initial(this.getContext());
        final List<String> calendarStrings = new ArrayList<>();
        final int[] days = new int[31];
        final int[] months = new int[12];
        final int[] years = new int[10];

        final TextView textHeight = view.findViewById(R.id.textHeight);
        final EditText textInputHeight = view.findViewById(R.id.textInputHeight);

        final TextView textWeight = view.findViewById(R.id.textWeight);
        final EditText textInputWeight = view.findViewById(R.id.textInputWeight);

        final TextView textPulse = view.findViewById(R.id.textPulse);
        final EditText textInputPulse = view.findViewById(R.id.textInputPulse);

        final TextView textPressure = view.findViewById(R.id.textPressure);
        final EditText textInputPressure = view.findViewById(R.id.textInputPressure);

        final TextView textAppetite = view.findViewById(R.id.textAppetite);
        final EditText textInputAppetite = view.findViewById(R.id.textInputAppetite);

        final TextView textSlepping = view.findViewById(R.id.textSlepping);
        final EditText textInputSlepping = view.findViewById(R.id.textInputSlepping);

        final TextView textHealth = view.findViewById(R.id.textHealth);
        final EditText textInputHealth = view.findViewById(R.id.textInputHealth);

        final View dayInfo = view.findViewById(R.id.dayInfo);
        final View dayHeight = view.findViewById(R.id.dayHeight);
        final View dayWeight = view.findViewById(R.id.dayWeight);
        final View dayPulse = view.findViewById(R.id.dayPulse);
        final View dayPressure = view.findViewById(R.id.dayPressure);
        final View dayAppetite = view.findViewById(R.id.dayAppetite);
        final View daySlepping = view.findViewById(R.id.daySlepping);

        final View dayHealth = view.findViewById(R.id.dayHealth);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                currentYear = year;
                currentMonth = month;
                currentDay = dayOfMonth;
                List<Date> current_day =
                if (dayInfo.getVisibility() == View.INVISIBLE)
                {
                    dayInfo.setVisibility(View.VISIBLE);
                    dayHeight.setVisibility(View.VISIBLE);
                    dayWeight.setVisibility(View.VISIBLE);
                    dayPulse.setVisibility(View.VISIBLE);
                    dayPressure.setVisibility(View.VISIBLE);
                    dayAppetite.setVisibility(View.VISIBLE);
                    daySlepping.setVisibility(View.VISIBLE);
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
                                        textInputHealth.setText(calendarStrings.get(i + 6));
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

                textInputHealth.setText("");
            }
        });


        final Button saveTextButton = view.findViewById(R.id.saveTextButton_1);
        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days[daysIndex] = currentDay;
                months[monthsIndex] = currentMonth;
                years[yearIndex] = currentYear;
                calendarStrings.add(daysIndex, textInputHealth.getText().toString());
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
                textInputHealth.setText("");
            }
        });

        return view;
    }

    private Map<String,String> fetchDate(byte year, byte month, byte date){
        DateWithNotes dateSQL=dataService.getDate(year, month, date);
        List<Note> notes=dateSQL.notes;
        Map<String,String> notesRes=new HashMap<>();
        for(Note n:notes){
            notesRes.put(n.getType(),n.getValue());
        }
        return notesRes;
    }
    private void setNotes(Map<String,String>notes,byte year, byte month, byte date){
        boolean isold=DAYS.curdate LocalDate.of(year,month,date);
    }
}