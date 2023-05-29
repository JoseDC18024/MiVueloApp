package com.example.mivueloapp.kevin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

public class Vm17017AvionInsertarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextIdAvion, editTextModeloAvion, editTextAñoFabricacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vm17017_avion_insertar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();


        // Obtener referencias de los elementos de la interfaz de usuario
        editTextIdAvion = findViewById(R.id.editTextIdAvion);
        editTextModeloAvion = findViewById(R.id.editTextModeloAvion);
        editTextAñoFabricacion = findViewById(R.id.editTextAñoFabricacion);
    }

    public void insertarAvion(View view) {
        String idAvion = editTextIdAvion.getText().toString();
        String modeloAvion = editTextModeloAvion.getText().toString();
        int añoFabricacion = Integer.parseInt(editTextAñoFabricacion.getText().toString());

        // Crear un objeto ContentValues para almacenar los valores a insertar
        ContentValues values = new ContentValues();
        values.put("id_avion", idAvion);
        values.put("modelo_avion", modeloAvion);
        values.put("año_fabricacion", añoFabricacion);

        // Insertar los valores en la tabla "avion"
        long resultado = database.insert("avion", null, values);


        if (resultado != -1) {
            Toast.makeText(this, "Avion insertado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al insertar el avion", Toast.LENGTH_SHORT).show();
        }
    }
}