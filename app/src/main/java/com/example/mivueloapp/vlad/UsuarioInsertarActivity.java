package com.example.mivueloapp.vlad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

public class UsuarioInsertarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    EditText editIdUsuario;
    EditText editEmail;
    EditText editPasaporte;
    EditText editContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_insertar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        // Obtener referencias de los elementos de la interfaz de usuario
        editIdUsuario = (EditText) findViewById(R.id.editTextIdUsuario);
        editEmail = (EditText) findViewById(R.id.editTextEmail);
        editPasaporte = (EditText) findViewById(R.id.editTextPasaporte);
        editContrasena = (EditText) findViewById(R.id.editTextContrasena);
    }

    public void insertarUsuario(View view) {
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

        // Insertar los valores en la tabla "pasajero"
        long resultado = database.insert("Usuario", null, values);

        if (resultado != -1) {
            Toast.makeText(this, "usuario insertado correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error al insertar el usuario", Toast.LENGTH_SHORT).show();
        }
    }
}