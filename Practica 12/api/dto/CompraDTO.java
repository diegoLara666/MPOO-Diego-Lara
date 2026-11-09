package mx.unam.fi.smartvend.api.dto;

import mx.unam.fi.smartvend.domain.MetodoPago;

public record CompraDTO(
    String codigoProducto,
    MetodoPago metodoPago,
    double cantidadRecibida
) {}
