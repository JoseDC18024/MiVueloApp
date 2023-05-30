package com.example.mivueloapp.joselucero;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TripulacionInsertarActivity extends AppCompatActivity {
    private EditText numeroTripulanteEditText;
    private EditText puestoTripulacionEditText;
    private Button insertarButton;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripulacion_insertar);

        numeroTripulanteEditText = findViewById(R.id.numeroTripulanteEditText);
        puestoTripulacionEditText = findViewById(R.id.puestoTripulacionEditText);
        insertarButton = findViewById(R.id.insertarButton);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        insertarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos ingresados por el usuario
                String numeroTripulante = numeroTripulanteEditText.getText().toString();
                String puestoTripulacion = puestoTripulacionEditText.getText().toString();

                // Insertar los datos en la tabla "tripulacion_vuelo"
                long resultado = insertarDatosTripulacion(numeroTripulante, puestoTripulacion);

                if (resultado != -1) {
                    Toast.makeText(TripulacionInsertarActivity.this, "Datos de tripulación insertados correctamente", Toast.LENGTH_SHORT).show();
                    // Limpiar los campos de entrada de datos
                    numeroTripulanteEditText.setText("");
                    puestoTripulacionEditText.setText("");
                } else {
                    Toast.makeText(TripulacionInsertarActivity.this, "Error al insertar los datos de tripulación", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private long insertarDatosTripulacion(String numeroTripulante, String puestoTripulacion) {
        ContentValues values = new ContentValues();
        values.put("numero_tripulante", numeroTripulante);
        values.put("puesto_tripulacion", puestoTripulacion);

        return database.insert("tripulacion_vuelo", null, values);
    }
}
