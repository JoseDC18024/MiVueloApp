package com.example.mivueloapp.vlad;

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
            editIdReclamo.setEnabled(true);
            editFecha.setEnabled(true);
            editDescripcionReclamo.setEnabled(true);
            editEstado.setEnabled(true);
            editIdReclamo.setText(cursor.getString(cursor.getColumnIndex("id_reclamo")));
            editFecha.setText(cursor.getString(cursor.getColumnIndex("fecha_reclamo")));
            editDescripcionReclamo.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("descripcion_reclamo"))));
            editEstado.setText(cursor.getString(cursor.getColumnIndex("estado")));
            findViewById(R.id.btnActualizar).setEnabled(true);
        } else {
            // El boleto no fue encontrado, mostrar un mensaje de error
            Toast.makeText(this, "El reclamo no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    public void actualizarReclamo(View view)  {
        String id= editIdReclamo.getText().toString();
        String fecha= editFecha.getText().toString();
        String descripcion= editDescripcionReclamo.getText().toString();
        String estado= editEstado.getText().toString();

        // Crear un objeto ContentValues para almacenar los valores a insertar
        ContentValues values = new ContentValues();
        values.put("id_reclamo", id);
        values.put("fecha_reclamo", fecha);
        values.put("descripcion_reclamo", descripcion);
        values.put("estado", estado);

        String whereClause = "id_reclamo = ?";
        String[] whereArgs = {id};
        int rowsAffected = database.update("Reclamo", values, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Reclamo insertado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al insertar el reclamo", Toast.LENGTH_SHORT).show();
        }
    }
}