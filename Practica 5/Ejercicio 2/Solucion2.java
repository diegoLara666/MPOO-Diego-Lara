import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

class Result {

    public static String evaluarLicencia(String fechaActual, String fechaVencimiento, String tipoLicencia, int renovacionesPrevias) {
        String[] dA = fechaActual.split("/");
        Calendar calActual = Calendar.getInstance();
        calActual.set(Integer.parseInt(dA[2]), Integer.parseInt(dA[1]) - 1, Integer.parseInt(dA[0]), 0, 0, 0);
        calActual.set(Calendar.MILLISECOND, 0);

        String[] dV = fechaVencimiento.split("/");
        Calendar calVencimiento = Calendar.getInstance();
        calVencimiento.set(Integer.parseInt(dV[2]), Integer.parseInt(dV[1]) - 1, Integer.parseInt(dV[0]), 0, 0, 0);
        calVencimiento.set(Calendar.MILLISECOND, 0);

        long diffMillis = calVencimiento.getTimeInMillis() - calActual.getTimeInMillis();
        long diferenciaDias = Math.round((double) diffMillis / (1000 * 60 * 60 * 24));

        String estado;
        if (diferenciaDias > 30) {
            estado = "VIGENTE";
        } else if (diferenciaDias >= 0) {
            estado = "PROXIMA_A_VENCER";
        } else if (diferenciaDias >= -90) {
            estado = "VENCIDA";
        } else {
            estado = "BLOQUEADA";
        }

        double costoBase = 0;
        int anosAgregar = 0;
        int mesesAgregar = 0;

        switch (tipoLicencia) {
            case "BASICA":
                costoBase = 1000.00;
                anosAgregar = 1;
                break;
            case "PROFESIONAL":
                costoBase = 1500.00;
                anosAgregar = 2;
                break;
            case "EMPRESARIAL":
                costoBase = 2500.00;
                anosAgregar = 3;
                break;
            case "TEMPORAL":
                costoBase = 600.00;
                mesesAgregar = 6;
                break;
        }

        double costoFinal = costoBase;
        String nuevaFechaStr = "NO_DISPONIBLE";

        if (estado.equals("BLOQUEADA")) {
            costoFinal = 0.00;
        } else {
            if (estado.equals("VIGENTE")) {
                costoFinal *= 0.90;
            } else if (estado.equals("VENCIDA")) {
                costoFinal *= 1.20;
            }

            if (renovacionesPrevias > 3) {
                costoFinal *= 0.95;
            }

            Calendar calNueva = (Calendar) (estado.equals("VENCIDA") ? calActual.clone() : calVencimiento.clone());
            
            if (anosAgregar > 0) {
                calNueva.add(Calendar.YEAR, anosAgregar);
            }
            if (mesesAgregar > 0) {
                calNueva.add(Calendar.MONTH, mesesAgregar);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            nuevaFechaStr = sdf.format(calNueva.getTime());
        }

        return String.format(Locale.US, "%s %d %.2f %s", estado, diferenciaDias, costoFinal, nuevaFechaStr);
    }

}

public class Solution {

    public static void main(String[] args) throws IOException {
        
      
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String fechaActual = bufferedReader.readLine();
        if (fechaActual == null) return; 
        String fechaVencimiento = bufferedReader.readLine();
        String tipoLicencia = bufferedReader.readLine();
        int renovacionesPrevias = Integer.parseInt(bufferedReader.readLine().trim());

        String result = Result.evaluarLicencia(fechaActual, fechaVencimiento, tipoLicencia, renovacionesPrevias);

        
        System.out.println(result);

        bufferedReader.close();
    }
}
