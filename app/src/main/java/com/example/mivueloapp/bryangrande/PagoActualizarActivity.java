package com.example.mivueloapp.bryangrande;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

public class PagoActualizarActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextBuscarIdPago, editTextIdPago, editTextFechaPago, editTextMontoPago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_actualizar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencias de los elementos de la interfaz de usuario
        editTextBuscarIdPago = findViewById(R.id.editTextBuscarIdPago);
        editTextIdPago = findViewById(R.id.editTextIdPago);
        editTextFechaPago = findViewById(R.id.editTextFechaPago);
        editTextMontoPago = findViewById(R.id.editTextMontoPago);

    }

    @SuppressLint("Range")
    public void buscarPago(View view) {
        String idPago = editTextBuscarIdPago.getText().toString();

        // Realizar la consulta para obtener los datos del pago
        String[] projection = {"id_pago", "fecha_pago", "monto_pago"};
        String selection = "id_pago = ?";
        String[] selectionArgs = {idPago};
        Cursor cursor = database.query("pago", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // El pago fue encontrado, habilitar la edición y mostrar los datos
            editTextIdPago.setEnabled(false);
            editTextFechaPago.setEnabled(true);
            editTextMontoPago.setEnabled(true);
            editTextIdPago.setText(cursor.getString(cursor.getColumnIndex("id_pago")));
            editTextFechaPago.setText(cursor.getString(cursor.getColumnIndex("fecha_pago")));
            editTextMontoPago.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("monto_pago"))));
            findViewById(R.id.btnActualizarP).setEnabled(true);
        } else {
            // El pago no fue encontrado, mostrar un mensaje de error
            Toast.makeText(this, "El pago no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    public void actualizarPago(View view) {
        String idPago = editTextIdPago.getText().toString();
        String fechaPago = editTextFechaPago.getText().toString();
        int montoPago = Integer.parseInt(editTextMontoPago.getText().toString());

        // Crear un objeto ContentValues para almacenar los valores a actualizar
        ContentValues values = new ContentValues();
        values.put("fecha_pago", fechaPago);
        values.put("monto_pago", montoPago);

        // Actualizar los valores en la tabla "pago"
        String whereClause = "id_pago = ?";
        String[] whereArgs = {idPago};
        int rowsAffected = database.update("pago", values, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Pago actualizado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al actualizar el pago", Toast.LENGTH_SHORT).show();
        }
    }
}
