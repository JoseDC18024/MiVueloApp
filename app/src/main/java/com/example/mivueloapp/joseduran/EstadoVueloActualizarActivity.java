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

public class EstadoVueloActualizarActivity extends AppCompatActivity {
    private EditText editIdEstado, editDescripcionEstado, editTiempoRetraso, editTextBuscarIdEstado;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_vuelo_actualizar);

        // Obtener referencias a los EditText
        editIdEstado = findViewById(R.id.editIdEstado);
        editDescripcionEstado = findViewById(R.id.editDescripcionEstado);
        editTiempoRetraso = findViewById(R.id.editTiempoRetraso);
        editTextBuscarIdEstado = findViewById(R.id.editTextBuscarIdEstado);

        // Obtener instancia de la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
    }

        @SuppressLint("Range")
        public void buscarEstado(View view) {
            String idEstado = editTextBuscarIdEstado.getText().toString();

            // Realizar la consulta para obtener los datos del boleto
            String[] projection = {"id_estado", "descripcion_estado", "tiempo_retraso"};
            String selection = "id_estado = ?";
            String[] selectionArgs = {idEstado};
            Cursor cursor = database.query("estado_vuelo", projection, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                // El Estado fue encontrado, habilitar la edición y mostrar los datos
                editIdEstado.setEnabled(false);
                editDescripcionEstado.setEnabled(true);
                editTiempoRetraso.setEnabled(true);
                editIdEstado.setText(cursor.getString(cursor.getColumnIndex("id_estado")));
                editDescripcionEstado.setText(cursor.getString(cursor.getColumnIndex("descripcion_estado")));
                editTiempoRetraso.setText(cursor.getString(cursor.getColumnIndex("tiempo_retraso")));
                findViewById(R.id.actualizarEButton).setEnabled(true);
            } else {
                // El Estado no fue encontrado, mostrar un mensaje de error
                Toast.makeText(this, "El Estado no existe", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        }

    public void actualizarEstado(View view) {
        String idEstado = editIdEstado.getText().toString();
        String descripcionEstado = editDescripcionEstado.getText().toString();
        String tiempoRetraso = editTiempoRetraso.getText().toString();

        // Verificar si los campos están vacíos
        if (idEstado.isEmpty() || descripcionEstado.isEmpty() || tiempoRetraso.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el id_estado existe en la base de datos
        Cursor cursor = database.query(
                "estado_vuelo",
                null,
                "id_estado = ?",
                new String[]{idEstado},
                null,
                null,
                null
        );

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "El ID Estado no existe en la base de datos", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }

        // Actualizar los datos del estado_vuelo
        ContentValues values = new ContentValues();
        values.put("descripcion_estado", descripcionEstado);
        values.put("tiempo_retraso", tiempoRetraso);

        int rowsAffected = database.update(
                "estado_vuelo",
                values,
                "id_estado = ?",
                new String[]{idEstado}
        );

        // Verificar el resultado de la actualización
        if (rowsAffected > 0) {
            Toast.makeText(this, "Estado actualizado correctamente", Toast.LENGTH_SHORT).show();
            // Limpiar los campos
            editIdEstado.setText("");
            editDescripcionEstado.setText("");
            editTiempoRetraso.setText("");
        } else {
            Toast.makeText(this, "Error al actualizar el estado", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }
}
