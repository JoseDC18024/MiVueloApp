package com.example.mivueloapp.joselucero;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class TripulacionConsultarActivity extends AppCompatActivity {
    private ListView tripulacionListView;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripulacion_consultar);

        tripulacionListView = findViewById(R.id.tripulacionLV);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener los datos de la tabla "tripulacion_vuelo"
        List<String> tripulacionList = obtenerDatosTripulacion();

        // Mostrar los datos en la lista
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tripulacionList);
        tripulacionListView.setAdapter(adapter);
    }

    private List<String> obtenerDatosTripulacion() {
        List<String> tripulacionList = new ArrayList<>();

        Cursor cursor = database.query("tripulacion_vuelo", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String numeroTripulante = cursor.getString(cursor.getColumnIndex("numero_tripulante"));
                String puestoTripulacion = cursor.getString(cursor.getColumnIndex("puesto_tripulacion"));

                String tripulacion = "Número de tripulante: " + numeroTripulante + "\n" +
                        "Puesto de tripulación: " + puestoTripulacion;

                tripulacionList.add(tripulacion);
            } while (cursor.moveToNext());

        }

        // Mostrar mensaje si no hay datos
        if (tripulacionList.isEmpty()) {
            Toast.makeText(this, "No hay datos disponibles.", Toast.LENGTH_SHORT).show();

        }
        cursor.close();

        return tripulacionList;
    }
}
