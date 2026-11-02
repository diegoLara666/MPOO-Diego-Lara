import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntradaEnvioDTO {
    private final TipoEnvio tipo;
    private final String destinatario;
    private final double pesoKg;
    private final double distanciaKm;
    private final List<ServicioAdicional> servicios;

    public EntradaEnvioDTO(TipoEnvio tipo, String destinatario, double pesoKg, double distanciaKm, List<ServicioAdicional> servicios) {
        this.tipo = tipo;
        this.destinatario = destinatario;
        this.pesoKg = pesoKg;
        this.distanciaKm = distanciaKm;
        this.servicios = Collections.unmodifiableList(new ArrayList<>(servicios));
    }

    public TipoEnvio getTipo() { return tipo; }
    public String getDestinatario() { return destinatario; }
    public double getPesoKg() { return pesoKg; }
    public double getDistanciaKm() { return distanciaKm; }
    public List<ServicioAdicional> getServicios() { return servicios; }
}
