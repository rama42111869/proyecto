package com.portfolio.mgb.Security.Entity;

import com.portfolio.mgb.Security.Enums.RolNombre;

import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.EnumType;
import javax.validation.constraints.NotNull;

@Entity
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private RolNombre rolNombre;

    public Rol(){

    }

    public Rol(RolNombre rolNombre){
        this.rolNombre=rolNombre;
    }

    //Getters y setters


    public RolNombre getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(RolNombre rolNombre) {
        this.rolNombre = rolNombre;
    }
}
