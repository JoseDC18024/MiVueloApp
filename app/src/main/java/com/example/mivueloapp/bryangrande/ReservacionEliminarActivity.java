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

public class ReservacionEliminarActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextIdReservacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservacion_eliminar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencia del EditText
        editTextIdReservacion = findViewById(R.id.editTextIdReservacion);
    }

    public void eliminarReservacion(View view) {
        final String idReservacion = editTextIdReservacion.getText().toString();

        // Realizar la consulta para verificar si la reservación existe
        String[] projection = {"id_reservacion"};
        String selection = "id_reservacion = ?";
        String[] selectionArgs = {idReservacion};
        Cursor cursor = database.query("reservacion", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Estás seguro de que deseas eliminar la reservación con ID: " + idReservacion + "?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Eliminar la reservación de la base de datos
                            String whereClause = "id_reservacion = ?";
                            String[] whereArgs = {idReservacion};
                            int rowsAffected = database.delete("reservacion", whereClause, whereArgs);

                            if (rowsAffected > 0) {
                                Toast.makeText(ReservacionEliminarActivity.this, "Reservación eliminada correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ReservacionEliminarActivity.this, "Error al eliminar la reservación", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Cancelar la eliminación de la reservación
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        } else {
            Toast.makeText(this, "La reservación no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }
}
