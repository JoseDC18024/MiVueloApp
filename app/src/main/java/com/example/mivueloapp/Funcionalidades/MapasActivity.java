package com.example.mivueloapp.Funcionalidades;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mivueloapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapasActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapas);

        // Obtener el SupportMapFragment y notificar cuando el mapa esté listo para ser utilizado
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Configurar la posición y el marcador en el mapa
        LatLng location = new LatLng(13.6929, -89.2181); // Coordenadas de San Salvador (Inicio de Mapa)
        mMap.addMarker(new MarkerOptions().position(location).title("Marcador en San Salvador"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
}
