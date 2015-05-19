package com.example.omar.mislugares;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
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
implements AdapterView.OnItemClickListener{

    public BaseAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adaptador = new AdaptadorLugares(this);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adaptador);
        listView.setOnItemClickListener(this);
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


}
