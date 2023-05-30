package com.example.mivueloapp.joselucero;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class VueloConsultarActivity extends AppCompatActivity {
    private ListView listView;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vuelo_consultar);

        listView = findViewById(R.id.listView);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getReadableDatabase();

        // Consultar los datos de vuelo
        List<String> vueloList = consultarDatosVuelo();

        // Mostrar los datos en la lista
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, vueloList);
        listView.setAdapter(adapter);
    }

    private List<String> consultarDatosVuelo() {
        List<String> vueloList = new ArrayList<>();

        // Realizar la consulta en la tabla "vuelo"
        Cursor cursor = database.query("vuelo", null, null, null, null, null, null);

        // Recorrer el cursor y obtener los datos de cada vuelo
        while (cursor.moveToNext()) {
            String idVuelo = cursor.getString(cursor.getColumnIndex("id_vuelo"));
            String numeroVuelo = cursor.getString(cursor.getColumnIndex("numero_vuelo"));
            String fechaSalida = cursor.getString(cursor.getColumnIndex("fecha_salida"));
            String fechaLlegada = cursor.getString(cursor.getColumnIndex("fecha_llegada"));
            String horaSalida = cursor.getString(cursor.getColumnIndex("hora_salida"));
            String horaLlegada = cursor.getString(cursor.getColumnIndex("hora_llegada"));

            // Crear una cadena con los detalles del vuelo
            String detallesVuelo = "ID Vuelo: " + idVuelo + "\n"
                    + "Número de Vuelo: " + numeroVuelo + "\n"
                    + "Fecha de Salida: " + fechaSalida + "\n"
                    + "Fecha de Llegada: " + fechaLlegada + "\n"
                    + "Hora de Salida: " + horaSalida + "\n"
                    + "Hora de Llegada: " + horaLlegada;

            vueloList.add(detallesVuelo);

        }

        // Mostrar mensaje si no hay datos
        if (vueloList.isEmpty()) {
            Toast.makeText(this, "No hay datos disponibles.", Toast.LENGTH_SHORT).show();

        }

        // Cerrar el cursor
        cursor.close();

        return vueloList;
    }
}
