package com.example.mivueloapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CancelacionConsultarActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> cancelacionList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelacion_consultar);

        listView = findViewById(R.id.listView);
        cancelacionList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cancelacionList);
        listView.setAdapter(adapter);

        // Abrir la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        // Consultar los datos de la tabla
        Cursor cursor = database.rawQuery("SELECT * FROM cancelacion", null);

        if (cursor.moveToFirst()) {
            do {
                String idCancelacion = cursor.getString(cursor.getColumnIndex("id_cancelacion"));
                String motivoCancelacion = cursor.getString(cursor.getColumnIndex("motivo_cancelacion"));
                String hastaFecha = cursor.getString(cursor.getColumnIndex("hasta_fecha"));
                String desdeFecha = cursor.getString(cursor.getColumnIndex("desde_fecha"));

                // Agregar los datos a la lista
                String item = "ID: " + idCancelacion + "\nMotivo: " + motivoCancelacion + "\nHasta fecha: " + hastaFecha + "\nDesde fecha: " + desdeFecha;
                cancelacionList.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        dbHelper.close();

        // Notificar al adaptador que los datos han cambiado
        adapter.notifyDataSetChanged();

        // Mostrar mensaje si no hay datos
        if (cancelacionList.isEmpty()) {
            Toast.makeText(this, "No hay datos disponibles.", Toast.LENGTH_SHORT).show();
        }
    }
}
