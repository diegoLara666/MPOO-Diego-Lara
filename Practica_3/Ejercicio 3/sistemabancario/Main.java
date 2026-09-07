
package sistemabancario;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CuentaBancariaService banco = new CuentaBancariaService();
        int opcion = 0;

        while (opcion != 4) {
            System.out.println("\n--- MENU DEL BANCO ---");
            System.out.println("1. Transferir dinero");
            System.out.println("2. Depositar dinero");
            System.out.println("3. Retirar dinero");
            System.out.println("4. Salir");
            System.out.print("Elige una opcion: ");
            
            opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.print("Cuenta de donde sale el dinero: ");
                    String origen = scanner.nextLine();
                    System.out.print("Cuenta a donde va el dinero: ");
                    String destino = scanner.nextLine();
                    System.out.print("Cuanto vas a pasar?: ");
                    double cantTrans = scanner.nextDouble();
                    
                    if (banco.transferir(origen, destino, cantTrans)) {
                        System.out.println("Simon, ya quedo la transferencia.");
                    } else {
                        System.out.println("Fallo. Checa bien los numeros o que tengas saldo suficiente.");
                    }
                    break;
                case 2:
                    System.out.print("A que cuenta le vas a meter dinero?: ");
                    String cDestino = scanner.nextLine();
                    System.out.print("De a cuanto?: ");
                    double cantDep = scanner.nextDouble();
                    
                    if (banco.depositar(cDestino, cantDep)) {
                        System.out.println("Va, deposito hecho.");
                    } else {
                        System.out.println("No se armo el deposito, igual y la cuenta no existe.");
                    }
                    break;
                case 3:
                    System.out.print("De que cuenta vas a sacar?: ");
                    String cOrigen = scanner.nextLine();
                    System.out.print("Cuanto ocupas sacar?: ");
                    double cantRet = scanner.nextDouble();
                    
                    if (banco.retirar(cOrigen, cantRet)) {
                        System.out.println("Sobres, ya se retiro el varo.");
                    } else {
                        System.out.println("No paso. Revisa tu saldo o que la cuenta este bien.");
                    }
                    break;
                case 4:
                    System.out.println("Cerrando sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida wey, pon una del 1 al 4.");
            }
        }
        scanner.close();
    }
}
