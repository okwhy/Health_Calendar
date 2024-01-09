package com.example.health_calendar;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.health_calendar.dtos.PressureValue;
import com.example.health_calendar.services.DataService;
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
import java.util.Arrays;
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
    TextView avgHeightText;
    TextView avgWeightText;
    TextView avgbpmText;
    TextView avgPressureText;
    TextView avgSleepText;
    TextView avgappetitText;
    TextView avgfeelingsText;
    BarChart barChartHeight;
    BarChart barChartWeight;
    BarChart barChartCHSS;
    LineChart lineChartPressure;
    BarChart barChartSleep;

    List<TextView>texts=new ArrayList<>(Arrays.asList(
            avgHeightText,
            avgWeightText,
            avgbpmText,
            avgSleepText,
            avgappetitText,
            avgfeelingsText,
            avgPressureText
            )
    );
    DatePickerDialog datePickerDialogOt;
    DatePickerDialog datePickerDialogDo;
    private float avgHeight;
    private List<Float> heights;
    private float avgWeight;
    private List<Float> weights;
    private float avgbpm;
    private List<Float> bpms;
    private float avgSleep;
    private List<Float> sleeps;
    private String avgappetit;
    private String avgfeelings;
    private int byear;
    private int ayear;
    private int bmonth;
    private int amonth;
    private int bday;
    private int aday;
    private DataService dataService;
    private boolean uptHeight=false;
    private boolean uptWeight=false;
    private boolean uptBPM=false;
    private boolean uptSleep=false;
    private boolean uptAppetit=false;
    private boolean uptFellings =false;
    private boolean uptPressure =false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.statistic_page, container, false);
        dataService = DataService.initial(this.getContext());
        // get The references of Button and ViewFlipper
        btnNext = (Button) rootView.findViewById(R.id.buttonNext);
        avgHeightText=rootView.findViewById(R.id.AverageValueHeight);
        avgWeightText=rootView.findViewById(R.id.AverageValueWeight);
        avgbpmText=rootView.findViewById(R.id.AverageValueCHSS);
        avgSleepText=rootView.findViewById(R.id.AverageValueSleep);
        avgfeelingsText=rootView.findViewById(R.id.AverageValueHealth);
        avgPressureText =rootView.findViewById(R.id.AverageValuePressure);
        avgappetitText=rootView.findViewById(R.id.AverageValueAppetite);
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
                Log.d("d",""+simpleViewFlipper.getDisplayedChild());

                int ref=simpleViewFlipper.getDisplayedChild();
                if(simpleViewFlipper.getDisplayedChild()==6){
                    ref=0;
                }
                try {
                    if(getbool(ref+1)){
                        update(ref,true);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                chooseBool(ref);
                simpleViewFlipper.showNext();
            }
        });

        //РОСТ
        barChartHeight = rootView.findViewById(R.id.barChartHeight);

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
        barChartWeight = rootView.findViewById(R.id.barChartWeight);
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
        barChartCHSS = rootView.findViewById(R.id.barChartCHSS);
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
        lineChartPressure = rootView.findViewById(R.id.lineChartPressure);

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
        barChartSleep = rootView.findViewById(R.id.barChartSleep);
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
                        byear=year;
                        bmonth=monthOfYear+1;
                        bday=dayOfMonth;
