import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CentroEnvios {
    public String procesar(ServicioEnvio envio) {
        return String.format(Locale.US, "%s|%.2f", envio.getDescripcion(), envio.calcularCosto());
    }

    public List<String> procesar(List<ServicioEnvio> envios) {
        List<String> resultados = new ArrayList<>();
        for (ServicioEnvio envio : envios) {
            resultados.add(procesar(envio));
        }
        return resultados;
    }
}
