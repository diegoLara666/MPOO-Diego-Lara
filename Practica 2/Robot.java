
package battlerobots;


import java.util.Objects; 

public class Robot {


    // Puntos 4, 5 y 9
  
    
 
    private static int totalRobots = 0;

  
    private Identidad identidad;
    private Bateria bateria;
    private Arma armaPrincipal;

 
    private int integridadArmadura;
    private double velocidadActual;

   
    // Puntos 5 y 9
  
    public Robot(Identidad identidad, Bateria bateria, Arma armaPrincipal, int integridadArmadura) {
        this.identidad = identidad; 
        this.bateria = bateria;
        this.armaPrincipal = armaPrincipal;
        this.integridadArmadura = integridadArmadura;
        this.velocidadActual = 0.0;
        
    
        totalRobots++; 
    }

 
    //Punto 6
   
    

    public void recibirDano(int cantidadDano) {
        this.integridadArmadura -= cantidadDano;
        if (this.integridadArmadura < 0) {
            this.integridadArmadura = 0;
        }
        System.out.println(this.identidad.getNombre() + " recibió " + cantidadDano + " de daño. Vida: " + this.integridadArmadura);
    }


    public boolean estaDestruido() {
        return this.integridadArmadura <= 0;
    }

  
    public void embestir(double nuevaVelocidad) {
        this.velocidadActual = nuevaVelocidad;
        int energiaActual = this.bateria.getNivelCarga();
        this.bateria.setNivelCarga(energiaActual - 10);
        System.out.println(this.identidad.getNombre() + " embiste a " + this.velocidadActual + "km/h! Batería: " + this.bateria.getNivelCarga() + "%");
    }

   
    public void frenar() {
        this.velocidadActual = 0.0;
        System.out.println(this.identidad.getNombre() + " se detuvo.");
    }

   
    //Punto 8
    
    
   
    @Override
    public String toString() {
        return "Robot[" + identidad.getNombre() + " | ID: " + identidad.getId() + 
               " | Vida: " + integridadArmadura + " | Batería: " + bateria.getNivelCarga() + "%]";
    }

    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Robot robot = (Robot) obj;
        return Objects.equals(this.identidad.getId(), robot.identidad.getId());
    }

    
    @Override
    public int hashCode() {
        return Objects.hash(this.identidad.getId());
    }

    // (Punto 5)
 
    public Identidad getIdentidad() { return identidad; }
    public Bateria getBateria() { return bateria; }
    public Arma getArmaPrincipal() { return armaPrincipal; }
    
    public int getIntegridadArmadura() { return integridadArmadura; }
    public void setIntegridadArmadura(int integridadArmadura) { this.integridadArmadura = integridadArmadura; }
    
    public double getVelocidadActual() { return velocidadActual; }
    public void setVelocidadActual(double velocidadActual) { this.velocidadActual = velocidadActual; }
    
    
    public static int getTotalRobots() { return totalRobots; }
}