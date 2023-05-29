package com.example.mivueloapp.kevin;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class InformeMenuActivity extends ListActivity {

    String[] menu = {"Insertar Informe", "Consultar Informe", "Actualizar Informe", "Eliminar Informe"};
    String[] activities = {"Vm17017InformeInsetarActivity", "Vm17017InformeConsultarActivity", "Vm17017InformeActualizarActivity", "Vm17017InformeEliminarActivity"};

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