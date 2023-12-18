package com.example.health_calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

import java.io.*;

public class MainActivity extends AppCompatActivity
        implements OnNavigationItemSelectedListener {

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

        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }
    FirstFragment firstFragment = new FirstFragment();
    SecondFragment secondFragment = new SecondFragment();
    ThirdFragment thirdFragment = new ThirdFragment();

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean
    onNavigationItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == Calendar_page) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, firstFragment)
                    .commit();
            return true;
        }
        else if (item.getItemId() == Statistic_page) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, secondFragment)
                    .commit();
            return true;
        }
        else if (item.getItemId() == Export_page) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flFragment, thirdFragment)
                    .commit();
            return true;
        }
        else
            return false;
    }
}
