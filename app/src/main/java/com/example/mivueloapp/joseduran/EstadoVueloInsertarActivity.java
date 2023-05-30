package com.example.mivueloapp.joseduran;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

public class EstadoVueloInsertarActivity extends AppCompatActivity {
    private EditText editIdEstado, editDescripcionEstado, editTiempoRetraso;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_vuelo_insertar);

        // Obtener referencias a los EditText
        editIdEstado = findViewById(R.id.editIdEstado);
        editDescripcionEstado = findViewById(R.id.editDescripcionEstado);
        editTiempoRetraso = findViewById(R.id.editTiempoRetraso);

        // Obtener instancia de la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
    }

    public void insertarEstado(View view) {
        String idEstado = editIdEstado.getText().toString();
        String descripcionEstado = editDescripcionEstado.getText().toString();
        String tiempoRetraso = editTiempoRetraso.getText().toString();

        // Verificar si los campos están vacíos
        if (idEstado.isEmpty() || descripcionEstado.isEmpty() || tiempoRetraso.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un nuevo registro con los valores proporcionados
        ContentValues values = new ContentValues();
        values.put("id_estado", idEstado);
        values.put("descripcion_estado", descripcionEstado);
        values.put("tiempo_retraso", tiempoRetraso);

        // Insertar el nuevo registro en la tabla estado_vuelo
        long result = database.insert("estado_vuelo", null, values);

        // Verificar el resultado de la inserción
        if (result != -1) {
            Toast.makeText(this, "Estado insertado correctamente", Toast.LENGTH_SHORT).show();
            // Limpiar los campos
            editIdEstado.setText("");
            editDescripcionEstado.setText("");
            editTiempoRetraso.setText("");
        } else {
            Toast.makeText(this, "Error al insertar el estado", Toast.LENGTH_SHORT).show();
        }
    }
}
