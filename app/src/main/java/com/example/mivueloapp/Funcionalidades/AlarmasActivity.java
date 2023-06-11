package com.example.mivueloapp.Funcionalidades;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.mivueloapp.R;
import java.util.ArrayList;
import java.util.Calendar;

public class AlarmasActivity extends Activity {
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Button btnSetAlarm;
    private ListView alarmListView;
    private ArrayAdapter<String> alarmListAdapter;
    private ArrayList<PendingIntent> pendingIntentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmas);

        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        btnSetAlarm = findViewById(R.id.btnSetAlarm);
        alarmListView = findViewById(R.id.alarmListView);

        // Inicializar lista de alarmas y adaptador
        pendingIntentsList = new ArrayList<>();
        alarmListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        alarmListView.setAdapter(alarmListAdapter);

        // Obtener alarmas configuradas previamente
        getSavedAlarms();

        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });

        // Eliminar una alarma al hacer clic en la lista
        alarmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PendingIntent pendingIntent = pendingIntentsList.get(position);
                cancelAlarm(pendingIntent);
            }
        });
    }

    private void setAlarm() {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        // Guardar el PendingIntent para futuras referencias
        pendingIntentsList.add(pendingIntent);

        // Agregar la alarma a la lista
        String alarmDateTime = String.format("%04d-%02d-%02d %02d:%02d", year, month + 1, day, hour, minute);
        alarmListAdapter.add(alarmDateTime);

        Toast.makeText(this, "Alarma configurada para " + alarmDateTime, Toast.LENGTH_SHORT).show();
    }

    private void cancelAlarm(PendingIntent pendingIntent) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        // Eliminar el PendingIntent de la lista
        int position = pendingIntentsList.indexOf(pendingIntent);
        if (position != -1) {
            pendingIntentsList.remove(position);
            alarmListAdapter.remove(alarmListAdapter.getItem(position));
        }

        Toast.makeText(this, "Alarma cancelada", Toast.LENGTH_SHORT).show();
    }

    private void getSavedAlarms() {
        // Aquí puedes obtener las alarmas previamente guardadas y agregarlas a la lista

    }
}
