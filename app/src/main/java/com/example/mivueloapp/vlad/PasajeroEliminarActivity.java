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
import com.example.mivueloapp.joseduran.BoletosEliminarActivity;

public class PasajeroEliminarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextIdPasajero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasajero_eliminar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencia del EditText
        editTextIdPasajero = findViewById(R.id.editTextIdPasajero);
    }

    public void eliminarBoleto(View view) {
        final String idPasajero = editTextIdPasajero.getText().toString();

        // Realizar la consulta para verificar si el boleto existe
        String[] projection = {"id_pasajero"};
        String selection = "id_pasajero = ?";
        String[] selectionArgs = {idPasajero};
        Cursor cursor = database.query("pasajero", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Estás seguro de que deseas eliminar el pasajero con ID: " + idPasajero + "?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Eliminar el boleto de la base de datos
                            String whereClause = "id_pasajero = ?";
                            String[] whereArgs = {idPasajero};
                            int rowsAffected = database.delete("boleto", whereClause, whereArgs);

                            if (rowsAffected > 0) {
                                Toast.makeText(PasajeroEliminarActivity.this, "pasajero eliminado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PasajeroEliminarActivity.this, "Error al eliminar el pasajero", Toast.LENGTH_SHORT).show();
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