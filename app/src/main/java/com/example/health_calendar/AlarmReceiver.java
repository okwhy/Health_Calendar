package com.example.health_calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.example.health_calendar.UserNotificationService;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Запуск службы при получении события от AlarmManager
        Intent serviceIntent = new Intent(context, UserNotificationService.class);

        context.startForegroundService(serviceIntent);
    }
}