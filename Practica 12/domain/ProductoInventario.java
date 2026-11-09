package mx.unam.fi.smartvend.domain;

public record ProductoInventario(
    Producto producto,
    double precio,
    int existencias
) {}
