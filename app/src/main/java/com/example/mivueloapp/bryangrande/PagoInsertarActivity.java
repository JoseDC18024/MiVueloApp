package com.example.mivueloapp.bryangrande;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PagoInsertarActivity extends AppCompatActivity {

    private EditText idPagoEditText, fechaPagoEditText, montoPagoEditText;
    private Button insertarButton;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_insertar);

        idPagoEditText = findViewById(R.id.id_pago_edit);
        fechaPagoEditText = findViewById(R.id.fecha_pago_edit);
        montoPagoEditText = findViewById(R.id.monto_pago_edit);
        insertarButton = findViewById(R.id.insertarp_button);

        // Inicializar la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        insertarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idPago = idPagoEditText.getText().toString();
                String fechaPago = fechaPagoEditText.getText().toString();
                String montoPago = montoPagoEditText.getText().toString();

                // Verificar si los campos están vacíos
                if (idPago.isEmpty() || fechaPago.isEmpty() || montoPago.isEmpty()){
                    Toast.makeText(PagoInsertarActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validar el formato de la fecha de pago utilizando una expresión regular (dd/mm/yyyy)
                String fechaPagoPattern = "^(?:3[01]|[12][0-9]|0?[1-9])([\\-/.])(0?[1-9]|1[1-2])\\1\\d{4}$";
                if (!fechaPago.matches(fechaPagoPattern)) {
                    Toast.makeText(PagoInsertarActivity.this, "El formato de la fecha de pago no es válido. Debe ser dd/mm/yyyy ", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {

                    // Insertar datos en la tabla 'pago'
                    ContentValues values = new ContentValues();
                    values.put("id_pago", idPago);
                    values.put("fecha_pago", fechaPago);
                    values.put("monto_pago", montoPago);

                    long result = database.insert("pago", null, values);

                    if (result == -1) {
                        Toast.makeText(PagoInsertarActivity.this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(PagoInsertarActivity.this, "Error al insertar los datos", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLiteException e) {
                    Toast.makeText(PagoInsertarActivity.this, "Error en la inserción: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}