package com.example.mivueloapp.Funcionalidades;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.mivueloapp.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.CompoundBarcodeView;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import java.util.List;

public class LectorQRActivity extends AppCompatActivity implements BarcodeCallback {

    private CompoundBarcodeView barcodeScanner;
    private TextView resultTextView;
    private Button visitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lector_qractivity);

        barcodeScanner = findViewById(R.id.barcodeScanner);
        resultTextView = findViewById(R.id.resultTextView);
        visitButton = findViewById(R.id.visitButton);

        barcodeScanner.decodeContinuous(this);

        visitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scannedUrl = resultTextView.getText().toString();
                openWebsite(scannedUrl);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeScanner.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeScanner.pause();
    }

    @Override
    public void barcodeResult(BarcodeResult result) {
        if (result != null) {
            String barcodeValue = result.getText();
            resultTextView.setText(barcodeValue);
        }
    }


    private void openWebsite(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
