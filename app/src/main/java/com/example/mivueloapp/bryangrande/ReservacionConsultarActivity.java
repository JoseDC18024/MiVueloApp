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

public class ReservacionConsultarActivity extends AppCompatActivity {
    private ListView reservacionListView;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservacion_consultar);

        reservacionListView = findViewById(R.id.reservacionLV);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener los datos de la tabla "reservacion"
        List<String> reservacionList = obtenerDatosReservacion();

        // Mostrar los datos en la lista
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reservacionList);
        reservacionListView.setAdapter(adapter);
    }

    private List<String> obtenerDatosReservacion() {
        List<String> reservacionList = new ArrayList<>();

        Cursor cursor = database.query("reservacion", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String idReservacion = cursor.getString(cursor.getColumnIndex("id_reservacion"));
                String fechaReservacion = cursor.getString(cursor.getColumnIndex("fecha_reservacion"));
                int numeroAsiento = cursor.getInt(cursor.getColumnIndex("numero_asiento"));
                String estadoReservacion = cursor.getString(cursor.getColumnIndex("estado_reservacion"));

                String reservacion = "ID de reservación: " + idReservacion + "\n" +
                        "Fecha de reservación: " + fechaReservacion + "\n" +
                        "Número de asiento: " + numeroAsiento + "\n" +
                        "Estado de reservación: " + estadoReservacion;

                reservacionList.add(reservacion);
            } while (cursor.moveToNext());
        }

        // Mostrar mensaje si no hay datos
        if (reservacionList.isEmpty()) {
            Toast.makeText(this, "No hay datos disponibles.", Toast.LENGTH_SHORT).show();
        }
        cursor.close();

        return reservacionList;
    }
}
