public class SeguroEnvio extends ServicioAdicionalDecorator {
    public SeguroEnvio(ServicioEnvio envio) {
        super(envio);
    }

    @Override
    public double calcularCosto() {
        return super.calcularCosto() * 1.10;
    }

    @Override
    public String getDescripcion() {
        return super.getDescripcion() + " + Seguro";
    }
}
