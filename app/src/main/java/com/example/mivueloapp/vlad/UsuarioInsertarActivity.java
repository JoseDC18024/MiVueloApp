package com.example.mivueloapp.vlad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
        String id = editIdUsuario.getText().toString();
        String email = editEmail.getText().toString();
        String pasaporte = editPasaporte.getText().toString();
        String contrasena = editContrasena.getText().toString();

        // Verificar si los campos están vacíos
        if (id.isEmpty() || email.isEmpty() || pasaporte.isEmpty() || contrasena.isEmpty() ){
            Toast.makeText(UsuarioInsertarActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

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

            // Insertar el usuario en la tabla "Usuario"
            ContentValues usuarioValues = new ContentValues();
            usuarioValues.put("id_usuario", id);
            usuarioValues.put("email", email);
            usuarioValues.put("pasaporte", pasaporte);
            usuarioValues.put("contrasena", contrasena);
            long usuarioResultado = database.insert("Usuario", null, usuarioValues);

            // Insertar el pasajero vacío en la tabla "Pasajero" con el mismo ID
            ContentValues pasajeroValues = new ContentValues();
            pasajeroValues.put("id_pasajero", id);
            pasajeroValues.put("nombre_pasajero", "");
            pasajeroValues.put("fecha_nacimiento", "");
            pasajeroValues.put("genero_pasajero", "");
            long pasajeroResultado = database.insert("Pasajero", null, pasajeroValues);

            if (usuarioResultado != -1 && pasajeroResultado != -1) {
                database.setTransactionSuccessful();
                Toast.makeText(this, "Usuario insertado correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al insertar el usuario", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLiteException e) {
            Toast.makeText(this, "Error en la inserción: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            database.endTransaction();
        }
    }

}