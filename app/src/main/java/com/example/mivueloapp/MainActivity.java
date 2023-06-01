package com.example.mivueloapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    String[] menu = {"Menu Jose Duran", "Menu Kevin Villalta","Menu Vladimir Soriano", "Menu Jose Lucero", "Menu Bryan Grande"};
    String[] activities = {"MenuJoseDuran", "MenuKevin","VladimirMenuActivity","MenuJoseLucero", "MenuBryan"};

    private ListView listView;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listView = findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menu));

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 5) {
                    String nombreValue = activities[position];
                    try {
                        Class<?> clase = Class.forName("com.example.mivueloapp." + nombreValue);
                        Intent intent = new Intent(MainActivity.this, clase);
                        startActivity(intent);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Lógica para el elemento en la posición 4
                }
            }
        });
    }
}
