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

public class VueloEliminarActivity extends AppCompatActivity {
    private EditText idVueloEditText;
    private Button eliminarButton;

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vuelo_eliminar);

        idVueloEditText = findViewById(R.id.idVueloEditText);
        eliminarButton = findViewById(R.id.eliminarButton);

        // Crear instancia del DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtener una referencia a la base de datos (esto creará la base de datos si no existe)
        database = databaseHelper.getWritableDatabase();

        eliminarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener el ID de vuelo ingresado por el usuario
                String idVuelo = idVueloEditText.getText().toString();

                // Verificar si el ID de vuelo existe en la base de datos
                boolean existeIdVuelo = verificarIdVuelo(idVuelo);

                if (existeIdVuelo) {
                    // Mostrar un cuadro de diálogo de confirmación antes de eliminar los datos
                    mostrarDialogoConfirmacionEliminar(idVuelo);
                } else {
                    Toast.makeText(VueloEliminarActivity.this, "El ID de vuelo no existe", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean verificarIdVuelo(String idVuelo) {
        String[] columns = {"id_vuelo"};
        String selection = "id_vuelo = ?";
        String[] selectionArgs = {idVuelo};

        Cursor cursor = database.query("vuelo", columns, selection, selectionArgs, null, null, null);
        boolean existeIdVuelo = cursor.moveToFirst();
        cursor.close();

        return existeIdVuelo;
    }

    private void mostrarDialogoConfirmacionEliminar(final String idVuelo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar datos");
        builder.setMessage("¿Está seguro de que desea eliminar los datos de vuelo?");

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Eliminar los datos de vuelo de la tabla "vuelo"
                String whereClause = "id_vuelo = ?";
                String[] whereArgs = {idVuelo};
                int rowsAffected = database.delete("vuelo", whereClause, whereArgs);

                if (rowsAffected > 0) {
                    Toast.makeText(VueloEliminarActivity.this, "Datos de vuelo eliminados correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(VueloEliminarActivity.this, "Error al eliminar los datos de vuelo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
