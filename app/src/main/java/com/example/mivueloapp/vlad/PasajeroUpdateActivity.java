package com.example.mivueloapp.vlad;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasajero_update);


        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencias de los elementos de la interfaz de usuario
        editIdPasajero = (EditText) findViewById(R.id.editTextIdPasajero);
        editNombre = (EditText) findViewById(R.id.editTextNombrePasajero);
        editFechanacimiento = (EditText) findViewById(R.id.editTextFechaNacimiento);
        editSexo = (EditText) findViewById(R.id.editTextGenero);
    }
    @SuppressLint("Range")
    public void buscarPasajero(View view) {
        String idPasajero =editIdPasajero.getText().toString();

        // Realizar la consulta para obtener los datos del boleto
        String[] projection = {"id_pasajero", "nombre_pasajero", "fecha_nacimiento", "genero_pasajero"};
        String selection = "id_pasajero = ?";
        String[] selectionArgs = {idPasajero};
        Cursor cursor = database.query("boleto", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // El boleto fue encontrado, habilitar la edición y mostrar los datos
            editIdPasajero.setEnabled(true);
            editNombre.setEnabled(true);
            editFechanacimiento.setEnabled(true);
            editSexo.setEnabled(true);
            editIdPasajero.setText(cursor.getString(cursor.getColumnIndex("id_pasajero")));
            editNombre.setText(cursor.getString(cursor.getColumnIndex("nombre_pasajero")));
            editFechanacimiento.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("fecha_nacimiento"))));
            editSexo.setText(cursor.getString(cursor.getColumnIndex("genero_pasajero")));
            findViewById(R.id.btnActualizar).setEnabled(true);
        } else {
            // El boleto no fue encontrado, mostrar un mensaje de error
            Toast.makeText(this, "El pasajero no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    public void actualizarPasajero(View view) throws ParseException {
        String id=editIdPasajero.getText().toString();
        String nombre=editNombre.getText().toString();
        String fechaf=editFechanacimiento.getText().toString();
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        Date fecha = formatoFecha.parse(fechaf);
        String sexo=editSexo.getText().toString();

        // Crear un objeto ContentValues para almacenar los valores a insertar
        ContentValues values = new ContentValues();
        values.put("id_pasajero", id);
        values.put("nombre_pasajero", nombre);
        values.put("precio_boleto", fechaf);
        values.put("ubicacion_asiento", sexo);

        // Insertar los valores en la tabla "pasajero"
        long resultado = database.insert("boleto", null, values);

        if (resultado != -1) {
            Toast.makeText(this, "Pasajero insertado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al insertar el pasajero", Toast.LENGTH_SHORT).show();
        }
    }
}