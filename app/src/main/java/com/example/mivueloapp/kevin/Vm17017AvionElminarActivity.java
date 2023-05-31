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
import com.example.mivueloapp.joseduran.BoletosEliminarActivity;

public class Vm17017AvionElminarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextIdAvion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vm17017_avion_elminar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencia del EditText
        editTextIdAvion = findViewById(R.id.editTextIdAvion);
    }

    public void eliminarAvion(View view) {
        final String idAvion = editTextIdAvion.getText().toString();

        // Realizar la consulta para verificar si el boleto existe
        String[] projection = {"id_avion"};
        String selection = "id_avion = ?";
        String[] selectionArgs = {idAvion};
        Cursor cursor = database.query("avion", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Estás seguro de que deseas eliminar el avion con ID: " + idAvion + "?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Eliminar el avion de la base de datos
                            String whereClause = "id_avion = ?";
                            String[] whereArgs = {idAvion};
                            int rowsAffected = database.delete("avion", whereClause, whereArgs);

                            if (rowsAffected > 0) {
                                Toast.makeText(Vm17017AvionElminarActivity.this, "Avion eliminado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Vm17017AvionElminarActivity.this, "Error al eliminar el avion", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(Vm17017AvionElminarActivity.this, "El avion no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }
}