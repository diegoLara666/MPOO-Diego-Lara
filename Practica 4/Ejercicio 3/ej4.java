import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    static class PilaDinamica {

        private double[] elementos;
        private int cima;

        public PilaDinamica(int capacidadInicial) {
            this.elementos = new double[capacidadInicial];
            this.cima = -1;
        }

        public void push(double valor) {
            if (this.cima == this.elementos.length - 1) {
                ampliarCapacidad();
            }
            this.cima++;
            this.elementos[this.cima] = valor;
        }

        public double pop() {
            if (isEmpty()) {
                return Double.NaN;
            }
            double valorEliminado = this.elementos[this.cima];
            this.cima--; 
            return valorEliminado;
        }

        public double peek() {
            if (isEmpty()) {
                return Double.NaN;
            }
            return this.elementos[this.cima];
        }

        public boolean isEmpty() {
            return this.cima == -1;
        }

        public int size() {
            return this.cima + 1;
        }

        public int capacity() {
            return this.elementos.length;
        }

        private void ampliarCapacidad() {
            int nuevaCapacidad = this.elementos.length * 2;
            double[] nuevosElementos = new double[nuevaCapacidad];
            
            for (int i = 0; i <= this.cima; i++) {
                nuevosElementos[i] = this.elementos[i];
            }
            
            this.elementos = nuevosElementos;
        }
    }

    /*
     * NO MODIFICAR. Reservado para HackerRank
     */
    public static List<String> procesarOperaciones(
            int capacidad, List<String> operaciones) {

        PilaDinamica pila = new PilaDinamica(capacidad);

        List<String> salida = new ArrayList<>();

        for (String linea : operaciones) {

            String[] partes = linea.trim().split("\\s+");

            String operacion = partes[0];

            switch (operacion) {

                case "PUSH":

                    double valor =
                            Double.parseDouble(partes[1]);

                    pila.push(valor);

                    salida.add("OK");

                    break;

                case "POP":

                    double eliminado = pila.pop();

                    if (Double.isNaN(eliminado)) {
                        salida.add("EMPTY");
                    } else {
                        salida.add(
                                String.valueOf(eliminado)
                        );
                    }

                    break;

                case "PEEK":

                    double cima = pila.peek();

                    if (Double.isNaN(cima)) {
                        salida.add("EMPTY");
                    } else {
                        salida.add(
                                String.valueOf(cima)
                        );
                    }

                    break;

                case "SIZE":

                    salida.add(
                            String.valueOf(
                                    pila.size()
                            )
                    );

                    break;

                case "CAPACITY":

                    salida.add(
                            String.valueOf(
                                    pila.capacity()
                            )
                    );

                    break;

                case "ISEMPTY":

                    salida.add(
                            String.valueOf(
                                    pila.isEmpty()
                            )
                    );

                    break;
            }
        }

        return salida;
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int capacidad = Integer.parseInt(bufferedReader.readLine().trim());

        int nOperaciones = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> operaciones = new ArrayList<>();

        for (int i = 0; i < nOperaciones; i++) {
            String operacionesItem = bufferedReader.readLine();
            operaciones.add(operacionesItem);
        }

        List<String> result = Result.procesarOperaciones(capacidad, operaciones);

        for (int i = 0; i < result.size(); i++) {
            bufferedWriter.write(result.get(i));

            if (i != result.size() - 1) {
                bufferedWriter.write("\n");
            }
        }

        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
