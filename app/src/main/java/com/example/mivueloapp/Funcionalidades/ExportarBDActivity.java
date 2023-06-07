package com.example.mivueloapp.Funcionalidades;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mivueloapp.DatabaseHelper;
import com.example.mivueloapp.R;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExportarBDActivity extends AppCompatActivity {

    private static final String EXPORT_FILE_NAME = "mivueloapp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exportar_bdactivity);
    }

    public void onExportToExcelClicked(View view) {
        exportToExcel(EXPORT_FILE_NAME + ".xlsx");
    }


    private void exportToExcel(String fileName) {
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String[] tables = { "Pasajero", "Reclamo", "Usuario" };

            Workbook workbook = new XSSFWorkbook();

            // Obtener el directorio de archivos externos de la aplicación
            File externalFilesDir = getExternalFilesDir(null);

            for (String tableName : tables) {
                Sheet sheet = workbook.createSheet(tableName);
                Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
                writeCursorToSheet(cursor, sheet);
                cursor.close();
            }

            // Crear el archivo en el directorio de archivos externos
            File file = new File(externalFilesDir, fileName);

            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();

            Toast.makeText(this, "Base de datos exportada correctamente", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al exportar la base de datos", Toast.LENGTH_SHORT).show();
        }
    }


    private void writeCursorToSheet(Cursor cursor, Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        int columnCount = cursor.getColumnCount();

        for (int i = 0; i < columnCount; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(cursor.getColumnName(i));
        }

        int rowIndex = 1;
        if (cursor.moveToFirst()) {
            do {
                Row dataRow = sheet.createRow(rowIndex);
                for (int i = 0; i < columnCount; i++) {
                    Cell cell = dataRow.createCell(i);
                    cell.setCellValue(cursor.getString(i));
                }
                rowIndex++;
            } while (cursor.moveToNext());
        }
    }
}