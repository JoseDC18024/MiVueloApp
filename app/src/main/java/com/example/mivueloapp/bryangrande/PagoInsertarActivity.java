package com.example.mivueloapp.bryangrande;

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

public class PagoInsertarActivity extends AppCompatActivity {

    private EditText idPagoEditText, fechaPagoEditText, montoPagoEditText;
    private Button insertarButton;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agencia_insertar);

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
                int montoPago = Integer.parseInt(montoPagoEditText.getText().toString());

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
            }
        });
    }
}