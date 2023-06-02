package com.example.mivueloapp.kevin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

public class Vm17017CupoInsertarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextIdCupo, editTextCantidadCupo, editTextIdAvion,editTextIdVuelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vm17017_cupo_insertar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencias de los elementos de la interfaz de usuario
        editTextIdCupo = findViewById(R.id.editTextIdCupo);
        editTextCantidadCupo = findViewById(R.id.editTextCantidadCupo);
        editTextIdAvion = findViewById(R.id.editTextIdAvion);
        editTextIdVuelo = findViewById(R.id.editTextIdVuelo);
    }

    public void insertarCupo(View view) {
        String idCupo = editTextIdCupo.getText().toString();
        String idAvion = editTextIdAvion.getText().toString();
        String idVuelo = editTextIdVuelo.getText().toString();
        String cantidadCupos = editTextCantidadCupo.getText().toString();

        // Verificar si los campos están vacíos
        if (idCupo.isEmpty() || idAvion.isEmpty() || idVuelo.isEmpty() || cantidadCupos.isEmpty() ){
            Toast.makeText(Vm17017CupoInsertarActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el ID de vuelo existe en la tabla "vuelo"
        String[] vueloProjection = {"id_vuelo"};
        String vueloSelection = "id_vuelo = ?";
        String[] vueloSelectionArgs = {idVuelo};
        Cursor vueloCursor = database.query("vuelo", vueloProjection, vueloSelection, vueloSelectionArgs, null, null, null);

        // Verificar si el ID de avión existe en la tabla "avion"
        String[] avionProjection = {"id_avion"};
        String avionSelection = "id_avion = ?";
        String[] avionSelectionArgs = {idAvion};
        Cursor avionCursor = database.query("avion", avionProjection, avionSelection, avionSelectionArgs, null, null, null);

        if (vueloCursor.moveToFirst() && avionCursor.moveToFirst()) {
            // Ambos ID de vuelo y avión existen en las respectivas tablas, proceder con la inserción
            ContentValues values = new ContentValues();
            values.put("id_cupo", idCupo);
            values.put("cantidad_cupo", cantidadCupos);
            values.put("id_vuelo", idVuelo);
            values.put("id_avion", idAvion);

            long resultado = database.insert("cupo", null, values);

            if (resultado != -1) {
                Toast.makeText(this, "Cupo insertado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al insertar el cupo", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Al menos uno de los ID de vuelo o avión no existe en las respectivas tablas, mostrar un mensaje de error
            Toast.makeText(this, "El ID de vuelo o avión no existe", Toast.LENGTH_SHORT).show();
        }

        vueloCursor.close();
        avionCursor.close();
    }

}