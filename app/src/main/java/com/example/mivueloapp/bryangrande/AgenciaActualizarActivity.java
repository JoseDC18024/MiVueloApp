package com.example.mivueloapp.bryangrande;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

public class AgenciaActualizarActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextBuscarIdAgencia, editTextIdAgencia, editTextNombreAgencia, editTextDireccionAgencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agencia_actualizar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencias de los elementos de la interfaz de usuario
        editTextBuscarIdAgencia = findViewById(R.id.editTextBuscarIdAgencia);
        editTextIdAgencia = findViewById(R.id.editTextIdAgencia);
        editTextNombreAgencia = findViewById(R.id.editTextNombreAgencia);
        editTextDireccionAgencia = findViewById(R.id.editTextDireccionAgencia);
    }

    @SuppressLint("Range")
    public void buscarAgencia(View view) {
        String idAgencia = editTextBuscarIdAgencia.getText().toString();

        // Realizar la consulta para obtener los datos de la agencia
        String[] projection = {"id_agencia", "nombre_agencia", "direccion_agencia"};
        String selection = "id_agencia = ?";
        String[] selectionArgs = {idAgencia};
        Cursor cursor = database.query("agencia_viajes", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // La agencia fue encontrada, habilitar la edición y mostrar los datos
            editTextIdAgencia.setEnabled(false);
            editTextNombreAgencia.setEnabled(true);
            editTextDireccionAgencia.setEnabled(true);
            editTextIdAgencia.setText(cursor.getString(cursor.getColumnIndex("id_agencia")));
            editTextNombreAgencia.setText(cursor.getString(cursor.getColumnIndex("nombre_agencia")));
            editTextDireccionAgencia.setText(cursor.getString(cursor.getColumnIndex("direccion_agencia")));
            findViewById(R.id.btnActualizarA).setEnabled(true);
        } else {
            // La agencia no fue encontrada, mostrar un mensaje de error
            Toast.makeText(this, "La agencia no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    public void actualizarAgencia(View view) {
        String idAgencia = editTextIdAgencia.getText().toString();
        String nombreAgencia = editTextNombreAgencia.getText().toString();
        String direccionAgencia = editTextDireccionAgencia.getText().toString();

        // Crear un objeto ContentValues para almacenar los valores a actualizar
        ContentValues values = new ContentValues();
        values.put("nombre_agencia", nombreAgencia);
        values.put("direccion_agencia", direccionAgencia);

        // Actualizar los valores en la tabla "agencia_viajes"
        String whereClause = "id_agencia = ?";
        String[] whereArgs = {idAgencia};
        int rowsAffected = database.update("agencia_viajes", values, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Agencia actualizada correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar la agencia", Toast.LENGTH_SHORT).show();
        }
    }
}
