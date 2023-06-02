package com.example.mivueloapp.kevin;

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

public class Vm17017AerolineaActualizarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextBuscarIdAerolinea, editTextIdAerolinea, editTextNombreAerolinea, editTextPaisAerolinea, editTextFechaAerolinea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vm17017_aerolinea_actualizar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencias de los elementos de la interfaz de usuario
        editTextBuscarIdAerolinea = findViewById(R.id.editTextBuscarIdAerolinea);
        editTextIdAerolinea = findViewById(R.id.editTextIdAerolinea);
        editTextNombreAerolinea = findViewById(R.id.editTextNombreAerolinea);
        editTextPaisAerolinea = findViewById(R.id.editTextPaisAerolinea);
        editTextFechaAerolinea = findViewById(R.id.editTextFechaAerolinea);
    }

    @SuppressLint("Range")
    public void buscarAerolinea(View view) {
        String idAerolinea = editTextBuscarIdAerolinea.getText().toString();

        // Realizar la consulta para obtener los datos del avio6

        String[] projection = {"id_aerolinea", "nombre_aerolinea", "pais_aerolinea", "fecha_aerolinea"};
        String selection = "id_aerolinea = ?";
        String[] selectionArgs = {idAerolinea};
        Cursor cursor = database.query("aerolinea", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // La aerolinea fue encontrado, habilitar la edición y mostrar los datos
            editTextIdAerolinea.setEnabled(false);
            editTextNombreAerolinea.setEnabled(true);
            editTextPaisAerolinea.setEnabled(true);
            editTextFechaAerolinea.setEnabled(true);
            editTextIdAerolinea.setText(cursor.getString(cursor.getColumnIndex("id_aerolinea")));
            editTextNombreAerolinea.setText(cursor.getString(cursor.getColumnIndex("nombre_aerolinea")));
            editTextPaisAerolinea.setText(cursor.getString(cursor.getColumnIndex("pais_aerolinea")));
            editTextFechaAerolinea.setText(cursor.getString(cursor.getColumnIndex("fecha_aerolinea")));
            findViewById(R.id.btnActualizar).setEnabled(true);
        } else {
            // El avion no fue encontrado, mostrar un mensaje de error
            Toast.makeText(this, "La aerolinea no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    public void actualizarAerolinea(View view) {
        String idAerolinea = editTextIdAerolinea.getText().toString();
        String nombreAerolinea = editTextNombreAerolinea.getText().toString();
        String paisAerolinea = editTextPaisAerolinea.getText().toString();
        String fechaAerolinea = editTextFechaAerolinea.getText().toString();

        // Crear un objeto ContentValues para almacenar los valores a actualizar
        ContentValues values = new ContentValues();
        values.put("id_aerolinea", idAerolinea);
        values.put("nombre_aerolinea", nombreAerolinea);
        values.put("pais_aerolinea", paisAerolinea);
        values.put("fecha_aerolinea", fechaAerolinea);

        // Actualizar los valores en la tabla "avion"
        String whereClause = "id_aerolinea = ?";
        String[] whereArgs = {idAerolinea};
        int rowsAffected = database.update("aerolinea", values, whereClause, whereArgs);
        System.out.println(rowsAffected);
        if (rowsAffected > 0) {
            Toast.makeText(this, "Aerolinea actualizado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar la Aerolinea", Toast.LENGTH_SHORT).show();
        }
    }
}