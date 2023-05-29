package com.example.mivueloapp.joseduran;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

import java.util.ArrayList;
import java.util.List;

public class BoletosConsultarActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private ListView listViewBoletos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boletos_consultar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getReadableDatabase();

        // Obtener referencia del ListView
        listViewBoletos = findViewById(R.id.listViewBoletos);

        // Consultar los boletos de la base de datos
        List<String> listaBoletos = consultarBoletos();

        // Crear un ArrayAdapter para mostrar los boletos en el ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaBoletos);

        // Asignar el ArrayAdapter al ListView
        listViewBoletos.setAdapter(adapter);
    }

    private List<String> consultarBoletos() {
        List<String> listaBoletos = new ArrayList<>();

        // Realizar la consulta para obtener todos los boletos
        String[] projection = {"id_boleto", "clase_boleto", "precio_boleto", "ubicacion_asiento"};
        Cursor cursor = database.query("boleto", projection, null, null, null, null, null);

        // Iterar sobre el cursor y agregar los boletos a la lista
        while (cursor.moveToNext()) {
            String idBoleto = cursor.getString(cursor.getColumnIndex("id_boleto"));
            String claseBoleto = cursor.getString(cursor.getColumnIndex("clase_boleto"));
            int precioBoleto = cursor.getInt(cursor.getColumnIndex("precio_boleto"));
            String ubicacionAsiento = cursor.getString(cursor.getColumnIndex("ubicacion_asiento"));

            String boleto = "ID: " + idBoleto + "\n" +
                    "Clase: " + claseBoleto + "\n" +
                    "Precio: $" + precioBoleto + "\n" +
                    "Ubicación: " + ubicacionAsiento;

            listaBoletos.add(boleto);
        }

        cursor.close();

        return listaBoletos;
    }
}
