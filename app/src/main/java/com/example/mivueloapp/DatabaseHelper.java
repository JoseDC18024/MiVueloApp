package com.example.mivueloapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mivueloapp.s3db";
    private static final int DATABASE_VERSION = 1;

    // Sentencia SQL para crear la tabla "cupo"
    private static final String CREATE_TABLE_CUPO = "CREATE TABLE IF NOT EXISTS cupo (id_cupo CHAR(11) NOT NULL, " +
            "cantidad_cupo INTEGER NOT NULL, " +
            "id_vuelo CHAR(11) NOT NULL, " +
            "id_avion CHAR(11) NOT NULL, " +
            "PRIMARY KEY (id_cupo), " +
            "FOREIGN KEY (id_vuelo) REFERENCES vuelo(id_vuelo), " +
            "FOREIGN KEY (id_avion) REFERENCES avion(id_avion));";

    //Tigger para la tabla cupo
    private static final String CREATE_TRIGGER_VALIDAR_CUPO = "CREATE TRIGGER IF NOT EXISTS validar_cupo " +
            "BEFORE INSERT ON cupo " +
            "FOR EACH ROW BEGIN " +
            "SELECT RAISE(ABORT, 'El ID de vuelo no existe') " +
            "WHERE (SELECT COUNT(*) FROM vuelo WHERE id_vuelo = NEW.id_vuelo) = 0; " +
            "SELECT RAISE(ABORT, 'El ID de avion no existe') " +
            "WHERE (SELECT COUNT(*) FROM avion WHERE id_avion = NEW.id_avion) = 0; " +
            "END;";

    // Sentencia SQL para crear la tabla "avion"
    private static final String CREATE_TABLE_AVION = "CREATE TABLE IF NOT EXISTS avion (id_avion CHAR(11) PRIMARY KEY, " +
            "modelo_avion CHAR(30) NOT NULL, año_fabricacion INTEGER NOT NULL, " +
            "id_aerolinea CHAR(11) NOT NULL, " +
            "FOREIGN KEY (id_aerolinea) REFERENCES aerolinea(id_aerolinea));";

    // Tigger para la tabla avion
    private static final String CREATE_TRIGGER_VALIDAR_AEROLINEA = "CREATE TRIGGER IF NOT EXISTS validar_aerolinea " +
            "BEFORE INSERT ON avion " +
            "FOR EACH ROW BEGIN " +
            "SELECT RAISE(ABORT, 'El ID de aerolínea no existe') " +
            "WHERE (SELECT COUNT(*) FROM aerolinea WHERE id_aerolinea = NEW.id_aerolinea) = 0; " +
            "END;";

    // Sentencia SQL para crear la tabla "aerolinea"
    private static final String CREATE_TABLE_AEROLINEA = "CREATE TABLE IF NOT EXISTS aerolinea (id_aerolinea CHAR(11) PRIMARY KEY, " +
            "nombre_aerolinea CHAR(30) NOT NULL, pais_aerolinea CHAR(50) NOT NULL, fecha_aerolinea CHAR(20) NOT NULL);";

    // Sentencia SQL para crear la tabla "boleto"
    private static final String CREATE_TABLE_BOLETO = "CREATE TABLE boleto (id_boleto CHAR(11) PRIMARY KEY, " +
            "clase_boleto CHAR(30) NOT NULL, precio_boleto INTEGER NOT NULL, ubicacion_asiento CHAR(30) NOT NULL);";

    // Sentencia SQL para crear la tabla "estado_vuelo"
    private static final String CREATE_TABLE_ESTADO_VUELO = "CREATE TABLE estado_vuelo (id_estado CHAR(11) PRIMARY KEY, " +
            "descripcion_estado CHAR(50) NOT NULL, tiempo_retraso CHAR(30) NOT NULL);";

    // Sentencia SQL para crear la tabla "cancelacion"
    private static final String CREATE_TABLE_CANCELACION = "CREATE TABLE cancelacion (id_cancelacion CHAR(11) PRIMARY KEY, " +
            "motivo_cancelacion CHAR(30) NOT NULL, hasta_fecha CHAR(10) NOT NULL, desde_fecha CHAR(30) NOT NULL);";

    private static final String CREATE_TABLE_PASAJERO = "CREATE TABLE Pasajero (id_pasajero VARCHAR(50) PRIMARY KEY,nombre_pasajero VARCHAR(100),fecha_nacimiento VARCHAR(10),genero_pasajero VARCHAR(10));";

    private static final String CREATE_TABLE_RECLAMO = "CREATE TABLE Reclamo (\n" +
            "  id_reclamo VARCHAR(50) PRIMARY KEY,\n" +
            "  fecha_reclamo VARCHAR(20),\n" +
            "  descripcion_reclamo VARCHAR(255),\n" +
            "  estado VARCHAR(20)\n" +
            ");";

    private static final String CREATE_TABLE_USUARIO = "CREATE TABLE Usuario (\n" +
            "  id_usuario VARCHAR(50) PRIMARY KEY,\n" +
            "  email VARCHAR(100),\n" +
            "  pasaporte VARCHAR(50),\n" +
            "  contrasena VARCHAR(50)\n" +
            ");\n";

    private static final String CREATE_TABLE_VUELO = "CREATE TABLE vuelo (id_vuelo CHAR(11) PRIMARY KEY, numero_vuelo CHAR(11) NOT NULL, " +
        "fecha_salida CHAR(10) NOT NULL, fecha_llegada CHAR(10) NOT NULL, hora_salida CHAR(10) NOT NULL, hora_llegada CHAR(10) NOT NULL);";

    private static final String CREATE_TABLE_TRIPULACION_VUELO = "CREATE TABLE tripulacion_vuelo (numero_tripulante CHAR(11) PRIMARY KEY, puesto_tripulacion CHAR(50) NOT NULL );";

    private static final String CREATE_TABLE_TRIPULANTE = "CREATE TABLE tripulante (id_tripulante CHAR(11) PRIMARY KEY, nombre_tripulante CHAR(50) NOT NULL, campo CHAR(10) NOT NULL );";

    private static final String CREATE_TABLE_PAGO = "CREATE TABLE pago (id_pago CHAR(11) PRIMARY KEY, fecha_pago CHAR(30) NOT NULL, monto_pago CHAR(10) NOT NULL );";

    private static final String CREATE_TABLE_RESERVACION = "CREATE TABLE  reservacion (id_reservacion CHAR(11) PRIMARY KEY, fecha_reservacion CHAR(30) NOT NULL, numero_asiento INTEGER(10) NOT NULL, estado_reservacion CHAR(30) NOT NULL );";

    private static final String CREATE_TABLE_AGENCIA_VIAJES = "CREATE TABLE agencia_viajes (id_agencia CHAR(11) PRIMARY KEY, nombre_agencia CHAR(30) NOT NULL, direccion_agencia CHAR(60) NOT NULL );";

    //Triggers Vlad
    private static final String crear_pasajero = "CREATE TRIGGER crear_pasajero\n" +
            "AFTER INSERT ON Usuario\n" +
            "FOR EACH ROW\n" +
            "BEGIN\n" +
            "  DECLARE count_pasajero INT;\n" +
            "  \n" +
            "  -- Verificar si ya existe un pasajero con el mismo ID\n" +
            "  SELECT COUNT(*) INTO count_pasajero FROM Pasajero WHERE id_pasajero = NEW.id_usuario;\n" +
            "  \n" +
            "  -- Insertar un nuevo pasajero si no existe\n" +
            "  IF count_pasajero = 0 THEN\n" +
            "    INSERT INTO Pasajero (id_pasajero, nombre_pasajero, fecha_nacimiento, genero_pasajero)\n" +
            "    VALUES (NEW.id_usuario, '', '', '');\n" +
            "  END IF;\n" +
            "END;";

    private static final String evitar_duplicado_reclamo = "CREATE TRIGGER evitar_duplicado_reclamo\n" +
            "BEFORE INSERT ON Reclamo\n" +
            "FOR EACH ROW\n" +
            "BEGIN\n" +
            "  DECLARE count_reclamo INT;\n" +
            "  \n" +
            "  -- Verificar si ya existe un reclamo con el mismo ID\n" +
            "  SELECT COUNT(*) INTO count_reclamo FROM Reclamo WHERE id_reclamo = NEW.id_reclamo;\n" +
            "  \n" +
            "  -- Cancelar la inserción si el ID ya existe\n" +
            "  IF count_reclamo > 0 THEN\n" +
            "    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se puede insertar el reclamo. El ID ya está en uso.';\n" +
            "  END IF;\n" +
            "END;";

    private static final String validar_fecha_pasajero = "CREATE TRIGGER validar_fecha_pasajero\n" +
            "BEFORE INSERT ON Pasajero\n" +
            "FOR EACH ROW\n" +
            "BEGIN\n" +
            "  IF NEW.fecha_nacimiento REGEXP '^[0-9]{2}/[0-9]{2}/[0-9]{4}$' = 0 THEN\n" +
            "    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El formato de fecha para fecha_nacimiento debe ser dd/mm/yyyy.';\n" +
            "  END IF;\n" +
            "END;";

    private static final String validar_fecha_reclamo="CREATE TRIGGER validar_fecha_reclamo\n" +
            "BEFORE INSERT ON Reclamo\n" +
            "FOR EACH ROW\n" +
            "BEGIN\n" +
            "  IF NEW.fecha_reclamo REGEXP '^[0-9]{2}/[0-9]{2}/[0-9]{4}$' = 0 THEN\n" +
            "    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El formato de fecha para fecha_reclamo debe ser dd/mm/yyyy.';\n" +
            "  END IF;\n" +
            "END;";

    private static final String validar_genero_pasajero = "CREATE TRIGGER validar_genero_pasajero\n" +
            "BEFORE INSERT ON Pasajero\n" +
            "FOR EACH ROW\n" +
            "BEGIN\n" +
            "  DECLARE genero_valido BOOLEAN;\n" +
            "  \n" +
            "  SET genero_valido = FALSE;\n" +
            "  \n" +
            "  IF NEW.genero_pasajero IN ('M', 'F', 'Masculino', 'Femenino') THEN\n" +
            "    SET genero_valido = TRUE;\n" +
            "  END IF;\n" +
            "  \n" +
            "  IF genero_valido = FALSE THEN\n" +
            "    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El género del pasajero debe ser M, F, Masculino o Femenino.';\n" +
            "  END IF;\n" +
            "END;";

    private static final String evitar_duplicado_id_reclamo ="CREATE TRIGGER evitar_duplicado_id_reclamo\n" +
            "BEFORE INSERT ON Reclamo\n" +
            "FOR EACH ROW\n" +
            "BEGIN\n" +
            "  DECLARE count_reclamo INT;\n" +
            "  \n" +
            "  -- Verificar si ya existe un reclamo con el mismo ID\n" +
            "  SELECT COUNT(*) INTO count_reclamo FROM Reclamo WHERE id_reclamo = NEW.id_reclamo;\n" +
            "  \n" +
            "  -- Cancelar la inserción si el ID ya existe\n" +
            "  IF count_reclamo > 0 THEN\n" +
            "    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'No se puede insertar el reclamo. El ID ya está en uso.';\n" +
            "  END IF;\n" +
            "END;";

    private static final String validar_email_usuario="CREATE TRIGGER validar_email_usuario\n" +
            "BEFORE INSERT ON Usuario\n" +
            "FOR EACH ROW\n" +
            "BEGIN\n" +
            "  DECLARE valid_email BOOLEAN;\n" +
            "\n" +
            "  SET valid_email = FALSE;\n" +
            "\n" +
            "  IF NEW.email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$' THEN\n" +
            "    SET valid_email = TRUE;\n" +
            "  END IF;\n" +
            "\n" +
            "  IF valid_email = FALSE THEN\n" +
            "    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El formato del email no es válido.';\n" +
            "  END IF;\n" +
            "END;\n";

    private static final String validar_estado_reclamo = "CREATE TRIGGER validar_estado_reclamo\n" +
            "BEFORE INSERT ON Reclamo\n" +
            "FOR EACH ROW\n" +
            "BEGIN\n" +
            "  DECLARE valid_estado BOOLEAN;\n" +
            "\n" +
            "  SET valid_estado = FALSE;\n" +
            "\n" +
            "  IF NEW.estado IN ('activo', 'cancelado', 'solucionado', 'terminado') THEN\n" +
            "    SET valid_estado = TRUE;\n" +
            "  END IF;\n" +
            "\n" +
            "  IF valid_estado = FALSE THEN\n" +
            "    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El valor del estado no es válido. Los valores permitidos son: activo, cancelado, solucionado, terminado.';\n" +
            "  END IF;\n" +
            "END;";
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
        db.execSQL(CREATE_TABLE_VUELO);
        db.execSQL(CREATE_TABLE_TRIPULACION_VUELO);
        db.execSQL(CREATE_TABLE_TRIPULANTE);
        db.execSQL(CREATE_TABLE_AEROLINEA);
        db.execSQL(CREATE_TABLE_PAGO);
        db.execSQL(CREATE_TABLE_RESERVACION);
        db.execSQL(CREATE_TABLE_AGENCIA_VIAJES);
        db.execSQL(CREATE_TABLE_CUPO);

        //triggers desactivados
        /*
        db.execSQL(crear_pasajero);
        db.execSQL(evitar_duplicado_reclamo);
        db.execSQL(validar_fecha_pasajero);
        db.execSQL(validar_fecha_reclamo);
        db.execSQL(validar_genero_pasajero);
        db.execSQL(evitar_duplicado_id_reclamo);
        db.execSQL(validar_email_usuario);
        */
        db.execSQL(CREATE_TRIGGER_VALIDAR_CUPO);
        db.execSQL(CREATE_TRIGGER_VALIDAR_AEROLINEA);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí puedes realizar acciones si la base de datos necesita ser actualizada en futuras versiones
    }
}
