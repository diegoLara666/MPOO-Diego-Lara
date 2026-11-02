import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String primeraLinea = reader.readLine();

        if (primeraLinea == null || primeraLinea.isBlank()) return;

        int numeroEnvios = Integer.parseInt(primeraLinea.trim());
        List<EntradaEnvioDTO> entradas = new ArrayList<>();

        for (int i = 0; i < numeroEnvios; i++) {
            String linea = reader.readLine();
            if (linea == null) throw new IllegalArgumentException("Faltan datos de entrada");
            entradas.add(convertirEntrada(linea));
        }

        List<String> resultados = Result.procesar(entradas);
        if (resultados == null) throw new IllegalStateException("Result.procesar regreso null");

        for (String resultado : resultados) {
            System.out.println(resultado);
        }
    }

    private static EntradaEnvioDTO convertirEntrada(String linea) {
        if (linea == null || linea.isBlank()) throw new IllegalArgumentException("Linea vacia");
        
        String[] partes = linea.split("\\|", -1);
        if (partes.length != 5) throw new IllegalArgumentException("Formato invalido: " + linea);

        TipoEnvio tipo = TipoEnvio.valueOf(partes[0].trim().toUpperCase(Locale.ROOT));
        String destinatario = partes[1].trim();
        if (destinatario.isBlank()) throw new IllegalArgumentException("Destinatario vacio");

        double pesoKg = Double.parseDouble(partes[2].trim());
        double distanciaKm = Double.parseDouble(partes[3].trim());
        List<ServicioAdicional> servicios = convertirServicios(partes[4]);

        return new EntradaEnvioDTO(tipo, destinatario, pesoKg, distanciaKm, servicios);
    }

    private static List<ServicioAdicional> convertirServicios(String valor) {
        List<ServicioAdicional> servicios = new ArrayList<>();
        if (valor == null) return servicios;

        String texto = valor.trim();
        if (texto.isEmpty() || texto.equalsIgnoreCase("NINGUNO")) return servicios;

        String[] elementos = texto.split("\\+");
        for (String elemento : elementos) {
            servicios.add(ServicioAdicional.valueOf(elemento.trim().toUpperCase(Locale.ROOT)));
        }
        return servicios;
    }
}
