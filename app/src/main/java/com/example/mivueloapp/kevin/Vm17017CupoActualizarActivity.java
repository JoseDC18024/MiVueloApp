package com.example.mivueloapp.kevin;

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

public class Vm17017CupoActualizarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextBuscarIdCupo, editTextIdCupo, editTextCantidadCupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vm17017_cupo_actualizar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencias de los elementos de la interfaz de usuario
        editTextBuscarIdCupo = findViewById(R.id.editTextBuscarIdCupo);
        editTextIdCupo = findViewById(R.id.editTextIdCupo);
        editTextCantidadCupo = findViewById(R.id.editTextCantidadCupo);
    }

    @SuppressLint("Range")
    public void buscarCupo(View view) {
        String idCupo = editTextBuscarIdCupo.getText().toString();

        // Realizar la consulta para obtener los datos del boleto
        String[] projection = {"id_cupo", "cantidad_cupo"};
        String selection = "id_cupo = ?";
        String[] selectionArgs = {idCupo};
        Cursor cursor = database.query("cupo", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // El boleto fue encontrado, habilitar la edición y mostrar los datos
            editTextIdCupo.setEnabled(true);
            editTextCantidadCupo.setEnabled(true);

            editTextIdCupo.setText(cursor.getString(cursor.getColumnIndex("id_cupo")));
            editTextCantidadCupo.setText(cursor.getString(cursor.getColumnIndex("cantidad_cupo")));
            findViewById(R.id.btnActualizar).setEnabled(true);
        } else {
            // El boleto no fue encontrado, mostrar un mensaje de error
            Toast.makeText(this, "El cupo no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    public void actualizarCupo(View view) {
        String idCupo = editTextIdCupo.getText().toString();
        String cantidadCupo = editTextCantidadCupo.getText().toString();

        // Crear un objeto ContentValues para almacenar los valores a actualizar
        ContentValues values = new ContentValues();
        values.put("cantidad_cupo", cantidadCupo);

        // Actualizar los valores en la tabla "boleto"
        String whereClause = "id_cupo = ?";
        String[] whereArgs = {idCupo};
        int rowsAffected = database.update("cupo", values, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Cupo actualizado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar el cupo", Toast.LENGTH_SHORT).show();
        }
    }
}