package com.example.mivueloapp.kevin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;
import com.example.mivueloapp.bryangrande.ReservacionInsertarActivity;

public class Vm17017AerolineaInsertarActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextIdAerolinea, editTextNombreAerolinea, editTextPaisAerolinea, editTextFechaAerolinea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vm17017_aerolinea_insertar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencias de los elementos de la interfaz de usuario
        editTextIdAerolinea = findViewById(R.id.editTextIdAerolinea);
        editTextNombreAerolinea = findViewById(R.id.editTextNombreAerolinea);
        editTextPaisAerolinea = findViewById(R.id.editTextPaisAerolinea);
        editTextFechaAerolinea = findViewById(R.id.editTextFechaAerolinea);
    }

    public void insertarAerolinea(View view) {
        String idAerolinea = editTextIdAerolinea.getText().toString();
        String nombreAerolinea = editTextNombreAerolinea.getText().toString();
        String paisAerolinea = editTextPaisAerolinea.getText().toString();
        String fechaAerolinea = editTextFechaAerolinea.getText().toString();

        // Validar el formato de la fecha de aerolinea utilizando una expresión regular (dd/mm/yyyy)
        String fechaNacimientoPattern = "^(?:3[01]|[12][0-9]|0?[1-9])([\\-/.])(0?[1-9]|1[1-2])\\1\\d{4}$";
        if (!fechaAerolinea.matches(fechaNacimientoPattern)) {
            Toast.makeText(Vm17017AerolineaInsertarActivity.this, "El formato de la fecha de aerolinea no es válido. Debe ser dd/mm/yyyy ", Toast.LENGTH_SHORT).show();
            return;
        }

        try {

            // Crear un objeto ContentValues para almacenar los valores a insertar
            ContentValues values = new ContentValues();
            values.put("id_aerolinea", idAerolinea);
            values.put("nombre_aerolinea", nombreAerolinea);
            values.put("pais_aerolinea", paisAerolinea);
            values.put("fecha_aerolinea", fechaAerolinea);

            // Insertar los valores en la tabla "boleto"
            long resultado = database.insert("aerolinea", null, values);

            if (resultado != -1) {
                Toast.makeText(this, "Aerolinea insertada correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al insertar la aerolinea", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLiteException e) {
            Toast.makeText(Vm17017AerolineaInsertarActivity.this, "Error en la inserción: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
