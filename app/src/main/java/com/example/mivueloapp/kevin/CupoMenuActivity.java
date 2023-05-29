package com.example.mivueloapp.kevin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mivueloapp.R;

public class CupoMenuActivity extends ListActivity {

    String[] menu = {"Insertar Cupo", "Consultar Cupos", "Actualizar Cupo", "Eliminar Cupo"};
    String[] activities = {"Vm17017CupoInsertarActivity", "Vm17017CupoConsultarActivity", "Vm17017CupoActualizarActivity", "Vm17017CupoEliminarActivity"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, menu));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (position != 4) {

            String nombreValue = activities[position];

            try {
                Class<?>
                        clase = Class.forName("com.example.mivueloapp.kevin." + nombreValue);
                Intent inte = new Intent(this, clase);
                this.startActivity(inte);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {

        }
    }
}