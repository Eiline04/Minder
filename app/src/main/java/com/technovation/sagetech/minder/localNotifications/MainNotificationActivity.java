package com.technovation.sagetech.minder.localNotifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.technovation.sagetech.minder.R;

import java.text.DateFormat;
import java.util.Calendar;

public class MainNotificationActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    private TextView alarmInfoTextView;
    private Button buttonTimePicker;
    private Button buttonCancelAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notification);

        //HOOKS
        alarmInfoTextView = findViewById(R.id.nextAlarmTextView);
        buttonTimePicker = findViewById(R.id.timePickerButton);
        buttonCancelAlarm = findViewById(R.id.cancelAlarmButton);

        buttonTimePicker.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
        });

        buttonCancelAlarm.setOnClickListener(v -> cancelAlarm());
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendarInstance = Calendar.getInstance();
        calendarInstance.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendarInstance.set(Calendar.MINUTE, minute);
        calendarInstance.set(Calendar.SECOND, 1);
        updateTimeText(calendarInstance);
        startAlarm(calendarInstance);
    }

    private void updateTimeText(Calendar c) {
        String timeText = "Următoarea notificare va fi la ora: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        alarmInfoTextView.setText(timeText);
    }

    private void startAlarm(Calendar calendarInstance) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (calendarInstance.before(Calendar.getInstance())) {
            calendarInstance.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendarInstance.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
        alarmInfoTextView.setText("Notificarea a fost ștearsă!");
    }
}