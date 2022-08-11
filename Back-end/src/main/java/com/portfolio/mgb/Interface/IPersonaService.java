package com.portfolio.mgb.Interface;

import com.portfolio.mgb.Entity.Persona;

import java.util.List;

public interface IPersonaService {
    //Traer una lista de personas
    public List<Persona>getPersona();

    //Generar un objeto de tipo persona
    public void savePersona(Persona persona);

    //Eliminar un objeto por ID
    public void deletePersona(Long id);

    public Persona findPersona(Long id);
}
