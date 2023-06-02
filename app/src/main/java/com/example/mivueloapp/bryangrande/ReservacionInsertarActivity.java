package com.example.mivueloapp.bryangrande;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

public class ReservacionInsertarActivity extends AppCompatActivity {

    private EditText idReservacionEditText, fechaReservacionEditText, numeroAsientoEditText, estadoReservacionEditText;
    private Button insertarButton;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservacion_insertar);

        idReservacionEditText = findViewById(R.id.id_reservacion_edit);
        fechaReservacionEditText = findViewById(R.id.fecha_reservacion_edit);
        numeroAsientoEditText = findViewById(R.id.numero_asiento_edit);
        estadoReservacionEditText = findViewById(R.id.estado_reservacion_edit);
        insertarButton = findViewById(R.id.insertarR_button);

        // Inicializar la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        insertarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idReservacion = idReservacionEditText.getText().toString();
                String fechaReservacion = fechaReservacionEditText.getText().toString();
                String numeroAsiento = numeroAsientoEditText.getText().toString();
                String estadoReservacion = estadoReservacionEditText.getText().toString();

                // Verificar si los campos están vacíos
                if (idReservacion.isEmpty() || fechaReservacion.isEmpty() || numeroAsiento.isEmpty() || estadoReservacion.isEmpty()){
                    Toast.makeText(ReservacionInsertarActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar el formato de la fecha de reservacion utilizando una expresión regular (dd/mm/yyyy)
                String fechaReservacionPattern = "^(?:3[01]|[12][0-9]|0?[1-9])([\\-/.])(0?[1-9]|1[1-2])\\1\\d{4}$";
                if (!fechaReservacion.matches(fechaReservacionPattern)) {
                    Toast.makeText(ReservacionInsertarActivity.this, "El formato de la fecha de reservacion no es válido. Debe ser dd/mm/yyyy ", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {

                // Insertar datos en la tabla 'reservacion'
                ContentValues values = new ContentValues();
                values.put("id_reservacion", idReservacion);
                values.put("fecha_reservacion", fechaReservacion);
                values.put("numero_asiento", numeroAsiento);
                values.put("estado_reservacion", estadoReservacion);

                long result = database.insert("reservacion", null, values);

                if (result == -1) {
                    Toast.makeText(ReservacionInsertarActivity.this, "Error al insertar los datos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReservacionInsertarActivity.this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
                }
            } catch (SQLiteException e) {
                    Toast.makeText(ReservacionInsertarActivity.this, "Error en la inserción: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}