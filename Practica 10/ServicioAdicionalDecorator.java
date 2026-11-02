public abstract class ServicioAdicionalDecorator implements ServicioEnvio {
    protected ServicioEnvio envioWrappeado;

    public ServicioAdicionalDecorator(ServicioEnvio envio) {
        this.envioWrappeado = envio;
    }

    @Override
    public double calcularCosto() {
        return envioWrappeado.calcularCosto();
    }

    @Override
    public String getDescripcion() {
        return envioWrappeado.getDescripcion();
    }
}
