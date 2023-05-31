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

public class Vm17017CupoEliminarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextIdCupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vm17017_cupo_eliminar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencia del EditText
        editTextIdCupo = findViewById(R.id.editTextIdCupo);
    }

    public void eliminarCupo(View view) {
        final String idCupo = editTextIdCupo.getText().toString();

        // Realizar la consulta para verificar si el boleto existe
        String[] projection = {"id_cupo"};
        String selection = "id_cupo = ?";
        String[] selectionArgs = {idCupo};
        Cursor cursor = database.query("cupo", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Estás seguro de que deseas eliminar el cupo con ID: " + idCupo + "?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Eliminar el cupo de la base de datos
                            String whereClause = "id_cupo = ?";
                            String[] whereArgs = {idCupo};
                            int rowsAffected = database.delete("cupo", whereClause, whereArgs);

                            if (rowsAffected > 0) {
                                Toast.makeText(Vm17017CupoEliminarActivity.this, "Cupo eliminado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Vm17017CupoEliminarActivity.this, "Error al eliminar el Cupo", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Cancelar la eliminación del cupo
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        } else {
            Toast.makeText(this, "El cupo no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }
}