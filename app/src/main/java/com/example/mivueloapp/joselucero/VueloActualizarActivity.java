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

public class VueloActualizarActivity extends AppCompatActivity {
    private EditText idVueloEditText;
    private EditText numeroVueloEditText;
    private EditText fechaSalidaEditText;
    private EditText fechaLlegadaEditText;
    private EditText horaSalidaEditText;
    private EditText horaLlegadaEditText;
    private Button actualizarButton;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vuelo_actualizar);

        // Inicializar vistas
        idVueloEditText = findViewById(R.id.idVueloEditText);
        numeroVueloEditText = findViewById(R.id.numeroVueloEditText);
        fechaSalidaEditText = findViewById(R.id.fechaSalidaEditText);
        fechaLlegadaEditText = findViewById(R.id.fechaLlegadaEditText);
        horaSalidaEditText = findViewById(R.id.horaSalidaEditText);
        horaLlegadaEditText = findViewById(R.id.horaLlegadaEditText);
        actualizarButton = findViewById(R.id.actualizarButton);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        actualizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos ingresados por el usuario
                String idVuelo = idVueloEditText.getText().toString();
                String numeroVuelo = numeroVueloEditText.getText().toString();
                String fechaSalida = fechaSalidaEditText.getText().toString();
                String fechaLlegada = fechaLlegadaEditText.getText().toString();
                String horaSalida = horaSalidaEditText.getText().toString();
                String horaLlegada = horaLlegadaEditText.getText().toString();

                // Crear un objeto ContentValues con los datos a actualizar
                ContentValues values = new ContentValues();
                values.put("numero_vuelo", numeroVuelo);
                values.put("fecha_salida", fechaSalida);
                values.put("fecha_llegada", fechaLlegada);
                values.put("hora_salida", horaSalida);
                values.put("hora_llegada", horaLlegada);

                // Actualizar los datos en la tabla "vuelo"
                int rowsAffected = database.update("vuelo", values, "id_vuelo = ?", new String[]{idVuelo});

                if (rowsAffected > 0) {
                    Toast.makeText(VueloActualizarActivity.this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VueloActualizarActivity.this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
