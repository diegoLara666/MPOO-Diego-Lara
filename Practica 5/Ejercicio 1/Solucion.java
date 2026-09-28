import java.io.*;
import java.util.*;

class Result {

    public static String calcularEstancia(String tipoVehiculo, String fechaEntrada, String horaEntrada, String fechaSalida, String horaSalida) {
        String[] dE = fechaEntrada.split("/");
        String[] hE = horaEntrada.split(":");
        Calendar calEntrada = Calendar.getInstance();
        calEntrada.set(Integer.parseInt(dE[2]), Integer.parseInt(dE[1]) - 1, Integer.parseInt(dE[0]), Integer.parseInt(hE[0]), Integer.parseInt(hE[1]), 0);
        calEntrada.set(Calendar.MILLISECOND, 0);

        String[] dS = fechaSalida.split("/");
        String[] hS = horaSalida.split(":");
        Calendar calSalida = Calendar.getInstance();
        calSalida.set(Integer.parseInt(dS[2]), Integer.parseInt(dS[1]) - 1, Integer.parseInt(dS[0]), Integer.parseInt(hS[0]), Integer.parseInt(hS[1]), 0);
        calSalida.set(Calendar.MILLISECOND, 0);

        if (!calSalida.after(calEntrada)) {
            return "INVALID";
        }

        long diffMillis = calSalida.getTimeInMillis() - calEntrada.getTimeInMillis();
        long horasCobradas = (long) Math.ceil(diffMillis / (1000.0 * 60 * 60));

        double tarifaHora = 0;
        double tarifaMax = 0;
        switch(tipoVehiculo) {
            case "MOTOCICLETA": tarifaHora = 15.0; tarifaMax = 100.0; break;
            case "AUTOMOVIL": tarifaHora = 25.0; tarifaMax = 180.0; break;
            case "CAMIONETA": tarifaHora = 35.0; tarifaMax = 250.0; break;
            case "ELECTRICO": tarifaHora = 20.0; tarifaMax = 150.0; break;
        }

        long horasRestantes = horasCobradas;
        double costoBase = 0;
        while (horasRestantes > 0) {
            long horasBloque = Math.min(24, horasRestantes);
            costoBase += Math.min(horasBloque * tarifaHora, tarifaMax);
            horasRestantes -= horasBloque;
        }

        boolean finDeSemana = (calEntrada.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calEntrada.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                               calSalida.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calSalida.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
                               
        boolean fechasDiferentes = (calEntrada.get(Calendar.YEAR) != calSalida.get(Calendar.YEAR) ||
                                    calEntrada.get(Calendar.DAY_OF_YEAR) != calSalida.get(Calendar.DAY_OF_YEAR));
                                    
        boolean nocturna = (calEntrada.get(Calendar.HOUR_OF_DAY) >= 20) || (calSalida.get(Calendar.HOUR_OF_DAY) < 6) || fechasDiferentes;

        double costoFinal = costoBase;
        if (finDeSemana) {
            costoFinal *= 1.20;
        }
        if (nocturna) {
            costoFinal *= 1.15;
        }
        if (tipoVehiculo.equals("ELECTRICO")) {
            costoFinal *= 0.90;
        }

        String tipoEstancia = "NORMAL";
        if (finDeSemana && nocturna) {
            tipoEstancia = "MIXTA";
        } else if (finDeSemana) {
            tipoEstancia = "FIN_SEMANA";
        } else if (nocturna) {
            tipoEstancia = "NOCTURNA";
        }

        return String.format(Locale.US, "%d %.2f %s", horasCobradas, costoFinal, tipoEstancia);
    }
}

public class Solution {
    public static void main(String[] args) throws IOException {
     
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String tipoVehiculo = bufferedReader.readLine();
        if (tipoVehiculo == null) return; 
        String fechaEntrada = bufferedReader.readLine();
        String horaEntrada = bufferedReader.readLine();
        String fechaSalida = bufferedReader.readLine();
        String horaSalida = bufferedReader.readLine();

        String result = Result.calcularEstancia(tipoVehiculo, fechaEntrada, horaEntrada, fechaSalida, horaSalida);

        
        System.out.println(result);

        bufferedReader.close();
    }
}
