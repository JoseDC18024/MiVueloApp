package com.example.mivueloapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EstadoVueloActualizarActivity extends AppCompatActivity {
    private EditText editIdEstado, editDescripcionEstado, editTiempoRetraso;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_vuelo_actualizar);

        // Obtener referencias a los EditText
        editIdEstado = findViewById(R.id.editIdEstado);
        editDescripcionEstado = findViewById(R.id.editDescripcionEstado);
        editTiempoRetraso = findViewById(R.id.editTiempoRetraso);

        // Obtener instancia de la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
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
