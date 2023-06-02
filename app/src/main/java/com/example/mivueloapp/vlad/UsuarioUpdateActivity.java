package com.example.mivueloapp.vlad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

public class UsuarioUpdateActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    EditText editIdUsuario;
    EditText editEmail;
    EditText editPasaporte;
    EditText editContrasena;

    EditText editTextBuscarIdUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_update);


        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencias de los elementos de la interfaz de usuario
        editTextBuscarIdUsuario = findViewById(R.id.editTextBuscarIdUsuario);
        editIdUsuario = (EditText) findViewById(R.id.editTextIdUsuario);
        editEmail = (EditText) findViewById(R.id.editTextEmail);
        editPasaporte = (EditText) findViewById(R.id.editTextPasaporte);
        editContrasena = (EditText) findViewById(R.id.editTextContrasena);
    }
    @SuppressLint("Range")
    public void buscarUsuario(View view) {
        String idUsuario = editTextBuscarIdUsuario.getText().toString();

        // Realizar la consulta para obtener los datos del usuaario
        String[] projection = {"id_usuario", "email", "pasaporte", "contrasena"};
        String selection = "id_usuario = ?";
        String[] selectionArgs = {idUsuario};
        Cursor cursor = database.query("Usuario", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // El usuario fue encontrado, habilitar la edición y mostrar los datos
            editIdUsuario.setEnabled(false);
            editEmail.setEnabled(true);
            editPasaporte.setEnabled(true);
            editContrasena.setEnabled(true);
            editIdUsuario.setText(cursor.getString(cursor.getColumnIndex("id_usuario")));
            editEmail.setText(cursor.getString(cursor.getColumnIndex("email")));
            editPasaporte.setText(cursor.getString(cursor.getColumnIndex("pasaporte")));
            editContrasena.setText(cursor.getString(cursor.getColumnIndex("contrasena")));
            findViewById(R.id.btnActualizar).setEnabled(true);
        } else {
            // El usuario no fue encontrado, mostrar un mensaje de error
            Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    public void actualizarUsuario(View view) {
        String id = editIdUsuario.getText().toString();
        String email = editEmail.getText().toString();
        String pasaporte = editPasaporte.getText().toString();
        String contrasena = editContrasena.getText().toString();

        // Validar el formato de correo electrónico utilizando una expresión regular
        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailPattern)) {
            Toast.makeText(this, "El formato del email no es válido", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar el formato del pasaporte utilizando una expresión regular
        String pasaportePattern = "^[A-Za-z][0-9]{6}$";
        if (!pasaporte.matches(pasaportePattern)) {
            Toast.makeText(this, "El formato del pasaporte no es válido. Debe tener el formato X999999", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            database.beginTransaction();

            // Actualizar el usuario en la tabla "Usuario"
            ContentValues usuarioValues = new ContentValues();
            usuarioValues.put("id_usuario", id);
            usuarioValues.put("email", email);
            usuarioValues.put("pasaporte", pasaporte);
            usuarioValues.put("contrasena", contrasena);
            String usuarioWhereClause = "id_usuario = ?";
            String[] usuarioWhereArgs = {id};
            int usuarioRowsAffected = database.update("Usuario", usuarioValues, usuarioWhereClause, usuarioWhereArgs);

            // Actualizar el pasajero en la tabla "Pasajero" si cambia el ID
            ContentValues pasajeroValues = new ContentValues();
            pasajeroValues.put("id_pasajero", id);
            String pasajeroWhereClause = "id_pasajero = ?";
            String[] pasajeroWhereArgs = {id};
            int pasajeroRowsAffected = database.update("Pasajero", pasajeroValues, pasajeroWhereClause, pasajeroWhereArgs);

            if (usuarioRowsAffected > 0 || pasajeroRowsAffected > 0) {
                database.setTransactionSuccessful();
                Toast.makeText(this, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar el usuario", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLiteException e) {
            Toast.makeText(this, "Error en la actualización: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            database.endTransaction();
        }
    }

}