import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    /*
     * Complete the 'detectarZonaAjuste' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts INTEGER_ARRAY vagones as parameter.
     */

    public static int[] detectarZonaAjuste(int[] vagones) {
        if (vagones == null || vagones.length < 2) {
            return new int[]{-1, -1, 0};
        }

        int n = vagones.length;
        int L = 0;
        int R = n - 1;

        while (L < n - 1 && vagones[L] <= vagones[L + 1]) {
            L++;
        }

        if (L == n - 1) {
            return new int[]{-1, -1, 0};
        }

        while (R > 0 && vagones[R] >= vagones[R - 1]) {
            R--;
        }

        int minTramo = vagones[L];
        int maxTramo = vagones[L];
        for (int i = L; i <= R; i++) {
            if (vagones[i] < minTramo) minTramo = vagones[i];
            if (vagones[i] > maxTramo) maxTramo = vagones[i];
        }

        while (L > 0 && vagones[L - 1] > minTramo) {
            L--;
        }

        while (R < n - 1 && vagones[R + 1] < maxTramo) {
            R++;
        }

        return new int[]{L, R, R - L + 1};
    }

}

public class Solution {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader =
                new BufferedReader(new InputStreamReader(System.in));

        BufferedWriter bufferedWriter =
                new BufferedWriter(
                        new FileWriter(System.getenv("OUTPUT_PATH"))
                );

        String linea = bufferedReader.readLine();

        String[] datos = linea.trim().split("\\s+");

        int[] vagones = new int[datos.length];

        for (int i = 0; i < datos.length; i++) {
            vagones[i] = Integer.parseInt(datos[i]);
        }

        int[] result = Result.detectarZonaAjuste(vagones);

        for (int i = 0; i < result.length; i++) {

            bufferedWriter.write(String.valueOf(result[i]));

            if (i != result.length - 1) {
                bufferedWriter.write(" ");
            }
        }

        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
