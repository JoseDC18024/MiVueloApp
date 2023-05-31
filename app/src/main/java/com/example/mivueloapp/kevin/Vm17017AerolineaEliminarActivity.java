package com.example.mivueloapp.kevin;

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

public class Vm17017AerolineaEliminarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextIdAerolinea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vm17017_aerolinea_eliminar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencia del EditText
        editTextIdAerolinea = findViewById(R.id.editTextIdAerolinea);
    }

    public void eliminarAerolinea(View view) {
        final String idAerolinea = editTextIdAerolinea.getText().toString();

        // Realizar la consulta para verificar si el boleto existe
        String[] projection = {"id_aerolinea"};
        String selection = "id_aerolinea = ?";
        String[] selectionArgs = {idAerolinea};
        Cursor cursor = database.query("aerolinea", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Estás seguro de que deseas eliminar la aerolinea con ID: " + idAerolinea + "?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Eliminar el avion de la base de datos
                            String whereClause = "id_aerolinea = ?";
                            String[] whereArgs = {idAerolinea};
                            int rowsAffected = database.delete("aerolinea", whereClause, whereArgs);

                            if (rowsAffected > 0) {
                                Toast.makeText(Vm17017AerolineaEliminarActivity.this, "Avion eliminado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Vm17017AerolineaEliminarActivity.this, "Error al eliminar el avion", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Cancelar la eliminación del avion
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        } else {
            Toast.makeText(Vm17017AerolineaEliminarActivity.this, "La aerolinea no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }
}