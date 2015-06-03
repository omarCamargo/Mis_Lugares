package com.example.omar.mislugares;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by omar-camargo on 2/06/15.
 */
public class Mapa extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener {
    private GoogleMap mapa;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapa);
        mapa = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapa)).getMap();
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapa.setMyLocationEnabled(true);
        mapa.getUiSettings().setZoomControlsEnabled(true);
        mapa.getUiSettings().setCompassEnabled(true);
        if (Lugares.vectorLugares.size() > 0) {
            GeoPunto p = Lugares.vectorLugares.get(0).getPosicion();
            mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(p.getLatitud(), p.getLongitud()), 12));
            mapa.setOnInfoWindowClickListener(this);
        }

        for (Lugar lugar : Lugares.vectorLugares) {
            GeoPunto p = lugar.getPosicion();
            if (p != null && p.getLatitud() != 0) {
                BitmapDrawable iconoDrawable = (BitmapDrawable) getResources()
                        .getDrawable(lugar.getTipo().getRecurso());
                Bitmap iGrande = iconoDrawable.getBitmap();
                Bitmap icono = Bitmap.createScaledBitmap(iGrande,
                        iGrande.getWidth() / 7, iGrande.getHeight() / 7, false);
                mapa.addMarker(new MarkerOptions()
                        .position(new LatLng(p.getLatitud(), p.getLongitud()))
                        .title(lugar.getNombre()).snippet(lugar.getDireccion())
                        .icon(BitmapDescriptorFactory.fromBitmap(icono)));
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        for (int i=0; i< Lugares.vectorLugares.size(); i++){
            if(Lugares.vectorLugares.get(i).getNombre().equals(marker.getTitle())){
                Intent intent = new Intent(this, VistaLugar.class);
                intent.putExtra("id", (long) i);
                startActivity(intent);
                break;
            }
        }



    }
}
