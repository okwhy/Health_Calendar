package com.example.health_calendar;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ViewFlipper;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StatisticFragment extends Fragment {

    public StatisticFragment() {
        // require a empty public constructor
    }

    private LineDataSet createLineDataSet(List<Entry> entries, String label, int color) {
        LineDataSet dataSet = new LineDataSet(entries, label);
        dataSet.setColor(color);
        dataSet.setCircleColor(color);
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setValueTextSize(10f);
        dataSet.setDrawValues(false);
        return dataSet;
    }

    private void setupChart(LineChart chart) {

        Description description = new Description();
        description.setText("Артериальное давление");
        chart.setDescription(description);

        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);

        YAxis leftYAxis = chart.getAxisLeft();
        leftYAxis.setTextSize(12f);

    }

    private ViewFlipper simpleViewFlipper;
    Button btnNext;

    EditText dateOt;
    EditText dateDo;
    DatePickerDialog datePickerDialogOt;
    DatePickerDialog datePickerDialogDo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.statistic_page, container, false);

        // get The references of Button and ViewFlipper
        btnNext = (Button) rootView.findViewById(R.id.buttonNext);
        simpleViewFlipper = (ViewFlipper) rootView.findViewById(R.id.simpleViewFlipper); // get the reference of ViewFlipper
        // Declare in and out animations and load them using AnimationUtils class
        Animation in = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right);

        // set the animation type to ViewFlipper
        simpleViewFlipper.setInAnimation(in);
        simpleViewFlipper.setOutAnimation(out);

        // ClickListener for NEXT button
        // When clicked on Button ViewFlipper will switch between views
        // The current view will go out and next view will come in with specified animation
        btnNext.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                // show the next view of ViewFlipper
                simpleViewFlipper.showNext();
            }
        });

        //РОСТ
        BarChart barChartHeight = rootView.findViewById(R.id.barChartHeight);

        List<BarEntry> entriesHeight = new ArrayList<>();
        entriesHeight.add(new BarEntry(1, 176f));
        entriesHeight.add(new BarEntry(2, 176f));
        entriesHeight.add(new BarEntry(3, 175f));
        entriesHeight.add(new BarEntry(4, 177f));
        entriesHeight.add(new BarEntry(5, 177f));

        BarDataSet dataSetHeight = new BarDataSet(entriesHeight, "Рост");
        BarData barDataHeight = new BarData(dataSetHeight);
        barChartHeight.setData(barDataHeight);

        //ВЕС
        BarChart barChartWeight = rootView.findViewById(R.id.barChartWeight);
        List<BarEntry> entriesWeight = new ArrayList<>();
        entriesWeight.add(new BarEntry(1, 64f));
        entriesWeight.add(new BarEntry(2, 63f));
        entriesWeight.add(new BarEntry(3, 64f));
        entriesWeight.add(new BarEntry(4, 65f));
        entriesWeight.add(new BarEntry(5, 62f));

        BarDataSet dataSetWeight = new BarDataSet(entriesWeight, "Вес");
        BarData barDataWeight = new BarData(dataSetWeight);
        barChartWeight.setData(barDataWeight);

        //ЧСС
        BarChart barChartCHSS = rootView.findViewById(R.id.barChartCHSS);
        List<BarEntry> entriesCHSS = new ArrayList<>();
        entriesCHSS.add(new BarEntry(1, 71f));
        entriesCHSS.add(new BarEntry(2, 78f));
        entriesCHSS.add(new BarEntry(3, 76f));
        entriesCHSS.add(new BarEntry(4, 80f));
        entriesCHSS.add(new BarEntry(5, 75f));

        BarDataSet dataSetCHSS = new BarDataSet(entriesCHSS, "ЧСС");
        BarData barDataCHSS = new BarData(dataSetCHSS);
        barChartCHSS.setData(barDataCHSS);

        //АРТЕРИАЛЬНОЕ ДАВЛЕНИЕ
        LineChart lineChartPressure = rootView.findViewById(R.id.lineChartPressure);

        List<Entry> entriesUpperPressure = new ArrayList<>();
        List<Entry> entriesLowerPressure = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            entriesUpperPressure.add(new Entry(i, (float) (Math.random() * 20 + 110)));
            entriesLowerPressure.add(new Entry(i, (float) (Math.random() * 10 + 70)));
        }

        LineDataSet dataSetUpperPressure = createLineDataSet(entriesUpperPressure, "Верхнее давление", getResources().getColor(R.color.colorUpperPressure));
        LineDataSet dataSetLowerPressure = createLineDataSet(entriesLowerPressure, "Нижнее давление", getResources().getColor(R.color.colorLowerPressure));

        LineData lineData = new LineData(dataSetUpperPressure, dataSetLowerPressure);
        setupChart(lineChartPressure);
        lineChartPressure.setData(lineData);

        //СОН
        BarChart barChartSleep = rootView.findViewById(R.id.barChartSleep);
        List<BarEntry> entriesSleep = new ArrayList<>();
        entriesSleep.add(new BarEntry(1, 8f));
        entriesSleep.add(new BarEntry(2, 10f));
        entriesSleep.add(new BarEntry(3, 6f));
        entriesSleep.add(new BarEntry(4, 8f));
        entriesSleep.add(new BarEntry(5, 7f));

        BarDataSet dataSetSleep = new BarDataSet(entriesSleep, "Сон");
        BarData barDataSleep = new BarData(dataSetSleep);
        barChartSleep.setData(barDataSleep);


        dateOt = (EditText) rootView.findViewById(R.id.dateOt);
        dateDo = (EditText) rootView.findViewById(R.id.dateDo);

        dateOt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                int mMonth = c.get(Calendar.MONTH);
                int mYear = c.get(Calendar.YEAR);
                datePickerDialogOt = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateOt.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialogOt.show();
            }
        });

        dateDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                int mMonth = c.get(Calendar.MONTH);
                int mYear = c.get(Calendar.YEAR);
                datePickerDialogDo = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateDo.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialogDo.show();
            }
        });

        return rootView;
    }

}
