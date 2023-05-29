package com.example.mivueloapp.vlad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class PasajeroConsultarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private ListView listViewPasajero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasajero_consultar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getReadableDatabase();

        // Obtener referencia del ListView
        listViewPasajero = findViewById(R.id.listViewPasajeros);

        // Consultar los boletos de la base de datos
        List<String> listaPasajeros = consultarPasajeros();

        // Crear un ArrayAdapter para mostrar los boletos en el ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaPasajeros);

        // Asignar el ArrayAdapter al ListView
        listViewPasajero.setAdapter(adapter);
    }
    @SuppressLint("Range")
    private List<String> consultarPasajeros() {
        List<String> listaPasajeros = new ArrayList<>();

        // Realizar la consulta para obtener todos los boletos
        String[] projection = {"id_pasajero", "nombre_pasajero", "fecha_nacimiento", "genero_pasajero"};
        Cursor cursor = database.query("Pasajero", projection, null, null, null, null, null);

        // Iterar sobre el cursor y agregar los boletos a la lista
        while (cursor.moveToNext()) {
            String idPasajero = cursor.getString(cursor.getColumnIndex("id_pasajero"));
            String nombre = cursor.getString(cursor.getColumnIndex("nombre_pasajero"));
            String fecha_nacimiento = String.valueOf(cursor.getInt(cursor.getColumnIndex("fecha_nacimiento")));
            String sexo = cursor.getString(cursor.getColumnIndex("genero_pasajero"));

            String pasajero = "ID: " + idPasajero + "\n" +
                    "Nombre: " + nombre + "\n" +
                    "Fecha de Nacimiento" + fecha_nacimiento + "\n" +
                    "sexo: " + sexo;

            listaPasajeros.add(pasajero);
        }

        cursor.close();

        return listaPasajeros;
    }

}