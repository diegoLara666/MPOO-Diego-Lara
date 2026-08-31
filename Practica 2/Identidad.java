package battlerobots;

import java.util.UUID; 

public class Identidad {
    
    private UUID id;
    private String nombre;


    public Identidad(String nombre) {
        this.nombre = nombre;
        this.id = UUID.randomUUID(); 
    }

   
    public UUID getId() { return id; }
    public String getNombre() { return nombre; }
}