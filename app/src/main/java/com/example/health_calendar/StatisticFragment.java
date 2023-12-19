package com.example.health_calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

public class StatisticFragment extends Fragment {

    public StatisticFragment() {
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.statistic_page, container, false);


        BarChart barChart = rootView.findViewById(R.id.barChart);

        // Создадим данные для диаграммы
        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 50f));
        entries.add(new BarEntry(2, 80f));
        entries.add(new BarEntry(3, 60f));
        entries.add(new BarEntry(4, 30f));
        entries.add(new BarEntry(5, 90f));

        BarDataSet dataSet = new BarDataSet(entries, "Пример столбчатой диаграммы");

        // Создадим объект данных и передадим набор данных
        BarData barData = new BarData(dataSet);

        // Установим данные в BarChart
        barChart.setData(barData);

        return rootView;
    }
}