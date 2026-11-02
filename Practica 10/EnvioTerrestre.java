public class EnvioTerrestre extends Envio {
    public EnvioTerrestre(String destinatario, double peso, double distancia) {
        super(destinatario, peso, distancia);
    }

    @Override
    public double calcularCosto() {
        return (distancia * 0.50) + (peso * 2.0);
    }

    @Override
    public String getDescripcion() {
        return "Envio terrestre";
    }
}
