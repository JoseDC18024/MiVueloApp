package com.example.mivueloapp.kevin;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

import java.util.ArrayList;
import java.util.List;

public class Vm17017AerolineaConsultarActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private ListView listViewAerolinea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vm17017_aerolinea_consultar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getReadableDatabase();

        // Obtener referencia del ListView
        listViewAerolinea = findViewById(R.id.listViewAerolinea);

        // Consultar los boletos de la base de datos
        List<String> listaAerolinea = consultarAerolinea();

        // Crear un ArrayAdapter para mostrar los boletos en el ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaAerolinea);

        // Asignar el ArrayAdapter al ListView
        listViewAerolinea.setAdapter(adapter);
    }

    private List<String> consultarAerolinea() {
        List<String> listaAerolinea = new ArrayList<>();
        // Realizar la consulta para obtener todos las aerolineas
        String[] projection = {"id_aerolinea", "nombre_aerolinea", "pais_aerolinea", "fecha_aerolinea"};
        Cursor cursor = database.query("aerolinea", projection, null, null, null, null, null);

        // Iterar sobre el cursor y agregar los boletos a la lista
        while (cursor.moveToNext()) {
            String idAerolinea = cursor.getString(cursor.getColumnIndex("id_aerolinea"));
            String nombreAerolinea = cursor.getString(cursor.getColumnIndex("nombre_aerolinea"));
            String paisAerolinea = cursor.getString(cursor.getColumnIndex("pais_aerolinea"));
            String fechaAerolinea = cursor.getString(cursor.getColumnIndex("fecha_aerolinea"));


            String aerolinea = "ID: " + idAerolinea + "\n" +
                    "Nombre: " + nombreAerolinea + "\n" +
                    "Pais de origen: " + paisAerolinea + "\n" +
                    "Fecha de creacion: " + fechaAerolinea + "\n";

            listaAerolinea.add(aerolinea);
        }
  // Mostrar mensaje si no hay datos
        if (listaAerolinea.isEmpty()) {
            Toast.makeText(Vm17017AerolineaConsultarActivity.this, "No hay datos disponibles.", Toast.LENGTH_SHORT).show();

        }
        cursor.close();
        return listaAerolinea;
    }
}