package com.example.mivueloapp.joselucero;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
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
    private EditText editTextBuscarIdVuelo;
    private Button actualizarButton;
    private Button btnBuscarVuelo;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vuelo_actualizar);

        // Inicializar vistas
        idVueloEditText = findViewById(R.id.idVueloEditText);
        editTextBuscarIdVuelo = findViewById(R.id.editTextBuscarIdVuelo);
        numeroVueloEditText = findViewById(R.id.numeroVueloEditText);
        fechaSalidaEditText = findViewById(R.id.fechaSalidaEditText);
        fechaLlegadaEditText = findViewById(R.id.fechaLlegadaEditText);
        horaSalidaEditText = findViewById(R.id.horaSalidaEditText);
        horaLlegadaEditText = findViewById(R.id.horaLlegadaEditText);
        actualizarButton = findViewById(R.id.actualizarButton);
        btnBuscarVuelo = findViewById(R.id.btnBuscarVuelo);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();
    }
        @SuppressLint("Range")
        public void buscarVuelo(View view) {
            String idVuelo = editTextBuscarIdVuelo.getText().toString();

            // Realizar la consulta para obtener los datos del boleto
            String[] projection = {"id_vuelo", "numero_vuelo", "fecha_salida", "fecha_llegada", "hora_salida", "hora_llegada"};
            String selection = "id_vuelo = ?";
            String[] selectionArgs = {idVuelo};
            Cursor cursor = database.query("vuelo", projection, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                // El vuelo fue encontrado, habilitar la edición y mostrar los datos
                idVueloEditText.setEnabled(false);
                numeroVueloEditText.setEnabled(true);
                fechaSalidaEditText.setEnabled(true);
                fechaLlegadaEditText.setEnabled(true);
                horaSalidaEditText.setEnabled(true);
                horaLlegadaEditText.setEnabled(true);
                idVueloEditText.setText(cursor.getString(cursor.getColumnIndex("id_vuelo")));
                numeroVueloEditText.setText(cursor.getString(cursor.getColumnIndex("numero_vuelo")));
                fechaSalidaEditText.setText(cursor.getString(cursor.getColumnIndex("fecha_salida")));
                fechaLlegadaEditText.setText(cursor.getString(cursor.getColumnIndex("fecha_llegada")));
                horaSalidaEditText.setText(cursor.getString(cursor.getColumnIndex("hora_salida")));
                horaLlegadaEditText.setText(cursor.getString(cursor.getColumnIndex("hora_llegada")));
                findViewById(R.id.actualizarButton).setEnabled(true);
            } else {
                // El vuelo no fue encontrado, mostrar un mensaje de error
                Toast.makeText(this, "El vuelo no existe", Toast.LENGTH_SHORT).show();
            }

            cursor.close();

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
