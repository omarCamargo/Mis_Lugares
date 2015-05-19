package com.example.omar.mislugares;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;


/**
 * Created by omar-camargo on 18/05/15.
 */
public class VistaLugar extends ActionBarActivity {
    private long id;
    private Lugar lugar;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_lugar);
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("id",-1);
        lugar = Lugares.elemento((int) id);
        actualizarVista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vista_lugar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.accion_compartir:
                return true;
            case R.id.accion_llegar:
                return true;
            case R.id.accion_editar:
                lanzarEdicionLugar();
                return true;
            case R.id.accion_borrar:
                borrarLugar();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }//fin del metodo onOptionsItemSelected

    public void borrarLugar(){
        new AlertDialog.Builder(this)
                .setTitle("Borrado de lugar")
                .setMessage("estas seguro que deseas eliminar este lugar?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Lugares.borrar((int) id);
                        finish();//ojo los metodos finish no esperan a que suceda algo en las vistas deben dejarse siempre al final del metodo que debe cerrarla
                    }
                })

                .setNegativeButton("Cancelar", null)
                .show();

    }

    public void lanzarEdicionLugar(){
        Intent i = new Intent(this, EdicionLugar.class);
        i.putExtra("id", id);
        startActivityForResult(i, 1234);
    }

    public void actualizarVista(){
        TextView nombre = (TextView) findViewById(R.id.nombre);
        nombre.setText(lugar.getNombre());

        ImageView logo_tipo = (ImageView) findViewById(R.id.logo_tipo);
        logo_tipo.setImageResource(lugar.getTipo().getRecurso());
        TextView tipo = (TextView) findViewById(R.id.tipo);
        tipo.setText(lugar.getTipo().getTexto());
        TextView direccion = (TextView) findViewById(R.id.direccion);
        direccion.setText(lugar.getDireccion());
        TextView telefono = (TextView) findViewById(R.id.telefono);
        telefono.setText(Integer.toString(lugar.getTelefono()));
        TextView url = (TextView) findViewById(R.id.url);
        url.setText(lugar.getUrl());
        TextView comentario = (TextView) findViewById(R.id.comentario);
        comentario.setText(lugar.getComentario());
        TextView fecha = (TextView) findViewById(R.id.fecha);
        fecha.setText(DateFormat.getDateInstance().format(
                new Date(lugar.getFecha())));
        TextView hora = (TextView) findViewById(R.id.hora);
        hora.setText(DateFormat.getTimeInstance().format(
                new Date(lugar.getFecha())));
        RatingBar valoracion = (RatingBar)findViewById(R.id.valoracion);
        valoracion.setRating(lugar.getValoracion());
        valoracion.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        lugar.setValoracion(rating);
                    }
                }
        );
    }//fin del metodo actualizarVista

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1234 && resultCode==RESULT_OK){
            long nlugarid = data.getExtras().getLong("resultID");
            lugar = Lugares.elemento((int) nlugarid);
            actualizarVista();
            findViewById(R.id.scrollView1).invalidate();
        }
    }
}
