package com.example.mivueloapp.joselucero;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TripulanteInsertarActivity extends AppCompatActivity {
    private EditText idTripulanteEditText;
    private EditText nombreTripulanteEditText;
    private EditText campoEditText;
    private Button insertarButton;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripulante_insertar);

        idTripulanteEditText = findViewById(R.id.idTripulanteEditText);
        nombreTripulanteEditText = findViewById(R.id.nTripulanteEditText);
        campoEditText = findViewById(R.id.campEditText);
        insertarButton = findViewById(R.id.insertarButton);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        insertarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos ingresados por el usuario
                String idTripulante = idTripulanteEditText.getText().toString();
                String nombreTripulante = nombreTripulanteEditText.getText().toString();
                String campo = campoEditText.getText().toString();

                // Insertar los datos en la tabla "tripulante"
                boolean resultado = insertarDatosTripulante(idTripulante, nombreTripulante, campo);

                if (resultado) {
                    Toast.makeText(TripulanteInsertarActivity.this, "Datos insertados correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TripulanteInsertarActivity.this, "Error al insertar los datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean insertarDatosTripulante(String idTripulante, String nombreTripulante, String campo) {
        ContentValues values = new ContentValues();
        values.put("id_tripulante", idTripulante);
        values.put("nombre_tripulante", nombreTripulante);
        values.put("campo", campo);

        long resultado = database.insert("tripulante", null, values);

        return resultado != -1;
    }
}
