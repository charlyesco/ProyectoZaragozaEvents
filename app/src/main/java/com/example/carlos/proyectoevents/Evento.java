package com.example.carlos.proyectoevents;


import java.util.Date;

public class Evento {
    private String id;
    private String titleEvent;
    private String location;
    private String addres;
    private String region;
    private String cp;
    private String telefono;
    //tema del evento
    private String titleCategory;
    //longitud izquierda
    //latitud centro o izq
    //elevacion izquierda o no aparece
    private float latitud;
    private float longitud;
    private String startDate;
    private String description;
    private int image;
    private String endDate;

    public Evento(String titleEvent,String description,String lugar,String starDate,String titleCategory){
        this.description=description;
        this.titleEvent=titleEvent;
        this.addres=lugar;
        this.image=image;
        this.titleCategory=titleCategory;
        this.startDate=starDate;
    }


    public Evento(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTitleEvent() {
        return titleEvent;
    }

    public void setTitleEvent(String titleEvent) {
        this.titleEvent = titleEvent;
    }
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String starDate) {
        this.startDate = starDate;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitleCategory() {
        return titleCategory;
    }

    public void setTitleCategory(String titleCategory) {
        this.titleCategory = titleCategory;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

