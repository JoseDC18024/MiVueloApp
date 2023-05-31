package com.example.mivueloapp.kevin;

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

public class Vm17017CupoConsultarActivity extends AppCompatActivity {


    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private ListView listViewCupos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vm17017_cupo_consultar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getReadableDatabase();

        // Obtener referencia del ListView
        listViewCupos = findViewById(R.id.listViewCupos);

        // Consultar los boletos de la base de datos
        List<String> listaCupos = consultarCupos();

        System.out.println("///////////////////////////"+listaCupos);

        // Crear un ArrayAdapter para mostrar los boletos en el ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCupos);

        // Asignar el ArrayAdapter al ListView
        listViewCupos.setAdapter(adapter);
    }

    private List<String> consultarCupos() {
        List<String> listaCupos = new ArrayList<>();

        // Realizar la consulta para obtener todos los cupos
        String[] projection = {"id_cupo", "cantidad_cupo"};
        Cursor cursor = database.query("cupo", projection, null, null, null, null, null);

        // Iterar sobre el cursor y agregar los boletos a la lista
        while (cursor.moveToNext()) {
            String idCupo = cursor.getString(cursor.getColumnIndex("id_cupo"));
            int cantidadCupo = cursor.getInt(cursor.getColumnIndex("cantidad_cupo"));

            String cupo = "ID: " + idCupo + "\n" +
                    "Cupos: " + cantidadCupo + "\n";

            listaCupos.add(cupo);
        }

        // Mostrar mensaje si no hay datos
        if (listaCupos.isEmpty()) {
            Toast.makeText(this, "No hay cupos disponibles.", Toast.LENGTH_SHORT).show();

        }
        cursor.close();
        return listaCupos;
    }
}