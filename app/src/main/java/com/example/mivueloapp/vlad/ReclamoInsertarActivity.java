package com.example.mivueloapp.vlad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

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
        String id= editIdReclamo.getText().toString();
        String fecha=editFechaReclamo.getText().toString();
        String descripcion= editDescripcionReclamo.getText().toString();
        String estado=editEstado.getText().toString();

        // Crear un objeto ContentValues para almacenar los valores a insertar
        ContentValues values = new ContentValues();
        values.put("id_reclamo", id);
        values.put("fecha_reclamo", fecha);
        values.put("descripcion_reclamo", descripcion);
        values.put("estado", estado);

        // Insertar los valores en la tabla "pasajero"
        long resultado = database.insert("Reclamo", null, values);

        if (resultado != -1) {
            Toast.makeText(this, "reclamo insertado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al insertar el reclamo", Toast.LENGTH_SHORT).show();
        }
    }
}