package com.example.mivueloapp.joseduran;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

public class CancelacionActualizarActivity extends AppCompatActivity {
    private EditText idCancelacionEditText;
    private EditText motivoCancelacionEditText;
    private EditText hastaFechaEditText;
    private EditText desdeFechaEditText;
    private EditText editTextBuscarIdCancelacion;
    private Button actualizarButton;
    private Button buscarIDcancelacion;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancelacion_actualizar);

        idCancelacionEditText = findViewById(R.id.idCancelacionEditText);
        editTextBuscarIdCancelacion = findViewById(R.id.editTextBuscarIdCancelacion);
        motivoCancelacionEditText = findViewById(R.id.motivoCancelacionEditText);
        hastaFechaEditText = findViewById(R.id.hastaFechaEditText);
        desdeFechaEditText = findViewById(R.id.desdeFechaEditText);
        actualizarButton = findViewById(R.id.actualizarButton);
        buscarIDcancelacion = findViewById(R.id.btnBuscarCancelacion);
        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        // Obtener instancia de la base de datos
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();
    }

    @SuppressLint("Range")
    public void buscarCancelacion(View view) {
        String idCancelacion = editTextBuscarIdCancelacion.getText().toString();

        // Realizar la consulta para obtener los datos del boleto
        String[] projection = {"id_cancelacion", "motivo_cancelacion", "hasta_fecha", "desde_fecha"};
        String selection = "id_cancelacion = ?";
        String[] selectionArgs = {idCancelacion};
        Cursor cursor = database.query("cancelacion", projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            // La cancelacion fue encontrada, habilitar la edición y mostrar los datos
            idCancelacionEditText.setEnabled(true);
            motivoCancelacionEditText.setEnabled(true);
            hastaFechaEditText.setEnabled(true);
            desdeFechaEditText.setEnabled(true);
            idCancelacionEditText.setText(cursor.getString(cursor.getColumnIndex("id_cancelacion")));
            motivoCancelacionEditText.setText(cursor.getString(cursor.getColumnIndex("motivo_cancelacion")));
            hastaFechaEditText.setText(String.valueOf(cursor.getColumnIndex("hasta_fecha")));
            desdeFechaEditText.setText(String.valueOf(cursor.getColumnIndex("desde_fecha")));
            findViewById(R.id.actualizarButton).setEnabled(true);
        } else {
            // La Cancelacion no fue encontrada, mostrar un mensaje de error
            Toast.makeText(this, "La Cancelacion no existe", Toast.LENGTH_SHORT).show();
        }

        cursor.close();


        actualizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idCancelacion = idCancelacionEditText.getText().toString();

                // Verificar si el id_cancelacion existe en la base de datos
                if (existeCancelacion(idCancelacion)) {
                    String motivoCancelacion = motivoCancelacionEditText.getText().toString();
                    String hastaFecha = hastaFechaEditText.getText().toString();
                    String desdeFecha = desdeFechaEditText.getText().toString();

                    // Actualizar los datos en la tabla cancelacion
                    actualizarCancelacion(idCancelacion, motivoCancelacion, hastaFecha, desdeFecha);

                    Toast.makeText(CancelacionActualizarActivity.this, "Datos actualizados correctamente.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CancelacionActualizarActivity.this, "El ID de cancelación no existe en la base de datos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean existeCancelacion(String idCancelacion) {
        Cursor cursor = database.rawQuery("SELECT * FROM cancelacion WHERE id_cancelacion=?", new String[]{idCancelacion});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    private void actualizarCancelacion(String idCancelacion, String motivoCancelacion, String hastaFecha, String desdeFecha) {
        database.execSQL("UPDATE cancelacion SET motivo_cancelacion=?, hasta_fecha=?, desde_fecha=?", new String[]{motivoCancelacion, hastaFecha, desdeFecha});
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
