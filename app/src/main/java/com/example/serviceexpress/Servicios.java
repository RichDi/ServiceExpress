package com.example.serviceexpress;

import com.google.android.gms.maps.model.LatLng;

public class Servicios {

    public LatLng_FB ubicacion;
    public String nombre;
    public String telefono;
    public String email;
    public String whatsapp;
    public String hora_apertura;
    public String hora_clausura;
    public String categoria;
    public String userID;
    public String creationDate;
    public String no_servicios;

    public Servicios() {
    }

    public Servicios(LatLng_FB ubicacion, String nombre, String telefono, String email, String whatsapp, String hora_apertura, String hora_clausura, String categoria, String userID, String creationDate, String no_servicios) {
        this.ubicacion = ubicacion;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.whatsapp = whatsapp;
        this.hora_apertura = hora_apertura;
        this.hora_clausura = hora_clausura;
        this.categoria = categoria;
        this.userID = userID;
        this.creationDate = creationDate;
        this.no_servicios = no_servicios;
    }

    public LatLng_FB getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(LatLng_FB ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getHora_apertura() {
        return hora_apertura;
    }

    public void setHora_apertura(String hora_apertura) {
        this.hora_apertura = hora_apertura;
    }

    public String getHora_clausura() {
        return hora_clausura;
    }

    public void setHora_clausura(String hora_clausura) {
        this.hora_clausura = hora_clausura;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getNo_servicios() {
        return no_servicios;
    }

    public void setNo_servicios(String no_servicios) {
        this.no_servicios = no_servicios;
    }
}
