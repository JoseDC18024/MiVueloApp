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

public class AgenciaConsultarActivity extends AppCompatActivity {
    private ListView agenciaListView;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agencia_consultar);

        agenciaListView = findViewById(R.id.AgenciaLV);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener los datos de la tabla "agencia_viajes"
        List<String> agenciaViajesList = obtenerDatosAgenciaViajes();

        // Mostrar los datos en la lista
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, agenciaViajesList);
        agenciaListView.setAdapter(adapter);
    }

    private List<String> obtenerDatosAgenciaViajes() {
        List<String> agenciaViajesList = new ArrayList<>();

        Cursor cursor = database.query("agencia_viajes", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String idAgencia = cursor.getString(cursor.getColumnIndex("id_agencia"));
                String nombreAgencia = cursor.getString(cursor.getColumnIndex("nombre_agencia"));
                String direccionAgencia = cursor.getString(cursor.getColumnIndex("direccion_agencia"));

                String agenciaViajes = "ID de agencia: " + idAgencia + "\n" +
                        "Nombre de agencia: " + nombreAgencia + "\n" +
                        "Dirección de agencia: " + direccionAgencia;

                agenciaViajesList.add(agenciaViajes);
            } while (cursor.moveToNext());
        }

        // Mostrar mensaje si no hay datos
        if (agenciaViajesList.isEmpty()) {
            Toast.makeText(this, "No hay datos disponibles.", Toast.LENGTH_SHORT).show();
        }
        cursor.close();

        return agenciaViajesList;
    }
}
