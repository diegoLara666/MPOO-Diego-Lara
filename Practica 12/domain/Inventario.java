package mx.unam.fi.smartvend.domain;

import java.util.Optional;

public interface Inventario {
    boolean registrarProducto(Producto producto, double precio);
    Optional<ProductoInventario> buscarProducto(String codigo);
    boolean reabastecer(String codigo, int cantidad);
    boolean despachar(String codigo);
}
