package com.example.mivueloapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FuncionalidadesMenuActivity extends ListActivity {
    String[] menu = {"Calendario", "Mapa","Lector QR","Sqlite To Excel", "Alarmas", "Google Singin"};
    String[] activities = {"CalendarActivity", "MapasActivity","LectorQRActivity","ExportarBDActivity","AlarmasActivity", "GoogleloginActivity"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, menu));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);

        if(position!=6) {

            String nombreValue = activities[position];

            try {
                Class<?>
                        clase = Class.forName("com.example.mivueloapp.Funcionalidades." + nombreValue);
                Intent inte = new Intent(this, clase);
                this.startActivity(inte);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{

        }
    }
}