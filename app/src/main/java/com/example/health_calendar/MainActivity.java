package com.example.health_calendar;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    public final int Calendar_page = R.id.homepage;
    public final int Statistic_page = R.id.statisticpage;
    public final int Export_page = R.id.export;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRepeatingAlarm();

        bottomNavigationView
                = (BottomNavigationView)findViewById(R.id.bottomNavigationView);

        bottomNavigationView
                .setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.homepage);
    }
    CalendarFragment calendarFragment = new CalendarFragment();
    StatisticFragment statisticFragment = new StatisticFragment();
    ExportFragment exportFragment = new ExportFragment();

    private void setRepeatingAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Создаем Intent для нашего BroadcastReceiver
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Устанавливаем интервал в 24 часа
        long repeatInterval = 60 * 60 * 1000; // час в миллисекундах

        // Устанавливаем повторяющееся событие
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + repeatInterval, repeatInterval, pendingIntent);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == Calendar_page) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, calendarFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == Statistic_page) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, statisticFragment)
                    .commit();
            return true;
        } else if (item.getItemId() == Export_page) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, exportFragment)
                    .commit();
            return true;
        } else
            return false;
    }
}
