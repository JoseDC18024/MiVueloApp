package com.example.mivueloapp.bryangrande;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

public class ReservacionActualizarActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextBuscarIdReservacion, editTextIdReservacion, editTextFechaReservacion, editTextNumeroAsiento, editTextEstadoReservacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservacion_actualizar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencias de los elementos de la interfaz de usuario
        editTextBuscarIdReservacion = findViewById(R.id.editTextBuscarIdReservacion);
        editTextIdReservacion = findViewById(R.id.editTextIdReservacion);
        editTextFechaReservacion = findViewById(R.id.editTextFechaReservacion);
        editTextNumeroAsiento = findViewById(R.id.editTextNumeroAsiento);
        editTextEstadoReservacion = findViewById(R.id.editTextEstadoReservacion);
    }

    @SuppressLint("Range")
    public void buscarReservacion(View view) {
        String idReservacion = editTextBuscarIdReservacion.getText().toString();

        // Realizar la consulta para obtener los datos de la reservación
        String[] projection = {"id_reservacion", "fecha_reservacion", "numero_asiento", "estado_reservacion"};
        String selection = "id_reservacion = ?";
        String[] selectionArgs = {idReservacion};
        Cursor cursor = database.query("reservacion", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // La reservación fue encontrada, habilitar la edición y mostrar los datos
            editTextIdReservacion.setEnabled(true);
            editTextFechaReservacion.setEnabled(true);
            editTextNumeroAsiento.setEnabled(true);
            editTextEstadoReservacion.setEnabled(true);
            editTextIdReservacion.setText(cursor.getString(cursor.getColumnIndex("id_reservacion")));
            editTextFechaReservacion.setText(cursor.getString(cursor.getColumnIndex("fecha_reservacion")));
            editTextNumeroAsiento.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("numero_asiento"))));
            editTextEstadoReservacion.setText(cursor.getString(cursor.getColumnIndex("estado_reservacion")));
            findViewById(R.id.btnActualizarR).setEnabled(true);
        } else {
            // La reservación no fue encontrada, mostrar un mensaje de error
            Toast.makeText(this, "La reservación no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    public void actualizarReservacion(View view) {
        String idReservacion = editTextIdReservacion.getText().toString();
        String fechaReservacion = editTextFechaReservacion.getText().toString();
        int numeroAsiento = Integer.parseInt(editTextNumeroAsiento.getText().toString());
        String estadoReservacion = editTextEstadoReservacion.getText().toString();

        // Crear un objeto ContentValues para almacenar los valores a actualizar
        ContentValues values = new ContentValues();
        values.put("fecha_reservacion", fechaReservacion);
        values.put("numero_asiento", numeroAsiento);
        values.put("estado_reservacion", estadoReservacion);

        // Actualizar los valores en la tabla "reservacion"
        String whereClause = "id_reservacion = ?";
        String[] whereArgs = {idReservacion};
        int rowsAffected = database.update("reservacion", values, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Reservación actualizada correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar la reservación", Toast.LENGTH_SHORT).show();
        }
    }
}
