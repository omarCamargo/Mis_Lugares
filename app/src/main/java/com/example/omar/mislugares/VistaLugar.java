package com.example.omar.mislugares;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;


/**
 * Created by omar-camargo on 18/05/15.
 */
public class VistaLugar extends ActionBarActivity {
    private long id;
    private Lugar lugar;
    private ImageView imageView;
    final static int RESULTADO_EDITAR= 1;
    final static int RESULTADO_GALERIA= 2;
    final static int RESULTADO_FOTO= 3;
    Uri uriFoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_lugar);
        Bundle extras = getIntent().getExtras();
        id = extras.getLong("id",-1);
        lugar = Lugares.elemento((int) id);
        imageView = (ImageView) findViewById(R.id.foto);
        actualizarVista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.vista_lugar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.accion_compartir:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,
                        lugar.getNombre() + " - "+ lugar.getUrl());
                startActivity(intent);
                return true;
            case R.id.accion_llegar:
                verMapa(null);
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
        startActivityForResult(i, RESULTADO_EDITAR);
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

        ponerFoto(imageView, lugar.getFoto());
    }//fin del metodo actualizarVista

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RESULTADO_EDITAR && resultCode==RESULT_OK){
            long nlugarid = data.getExtras().getLong("resultID");
            lugar = Lugares.elemento((int) nlugarid);
            actualizarVista();
            findViewById(R.id.scrollView1).invalidate();
        }else if(requestCode==RESULTADO_GALERIA && resultCode==RESULT_OK){
            lugar.setFoto(data.getDataString());
            ponerFoto(imageView, lugar.getFoto());
        }else if(requestCode == RESULTADO_FOTO && resultCode == RESULT_OK && lugar!=null && uriFoto!=null){
            lugar.setFoto(uriFoto.toString());
            ponerFoto(imageView, lugar.getFoto());

        }
    }

    public void verMapa(View view){
        Uri uri;
        double lat=lugar.getPosicion().getLatitud();
        double lon=lugar.getPosicion().getLongitud();
        if(lat != 0 || lon != 0){
            uri=Uri.parse("geo:" + lat + "," + lon);
        }else{
            uri=Uri.parse("geo:0,0?q="+lugar.getDireccion());
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);

    }

    public void llamadaTelefono(View view) {
        startActivity(new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + lugar.getTelefono())));
    }

    public void pgWeb(View view) {
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(lugar.getUrl())));
    }

    public void galeria(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, RESULTADO_GALERIA);
    }

    protected void ponerFoto(ImageView imageView, String uri) {
        if (uri != null) {
            imageView.setImageURI(Uri.parse(uri));
        } else{
            imageView.setImageBitmap(null);
        }
    }

    public void tomarFoto(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        Uri uriFoto = Uri.fromFile(
                new File(Environment.getExternalStorageDirectory() + File.separator
                        + "img_" + (System.currentTimeMillis() / 1000) + ".jpg"));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriFoto);
        startActivityForResult(intent, RESULTADO_FOTO);
    }

    public void eliminarFoto(View view) {
        lugar.setFoto(null);
        ponerFoto(imageView, null);
    }
}
