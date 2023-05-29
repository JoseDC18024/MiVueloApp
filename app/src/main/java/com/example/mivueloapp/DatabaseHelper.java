package com.example.mivueloapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mivueloapp.s3db";
    private static final int DATABASE_VERSION = 1;

    // Sentencia SQL para crear la tabla "avion"
    private static final String CREATE_TABLE_AVION = "CREATE TABLE IF NOT EXISTS avion (id_avion CHAR(11) NOT NULL, " +
            "modelo_avion CHAR(30) NOT NULL, año_fabricacion INTEGER NOT NULL);";

    // Sentencia SQL para crear la tabla "boleto"
    private static final String CREATE_TABLE_BOLETO = "CREATE TABLE boleto (id_boleto CHAR(11) NOT NULL, " +
            "clase_boleto CHAR(30) NOT NULL, precio_boleto INTEGER NOT NULL, ubicacion_asiento CHAR(30) NOT NULL);";

    // Sentencia SQL para crear la tabla "estado_vuelo"
    private static final String CREATE_TABLE_ESTADO_VUELO = "CREATE TABLE estado_vuelo (id_estado CHAR(11) NOT NULL, " +
            "descripcion_estado CHAR(50) NOT NULL, tiempo_retraso CHAR(30) NOT NULL);";

    // Sentencia SQL para crear la tabla "cancelacion"
    private static final String CREATE_TABLE_CANCELACION = "CREATE TABLE cancelacion (id_cancelacion CHAR(11) NOT NULL, " +
            "motivo_cancelacion CHAR(30) NOT NULL, hasta_fecha CHAR(10) NOT NULL, desde_fecha CHAR(30) NOT NULL);";

    private static final String CREATE_TABLE_PASAJERO = "CREATE TABLE Pasajero (id_pasajero VARCHAR(50) PRIMARY KEY,nombre_pasajero VARCHAR(100),fecha_nacimiento DATE,genero_pasajero VARCHAR(10));";

    private static final String CREATE_TABLE_RECLAMO = "CREATE TABLE Reclamo (\n" +
            "  id_reclamo VARCHAR(50) PRIMARY KEY,\n" +
            "  fecha_reclamo DATE,\n" +
            "  descripcion_reclamo VARCHAR(255),\n" +
            "  estado VARCHAR(20),\n" +
            "  campo VARCHAR(50)\n" +
            ");";

    private static final String CREATE_TABLE_USUARIO = "CREATE TABLE Usuario (\n" +
            "  id_usuario VARCHAR(50) PRIMARY KEY,\n" +
            "  email VARCHAR(100),\n" +
            "  pasaporte VARCHAR(50),\n" +
            "  contrasena VARCHAR(50)\n" +
            ");\n";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BOLETO);
        db.execSQL(CREATE_TABLE_ESTADO_VUELO);
        db.execSQL(CREATE_TABLE_CANCELACION);
        db.execSQL(CREATE_TABLE_AVION);
        db.execSQL(CREATE_TABLE_PASAJERO);
        db.execSQL(CREATE_TABLE_RECLAMO);
        db.execSQL(CREATE_TABLE_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes realizar acciones si la base de datos necesita ser actualizada en futuras versiones
    }
}
