package com.example.mivueloapp.joselucero;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

public class TripulanteEliminarActivity extends AppCompatActivity {
    private EditText idTripulanteEditText;
    private Button eliminarButton;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripulante_eliminar);

        idTripulanteEditText = findViewById(R.id.idTripulanteEditText);
        eliminarButton = findViewById(R.id.eliminarButton);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        eliminarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el ID de tripulante ingresado por el usuario
                String idTripulante = idTripulanteEditText.getText().toString();

                // Verificar si el ID de tripulante existe en la base de datos
                boolean existeIdTripulante = verificarIdTripulante(idTripulante);

                if (existeIdTripulante) {
                    // Mostrar un cuadro de diálogo de confirmación antes de eliminar los datos
                    mostrarDialogoConfirmacionEliminar(idTripulante);
                } else {
                    Toast.makeText(TripulanteEliminarActivity.this, "El ID de tripulante no existe", Toast.LENGTH_SHORT).show();
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

    private void mostrarDialogoConfirmacionEliminar(final String idTripulante) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar datos");
        builder.setMessage("¿Está seguro de que desea eliminar los datos del tripulante?");

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Eliminar los datos del tripulante en la tabla "tripulante"
                String whereClause = "id_tripulante = ?";
                String[] whereArgs = {idTripulante};

                int rowsAffected = database.delete("tripulante", whereClause, whereArgs);

                if (rowsAffected > 0) {
                    Toast.makeText(TripulanteEliminarActivity.this, "Datos del tripulante eliminados correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TripulanteEliminarActivity.this, "Error al eliminar los datos del tripulante", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
