package com.portfolio.mgb.Security.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String nombre;
    @NotNull
    @Column(unique = true)
    private String nombreusuario;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol",joinColumns = @JoinColumn(name = "usuario_id"),inverseJoinColumns = @JoinColumn(name="rol_id"))
    private HashSet<Rol>roles=new HashSet<>();

    //Constructores
    public Usuario(){

    }
    public Usuario(String nombre, String nombreusuario, String email, String password) {
        this.nombre = nombre;
        this.nombreusuario = nombreusuario;
        this.email = email;
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashSet<Rol> getRoles() {
        return roles;
    }

    public void setRoles(HashSet<Rol> roles) {
        this.roles = roles;
    }
}
