package com.example.omar.mislugares;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edicion_lugar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
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
}
