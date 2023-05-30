package com.example.mivueloapp.vlad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

import java.util.ArrayList;
import java.util.List;

public class ReclamoConsultarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private ListView listViewReclamo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamo_consultar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getReadableDatabase();

        // Obtener referencia del ListView
        listViewReclamo = findViewById(R.id.listViewReclamos);

        // Consultar los boletos de la base de datos
        List<String> listaReclamos = consultarReclamos();

        // Crear un ArrayAdapter para mostrar los boletos en el ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaReclamos);

        // Asignar el ArrayAdapter al ListView
        listViewReclamo.setAdapter(adapter);
    }
    @SuppressLint("Range")
    private List<String> consultarReclamos() {
        List<String> listaReclamos = new ArrayList<>();

        // Realizar la consulta para obtener todos los boletos
        String[] projection = {"id_reclamo", "fecha_reclamo", "descripcion_reclamo", "estado"};
        Cursor cursor = database.query("Reclamo", projection, null, null, null, null, null);

        // Iterar sobre el cursor y agregar los boletos a la lista
        while (cursor.moveToNext()) {
            String idReclamo = cursor.getString(cursor.getColumnIndex("id_reclamo"));
            String fecha = cursor.getString(cursor.getColumnIndex("fecha_reclamo"));
            String descripcion = String.valueOf(cursor.getInt(cursor.getColumnIndex("descripcion_reclamo")));
            String estado = cursor.getString(cursor.getColumnIndex("estado"));

            String reclamo = "ID: " + idReclamo + "\n" +
                    "fecha del reclamo: " + fecha + "\n" +
                    "Descripcion del reclamo" + descripcion + "\n" +
                    "estado: " + estado;

            listaReclamos.add(reclamo);
        }

        cursor.close();

        return listaReclamos;
    }
}