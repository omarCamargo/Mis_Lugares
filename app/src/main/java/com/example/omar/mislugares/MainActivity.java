package com.example.omar.mislugares;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity
implements AdapterView.OnItemClickListener, LocationListener{

    public BaseAdapter adaptador;
    public static final long DOS_MINUTOS = 2*60*1000;
    private LocationManager manejador;
    private Location mejorLocaliz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adaptador = new AdaptadorLugares(this);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adaptador);
        listView.setOnItemClickListener(this);
        manejador =(LocationManager) getSystemService(LOCATION_SERVICE);
        if(manejador.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            actualizaMejorLocaliz(manejador.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        }

        if(manejador.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            actualizaMejorLocaliz(manejador.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
        }
    }//Fin de onCreate

    @Override
    protected void onResume() {
        super.onResume();
        activarProveedores();
    }

    @Override
    protected void onPause() {
        super.onPause();
        manejador.removeUpdates(this);
    }

    @Override
    public void onItemClick(AdapterView parent, View vista, int posicion, long id) {
        Intent i = new Intent(this, VistaLugar.class);
        i.putExtra("id", id);
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_buscar) {
            lanzarVistaLugar(null);
            return true;
        }else if(id == R.id.menu_acercaDe){
            Intent mIntent = new Intent(this, AcercaDe.class);
            startActivity(mIntent);
            return true;
        }else if(id == R.id.action_settings){
            Intent mIntent= new Intent(this, Preferencias.class);
            startActivity(mIntent);
        }else if (id == R.id.menu_mapa){
            Intent i = new Intent(this, Mapa.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }


    public void lanzarAcercaDe(View view){
        Intent mIntent = new Intent(this, AcercaDe.class);
        startActivity(mIntent);

    }

    public void lanzarPreferencias(View view){
        Intent mIntent= new Intent(this, Preferencias.class);
        startActivity(mIntent);
    }

    public void salir(View view){
        finish();
    }

    public void mostrarPreferancias(View view){
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
        String S="notifacaciones: "+pref.getBoolean("notificaciones",true)+", distancia minima: "+pref.getString("distancia","?");
        Toast.makeText(this,S,Toast.LENGTH_SHORT).show();
    }

    public void lanzarVistaLugar(View view){
        final EditText entrada = new EditText(this);
        entrada.setText("0");
        new AlertDialog.Builder(this)
                .setTitle("Selección de lugar")
                .setMessage("indica su id:")
                .setView(entrada)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        long id = Long.parseLong(entrada.getText().toString());
                        Intent i = new Intent(MainActivity.this, VistaLugar.class);
                        i.putExtra("id", id);
                        startActivity(i);
                    }})

                .setNegativeButton("Cancelar", null)
                .show();
    }


    private void activarProveedores(){
        if(manejador.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            manejador.requestLocationUpdates(LocationManager.GPS_PROVIDER,20*1000,5,this);
        }

        if(manejador.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            manejador.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,10*1000,10,this);
        }
    }//fin de activarProveedores

    private void actualizaMejorLocaliz(Location localiz){
        if (localiz == null || mejorLocaliz == null){
            return;
        }
        if (mejorLocaliz==null || localiz.getAccuracy() < 2*mejorLocaliz.getAccuracy() ||
                localiz.getTime() - mejorLocaliz.getTime() > DOS_MINUTOS){

            Log.d(Lugares.TAG , "Nueva mejor Localizacion");
            mejorLocaliz = localiz;
            Lugares.posicionActual.setLatitud(localiz.getLatitude());
            Lugares.posicionActual.setLongitud(localiz.getLongitude());

        }
    }



    @Override
    public void onLocationChanged(Location location) {
        Log.d(Lugares.TAG,"Nueva Localizacion"+location);
        actualizaMejorLocaliz(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(Lugares.TAG, "Cambia de estado: "+provider);
        activarProveedores();

    }

    @Override
    public void onProviderEnabled(String provider) {

        Log.d(Lugares.TAG,"SE habilita: "+provider);
        activarProveedores();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(Lugares.TAG,"Se deshabilita: "+ provider);
        activarProveedores();

    }
}
