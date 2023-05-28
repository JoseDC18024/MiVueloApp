package com.example.mivueloapp;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EstadoVueloEliminarActivity extends AppCompatActivity {
    private EditText editIdEstado;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estado_vuelo_eliminar);

        // Obtener referencia al EditText
        editIdEstado = findViewById(R.id.editIdEstado);

        // Obtener instancia de la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
    }

    public void eliminarEstado(View view) {
        final String idEstado = editIdEstado.getText().toString();

        // Verificar si el campo está vacío
        if (idEstado.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un ID de Estado", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el estado existe en la base de datos
        Cursor cursor = database.rawQuery("SELECT id_estado FROM estado_vuelo WHERE id_estado = ?", new String[]{idEstado});
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "El ID de Estado no existe en la base de datos", Toast.LENGTH_SHORT).show();
            return;
        }
        cursor.close();

        // Mostrar un diálogo de confirmación antes de eliminar
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Estado");
        builder.setMessage("¿Estás seguro de que deseas eliminar este estado?");
        builder.setPositiveButton("Eliminar", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Eliminar el estado de la tabla estado_vuelo
                int result = database.delete("estado_vuelo", "id_estado = ?", new String[]{idEstado});
                if (result > 0) {
                    Toast.makeText(EstadoVueloEliminarActivity.this, "Estado eliminado correctamente", Toast.LENGTH_SHORT).show();
                    editIdEstado.setText("");
                } else {
                    Toast.makeText(EstadoVueloEliminarActivity.this, "Error al eliminar el estado", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
}
