package com.example.mivueloapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mivueloapp.s3db";
    private static final int DATABASE_VERSION = 1;

    // Sentencia SQL para crear la tabla "boleto"
    private static final String CREATE_TABLE_BOLETO = "CREATE TABLE boleto (id_boleto CHAR(11) NOT NULL, " +
            "clase_boleto CHAR(30) NOT NULL, precio_boleto INTEGER NOT NULL, ubicacion_asiento CHAR(30) NOT NULL);";

    // Sentencia SQL para crear la tabla "estado_vuelo"
    private static final String CREATE_TABLE_ESTADO_VUELO = "CREATE TABLE estado_vuelo (id_estado CHAR(11) NOT NULL, " +
            "descripcion_estado CHAR(50) NOT NULL, tiempo_retraso CHAR(30) NOT NULL);";

    // Sentencia SQL para crear la tabla "cancelacion"
    private static final String CREATE_TABLE_CANCELACION = "CREATE TABLE cancelacion (id_cancelacion CHAR(11) NOT NULL, " +
            "motivo_cancelacion CHAR(30) NOT NULL, hasta_fecha CHAR(10) NOT NULL, desde_fecha CHAR(30) NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BOLETO);
        db.execSQL(CREATE_TABLE_ESTADO_VUELO);
        db.execSQL(CREATE_TABLE_CANCELACION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes realizar acciones si la base de datos necesita ser actualizada en futuras versiones
    }
}
