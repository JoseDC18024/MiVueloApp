package com.example.mivueloapp.joselucero;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;
import com.example.mivueloapp.bryangrande.ReservacionInsertarActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VueloInsertarActivity extends AppCompatActivity {
    private EditText idVueloEditText, numeroVueloEditText, fechaSalidaEditText, fechaLlegadaEditText, horaSalidaEditText, horaLlegadaEditText;
    private Button insertarButton;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vuelo_insertar);

        // Obtener referencias de los elementos del layout
        idVueloEditText = findViewById(R.id.idVueloEditText);
        numeroVueloEditText = findViewById(R.id.numeroVueloEditText);
        fechaSalidaEditText = findViewById(R.id.fechaSalidaEditText);
        fechaLlegadaEditText = findViewById(R.id.fechaLlegadaEditText);
        horaSalidaEditText = findViewById(R.id.horaSalidaEditText);
        horaLlegadaEditText = findViewById(R.id.horaLlegadaEditText);
        insertarButton = findViewById(R.id.insertarButton);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        insertarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos ingresados por el usuario
                String idVuelo = idVueloEditText.getText().toString();
                String numeroVuelo = numeroVueloEditText.getText().toString();
                String fechaSalida = fechaSalidaEditText.getText().toString();
                String fechaLlegada = fechaLlegadaEditText.getText().toString();
                String horaSalida = horaSalidaEditText.getText().toString();
                String horaLlegada = horaLlegadaEditText.getText().toString();

                // Validar el formato de la fecha de pago utilizando una expresión regular (dd/mm/yyyy)
                String fechaSalidaPattern = "^(?:3[01]|[12][0-9]|0?[1-9])([\\-/.])(0?[1-9]|1[1-2])\\1\\d{4}$";
                if (!fechaSalida.matches(fechaSalidaPattern)) {
                    Toast.makeText(VueloInsertarActivity.this, "El formato de la fecha de salida no es válido. Debe ser dd/mm/yyyy ", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar el formato de la fecha de pago utilizando una expresión regular (dd/mm/yyyy)
                String fechaLlegadaPattern = "^(?:3[01]|[12][0-9]|0?[1-9])([\\-/.])(0?[1-9]|1[1-2])\\1\\d{4}$";
                if (!fechaLlegada.matches(fechaLlegadaPattern)) {
                    Toast.makeText(VueloInsertarActivity.this, "El formato de la fecha de llegada no es válido. Debe ser dd/mm/yyyy ", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {

                    // Insertar los datos en la tabla "vuelo"
                    ContentValues values = new ContentValues();
                    values.put("id_vuelo", idVuelo);
                    values.put("numero_vuelo", numeroVuelo);
                    values.put("fecha_salida", fechaSalida);
                    values.put("fecha_llegada", fechaLlegada);
                    values.put("hora_salida", horaSalida);
                    values.put("hora_llegada", horaLlegada);

                    long resultado = database.insert("vuelo", null, values);

                    if (resultado != -1) {
                        // Se insertó correctamente
                        Toast.makeText(VueloInsertarActivity.this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        // Hubo un error al insertar
                        Toast.makeText(VueloInsertarActivity.this, "Error al insertar los datos", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLiteException e) {
                    Toast.makeText(VueloInsertarActivity.this, "Error en la inserción: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


