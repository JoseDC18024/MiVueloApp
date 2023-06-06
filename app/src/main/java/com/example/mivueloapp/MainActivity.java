package com.example.mivueloapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String[] menu = {"Menu Jose Duran", "Menu Kevin Villalta","Menu Vladimir Soriano", "Menu Jose Lucero", "Menu Bryan Grande", "Nuevas Funcionalidades"};
    String[] activities = {"MenuJoseDuran", "MenuKevin","VladimirMenuActivity","MenuJoseLucero", "MenuBryan", "FuncionalidadesMenuActivity"};

    private ListView listView;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private MediaPlayer mediaPlayer;

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

        // Iniciar reproducción de audio
        mediaPlayer = MediaPlayer.create(this, R.raw.audio);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start(); // Iniciar reproducción al estar preparado
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 6) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Liberar recursos del MediaPlayer
            mediaPlayer = null;
        }
    }
}
