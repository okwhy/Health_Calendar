package com.example.health_calendar;

import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExportFragment extends Fragment {

    public ExportFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.export_page, container, false);

        Button buttonCreateExcel = view.findViewById(R.id.buttonCreateExcel);
        buttonCreateExcel.setBackgroundColor(Color.GREEN);

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

        try {
            // Создание рабочей книги и листа
            WritableWorkbook workbook = Workbook.createWorkbook(new FileOutputStream(file));
            WritableSheet sheet = workbook.createSheet("Sheet1", 0);

            // Запись данных в ячейки
            for (int i = 1; i <= 7; i++) {
                Label label = new Label(0, i , String.valueOf(i));
                sheet.addCell(label);
            }
            setColumnWidth(sheet, 0);


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

            setColumnWidth(sheet, 1);



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
