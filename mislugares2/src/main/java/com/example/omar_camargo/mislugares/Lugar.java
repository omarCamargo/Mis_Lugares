package com.example.omar_camargo.mislugares;

/**
 * Created by omar-camargo on 18/05/15.
 */



public class Lugar {

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getFoto() {
        return foto;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getUrl() {
        return url;
    }

    public String getComentario() {
        return comentario;
    }

    public long getFecha() {
        return fecha;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    private String nombre;
    private String direccion;
    private GeoPunto posicion;
    private String foto;
    private int telefono;
    private String url;
    private String comentario;
    private long fecha;
    private float valoracion;

    public Lugar(String nombre, String direccion, double longitud,
                 double latitud, int telefono, String url, String comentario,
                 int valoracion) {
        fecha = System.currentTimeMillis();
        posicion = new GeoPunto(longitud, latitud);
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.url = url;
        this.comentario = comentario;
        this.valoracion = valoracion;
    }

    @Override
    public String toString() {
        return "com.example.omar_camargo.mislugares.Lugar{nombre"+nombre+", direccion="+ direccion+", posicion="+
                posicion+ ", foto="+ foto+ ", telefono="+telefono+ ", url="+ url+ ", comentario="+
                comentario+ ", fecha="+ fecha+ ", valoracion="+ valoracion+"}";
    }
}
