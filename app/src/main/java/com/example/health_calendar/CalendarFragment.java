package com.example.health_calendar;

import static com.google.android.gms.common.util.CollectionUtils.listOf;
import static java.time.temporal.ChronoUnit.DAYS;

import android.database.Cursor;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.example.health_calendar.entites.DateSQL;
import com.example.health_calendar.entites.DateWithNotes;
import com.example.health_calendar.entites.Note;
import com.example.health_calendar.services.DataService;

public class CalendarFragment extends Fragment {

    public CalendarFragment() {
        // require a empty public constructor
    }

    private LocalDate curdate = null;
    private Calendar calendar;
    private int currentYear = 0;
    private int currentMonth = 0;
    private int currentDay = 0;

    private int daysIndex = 0;
    private int monthsIndex = 0;
    private int yearIndex = 0;

    private DataService dataService;
    private List<EditText> texts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.calendar_page, container, false);
        calendar = Calendar.getInstance();
        curdate = LocalDateTime.now().toLocalDate();

        currentDay = curdate.getDayOfMonth();
        currentMonth = curdate.getMonthValue();
        currentYear = curdate.getYear();

        com.applandeo.materialcalendarview.CalendarView calendarView = view.findViewById(R.id.calendarView);
        dataService = DataService.initial(this.getContext());
        final List<String> calendarStrings = new ArrayList<>();
        final int[] days = new int[31];
        final int[] months = new int[12];
        final int[] years = new int[10];

        final EditText textInputHeight = view.findViewById(R.id.textInputHeight);

        final EditText textInputWeight = view.findViewById(R.id.textInputWeight);

        final EditText textInputPulse = view.findViewById(R.id.textInputPulse);

        final EditText textInputPressure = view.findViewById(R.id.textInputPressure);

        final EditText textInputAppetite = view.findViewById(R.id.textInputAppetite);

        final EditText textInputSlepping = view.findViewById(R.id.textInputSlepping);

        final EditText textInputHealth = view.findViewById(R.id.textInputHealth);

        texts = new ArrayList<>(Arrays.asList(textInputHeight, textInputWeight, textInputPulse, textInputPressure
                , textInputAppetite, textInputSlepping, textInputHealth));

        final View dayInfo = view.findViewById(R.id.dayInfo);
        final View dayHeight = view.findViewById(R.id.dayHeight);
        final View dayWeight = view.findViewById(R.id.dayWeight);
        final View dayPulse = view.findViewById(R.id.dayPulse);
        final View dayPressure = view.findViewById(R.id.dayPressure);
        final View dayAppetite = view.findViewById(R.id.dayAppetite);
        final View daySlepping = view.findViewById(R.id.daySlepping);

        final View dayHealth = view.findViewById(R.id.dayHealth);

        Map<String, String> notes = null;
        try {
            notes = fetchDate(currentYear, currentMonth, currentDay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        checkdate(currentYear, currentMonth, currentDay);
        if (!(notes == null || notes.isEmpty())) {
            textInputHeight.setText(notes.get("HEIGHT") == null ? "Нет данных" : notes.get("HEIGHT"));
            textInputWeight.setText(notes.get("WEIGHT") == null ? "Нет данных" : notes.get("WEIGHT"));
            textInputPulse.setText(notes.get("PULSE") == null ? "Нет данных" : notes.get("PULSE"));
            textInputPressure.setText(notes.get("APPETITE") == null ? "Нет данных" : notes.get("APPETITE"));
            textInputAppetite.setText(notes.get("APPETITE") == null ? "Нет данных" : notes.get("PULSE"));
            textInputSlepping.setText(notes.get("SLEEP") == null ? "Нет данных" : notes.get("SLEEP"));
            textInputHealth.setText(notes.get("HEALTH") == null ? "Нет данных" : notes.get("HEALTH"));
        } else {
            textInputHeight.setText("Нет данных");
            textInputWeight.setText("Нет данных");
            textInputPulse.setText("Нет данных");
            textInputPressure.setText("Нет данных");
            textInputAppetite.setText("Нет данных");
            textInputSlepping.setText("Нет данных");
            textInputHealth.setText("Нет данных");
        }
        calendarView.setOnDayClickListener(new OnDayClickListener(){
            @Override
            public void onDayClick(EventDay eventDay) {

                Calendar cal = eventDay.getCalendar();

                int year = cal.get(1);

                int month = cal.get(2);

                    int dayOfMonth = cal.get(5);

                currentYear = year;
                currentMonth = (month + 1);
                currentDay = dayOfMonth;
                if (dayInfo.getVisibility() == View.INVISIBLE) {
                    dayInfo.setVisibility(View.VISIBLE);
                    dayHeight.setVisibility(View.VISIBLE);
                    dayWeight.setVisibility(View.VISIBLE);
                    dayPulse.setVisibility(View.VISIBLE);
                    dayPressure.setVisibility(View.VISIBLE);
                    dayAppetite.setVisibility(View.VISIBLE);
                    daySlepping.setVisibility(View.VISIBLE);
                    dayHealth.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < 31; i++) {
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
                Map<String, String> notes = null;
                try {
                    notes = fetchDate(currentYear, currentMonth, currentDay); // все записи за текущий день
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                checkdate(currentYear, currentMonth, currentDay);
                if (!(notes == null || notes.isEmpty())) {
                    textInputHeight.setText(notes.get("HEIGHT") == null ? "Нет данных" : notes.get("HEIGHT"));
                    textInputWeight.setText(notes.get("WEIGHT") == null ? "Нет данных" : notes.get("WEIGHT"));
                    textInputPulse.setText(notes.get("PULSE") == null ? "Нет данных" : notes.get("PULSE"));
                    textInputPressure.setText(notes.get("PRESSURE") == null ? "Нет данных" : notes.get("PRESSURE"));
                    textInputAppetite.setText(notes.get("APPETITE") == null ? "Нет данных" : notes.get("APPETITE"));
                    textInputSlepping.setText(notes.get("SLEEP") == null ? "Нет данных" : notes.get("SLEEP"));
                    textInputHealth.setText(notes.get("HEALTH") == null ? "Нет данных" : notes.get("HEALTH"));
                } else {
                    textInputHeight.setText("Нет данных");
                    textInputWeight.setText("Нет данных");
                    textInputPulse.setText("Нет данных");
                    textInputPressure.setText("Нет данных");
                    textInputAppetite.setText("Нет данных");
                    textInputSlepping.setText("Нет данных");
                    textInputHealth.setText("Нет данных");
                }

            }
        });
        


        final Button saveTextButton = view.findViewById(R.id.saveTextButton_1);
        saveTextButton.setOnClickListener(v -> {
            Map<String, String> notes1 = new HashMap<>();
            if (!textInputHeight.getText().toString().equals("Нет данных")) {
                notes1.put("HEIGHT", textInputHeight.getText().toString());
            }
            if (!textInputWeight.getText().toString().equals("Нет данных")) {
                notes1.put("WEIGHT", textInputWeight.getText().toString());
            }
            if (!textInputWeight.getText().toString().equals("Нет данных")) {
                notes1.put("WEIGHT", textInputWeight.getText().toString());
            }
            if (!textInputPressure.getText().toString().equals("Нет данных")) {
                notes1.put("PULSE", textInputPressure.getText().toString());
            }
            if (!textInputSlepping.getText().toString().equals("Нет данных")) {
                notes1.put("SLEEP", textInputSlepping.getText().toString());
            }
            if (!textInputHealth.getText().toString().equals("Нет данных")) {
                notes1.put("HEALTH", textInputHealth.getText().toString());
            }
            Log.d("dafa", notes1.toString() + " " + currentYear);
            insertorupdateDate(notes1, currentYear, currentMonth, currentDay);
        });
        return view;

    }

    private void insertorupdateDate(Map<String, String> notes, int year, int month, int date) {
        final DateSQL[] ref = new DateSQL[1];
        final long[] id = new long[1];
        List<Note> noteslist = new ArrayList<>();
        Runnable runnable = () -> {
            ref[0] = dataService.getDateNoNotes(year, month, date);
            if (ref[0] == null) {
                id[0] = dataService.insertDate(year, month, date);
            } else id[0] = ref[0].getId();
            for (String n :
                    notes.keySet()) {
                noteslist.add(new Note(n, notes.get(n), id[0]));
            }
            for (Note n :
                    noteslist) {
                Long a = dataService.insertOrUpdateNote(n);

            }

        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private Map<String, String> fetchDate(int year, int month, int date) throws InterruptedException {
        final DateWithNotes[] dateSQL = new DateWithNotes[1];
        Runnable runnable = () -> {
            dateSQL[0] = dataService.getDate(year, month, date);
        };
        Thread thread = new Thread(runnable);
        thread.start();
        thread.join();
        if (dateSQL[0] == null) {
            return null;
        }
        List<Note> notes = dateSQL[0].notes;
        Map<String, String> notesRes = new HashMap<>();
        for (Note n : notes) {
            notesRes.put(n.getType(), n.getValue());
        }
        return notesRes;

    }

    private void checkdate(int year, int month, int date) {

        LocalDate seldate = LocalDate.of(year, month, date);
        Log.d("afafa", seldate + " " + curdate);
        boolean noedit = seldate.isAfter(curdate) || DAYS.between(curdate, seldate) < -3;
        Log.d("afafa", " " + noedit);
        for (EditText t : texts) {
            t.setFocusable(!noedit);
            t.setFocusableInTouchMode(!noedit);
            t.setClickable(!noedit);
            t.setLongClickable(!noedit);
            t.setCursorVisible(!noedit);
        }

    }
}