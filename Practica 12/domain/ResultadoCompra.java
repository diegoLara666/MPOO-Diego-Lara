package mx.unam.fi.smartvend.domain;

public record ResultadoCompra(
    EstadoCompra estado,
    Producto producto,
    double cambio
) {}
