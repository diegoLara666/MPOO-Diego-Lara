public class EnvioAereo extends Envio {
    public EnvioAereo(String destinatario, double peso, double distancia) {
        super(destinatario, peso, distancia);
    }

    @Override
    public double calcularCosto() {
        return (distancia * 1.20) + (peso * 5.0);
    }

    @Override
    public String getDescripcion() {
        return "Envio aereo";
    }
}
