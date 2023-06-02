package com.example.mivueloapp.joseduran;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

public class BoletosActualizarActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextBuscarIdBoleto, editTextIdBoleto, editTextClaseBoleto, editTextPrecioBoleto, editTextUbicacionAsiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boletos_actualizar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencias de los elementos de la interfaz de usuario
        editTextBuscarIdBoleto = findViewById(R.id.editTextBuscarIdBoleto);
        editTextIdBoleto = findViewById(R.id.editTextIdBoleto);
        editTextClaseBoleto = findViewById(R.id.editTextClaseBoleto);
        editTextPrecioBoleto = findViewById(R.id.editTextPrecioBoleto);
        editTextUbicacionAsiento = findViewById(R.id.editTextUbicacionAsiento);
    }

    @SuppressLint("Range")
    public void buscarBoleto(View view) {
        String idBoleto = editTextBuscarIdBoleto.getText().toString();

        // Realizar la consulta para obtener los datos del boleto
        String[] projection = {"id_boleto", "clase_boleto", "precio_boleto", "ubicacion_asiento"};
        String selection = "id_boleto = ?";
        String[] selectionArgs = {idBoleto};
        Cursor cursor = database.query("boleto", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // El boleto fue encontrado, habilitar la edición y mostrar los datos
            editTextIdBoleto.setEnabled(false);
            editTextClaseBoleto.setEnabled(true);
            editTextPrecioBoleto.setEnabled(true);
            editTextUbicacionAsiento.setEnabled(true);
            editTextIdBoleto.setText(cursor.getString(cursor.getColumnIndex("id_boleto")));
            editTextClaseBoleto.setText(cursor.getString(cursor.getColumnIndex("clase_boleto")));
            editTextPrecioBoleto.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("precio_boleto"))));
            editTextUbicacionAsiento.setText(cursor.getString(cursor.getColumnIndex("ubicacion_asiento")));
            findViewById(R.id.btnActualizar).setEnabled(true);
        } else {
            // El boleto no fue encontrado, mostrar un mensaje de error
            Toast.makeText(this, "El boleto no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    public void actualizarBoleto(View view) {
        String idBoleto = editTextIdBoleto.getText().toString();
        String claseBoleto = editTextClaseBoleto.getText().toString();
        int precioBoleto = Integer.parseInt(editTextPrecioBoleto.getText().toString());
        String ubicacionAsiento = editTextUbicacionAsiento.getText().toString();

        // Crear un objeto ContentValues para almacenar los valores a actualizar
        ContentValues values = new ContentValues();
        values.put("clase_boleto", claseBoleto);
        values.put("precio_boleto", precioBoleto);
        values.put("ubicacion_asiento", ubicacionAsiento);

        // Actualizar los valores en la tabla "boleto"
        String whereClause = "id_boleto = ?";
        String[] whereArgs = {idBoleto};
        int rowsAffected = database.update("boleto", values, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Boleto actualizado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar el boleto", Toast.LENGTH_SHORT).show();
        }
    }
}
