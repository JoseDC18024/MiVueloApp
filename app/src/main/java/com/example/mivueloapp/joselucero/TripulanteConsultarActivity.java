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

public class TripulanteConsultarActivity extends AppCompatActivity {
    private ListView tripulanteListView;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripulante_consultar);

        tripulanteListView = findViewById(R.id.tripulanteListV);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Consultar los datos de la tabla "tripulante"
        List<String> tripulanteList = consultarDatosTripulante();

        if (tripulanteList.isEmpty()) {
            Toast.makeText(this, "No hay datos de tripulante disponibles", Toast.LENGTH_SHORT).show();
        } else {
            // Crear un adaptador para la lista de tripulantes
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tripulanteList);

            // Asignar el adaptador a la ListView
            tripulanteListView.setAdapter(adapter);
        }
    }

    private List<String> consultarDatosTripulante() {
        List<String> tripulanteList = new ArrayList<>();

        Cursor cursor = database.query("tripulante", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String idTripulante = cursor.getString(cursor.getColumnIndex("id_tripulante"));
            String nombreTripulante = cursor.getString(cursor.getColumnIndex("nombre_tripulante"));
            String campo = cursor.getString(cursor.getColumnIndex("campo"));

            String tripulanteDetails = "ID: " + idTripulante + "\nNombre: " + nombreTripulante + "\nCampo: " + campo;
            tripulanteList.add(tripulanteDetails);

        }

        // Mostrar mensaje si no hay datos
        if (tripulanteList.isEmpty()) {
            Toast.makeText(this, "No hay datos disponibles.", Toast.LENGTH_SHORT).show();

        }

        cursor.close();

        return tripulanteList;
    }
}
