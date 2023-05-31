package com.example.mivueloapp.vlad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PasajeroUpdateActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    EditText editIdPasajero;
    EditText editNombre;
    EditText editFechanacimiento;
    EditText editSexo;

    EditText editTextBuscarIdPasajero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasajero_update);


        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencias de los elementos de la interfaz de usuario
        editTextBuscarIdPasajero = findViewById(R.id.editTextBuscarIdPasajero);
        editIdPasajero = (EditText) findViewById(R.id.editTextIdPasajero);
        editNombre = (EditText) findViewById(R.id.editTextNombrePasajero);
        editFechanacimiento = (EditText) findViewById(R.id.editTextFechaNacimiento);
        editSexo = (EditText) findViewById(R.id.editTextGenero);
    }
    @SuppressLint("Range")
    public void buscarPasajero(View view) {
        String idPasajero =editTextBuscarIdPasajero.getText().toString();

        // Realizar la consulta para obtener los datos del boleto
        String[] projection = {"id_pasajero", "nombre_pasajero", "fecha_nacimiento", "genero_pasajero"};
        String selection = "id_pasajero = ?";
        String[] selectionArgs = {idPasajero};
        Cursor cursor = database.query("Pasajero", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // El pasajero fue encontrado, habilitar la edición y mostrar los datos
            editIdPasajero.setEnabled(true);
            editNombre.setEnabled(true);
            editFechanacimiento.setEnabled(true);
            editSexo.setEnabled(true);
            editIdPasajero.setText(cursor.getString(cursor.getColumnIndex("id_pasajero")));
            editNombre.setText(cursor.getString(cursor.getColumnIndex("nombre_pasajero")));
            editFechanacimiento.setText(cursor.getString(cursor.getColumnIndex("fecha_nacimiento")));
            editSexo.setText(cursor.getString(cursor.getColumnIndex("genero_pasajero")));
            findViewById(R.id.btnActualizar).setEnabled(true);
        } else {
            // El boleto no fue encontrado, mostrar un mensaje de error
            Toast.makeText(this, "El pasajero no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    public void actualizarPasajero(View view) {
        String id = editIdPasajero.getText().toString();
        String nombre = editNombre.getText().toString();
        String fechaf = editFechanacimiento.getText().toString();
        String sexo = editSexo.getText().toString();

        // Validar el formato de la fecha de nacimiento utilizando una expresión regular (dd/mm/yyyy)
        String fechaNacimientoPattern = "^(?:3[01]|[12][0-9]|0?[1-9])([\\-/.])(0?[1-9]|1[1-2])\\1\\d{4}$";
        if (!fechaf.matches(fechaNacimientoPattern)) {
            Toast.makeText(this, "El formato de la fecha de nacimiento no es válido. Debe ser dd/mm/yyyy ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar que el género sea válido (M, F, Masculino, Femenino)
        String generoPattern = "^(M|F|Masculino|Femenino)$";
        if (!sexo.matches(generoPattern)) {
            Toast.makeText(this, "El género no es válido. Los valores permitidos son M, F, Masculino o Femenino", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Crear un objeto ContentValues para almacenar los valores a actualizar
            ContentValues values = new ContentValues();
            values.put("nombre_pasajero", nombre);
            values.put("fecha_nacimiento", fechaf);
            values.put("genero_pasajero", sexo);

            String whereClause = "id_pasajero = ?";
            String[] whereArgs = {id};
            int rowsAffected = database.update("Pasajero", values, whereClause, whereArgs);

            if (rowsAffected > 0) {
                Toast.makeText(this, "Pasajero actualizado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar el pasajero", Toast.LENGTH_SHORT).show();
            }
            // Limpiar los campos de texto
            editIdPasajero.setText("");
            editNombre.setText("");
            editFechanacimiento.setText("");
            editSexo.setText("");

            // Restaurar el foco al primer campo de texto
            editIdPasajero.requestFocus();
        } catch (SQLiteException e) {
            Toast.makeText(this, "Error en la actualización: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}