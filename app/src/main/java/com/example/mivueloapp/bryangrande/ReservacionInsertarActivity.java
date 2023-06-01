package com.example.mivueloapp.bryangrande;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

public class ReservacionInsertarActivity extends AppCompatActivity {

    private EditText idReservacionEditText, fechaReservacionEditText, numeroAsientoEditText, estadoReservacionEditText;
    private Button insertarButton;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservacion_insertar);

        idReservacionEditText = findViewById(R.id.id_reservacion_edit);
        fechaReservacionEditText = findViewById(R.id.fecha_reservacion_edit);
        numeroAsientoEditText = findViewById(R.id.numero_asiento_edit);
        estadoReservacionEditText = findViewById(R.id.estado_reservacion_edit);
        insertarButton = findViewById(R.id.insertarR_button);

        // Inicializar la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        insertarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idReservacion = idReservacionEditText.getText().toString();
                String fechaReservacion = fechaReservacionEditText.getText().toString();
                int numeroAsiento = Integer.parseInt(numeroAsientoEditText.getText().toString());
                String estadoReservacion = estadoReservacionEditText.getText().toString();

                // Insertar datos en la tabla 'reservacion'
                ContentValues values = new ContentValues();
                values.put("id_reservacion", idReservacion);
                values.put("fecha_reservacion", fechaReservacion);
                values.put("numero_asiento", numeroAsiento);
                values.put("estado_reservacion", estadoReservacion);

                long result = database.insert("reservacion", null, values);

                if (result == -1) {
                    Toast.makeText(ReservacionInsertarActivity.this, "Error al insertar los datos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ReservacionInsertarActivity.this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
