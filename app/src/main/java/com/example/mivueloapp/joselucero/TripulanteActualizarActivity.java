package com.example.mivueloapp.joselucero;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TripulanteActualizarActivity extends AppCompatActivity {
    private EditText idTripulanteEditText;
    private EditText nombreTripulanteEditText;
    private EditText campoEditText;
    private Button actualizarButton;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripulante_actualizar);

        idTripulanteEditText = findViewById(R.id.idTripulanteEditText);
        nombreTripulanteEditText = findViewById(R.id.nombreTripulanteEDT);
        campoEditText = findViewById(R.id.campoEDT);
        actualizarButton = findViewById(R.id.actualizarButton);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        actualizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores ingresados por el usuario
                String idTripulante = idTripulanteEditText.getText().toString();
                String nuevoNombreTripulante = nombreTripulanteEditText.getText().toString();
                String nuevoCampo = campoEditText.getText().toString();

                // Verificar si el ID de tripulante existe en la base de datos
                boolean existeIdTripulante = verificarIdTripulante(idTripulante);

                if (existeIdTripulante) {
                    // Actualizar los datos del tripulante en la tabla "tripulante"
                    boolean actualizacionExitosa = actualizarDatosTripulante(idTripulante, nuevoNombreTripulante, nuevoCampo);

                    if (actualizacionExitosa) {
                        Toast.makeText(TripulanteActualizarActivity.this, "Datos del tripulante actualizados correctamente", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(TripulanteActualizarActivity.this, "Error al actualizar los datos del tripulante", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TripulanteActualizarActivity.this, "El ID de tripulante no existe", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean verificarIdTripulante(String idTripulante) {
        String[] columns = {"id_tripulante"};
        String selection = "id_tripulante = ?";
        String[] selectionArgs = {idTripulante};

        Cursor cursor = database.query("tripulante", columns, selection, selectionArgs, null, null, null);
        boolean existeIdTripulante = cursor.moveToFirst();
        cursor.close();

        return existeIdTripulante;
    }

    private boolean actualizarDatosTripulante(String idTripulante, String nuevoNombreTripulante, String nuevoCampo) {
        ContentValues values = new ContentValues();
        values.put("nombre_tripulante", nuevoNombreTripulante);
        values.put("campo", nuevoCampo);

        String whereClause = "id_tripulante = ?";
        String[] whereArgs = {idTripulante};

        int rowsAffected = database.update("tripulante", values, whereClause, whereArgs);

        return rowsAffected > 0;
    }
}
