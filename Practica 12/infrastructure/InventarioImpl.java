package mx.unam.fi.smartvend.infrastructure;

import mx.unam.fi.smartvend.domain.Inventario;
import mx.unam.fi.smartvend.domain.Producto;
import mx.unam.fi.smartvend.domain.ProductoInventario;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InventarioImpl implements Inventario {
    private static class Registro {
        Producto producto;
        double precio;
        int existencias;

        Registro(Producto producto, double precio, int existencias) {
            this.producto = producto;
            this.precio = precio;
            this.existencias = existencias;
        }
    }

    private final Map<String, Registro> registros = new HashMap<>();

    @Override
    public boolean registrarProducto(Producto producto, double precio) {
        if (registros.containsKey(producto.getCodigo()) || precio <= 0) {
            return false;
        }
        registros.put(producto.getCodigo(), new Registro(producto, precio, 0));
        return true;
    }

    @Override
    public Optional<ProductoInventario> buscarProducto(String codigo) {
        Registro reg = registros.get(codigo);
        if (reg == null) return Optional.empty();
        return Optional.of(new ProductoInventario(reg.producto, reg.precio, reg.existencias));
    }

    @Override
    public boolean reabastecer(String codigo, int cantidad) {
        if (cantidad <= 0 || !registros.containsKey(codigo)) return false;
        registros.get(codigo).existencias += cantidad;
        return true;
    }

    @Override
    public boolean despachar(String codigo) {
        Registro reg = registros.get(codigo);
        if (reg == null || reg.existencias <= 0) return false;
        reg.existencias--;
        return true;
    }
}
