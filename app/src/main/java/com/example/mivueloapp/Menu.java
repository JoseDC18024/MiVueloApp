package com.example.mivueloapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Obtén la referencia de la ListView en el archivo XML
        listView = findViewById(R.id.listView);

        // Crea una matriz de ejemplo de elementos para la ListView
       // String[] items = {"Elemento 1", "Elemento 2", "Elemento 3", "Elemento 4"};
        String[] menu={"Menu Jose Duran","Menu Kevin Villalta","Menu Jose Lucero","Menu Bladimir Soriano","Menu Bryan Grande"};
        String[] activities={"MenuJoseDuran","MenuKevin"};

        // Crea un adaptador para la ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, menu);

        // Establece el adaptador en la ListView
        listView.setAdapter(adapter);
    }
}
