package com.example.mivueloapp.Funcionalidades;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mivueloapp.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private EditText editEventType, editEventPriority;
    private Button btnAddEvent;
    private TextView textViewEvents;
    private static final String FILENAME = "events.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView = findViewById(R.id.calendarView);
        editEventType = findViewById(R.id.editEventType);
        editEventPriority = findViewById(R.id.editEventPriority);
        btnAddEvent = findViewById(R.id.btnAddEvent);
        textViewEvents = findViewById(R.id.textViewEvents);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
            }
        });

        btnAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eventType = editEventType.getText().toString().trim();
                String eventPriority = editEventPriority.getText().toString().trim();

                if (!eventType.isEmpty() && !eventPriority.isEmpty()) {
                    String event = "Tipo de evento: " + eventType + ", Prioridad: " + eventPriority;
                    saveEventToFile(event);
                    showAllEvents();
                    clearInputFields();
                } else {
                    Toast.makeText(CalendarActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        showAllEvents();
    }

    private void saveEventToFile(String event) {
        try {
            FileOutputStream fileOutputStream = openFileOutput(FILENAME, Context.MODE_APPEND);
            fileOutputStream.write(event.getBytes());
            fileOutputStream.write('\n');
            fileOutputStream.close();
            Toast.makeText(this, "Evento guardado exitosamente", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar el evento", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAllEvents() {
        try {
            FileInputStream fileInputStream = openFileInput(FILENAME);
            StringBuilder stringBuilder = new StringBuilder();
            int c;
            while ((c = fileInputStream.read()) != -1) {
                stringBuilder.append((char) c);
            }
            fileInputStream.close();
            textViewEvents.setText(stringBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al leer los eventos", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearInputFields() {
        editEventType.setText("");
        editEventPriority.setText("");
    }
}
