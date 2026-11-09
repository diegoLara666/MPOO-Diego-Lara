package mx.unam.fi.smartvend.infrastructure;

import mx.unam.fi.smartvend.domain.EstadoCobro;
import mx.unam.fi.smartvend.domain.Monedero;
import mx.unam.fi.smartvend.domain.ResultadoCobro;

public class MonederoTarjeta implements Monedero {
    private double fondos;

    public MonederoTarjeta(double fondosIniciales) {
        this.fondos = Math.max(0, fondosIniciales);
    }

    @Override
    public ResultadoCobro cobrar(double precio, double cantidadRecibida) {
        if (cantidadRecibida < precio) {
            return new ResultadoCobro(EstadoCobro.MONTO_INVALIDO, cantidadRecibida);
        }
        this.fondos += precio;
        return new ResultadoCobro(EstadoCobro.COBRO_REALIZADO, 0.0);
    }

    @Override
    public double consultarFondos() {
        return fondos;
    }
}
