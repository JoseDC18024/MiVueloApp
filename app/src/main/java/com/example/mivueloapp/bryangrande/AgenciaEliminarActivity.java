package com.example.mivueloapp.bryangrande;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

public class AgenciaEliminarActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextIdAgencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agencia_eliminar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencia del EditText
        editTextIdAgencia = findViewById(R.id.editTextIdAgencia);
    }

    public void eliminarAgencia(View view) {
        final String idReservacion = editTextIdAgencia.getText().toString();

        // Realizar la consulta para verificar si la reservación existe
        String[] projection = {"id_agencia"};
        String selection = "id_agencia = ?";
        String[] selectionArgs = {idReservacion};
        Cursor cursor = database.query("agencia_viajes", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Estás seguro de que deseas eliminar la agencia de viajes con ID: " + idReservacion + "?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Eliminar la agencia de viajes de la base de datos
                            String whereClause = "id_agencia = ?";
                            String[] whereArgs = {idReservacion};
                            int rowsAffected = database.delete("agencia_viajes", whereClause, whereArgs);

                            if (rowsAffected > 0) {
                                Toast.makeText(AgenciaEliminarActivity.this, "Agencia de viajes eliminada correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AgenciaEliminarActivity.this, "Error al eliminar la agencia de viajes", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Cancelar la eliminación de la agencia de viajes
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        } else {
            Toast.makeText(this, "La agencia de viajes no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }
}
