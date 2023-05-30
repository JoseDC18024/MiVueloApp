package com.example.mivueloapp.vlad;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

import java.util.ArrayList;
import java.util.List;

public class UsuarioConsultarActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private ListView listViewUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_consultar);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getReadableDatabase();

        // Obtener referencia del ListView
        listViewUsuarios = findViewById(R.id.listViewUsuarios);

        // Consultar los boletos de la base de datos
        List<String> listaUsuarios = consultarUsuarios();

        // Crear un ArrayAdapter para mostrar los boletos en el ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaUsuarios);

        // Asignar el ArrayAdapter al ListView
        listViewUsuarios.setAdapter(adapter);
    }
    @SuppressLint("Range")
    private List<String> consultarUsuarios() {
        List<String> listaUsuarios = new ArrayList<>();

        // Realizar la consulta para obtener todos los boletos
        String[] projection = {"id_usuario", "email", "pasaporte", "contrasena"};
        Cursor cursor = database.query("Usuario", projection, null, null, null, null, null);

        // Iterar sobre el cursor y agregar los boletos a la lista
        while (cursor.moveToNext()) {
            String idUsuario = cursor.getString(cursor.getColumnIndex("id_usuario"));
            String email = cursor.getString(cursor.getColumnIndex("email"));
            String pasaporte = String.valueOf(cursor.getInt(cursor.getColumnIndex("pasaporte")));
            String contrasena = cursor.getString(cursor.getColumnIndex("contrasena"));

            String usuario = "ID: " + idUsuario + "\n" +
                    "Email: " + email + "\n" +
                    "Numero de pasaporte" + pasaporte + "\n" +
                    "contraseña: " + contrasena;

            listaUsuarios.add(usuario);
        }

        cursor.close();

        return listaUsuarios;
    }

}