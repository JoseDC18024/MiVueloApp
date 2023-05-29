package com.example.mivueloapp.joseduran;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

import java.util.ArrayList;
import java.util.List;

public class EstadoVueloConsultarActivity extends AppCompatActivity {
    private ListView listViewEstados;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_vuelo_consultar);

        // Obtener referencia al ListView
        listViewEstados = findViewById(R.id.listViewEstados);

        // Obtener instancia de la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getReadableDatabase();

        // Consultar los datos de estado_vuelo
        List<String> estados = consultarEstados();

        // Crear un ArrayAdapter y configurarlo en el ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, estados);
        listViewEstados.setAdapter(adapter);
    }

    private List<String> consultarEstados() {
        List<String> estados = new ArrayList<>();

        // Consultar todos los registros de la tabla estado_vuelo
        Cursor cursor = database.rawQuery("SELECT * FROM estado_vuelo", null);

        // Recorrer el cursor y agregar los datos a la lista
        if (cursor.moveToFirst()) {
            do {
                String idEstado = cursor.getString(cursor.getColumnIndex("id_estado"));
                String descripcionEstado = cursor.getString(cursor.getColumnIndex("descripcion_estado"));
                String tiempoRetraso = cursor.getString(cursor.getColumnIndex("tiempo_retraso"));

                // Crear una cadena con los datos del estado
                String estado = "ID: " + idEstado + "\nDescripción: " + descripcionEstado + "\nTiempo de Retraso: " + tiempoRetraso;

                estados.add(estado);
            } while (cursor.moveToNext());
        }

        // Cerrar el cursor
        cursor.close();

        return estados;
    }
}
