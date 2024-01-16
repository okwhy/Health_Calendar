package com.example.health_calendar;

import static com.google.android.gms.common.util.CollectionUtils.listOf;
import static java.time.temporal.ChronoUnit.DAYS;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.fragment.app.Fragment;

import com.applandeo.materialcalendarview.CalendarView;
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

    private DataService dataService;
    private List<EditText> texts;

    private AlertDialog alertDialog;

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

        //Настройка календаря:

        calendarView.setSwipeEnabled(false);


        //------------------------------------------------------

        dataService = DataService.initial(this.getContext());

        try {
            addmarks(calendarView);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


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
            textInputPressure.setText(notes.get("PRESSURE") == null ? "Нет данных" : notes.get("PRESSURE")); // Артем
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

        calendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {

                try {
                    addmarks(calendarView);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        calendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {

                try {
                    addmarks(calendarView);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        String[] stateOfAppetite = {"Введите аппетит", "Отличный", "Хороший", "Средний", "Плохой", "Нет аппетита"};
        Spinner spinnerAppetite = view.findViewById(R.id.textInputAppetite_Spiner);
        ArrayAdapter<String> appetiteAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, stateOfAppetite);
        appetiteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAppetite.setAdapter(appetiteAdapter);

        String[] stateOfHealth = {"Введите самочувствие", "Отличное", "Хорошее", "Среднее", "Плохое", "Ужасное"};
        Spinner spinnerHealth = view.findViewById(R.id.textInputHealth_Spiner);
        ArrayAdapter<String> appetiteHealth = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, stateOfHealth);
        appetiteHealth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHealth.setAdapter(appetiteHealth);

        final Button saveTextButton = view.findViewById(R.id.saveTextButton_1);
        saveTextButton.setOnClickListener(v -> {
            Map<String, String> notes1 = new HashMap<>();
            if (!textInputHeight.getText().toString().equals("Нет данных")) {
                notes1.put("HEIGHT", textInputHeight.getText().toString());
            }
            if (!textInputWeight.getText().toString().equals("Нет данных")) {
                notes1.put("WEIGHT", textInputWeight.getText().toString());
            }
            if (!textInputPulse.getText().toString().equals("Нет данных")) {
                notes1.put("PULSE", textInputPulse.getText().toString());
            }
            if (!textInputPressure.getText().toString().equals("Нет данных")) {
                notes1.put("PRESSURE", textInputPressure.getText().toString());
            }
            if (!textInputAppetite.getText().toString().equals("Нет данных")) {
                notes1.put("APPETITE", textInputAppetite.getText().toString());
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

        // Подключаемся к кнопке в макете вашей активности
        Button showPopupButton = view.findViewById(R.id.showPopupButton);

        // Устанавливаем слушателя нажатия на кнопку
        showPopupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup();
            }
        });
        return view;
    }

    private void showPopup() {
        Context context = requireContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View popupView = inflater.inflate(R.layout.popup_layout, null);

        TextView popupText = popupView.findViewById(R.id.popupText);
        Button closeButton = popupView.findViewById(R.id.closeButton);

        popupText.setText("Приложение Health Calendar представляет собой инструмент для отслеживания основных показателей здоровья.\n" +
                "\n" +
                "На главном экране приложения расположен календарь, в котором можно установить, за какой день вносятся данные. Для сохранения введенных данных необходимо нажать на кнопку \"Сохранить\".\n" +
                "\n" +
                "Для удобства использования календаря в приложение были добавлены метки для дней. Дни, в которые данные не были своевременно введены помечаются красной полоской, а дни, в которые данные были внесены вовремя помечаются зеленой полоской.\n" +
                "\n" +
                "Приятного вам использования!");

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        builder.setView(popupView);
        alertDialog = builder.create();
        alertDialog.show();
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

    public void addmarks(CalendarView calendarView) throws InterruptedException {

        Calendar cal = calendarView.getCurrentPageDate();

        int year = cal.get(1);

        int month = cal.get(2)+1;

        int days_amount = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        int day = cal.get(5);

        boolean noedit;

        LocalDate seldate;
        List<Integer> daysWithData=new ArrayList<>();
        Runnable runnable= () -> {
            daysWithData.addAll(dataService.getDaysByMonth(year,month));
        };
        Thread thread = new Thread(runnable);
        thread.start();
        thread.join();
        List<EventDay> events = new ArrayList<>();

        for(int iter = 0; iter < days_amount; iter++){

            Calendar calendar_temp = Calendar.getInstance();

            seldate = LocalDate.of(year, month, (day+iter));

            noedit = seldate.isAfter(curdate) || DAYS.between(curdate, seldate) < -3;
            calendar_temp.set(year,month-1,day+iter);
            if(noedit){
                if(daysWithData.contains(day+iter)){
                    events.add(new EventDay(calendar_temp, R.drawable.ic_line2));
                }else{
                    events.add(new EventDay(calendar_temp, R.drawable.ic_line));
                }

            }
            if(daysWithData.contains(day+iter)){
                events.add(new EventDay(calendar_temp, R.drawable.ic_line2));
            }
        }

        calendarView.setEvents(events);
    }

    private void checkdate(int year, int month, int date) {

        LocalDate seldate = LocalDate.of(year, month, date);

        boolean noedit = seldate.isAfter(curdate) || DAYS.between(curdate, seldate) < -3;

        for (EditText t : texts) {
            t.setFocusable(!noedit);
            t.setFocusableInTouchMode(!noedit);
            t.setClickable(!noedit);
            t.setLongClickable(!noedit);
            t.setCursorVisible(!noedit);
        }

    }
}