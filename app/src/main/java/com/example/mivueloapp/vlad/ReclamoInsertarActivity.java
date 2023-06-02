package com.example.mivueloapp.vlad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ReclamoInsertarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    EditText editIdReclamo;
    EditText editFechaReclamo;
    EditText editDescripcionReclamo;
    EditText editEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamo_insertar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencias de los elementos de la interfaz de usuario
        editIdReclamo = (EditText) findViewById(R.id.editTextIdReclamo);
        editFechaReclamo = (EditText) findViewById(R.id.editTextFechaReclamo);
        editDescripcionReclamo = (EditText) findViewById(R.id.editTextDescripcionReclamo);
        editEstado = (EditText) findViewById(R.id.editTextEstado);
    }

    public void insertarReclamo(View view) {
        String id = editIdReclamo.getText().toString();
        String fecha = editFechaReclamo.getText().toString();
        String descripcion = editDescripcionReclamo.getText().toString();
        String estado = editEstado.getText().toString();

        // Verificar si los campos están vacíos
        if (id.isEmpty() || fecha.isEmpty() || descripcion.isEmpty() || estado.isEmpty() ){
            Toast.makeText(ReclamoInsertarActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar el formato del ID de reclamo
        if (!id.matches("[A-Za-z0-9]+")) {
            Toast.makeText(this, "El ID del reclamo debe contener solo letras y números", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el ID de reclamo ya existe en la base de datos
        Cursor cursor = database.rawQuery("SELECT COUNT(*) FROM Reclamo WHERE id_reclamo = ?", new String[]{id});
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                Toast.makeText(this, "El ID del reclamo ya existe", Toast.LENGTH_SHORT).show();
                cursor.close();
                return;
            }
        }
        cursor.close();

        // Verificar el formato de fecha del reclamo utilizando una expresión regular (dd/MM/yyyy)
        String fechaReclamoPattern = "^(?:3[01]|[12][0-9]|0?[1-9])([\\-/.])(0?[1-9]|1[1-2])\\1\\d{4}$";
        if (!fecha.matches(fechaReclamoPattern)) {
            Toast.makeText(this, "Formato de fecha inválido. Debe ser dd/mm/yyyy ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar el estado del reclamo
        if (!estado.matches("^(activo|cancelado|solucionado|terminado)$")) {
            Toast.makeText(this, "El estado del reclamo debe ser activo, cancelado, solucionado o terminado", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Crear un objeto ContentValues para almacenar los valores a insertar
            ContentValues values = new ContentValues();
            values.put("id_reclamo", id);
            values.put("fecha_reclamo", fecha);
            values.put("descripcion_reclamo", descripcion);
            values.put("estado", estado);

            // Insertar los valores en la tabla "Reclamo"
            long resultado = database.insert("Reclamo", null, values);

            if (resultado != -1) {
                Toast.makeText(this, "Reclamo insertado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al insertar el reclamo", Toast.LENGTH_SHORT).show();
            }

            // Limpiar los campos de texto
            editIdReclamo.setText("");
            editFechaReclamo.setText("");
            editDescripcionReclamo.setText("");
            editEstado.setText("");

            // Restaurar el foco al primer campo de texto
            editIdReclamo.requestFocus();
        } catch (SQLiteException e) {
            Toast.makeText(this, "Error en la inserción: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}