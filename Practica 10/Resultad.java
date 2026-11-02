import java.util.ArrayList;
import java.util.List;

public class Result {
    public static List<String> procesar(List<EntradaEnvioDTO> entradas) {
        List<ServicioEnvio> envios = new ArrayList<>();

        for (EntradaEnvioDTO entrada : entradas) {
            ServicioEnvio envio = null;
            
            switch (entrada.getTipo()) {
                case TERRESTRE:
                    envio = new EnvioTerrestre(entrada.getDestinatario(), entrada.getPesoKg(), entrada.getDistanciaKm());
                    break;
                case AEREO:
                    envio = new EnvioAereo(entrada.getDestinatario(), entrada.getPesoKg(), entrada.getDistanciaKm());
                    break;
            }

            for (ServicioAdicional servicio : entrada.getServicios()) {
                switch (servicio) {
                    case SEGURO:
                        envio = new SeguroEnvio(envio);
                        break;
                    case EXPRESS:
                        envio = new EntregaExpress(envio);
                        break;
                }
            }
            envios.add(envio);
        }

        CentroEnvios centroEnvios = new CentroEnvios();
        return centroEnvios.procesar(envios);
    }
}