//                        update(simpleViewFlipper.getDisplayedChild(),false);
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
                        ayear=year;
                        amonth=monthOfYear+1;
                        aday=dayOfMonth;
                        try {
                            update(simpleViewFlipper.getDisplayedChild(),false);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        uptFellings =true;
                        uptAppetit=true;
                        uptBPM=true;
                        uptHeight=true;
                        uptSleep=true;
                        uptWeight=true;
                        uptPressure=true;
                        chooseBool(simpleViewFlipper.getDisplayedChild());
                    }
                }, mYear, mMonth, mDay);
                datePickerDialogDo.show();
            }
        });

        return rootView;
    }
    private void showinfoFloats(TextView text,BarChart barChart,String cat,String name) throws InterruptedException {
        List<Float> vals=new ArrayList<>();
        final float[] avg = new float[1];
        Runnable runnable = () -> {
            avg[0] =dataService.getMediumByDateandtype(cat,byear,ayear,bmonth,amonth,bday,aday);
            vals.addAll(dataService.getNotesByDateAndTypeF(cat,byear,ayear,bmonth,amonth,bday,aday));
        };
        Thread thread = new Thread(runnable);
        thread.start();
        thread.join();
        text.setText(Float.toString(avg[0]));

        List<BarEntry> entriesSleep = new ArrayList<>();
        for(int i=0;i<vals.size();i++){
            entriesSleep.add(new BarEntry(i+1,vals.get(i)));
        }
        BarDataSet dataSetSleep = new BarDataSet(entriesSleep, name);
        BarData barDataSleep = new BarData(dataSetSleep);
        barChart.setData(barDataSleep);
    }
    private void showinfoCommon(TextView text,String cat) throws InterruptedException {
        final String[] avg = new String[1];
        Runnable runnable = () -> {
            avg[0] = dataService.getMostCommonNote(cat, byear, ayear, bmonth, amonth, bday, aday);
        };
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        text.setText(avg[0]);
    }
    private void showinfoPressure(TextView text,LineChart lineChart,String cat) throws InterruptedException {
        List<PressureValue>vals=new ArrayList<>();
        Runnable runnable = () -> {
               vals.addAll(dataService.getPressureByDate(byear, ayear, bmonth, amonth, bday, aday));
        };
        Thread thread = new Thread(runnable);
        thread.start();
        thread.join();
        List<Entry> entriesUpperPressure = new ArrayList<>();
        List<Entry> entriesLowerPressure = new ArrayList<>();
        int highsSum=0;
        int lowsSum=0;
        for(int i=0;i<vals.size();i++){
            entriesUpperPressure.add(new Entry(i,vals.get(i).high));
            entriesLowerPressure.add(new Entry(i,vals.get(i).low));
            highsSum+=vals.get(i).high;
            lowsSum+=vals.get(i).low;
        }
        int reshigh=highsSum/vals.size();
        int reslow=lowsSum/vals.size();
        String vas=reshigh+"/"+reslow;
        text.setText(vas);
        LineDataSet dataSetUpperPressure = createLineDataSet(entriesUpperPressure, "Верхнее давление", getResources().getColor(R.color.colorUpperPressure));
        LineDataSet dataSetLowerPressure = createLineDataSet(entriesLowerPressure, "Нижнее давление", getResources().getColor(R.color.colorLowerPressure));

        LineData lineData = new LineData(dataSetUpperPressure, dataSetLowerPressure);
        setupChart(lineChart);
        lineChart.setData(lineData);
    }
    private void update(int page,boolean next) throws InterruptedException {
        List<String> cats=new ArrayList<>(Arrays.asList(
                "HEIGHT",
                "WEIGHT",
                "PULSE",
                "PRESSURE",
                "APPETITE",
                "SLEEP",
                "HEALTH"
        ));
        int realnumber=page;
        if(next){
            if(page==6){
                realnumber=0;
            }else{
                realnumber++;
            }
        }
        Log.d("dada",realnumber+" "+next);
        if(realnumber==3){
            showinfoPressure(avgPressureText,lineChartPressure,"PRESSURE");
        }else if(realnumber==4) {
            showinfoCommon(avgappetitText, cats.get(realnumber));
        }else if(realnumber==6){
            showinfoCommon(avgfeelingsText,cats.get(realnumber));
        }else if(realnumber==0){
            showinfoFloats(avgHeightText,barChartHeight, cats.get(realnumber),"Рост");
        }else if(realnumber==1){
            showinfoFloats(avgWeightText,barChartWeight, cats.get(realnumber),"Вес");
        }else if(realnumber==2){
            showinfoFloats(avgbpmText,barChartCHSS, cats.get(realnumber),"ЧСС");
        }else if(realnumber==5){
            showinfoFloats(avgSleepText,barChartCHSS, cats.get(realnumber),"Сон");
        }
    }
    private void chooseBool(int val){
        switch (val){
            case (0):uptHeight=false;break;
            case (1):uptWeight=false;break;
            case (2):uptBPM=false;break;
            case (3):uptPressure=false;break;
            case (4):uptAppetit=false;break;
            case (5):uptSleep=false;break;
            case (6):uptFellings=false;break;
            default:;break;
        }
    }
    private boolean getbool(int val){
        switch (val){
            case (0):return uptHeight;
            case (1):return uptWeight;
            case (2):return uptBPM;
            case (3):return uptPressure;
            case (4):return uptAppetit;
            case (5):return uptSleep;
            case (6):return uptFellings=false;
            default:return false;
        }
    }
}
