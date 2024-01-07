package com.example.health_calendar;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.health_calendar.entites.DateSQL;
import com.example.health_calendar.entites.DateWithNotes;
import com.example.health_calendar.entites.Note;
import com.example.health_calendar.services.DataService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExportFragment extends Fragment {

    public ExportFragment() {
        // require a empty public constructor
    }
    private int byear;
    private int ayear;
    private int bmonth;
    private int amonth;
    private int bday;
    private int aday;

    EditText dateOt;
    EditText dateDo;
    DatePickerDialog datePickerDialogOt;
    DatePickerDialog datePickerDialogDo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.export_page, container, false);

        Button buttonCreateExcel = view.findViewById(R.id.buttonCreateExcel);
        buttonCreateExcel.setBackgroundColor(Color.GREEN);

        dateOt = (EditText) view.findViewById(R.id.dateOt);
        dateDo = (EditText) view.findViewById(R.id.dateDo);

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
                    }
                }, mYear, mMonth, mDay);
                datePickerDialogDo.show();
            }
        });

        buttonCreateExcel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createExcelFile();
            }
        });

        return view;
    }

    private void createExcelFile() {
        // Создание нового файла Excel
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String baseFileName = "Health_Diary";
        File file = getUniqueFile(downloadsDir, baseFileName, "xls");

        Log.d("Kd",byear+" "+ayear+" "+bmonth+" "+amonth+" "+bday+" "+aday);
        final DataService dataService = DataService.initial(this.getContext());

        final DateSQL[] ref = new DateSQL[1];
        final long[] id = new long[1];


        try {
            final List<DateWithNotes>[] dateWithNotes = new List[]{new ArrayList<>()};
            Runnable runnable = () -> {
                dateWithNotes[0] = dataService.getBetween(byear, ayear, bmonth, amonth, bday, aday);
//                Log.d("cok",""+dataService.getMediumByDateandtype("HEIGHT",byear, ayear, bmonth, amonth, bday, aday));
            };

            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
            if(dateWithNotes[0]==null || dateWithNotes[0].size()==0){
                Toast.makeText(getActivity(), "Записи за данный день отсутствуют", Toast.LENGTH_SHORT).show();
                return;
            }


            // Создание рабочей книги и листа
            WritableWorkbook workbook = Workbook.createWorkbook(new FileOutputStream(file));
            WritableSheet sheet = workbook.createSheet("Дневник здоровья", 0);

            // Запись данных в ячейки
            /* for (int i = 1; i <= 7; i++) {
                Label label = new Label(0, i, String.valueOf(i));
                sheet.addCell(label);
            }

            Label label = new Label(1, 1, "Рост (см)");
            sheet.addCell(label);

            label = new Label(1, 2, "Вес (кг)");
            sheet.addCell(label);

            label = new Label(1, 3, "ЧСС (уд/мин) в покое");
            sheet.addCell(label);

            label = new Label(1, 4, "Давление (А/Д)");
            sheet.addCell(label);

            label = new Label(1, 5, "Аппетит");
            sheet.addCell(label);

            label = new Label(1, 6, "Сон");
            sheet.addCell(label);

            label = new Label(1, 7, "Самочувствие");
            sheet.addCell(label);

            for (int i = 2; i < dateFormat.size() + 2; i++) {
                label = new Label(i, 0, dateFormat.get(i - 2));
                Log.d("вававав", dateFormat.get(i - 2));
                sheet.addCell(label);
            }
            for (int i = 2; i < 10; i++) {
                label = new Label(i, 1, height.get(i - 2));
                sheet.addCell(label);
            }

            for (int i = 2; i < 10; i++) {
                label = new Label(i, 2, weight.get(i - 2));
                sheet.addCell(label);
            }

            for (int i = 2; i < 10; i++) {
                label = new Label(i, 3, pulse.get(i - 2));
                sheet.addCell(label);
            }

            for (int i = 2; i < 10; i++) {
                label = new Label(i, 4, pressure.get(i - 2));
                sheet.addCell(label);
            }

            for (int i = 2; i < 10; i++) {
                label = new Label(i, 5, appetite.get(i - 2));
                sheet.addCell(label);
            }

            for (int i = 2; i < 10; i++) {
                label = new Label(i, 6, sleep.get(i - 2));
                sheet.addCell(label);
            }

            for (int i = 2; i < 10; i++) {
                label = new Label(i, 7, health.get(i - 2));
                sheet.addCell(label);
            }

            for (int i = 0; i < 10; i++) {
                setColumnWidth(sheet, i);
            }  */
                Label label = new Label(0, 0, "Дата");
                sheet.addCell(label);
                label = new Label(1, 0, "Рост (см)");
                sheet.addCell(label);

                label = new Label(2, 0, "Вес (кг)");
                sheet.addCell(label);

                label = new Label(3, 0, "ЧСС (уд/мин) в покое");
                sheet.addCell(label);

                label = new Label(4, 0, "Давление (А/Д)");
                sheet.addCell(label);

                label = new Label(5, 0, "Аппетит");
                sheet.addCell(label);

                label = new Label(6, 0, "Сон");
                sheet.addCell(label);

                label = new Label(7, 0, "Самочувствие");
                sheet.addCell(label);
                int rown=1;
            LocalDate refDate=LocalDate.of(ayear,amonth,aday);
            LocalDate endDate=LocalDate.of(byear,bmonth,bday);
            for(DateWithNotes dwn: dateWithNotes[0]){
                while (!refDate.isEqual(dwn.dateSQL.getAsLocalDate())){
                    DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("d-M-yyyy");
                    label = new Label(0,rown,refDate.format(dateTimeFormatter));
                    sheet.addCell(label);
                    for(int i=1;i<sheet.getColumns();i++){
                        label = new Label(i,rown,"-");
                        sheet.addCell(label);
                    }
                    refDate=refDate.minusDays(1);
                    Log.d("sdas",dwn.dateSQL.getDateString() +" afaga "+refDate);
                    rown++;
                }
//                Log.d("fd",tday+" "+tmonth+" "+tyear);
                label = new Label(0,rown,dwn.dateSQL.getDateString());
                sheet.addCell(label);
                List<Note> notes=new ArrayList<>();
                notes.addAll(dwn.notes);
                Map<String,String> cellval=new HashMap();
                Set<String> catgs=new HashSet<>();
                catgs.addAll(dataService.getNoteCategories());
                for(String s:catgs){
                    String value="-";
                    Note refNote=notes.stream().filter(x->x.getType().equals(s)).findFirst().orElse(null);
                    if (refNote!=null&&refNote.getValue()!="Нет данных")
                        value=refNote.getValue();
                    cellval.put(s,value);
                }
                    label = new Label(1, rown,cellval.get("HEIGHT"));
                    sheet.addCell(label);
                label = new Label(2, rown,cellval.get("WEIGHT"));
                sheet.addCell(label);
                label = new Label(3, rown,cellval.get("PULSE"));
                sheet.addCell(label);
                label = new Label(4, rown,cellval.get("PRESSURE"));
                sheet.addCell(label);
                label = new Label(5, rown,cellval.get("APPETITE"));
                sheet.addCell(label);
                label = new Label(6, rown,cellval.get("SLEEP"));
                sheet.addCell(label);
                label = new Label(7, rown,cellval.get("HEALTH"));
                sheet.addCell(label);
                rown++;
                refDate=refDate.minusDays(1);
            }
            while (!refDate.isEqual(endDate)){
                DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("d-M-yyyy");
                label = new Label(0,rown,refDate.format(dateTimeFormatter));
                sheet.addCell(label);
                for(int i=1;i<sheet.getColumns();i++){
                    label = new Label(i,rown,"-");
                    sheet.addCell(label);
                }
                rown++;
                refDate=refDate.minusDays(1);
            }
            for(int i=0;i<sheet.getColumns();i++){
                setColumnWidth(sheet,i);
            }
            workbook.write();
            workbook.close();

            Toast.makeText(getActivity(), "Файл Excel создан успешно", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Ошибка при создании файла Excel", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }

    private File getUniqueFile(File directory, String baseName, String extension) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = baseName + "_" + timeStamp + "." + extension;
        File file = new File(directory, fileName);

        // Проверка на уникальность имени файла, добавление числа, если файл уже существует
        int count = 1;
        while (file.exists()) {
            fileName = baseName + "_" + timeStamp + "_" + count + "." + extension;
            file = new File(directory, fileName);
            count++;
        }

        return file;
    }

    private void setColumnWidth(WritableSheet sheet, int columnIndex) {
        int maxContentLength = 0;

        // Находим максимальную длину данных в столбце
        for (int i = 0; i < sheet.getRows(); i++) {
            String content = sheet.getCell(columnIndex, i).getContents();
            maxContentLength = Math.max(maxContentLength, content.length());
        }

        // Устанавливаем ширину столбца на основе максимальной длины данных
        sheet.setColumnView(columnIndex, maxContentLength + 2); // Увеличиваем ширину на 2 символа для дополнительного пространства
    }

}
