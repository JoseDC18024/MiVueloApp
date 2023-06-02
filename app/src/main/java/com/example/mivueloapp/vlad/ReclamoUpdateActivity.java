package com.example.mivueloapp.vlad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class ReclamoUpdateActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    EditText editIdReclamo;
    EditText editFecha;
    EditText editDescripcionReclamo;
    EditText editEstado;

    EditText editTextBuscarIdReclamo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamo_update);


        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencias de los elementos de la interfaz de usuario
        editTextBuscarIdReclamo = findViewById(R.id.editTextBuscarIdReclamo);
        editIdReclamo = (EditText) findViewById(R.id.editTextIdReclamo);
        editFecha = (EditText) findViewById(R.id.editTextFechaReclamo);
        editDescripcionReclamo = (EditText) findViewById(R.id.editTextDescripcionReclamo);
        editEstado = (EditText) findViewById(R.id.editTextEstado);
    }
    @SuppressLint("Range")
    public void buscarReclamo(View view) {
        String idReclamo = editTextBuscarIdReclamo.getText().toString();

        // Realizar la consulta para obtener los datos del boleto
        String[] projection = {"id_reclamo", "fecha_reclamo", "descripcion_reclamo", "estado"};
        String selection = "id_reclamo = ?";
        String[] selectionArgs = {idReclamo};
        Cursor cursor = database.query("Reclamo", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // El pasajero fue encontrado, habilitar la edición y mostrar los datos
            editIdReclamo.setEnabled(false);
            editFecha.setEnabled(true);
            editDescripcionReclamo.setEnabled(true);
            editEstado.setEnabled(true);
            editIdReclamo.setText(cursor.getString(cursor.getColumnIndex("id_reclamo")));
            editFecha.setText(cursor.getString(cursor.getColumnIndex("fecha_reclamo")));
            editDescripcionReclamo.setText(cursor.getString(cursor.getColumnIndex("descripcion_reclamo")));
            editEstado.setText(cursor.getString(cursor.getColumnIndex("estado")));
            findViewById(R.id.btnActualizar).setEnabled(true);
        } else {
            // El boleto no fue encontrado, mostrar un mensaje de error
            Toast.makeText(this, "El reclamo no existe ", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    public void actualizarReclamo(View view) {
        String id = editIdReclamo.getText().toString();
        String fecha = editFecha.getText().toString();
        String descripcion = editDescripcionReclamo.getText().toString();
        String estado = editEstado.getText().toString();

        // Validar el formato de la fecha del reclamo utilizando una expresión regular (dd/mm/yyyy)
        String fechaReclamoPattern = "^(?:3[01]|[12][0-9]|0?[1-9])([\\-/.])(0?[1-9]|1[1-2])\\1\\d{4}$";
        if (!fecha.matches(fechaReclamoPattern)) {
            Toast.makeText(this, "El formato de la fecha del reclamo no es válido. Debe ser dd/mm/yyyy", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar que el estado del reclamo sea uno de los valores permitidos (activo, cancelado, solucionado, terminado)
        String estadoPattern = "^(activo|cancelado|solucionado|terminado)$";
        if (!estado.matches(estadoPattern)) {
            Toast.makeText(this, "El estado del reclamo no es válido. Los valores permitidos son activo, cancelado, solucionado o terminado", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Crear un objeto ContentValues para almacenar los valores a actualizar
            ContentValues values = new ContentValues();
            values.put("fecha_reclamo", fecha);
            values.put("descripcion_reclamo", descripcion);
            values.put("estado", estado);

            String whereClause = "id_reclamo = ?";
            String[] whereArgs = {id};
            int rowsAffected = database.update("Reclamo", values, whereClause, whereArgs);

            if (rowsAffected > 0) {
                Toast.makeText(this, "Reclamo actualizado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar el reclamo", Toast.LENGTH_SHORT).show();
            }
            // Limpiar los campos de texto
            editIdReclamo.setText("");
            editFecha.setText("");
            editDescripcionReclamo.setText("");
            editEstado.setText("");

            // Restaurar el foco al primer campo de texto
            editIdReclamo.requestFocus();
        } catch (SQLiteException e) {
            Toast.makeText(this, "Error en la actualización: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}