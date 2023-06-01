package com.example.mivueloapp.kevin;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

import java.util.ArrayList;
import java.util.List;


public class Vm17017AvionConsultarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private ListView listViewAvion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vm17017_avion_consultar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getReadableDatabase();

        // Obtener referencia del ListView
        listViewAvion = findViewById(R.id.listViewAvion);

        // Consultar los boletos de la base de datos
        List<String> listaAvion = consultarAvion();

        // Crear un ArrayAdapter para mostrar los boletos en el ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaAvion);

        // Asignar el ArrayAdapter al ListView
        listViewAvion.setAdapter(adapter);
    }

    private List<String> consultarAvion() {
        List<String> listaAviones = new ArrayList<>();

        // Realizar la consulta para obtener todos los boletos
        String[] projection = {"id_avion", "modelo_avion", "año_fabricacion", "id_aerolinea"};
        Cursor cursor = database.query("avion", projection, null, null, null, null, null);

        // Iterar sobre el cursor y agregar los boletos a la lista
        while (cursor.moveToNext()) {
            String idAvion = cursor.getString(cursor.getColumnIndex("id_avion"));
            String modeloAvion = cursor.getString(cursor.getColumnIndex("modelo_avion"));
            String idAerolinea = cursor.getString(cursor.getColumnIndex("id_aerolinea"));
            int anioFabricacion = cursor.getInt(cursor.getColumnIndex("año_fabricacion"));

            String avion = "ID: " + idAvion + "\n" +
                    "Modelo: " + modeloAvion + "\n" +
                    "Año de fabricacion: " + anioFabricacion + "\n" +
                    "Identificador de aerolinea: " + idAerolinea + "\n" ;

            listaAviones.add(avion);
        }

        // Mostrar mensaje si no hay datos
        if (listaAviones.isEmpty()) {
            Toast.makeText(Vm17017AvionConsultarActivity.this, "No hay datos disponibles.", Toast.LENGTH_SHORT).show();

        }
        cursor.close();
        return listaAviones;
    }
}