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
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExportFragment extends Fragment {

    public ExportFragment() {
        // require a empty public constructor
    }

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

        final int byear = 2023;
        final int bmonth = 12;
        final int bday = 18;

        final int ayear = 2023;
        final int amonth = 12;
        final int aday = 20;

        final DataService dataService = DataService.initial(this.getContext());

        final DateSQL[] ref = new DateSQL[1];
        final long[] id = new long[1];


        try {
            final List<DateWithNotes>[] dateWithNotes = new List[]{new ArrayList<>()};
            Runnable runnable = () -> {
                dateWithNotes[0] = dataService.getBetween(byear, ayear, bmonth, amonth, bday, aday);

            };

            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();

            List<DateSQL> date = new ArrayList<>();

            ArrayList<String> days = new ArrayList<>();

            for (DateWithNotes d : dateWithNotes[0]) {
                date.add(d.dateSQL);
            }

            List<String> dateFormat = new ArrayList<>();

            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            for (DateSQL d : date) {
                dateFormat.add(LocalDate.of(d.getYear(), d.getMonth(), d.getDay()).format(pattern));
            }


            days.add("10.09.2022");
            days.add("11.09.2022");
            days.add("12.09.2022");
            days.add("13.09.2022");
            days.add("14.09.2022");
            days.add("15.09.2022");
            days.add("16.09.2022");
            days.add("17.09.2022");

            ArrayList<String> height = new ArrayList<String>();

            height.add("176");
            height.add("176");
            height.add("176");
            height.add("176");
            height.add("176");
            height.add("176");
            height.add("176");
            height.add("176");

            ArrayList<String> weight = new ArrayList<String>();

            weight.add("64");
            weight.add("65");
            weight.add("65");
            weight.add("65");
            weight.add("64");
            weight.add("64");
            weight.add("65");
            weight.add("65");

            ArrayList<String> pulse = new ArrayList<String>();

            pulse.add("77");
            pulse.add("65");
            pulse.add("67");
            pulse.add("68");
            pulse.add("74");
            pulse.add("64");
            pulse.add("65");
            pulse.add("65");

            ArrayList<String> pressure = new ArrayList<String>();

            pressure.add("120/69");
            pressure.add("123/81");
            pressure.add("130/79");
            pressure.add("120/67");
            pressure.add("130/79");
            pressure.add("123/81");
            pressure.add("130/79");
            pressure.add("123/75");

            ArrayList<String> appetite = new ArrayList<String>();

            appetite.add("Хороший");
            appetite.add("Хороший");
            appetite.add("Плохой");
            appetite.add("Плохой");
            appetite.add("Плохой");
            appetite.add("Хороший");
            appetite.add("Хороший");
            appetite.add("Хороший");

            ArrayList<String> sleep = new ArrayList<String>();

            sleep.add("5");
            sleep.add("4");
            sleep.add("5");
            sleep.add("5");
            sleep.add("7");
            sleep.add("6");
            sleep.add("8");
            sleep.add("8");

            ArrayList<String> health = new ArrayList<String>();

            health.add("Хорошее");
            health.add("Хорошее");
            health.add("Удоволетворительное");
            health.add("Удоволетворительное");
            health.add("Удоволетворительное");
            health.add("Отличное");
            health.add("Хороший");
            health.add("Хороший");

            // Создание рабочей книги и листа
            WritableWorkbook workbook = Workbook.createWorkbook(new FileOutputStream(file));
            WritableSheet sheet = workbook.createSheet("Дневник здоровья", 0);

            // Запись данных в ячейки
            for (int i = 1; i <= 7; i++) {
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
            }


            // Сохранение рабочей книги
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
