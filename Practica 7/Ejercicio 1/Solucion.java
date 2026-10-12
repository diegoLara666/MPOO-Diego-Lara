import java.io.*;
import java.util.*;

class Result {

    public static List<String> generarDirecciones(String digitos) {
        // Desarrolle su solucion aqui
        List<String> res = new ArrayList<>();
        if (digitos == null) return res;
        
        int n = digitos.length();
        if (n < 4 || n > 12) return res;

        for (int i = 1; i <= 3 && i < n; i++) {
            for (int j = i + 1; j <= i + 3 && j < n; j++) {
                for (int k = j + 1; k <= j + 3 && k < n; k++) {
                    String s1 = digitos.substring(0, i);
                    String s2 = digitos.substring(i, j);
                    String s3 = digitos.substring(j, k);
                    String s4 = digitos.substring(k);

                    if (esValido(s1) && esValido(s2) && esValido(s3) && esValido(s4)) {
                        res.add(s1 + "." + s2 + "." + s3 + "." + s4);
                    }
                }
            }
        }
        return res;
    }

    private static boolean esValido(String s) {
        if (s.length() == 0 || s.length() > 3) return false;
        if (s.length() > 1 && s.charAt(0) == '0') return false;
        int val = Integer.parseInt(s);
        return val >= 0 && val <= 255;
    }

}

public class Solution {

    public static void main(String[] args)
            throws Exception {

        BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(System.in));

        String linea = br.readLine();
        String digitos = (linea != null) ? linea.trim() : "";

        List<String> direcciones =
                Result.generarDirecciones(digitos);

        Collections.sort(direcciones);

        System.out.println(direcciones.size());

        for (String direccion : direcciones) {
            System.out.println(direccion);
        }
    }
}
