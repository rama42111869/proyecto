package com.portfolio.mgb.Security.Controller;

public class Mensaje {

    private String mensaje;

    //Constructores
    public Mensaje(){

    }
    public Mensaje(String mensaje){
    this.mensaje=mensaje;
    }

    //Getters y setters

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
