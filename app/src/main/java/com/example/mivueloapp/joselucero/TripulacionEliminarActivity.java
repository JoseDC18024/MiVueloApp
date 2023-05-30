package com.example.mivueloapp.joselucero;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

public class TripulacionEliminarActivity extends AppCompatActivity {
    private EditText numeroTripulanteEditText;
    private Button eliminarButton;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripulacion_eliminar);

        numeroTripulanteEditText = findViewById(R.id.numeroTripulanteEditText);
        eliminarButton = findViewById(R.id.eliminarButton);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        eliminarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el número de tripulante ingresado por el usuario
                String numeroTripulante = numeroTripulanteEditText.getText().toString();

                // Verificar si el número de tripulante existe en la base de datos
                boolean existeNumeroTripulante = verificarNumeroTripulante(numeroTripulante);

                if (existeNumeroTripulante) {
                    // Mostrar un cuadro de diálogo de confirmación antes de eliminar los datos
                    mostrarDialogoConfirmacionEliminar(numeroTripulante);
                } else {
                    Toast.makeText(TripulacionEliminarActivity.this, "El número de tripulante no existe", Toast.LENGTH_SHORT).show();
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

    private void mostrarDialogoConfirmacionEliminar(final String numeroTripulante) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar datos");
        builder.setMessage("¿Está seguro de que desea eliminar los datos de la tripulación?");

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Eliminar los datos de la tripulación en la tabla "tripulacion_vuelo"
                String whereClause = "numero_tripulante = ?";
                String[] whereArgs = {numeroTripulante};

                int rowsAffected = database.delete("tripulacion_vuelo", whereClause, whereArgs);

                if (rowsAffected > 0) {
                    Toast.makeText(TripulacionEliminarActivity.this, "Datos de la tripulación eliminados correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TripulacionEliminarActivity.this, "Error al eliminar los datos de la tripulación", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
