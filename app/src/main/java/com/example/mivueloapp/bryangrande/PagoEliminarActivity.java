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

public class PagoEliminarActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextIdPago;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_eliminar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencia del EditText
        editTextIdPago = findViewById(R.id.editTextIdPago);
    }

    public void eliminarPago(View view) {
        final String idPago = editTextIdPago.getText().toString();

        // Realizar la consulta para verificar si el pago existe
        String[] projection = {"id_pago"};
        String selection = "id_pago = ?";
        String[] selectionArgs = {idPago};
        Cursor cursor = database.query("pago", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Estás seguro de que deseas eliminar el pago con ID: " + idPago + "?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Eliminar el pago de la base de datos
                            String whereClause = "id_pago = ?";
                            String[] whereArgs = {idPago};
                            int rowsAffected = database.delete("pago", whereClause, whereArgs);

                            if (rowsAffected > 0) {
                                Toast.makeText(PagoEliminarActivity.this, "Pago eliminado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PagoEliminarActivity.this, "Error al eliminar el pago", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Cancelar la eliminación del pago
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
        } else {
            Toast.makeText(this, "El pago no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }
}
