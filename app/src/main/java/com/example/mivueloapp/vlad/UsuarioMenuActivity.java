package com.example.mivueloapp.vlad;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mivueloapp.R;

public class UsuarioMenuActivity extends ListActivity {

    String[] menu = {"Insertar Usuario", "Consultar Usuario", "Actualizar Usuario", "Eliminar Usuario"};
    String[] activities = {"UsuarioInsertarActivity","UsuarioConsultarActivity","UsuarioUpdateActivity","UsuarioEliminarActivity"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, menu));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);

        if(position!=4) {

            String nombreValue = activities[position];

            try {
                Class<?>
                        clase = Class.forName("com.example.mivueloapp.vlad." + nombreValue);
                Intent inte = new Intent(this, clase);
                this.startActivity(inte);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{

        }
    }
}