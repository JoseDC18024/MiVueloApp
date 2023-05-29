package com.example.mivueloapp.vlad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PasajeroInsertarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    EditText editIdPasajero;
    EditText editNombre;
    EditText editFechanacimiento;
    EditText editSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasajero_insertar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencias de los elementos de la interfaz de usuario
        editIdPasajero = (EditText) findViewById(R.id.editTextIdPasajero);
        editNombre = (EditText) findViewById(R.id.editTextNombrePasajero);
        editFechanacimiento = (EditText) findViewById(R.id.editTextFechaNacimiento);
        editSexo = (EditText) findViewById(R.id.editTextGenero);
    }

    public void insertarPasajero(View view) {
        String id=editIdPasajero.getText().toString();
        String nombre=editNombre.getText().toString();
        String fechaf=editFechanacimiento.getText().toString();
        String sexo=editSexo.getText().toString();

        // Crear un objeto ContentValues para almacenar los valores a insertar
        ContentValues values = new ContentValues();
        values.put("id_pasajero", id);
        values.put("nombre_pasajero", nombre);
        values.put("fecha_nacimiento", fechaf);
        values.put("genero_pasajero", sexo);

        // Insertar los valores en la tabla "pasajero"
        long resultado = database.insert("Pasajero", null, values);

        if (resultado != -1) {
            Toast.makeText(this, "Pasajero insertado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al insertar el pasajero", Toast.LENGTH_SHORT).show();
        }
    }
}