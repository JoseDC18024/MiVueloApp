package com.example.mivueloapp.bryangrande;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

import java.util.ArrayList;
import java.util.List;

public class PagoConsultarActivity extends AppCompatActivity {
    private ListView pagoListView;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_consultar);

        pagoListView = findViewById(R.id.pagoLV);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener los datos de la tabla "pago"
        List<String> pagoList = obtenerDatosPago();

        // Mostrar los datos en la lista
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pagoList);
        pagoListView.setAdapter(adapter);
    }

    private List<String> obtenerDatosPago() {
        List<String> pagoList = new ArrayList<>();

        Cursor cursor = database.query("pago", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String idPago = cursor.getString(cursor.getColumnIndex("id_pago"));
                String fechaPago = cursor.getString(cursor.getColumnIndex("fecha_pago"));
                int montoPago = cursor.getInt(cursor.getColumnIndex("monto_pago"));

                String pago = "ID de pago: " + idPago + "\n" +
                        "Fecha de pago: " + fechaPago + "\n" +
                        "Monto de pago: " + montoPago;

                pagoList.add(pago);
            } while (cursor.moveToNext());

        }

        // Mostrar mensaje si no hay datos
        if (pagoList.isEmpty()) {
            Toast.makeText(this, "No hay datos disponibles.", Toast.LENGTH_SHORT).show();
        }
        cursor.close();

        return pagoList;
    }
}
