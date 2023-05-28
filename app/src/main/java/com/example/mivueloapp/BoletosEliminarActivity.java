package com.example.mivueloapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BoletosEliminarActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextIdBoleto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boletos_eliminar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencia del EditText
        editTextIdBoleto = findViewById(R.id.editTextIdBoleto);
    }

    public void eliminarBoleto(View view) {
        final String idBoleto = editTextIdBoleto.getText().toString();

        // Realizar la consulta para verificar si el boleto existe
        String[] projection = {"id_boleto"};
        String selection = "id_boleto = ?";
        String[] selectionArgs = {idBoleto};
        Cursor cursor = database.query("boleto", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Estás seguro de que deseas eliminar el boleto con ID: " + idBoleto + "?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Eliminar el boleto de la base de datos
                            String whereClause = "id_boleto = ?";
                            String[] whereArgs = {idBoleto};
                            int rowsAffected = database.delete("boleto", whereClause, whereArgs);

                            if (rowsAffected > 0) {
                                Toast.makeText(BoletosEliminarActivity.this, "Boleto eliminado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(BoletosEliminarActivity.this, "Error al eliminar el boleto", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Cancelar la eliminación del boleto
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        } else {
            Toast.makeText(this, "El boleto no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }
}




