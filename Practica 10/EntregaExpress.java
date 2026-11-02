public class EntregaExpress extends ServicioAdicionalDecorator {
    public EntregaExpress(ServicioEnvio envio) {
        super(envio);
    }

    @Override
    public double calcularCosto() {
        return super.calcularCosto() + 100.0;
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " + Express";
    }
}
