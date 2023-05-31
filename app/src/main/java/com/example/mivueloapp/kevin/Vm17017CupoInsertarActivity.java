package com.example.mivueloapp.kevin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
    private EditText editTextIdCupo, editTextCantidadCupo;

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
    }

    public void insertarCupo(View view) {
        String idCupo = editTextIdCupo.getText().toString();
        int cantidadCupos = Integer.parseInt(editTextCantidadCupo.getText().toString());

        // Crear un objeto ContentValues para almacenar los valores a insertar
        ContentValues values = new ContentValues();
        values.put("id_cupo", idCupo);
        values.put("cantidad_cupo", cantidadCupos);

        // Insertar los valores en la tabla "cupo"
        long resultado = database.insert("cupo", null, values);

        if (resultado != -1) {
            Toast.makeText(this, "Cupo insertado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al insertar el cupo", Toast.LENGTH_SHORT).show();
        }
    }
}