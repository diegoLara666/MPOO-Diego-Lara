import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    /*
     * Complete the 'comprimir' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING datos as parameter.
     */

    public static String comprimir(String datos) {
        if (datos == null || datos.isEmpty()) {
            return "";
        }

        StringBuilder comprimido = new StringBuilder();
        int contador = 1;
        char letraActual = datos.charAt(0);

        for (int i = 1; i < datos.length(); i++) {
            if (datos.charAt(i) == letraActual && contador < 9) {
                contador++;
            } else {
                comprimido.append(contador).append(letraActual);
                letraActual = datos.charAt(i);
                contador = 1;
            }
        }
        
        comprimido.append(contador).append(letraActual);

        return comprimido.toString();
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String datos = bufferedReader.readLine();

        String result = Result.comprimir(datos);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
