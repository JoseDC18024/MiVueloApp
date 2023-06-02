package com.example.mivueloapp.joseduran;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

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
        String precioBoleto = editTextPrecioBoleto.getText().toString();
        String ubicacionAsiento = editTextUbicacionAsiento.getText().toString();

        if (idBoleto.isEmpty() || claseBoleto.isEmpty() || precioBoleto.isEmpty() || ubicacionAsiento.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

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
