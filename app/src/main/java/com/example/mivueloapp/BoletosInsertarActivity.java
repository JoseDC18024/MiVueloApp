package com.example.mivueloapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BoletosInsertarActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextIdBoleto, editTextClaseBoleto, editTextPrecioBoleto, editTextUbicacionAsiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boletos_insertar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencias de los elementos de la interfaz de usuario
        editTextIdBoleto = findViewById(R.id.editTextIdBoleto);
        editTextClaseBoleto = findViewById(R.id.editTextClaseBoleto);
        editTextPrecioBoleto = findViewById(R.id.editTextPrecioBoleto);
        editTextUbicacionAsiento = findViewById(R.id.editTextUbicacionAsiento);
    }

    public void insertarBoleto(View view) {
        String idBoleto = editTextIdBoleto.getText().toString();
        String claseBoleto = editTextClaseBoleto.getText().toString();
        int precioBoleto = Integer.parseInt(editTextPrecioBoleto.getText().toString());
        String ubicacionAsiento = editTextUbicacionAsiento.getText().toString();

        // Crear un objeto ContentValues para almacenar los valores a insertar
        ContentValues values = new ContentValues();
        values.put("id_boleto", idBoleto);
        values.put("clase_boleto", claseBoleto);
        values.put("precio_boleto", precioBoleto);
        values.put("ubicacion_asiento", ubicacionAsiento);

        // Insertar los valores en la tabla "boleto"
        long resultado = database.insert("boleto", null, values);

        if (resultado != -1) {
            Toast.makeText(this, "Boleto insertado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al insertar el boleto", Toast.LENGTH_SHORT).show();
        }
    }
}
