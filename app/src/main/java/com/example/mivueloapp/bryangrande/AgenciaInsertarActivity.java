package com.example.mivueloapp.bryangrande;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

public class AgenciaInsertarActivity extends AppCompatActivity {

    private EditText idAgenciaEditText, nombreAgenciaEditText, direccionAgenciaEditText;
    private Button insertarButton;

    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agencia_insertar);

        idAgenciaEditText = findViewById(R.id.id_agencia_edit);
        nombreAgenciaEditText = findViewById(R.id.nombre_agencia_edit);
        direccionAgenciaEditText = findViewById(R.id.direccion_agencia_edit);
        insertarButton = findViewById(R.id.insertarA_button);

        // Inicializar la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        insertarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idAgencia = idAgenciaEditText.getText().toString();
                String nombreAgencia = nombreAgenciaEditText.getText().toString();
                String direccionAgencia = direccionAgenciaEditText.getText().toString();

                // Insertar datos en la tabla 'agencia_viajes'
                ContentValues values = new ContentValues();
                values.put("id_agencia", idAgencia);
                values.put("nombre_agencia", nombreAgencia);
                values.put("direccion_agencia", direccionAgencia);

                long result = database.insert("agencia_viajes", null, values);

                if (result == -1) {
                    Toast.makeText(AgenciaInsertarActivity.this, "Error al insertar los datos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AgenciaInsertarActivity.this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
