package mx.unam.fi.smartvend.api.dto;

public record FondosDTO(
    double efectivo,
    double tarjeta,
    double total
) {}
