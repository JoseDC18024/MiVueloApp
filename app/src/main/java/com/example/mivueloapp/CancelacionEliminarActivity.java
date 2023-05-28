package com.example.mivueloapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CancelacionEliminarActivity extends AppCompatActivity {
    private EditText idCancelacionEditText;
    private Button eliminarButton;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelacion_eliminar);

        idCancelacionEditText = findViewById(R.id.idCancelacionEditText);
        eliminarButton = findViewById(R.id.eliminarButton);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        eliminarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarDatos();
            }
        });
    }

    private void eliminarDatos() {
        String idCancelacion = idCancelacionEditText.getText().toString().trim();

        if (idCancelacion.isEmpty()) {
            Toast.makeText(this, "Ingresa un id_cancelacion", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si el id_cancelacion existe en la base de datos
        Cursor cursor = database.rawQuery("SELECT id_cancelacion FROM cancelacion WHERE id_cancelacion = ?", new String[]{idCancelacion});
        if (!cursor.moveToFirst()) {
            Toast.makeText(this, "El id_cancelacion no existe en la base de datos", Toast.LENGTH_SHORT).show();
            cursor.close();
            return;
        }
        cursor.close();

        // Eliminar los datos de la base de datos
        database.execSQL("DELETE FROM cancelacion WHERE id_cancelacion = ?", new Object[]{idCancelacion});

        Toast.makeText(this, "Datos eliminados correctamente", Toast.LENGTH_SHORT).show();

        // Limpiar el campo de entrada
        idCancelacionEditText.setText("");
    }
}
