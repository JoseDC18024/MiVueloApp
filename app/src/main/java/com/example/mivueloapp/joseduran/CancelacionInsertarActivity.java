package com.example.mivueloapp.joseduran;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;
import com.example.mivueloapp.joselucero.VueloInsertarActivity;

public class CancelacionInsertarActivity extends AppCompatActivity {
    private EditText etIdCancelacion;
    private EditText etMotivoCancelacion;
    private EditText etHastaFecha;
    private EditText etDesdeFecha;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelacion_insertar);

        // Obtener referencias a los EditText
        etIdCancelacion = findViewById(R.id.etIdCancelacion);
        etMotivoCancelacion = findViewById(R.id.etMotivoCancelacion);
        etHastaFecha = findViewById(R.id.etHastaFecha);
        etDesdeFecha = findViewById(R.id.etDesdeFecha);

        // Obtener una instancia de la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
    }

    public void insertarCancelacion(View view) {
        String idCancelacion = etIdCancelacion.getText().toString().trim();
        String motivoCancelacion = etMotivoCancelacion.getText().toString().trim();
        String hastaFecha = etHastaFecha.getText().toString().trim();
        String desdeFecha = etDesdeFecha.getText().toString().trim();

        // Verificar si los campos están vacíos
        if (idCancelacion.isEmpty() || motivoCancelacion.isEmpty() || hastaFecha.isEmpty() || desdeFecha.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar el formato de la fecha hasta utilizando una expresión regular (dd/mm/yyyy)
        String fechaSalidaPattern = "^(?:3[01]|[12][0-9]|0?[1-9])([\\-/.])(0?[1-9]|1[1-2])\\1\\d{4}$";
        if (!hastaFecha.matches(fechaSalidaPattern)) {
            Toast.makeText(CancelacionInsertarActivity.this, "El formato de la fecha hasta no es válido. Debe ser dd/mm/yyyy ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar el formato de la fecha desde utilizando una expresión regular (dd/mm/yyyy)
        String fechaLlegadaPattern = "^(?:3[01]|[12][0-9]|0?[1-9])([\\-/.])(0?[1-9]|1[1-2])\\1\\d{4}$";
        if (!desdeFecha.matches(fechaLlegadaPattern)) {
            Toast.makeText(CancelacionInsertarActivity.this, "El formato de la fecha desde no es válido. Debe ser dd/mm/yyyy ", Toast.LENGTH_SHORT).show();
            return;
        }

        try {

        // Insertar datos en la tabla "cancelacion"
        ContentValues values = new ContentValues();
        values.put("id_cancelacion", idCancelacion);
        values.put("motivo_cancelacion", motivoCancelacion);
        values.put("hasta_fecha", hastaFecha);
        values.put("desde_fecha", desdeFecha);

        long resultado = db.insert("cancelacion", null, values);

        if (resultado != -1) {
            Toast.makeText(this, "Cancelación insertada exitosamente", Toast.LENGTH_SHORT).show();
            // Restablecer los campos de entrada
            etIdCancelacion.setText("");
            etMotivoCancelacion.setText("");
            etHastaFecha.setText("");
            etDesdeFecha.setText("");
        } else {
            Toast.makeText(this, "Error al insertar la cancelación", Toast.LENGTH_SHORT).show();

        }

        } catch (SQLiteException e) {
                Toast.makeText(CancelacionInsertarActivity.this, "Error en la inserción: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
