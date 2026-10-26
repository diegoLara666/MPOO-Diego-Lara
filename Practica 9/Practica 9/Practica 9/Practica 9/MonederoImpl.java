public class MonederoImpl implements Monedero {
    private double fondos;

    public MonederoImpl(double fondosIniciales) {
        this.fondos = Math.max(0, fondosIniciales);
    }

    @Override
    public ResultadoCobro cobrar(double precio, double cantidadRecibida) {
        if (cantidadRecibida < precio) return new ResultadoCobro(EstadoCobro.MONTO_INVALIDO, cantidadRecibida);
        
        double cambio = cantidadRecibida - precio;
        if (cambio > fondos) return new ResultadoCobro(EstadoCobro.SIN_FONDOS_SUFICIENTES, cantidadRecibida);
        
        this.fondos += precio;
        return new ResultadoCobro(EstadoCobro.COBRO_REALIZADO, cambio);
    }

    @Override
    public double consultarFondos() {
        return fondos;
    }
}
