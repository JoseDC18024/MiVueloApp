package com.example.mivueloapp.vlad;

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

public class ReclamoEliminarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextIdReclamo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamo_eliminar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencia del EditText
        editTextIdReclamo = findViewById(R.id.editTextIdReclamo);
    }

    public void eliminarReclamo(View view) {
        final String idReclamo = editTextIdReclamo.getText().toString();

        // Realizar la consulta para verificar si el boleto existe
        String[] projection = {"id_reclamo"};
        String selection = "id_reclamo = ?";
        String[] selectionArgs = {idReclamo};
        Cursor cursor = database.query("Reclamo", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Estás seguro de que deseas eliminar el reclamo con ID: " + idReclamo + "?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Eliminar el boleto de la base de datos
                            String whereClause = "id_reclamo = ?";
                            String[] whereArgs = {idReclamo};
                            int rowsAffected = database.delete("Reclamo", whereClause, whereArgs);

                            if (rowsAffected > 0) {
                                Toast.makeText(ReclamoEliminarActivity.this, "reclamo eliminado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ReclamoEliminarActivity.this, "Error al eliminar el reclamo", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Cancelar la eliminación
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        } else {
            Toast.makeText(this, "El reclamo no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }
}