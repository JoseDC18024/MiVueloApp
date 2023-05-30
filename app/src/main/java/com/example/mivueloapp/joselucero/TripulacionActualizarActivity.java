package com.example.mivueloapp.joselucero;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.os.Bundle;
import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TripulacionActualizarActivity extends AppCompatActivity {
    private EditText numeroTripulanteEditText;
    private EditText puestoTripulacionEditText;
    private Button actualizarButton;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripulacion_actualizar);

        numeroTripulanteEditText = findViewById(R.id.numeroTripulanteEditText);
        puestoTripulacionEditText = findViewById(R.id.puestoTripulacionEditText);
        actualizarButton = findViewById(R.id.actualizarButton);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        actualizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el número de tripulante y el puesto de tripulación ingresados por el usuario
                String numeroTripulante = numeroTripulanteEditText.getText().toString();
                String nuevoPuesto = puestoTripulacionEditText.getText().toString();

                // Verificar si el número de tripulante existe en la base de datos
                boolean existeNumeroTripulante = verificarNumeroTripulante(numeroTripulante);

                if (existeNumeroTripulante) {
                    // Mostrar un cuadro de diálogo de confirmación antes de actualizar los datos
                    mostrarDialogoConfirmacionActualizar(numeroTripulante, nuevoPuesto);
                } else {
                    Toast.makeText(TripulacionActualizarActivity.this, "El número de tripulante no existe", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean verificarNumeroTripulante(String numeroTripulante) {
        String[] columns = {"numero_tripulante"};
        String selection = "numero_tripulante = ?";
        String[] selectionArgs = {numeroTripulante};

        Cursor cursor = database.query("tripulacion_vuelo", columns, selection, selectionArgs, null, null, null);
        boolean existeNumeroTripulante = cursor.moveToFirst();
        cursor.close();

        return existeNumeroTripulante;
    }

    private void mostrarDialogoConfirmacionActualizar(final String numeroTripulante, final String nuevoPuesto) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Actualizar datos");
        builder.setMessage("¿Está seguro de que desea actualizar los datos de tripulación?");

        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Actualizar el puesto de tripulación en la tabla "tripulacion_vuelo"
                String whereClause = "numero_tripulante = ?";
                String[] whereArgs = {numeroTripulante};

                ContentValues values = new ContentValues();
                values.put("puesto_tripulacion", nuevoPuesto);

                int rowsAffected = database.update("tripulacion_vuelo", values, whereClause, whereArgs);

                if (rowsAffected > 0) {
                    Toast.makeText(TripulacionActualizarActivity.this, "Datos de tripulación actualizados correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TripulacionActualizarActivity.this, "Error al actualizar los datos de tripulación", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
