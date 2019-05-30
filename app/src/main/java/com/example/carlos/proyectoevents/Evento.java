package com.example.carlos.proyectoevents;


public class Evento {
    private String id;
    private String titleEvent;
    private String place_title;
    private String addres;
    private String region;
    private String telefono;
    //tema del evento
    private String titleCategory;
    //longitud izquierda
    //latitud centro o izq
    //elevacion izquierda o no aparece
   // private float latitud;
   // private float longitud;
    private String c0;
    private String c1;
    private String startDate;
    private String description;
    private String image;
    private String endDate;
    //horas
    private String startTime;
    private String endTime;
    private String dayOfWeek;
    private String horario;


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
    public String getPlace() {
        return place_title;
    }

    public void setPlace(String location) {
        this.place_title = location;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getC0() {
        return c0;
    }

    public void setC0(String c0) {
        this.c0 = c0;
    }

    public String getC1() {
        return c1;
    }

    public void setC1(String c1) {
        this.c1 = c1;
    }
}

