
package battlerobots;

import java.util.Random;

public class battlerobots {
    public static void main(String[] args) {

        // PUNTO 10

        System.out.println(" PREPARANDO LA BATALLA ");
        
        // Robot 1
        Identidad id1 = new Identidad("Destructor");
        Bateria bat1 = new Bateria("LiPo", 100);
        Arma arma1 = new Arma("Sierra Circular", 30);
        Robot robot1 = new Robot(id1, bat1, arma1, 100);

        // Robot 2
        Identidad id2 = new Identidad("Triturador");
        Bateria bat2 = new Bateria("NiMH", 100);
        Arma arma2 = new Arma("Martillo Neumático", 40);
        Robot robot2 = new Robot(id2, bat2, arma2, 100);

     
        Identidad id3 = new Identidad("LanzaFuego");
        Bateria bat3 = new Bateria("LiPo", 100);
        Arma arma3 = new Arma("Lanzallamas", 25);
        Robot robot3 = new Robot(id3, bat3, arma3, 100);

     
        System.out.println("Total de robots participantes: " + Robot.getTotalRobots());
        
   
        System.out.println("\nESTADO INICIAL:");
        System.out.println(robot1);
        System.out.println(robot2);
        System.out.println(robot3);

        
        // PUNTO 7 y 10

        System.out.println("\nINICIA LA BATALLA");
        Random random = new Random(); 

    
        double velocidadAtaque = 10.0 + random.nextDouble() * 40.0; 
        int danoAleatorio = random.nextInt(31) + 20; 

        
        robot1.embestir(velocidadAtaque); 
        robot2.recibirDano(danoAleatorio);
        
       
        robot3.embestir(25.5);
        robot1.recibirDano(random.nextInt(40) + 10); 
        
        robot2.frenar(); 


        System.out.println("\nESTADO DESPUÉS DE LA BATALLA:");
        System.out.println(robot1);
        System.out.println(robot2);
        System.out.println(robot3);

      
        // PUNTO 11
      
        System.out.println("\n EXPERIMENTO DE REFERENCIAS ");
        
      
        Robot robotFavorito = robot1; 
        
        
        robot1.recibirDano(99); 
        
    
        System.out.println("Estado de robotFavorito (debe tener la misma vida que Destructor):");
        System.out.println(robotFavorito);

    
        Identidad idClon = new Identidad("Destructor");
        Bateria batClon = new Bateria("LiPo", 100);
        Arma armaClon = new Arma("Sierra Circular", 30);
        Robot robotClon = new Robot(idClon, batClon, armaClon, 100);

        System.out.println("\nPRUEBAS DE IGUALDAD (equals):");
       
        System.out.println("robot1.equals(robotClon) : " + robot1.equals(robotClon)); 
        
        
        System.out.println("robot1.equals(robotFavorito) : " + robot1.equals(robotFavorito)); 
    }
}