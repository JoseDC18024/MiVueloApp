package com.example.mivueloapp.vlad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
            editIdUsuario.setEnabled(true);
            editEmail.setEnabled(true);
            editPasaporte.setEnabled(true);
            editContrasena.setEnabled(true);
            editIdUsuario.setText(cursor.getString(cursor.getColumnIndex("id_usuario")));
            editEmail.setText(cursor.getString(cursor.getColumnIndex("email")));
            editPasaporte.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex("pasaporte"))));
            editContrasena.setText(cursor.getString(cursor.getColumnIndex("contrasena")));
            findViewById(R.id.btnActualizar).setEnabled(true);
        } else {
            // El usuario no fue encontrado, mostrar un mensaje de error
            Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
    }

    public void actualizarUsuario(View view)  {
        String id= editIdUsuario.getText().toString();
        String email= editEmail.getText().toString();
        String pasaporte= editPasaporte.getText().toString();
        String contrasena= editContrasena.getText().toString();

        // Crear un objeto ContentValues para almacenar los valores a insertar
        ContentValues values = new ContentValues();
        values.put("id_usuario", id);
        values.put("email", email);
        values.put("pasaporte", pasaporte);
        values.put("contrasena", contrasena);

        String whereClause = "id_usuario = ?";
        String[] whereArgs = {id};
        int rowsAffected = database.update("Usuario", values, whereClause, whereArgs);

        if (rowsAffected > 0) {
            Toast.makeText(this, "usuario insertado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al insertar el usuario", Toast.LENGTH_SHORT).show();
        }
    }
}