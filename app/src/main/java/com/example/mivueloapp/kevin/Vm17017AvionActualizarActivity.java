package com.example.mivueloapp.kevin;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

public class Vm17017AvionActualizarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextBuscarIdAvion, editTextIdAvion, editTextModeloAvion, editTextAñoFabricacion, editTextIdAerolinea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vm17017_avion_actualizar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencias de los elementos de la interfaz de usuario
        editTextBuscarIdAvion = findViewById(R.id.editTextBuscarIdAvion);
        editTextIdAvion = findViewById(R.id.editTextIdAvion);
        editTextModeloAvion = findViewById(R.id.editTextModeloAvion);
        editTextAñoFabricacion = findViewById(R.id.editTextAñoFabricacion);
        editTextIdAerolinea = findViewById(R.id.editTextIdAerolinea);

    }

    @SuppressLint("Range")
    public void buscarAvion(View view) {
        String idAvion = editTextBuscarIdAvion.getText().toString();

        // Realizar la consulta para obtener los datos del avio6

        String[] projection = {"id_avion", "modelo_avion", "año_fabricacion", "id_aerolinea"};
        String selection = "id_avion = ?";
        String[] selectionArgs = {idAvion};
        Cursor cursor = database.query("avion", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // El avion fue encontrado, habilitar la edición y mostrar los datos
            editTextIdAvion.setEnabled(true);
            editTextModeloAvion.setEnabled(true);
            editTextAñoFabricacion.setEnabled(true);
            editTextIdAvion.setText(cursor.getString(cursor.getColumnIndex("id_avion")));
            editTextModeloAvion.setText(cursor.getString(cursor.getColumnIndex("modelo_avion")));
            editTextAñoFabricacion.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("año_fabricacion"))));
            editTextIdAerolinea.setText(cursor.getString(cursor.getColumnIndex("id_aerolinea")));
            findViewById(R.id.btnActualizar).setEnabled(true);
        } else {
            // El avion no fue encontrado, mostrar un mensaje de error
            Toast.makeText(this, "El avion no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    public void actualizarAvion(View view) {
        String idAvion = editTextIdAvion.getText().toString();
        String modeloAvion = editTextModeloAvion.getText().toString();
        int anioFabricacion = Integer.parseInt(editTextAñoFabricacion.getText().toString());
        String idAerolinea = editTextIdAerolinea.getText().toString();

        // Crear un objeto ContentValues para almacenar los valores a actualizar
        ContentValues values = new ContentValues();
        values.put("modelo_avion", modeloAvion);
        values.put("año_fabricacion", anioFabricacion);
        values.put("id_aerolinea", idAerolinea);

        // Actualizar los valores en la tabla "avion"
        String whereClause = "id_avion = ?";
        String[] whereArgs = {idAvion};
        int rowsAffected = database.update("avion", values, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Avion actualizado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar el avion", Toast.LENGTH_SHORT).show();
        }
    }
}