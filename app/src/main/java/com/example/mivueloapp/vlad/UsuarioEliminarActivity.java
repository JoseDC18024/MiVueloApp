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

public class UsuarioEliminarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private EditText editTextIdUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_eliminar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencia del EditText
        editTextIdUsuario = findViewById(R.id.editTextIdUsuario);
    }

    public void eliminarUsuario(View view) {
        final String idUsuario = editTextIdUsuario.getText().toString();

        // Realizar la consulta para verificar si el boleto existe
        String[] projection = {"id_usuario"};
        String selection = "id_usuario = ?";
        String[] selectionArgs = {idUsuario};
        Cursor cursor = database.query("Usuario", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("¿Estás seguro de que deseas eliminar el usuario con ID: " + idUsuario + "?")
                    .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Eliminar el boleto de la base de datos
                            String whereClause = "id_usuario = ?";
                            String[] whereArgs = {idUsuario};
                            int rowsAffected = database.delete("Usuario", whereClause, whereArgs);

                            if (rowsAffected > 0) {
                                Toast.makeText(UsuarioEliminarActivity.this, "usuario eliminado correctamente", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(UsuarioEliminarActivity.this, "Error al eliminar el usuario", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }
}