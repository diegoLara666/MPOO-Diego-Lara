package mx.unam.fi.smartvend.domain;

public interface Monedero {
    ResultadoCobro cobrar(double precio, double cantidadRecibida);
    double consultarFondos();
}
