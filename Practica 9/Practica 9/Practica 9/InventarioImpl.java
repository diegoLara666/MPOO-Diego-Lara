import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InventarioImpl implements Inventario {
    private static class RegistroInterno {
        Producto producto;
        double precio;
        int existencias;

        RegistroInterno(Producto producto, double precio, int existencias) {
            this.producto = producto;
            this.precio = precio;
            this.existencias = existencias;
        }
    }

    private final Map<String, RegistroInterno> registros = new HashMap<>();

    @Override
    public ResultadoRegistro registrarProducto(Producto producto, double precio) {
        if (producto == null || producto.getCodigo() == null || producto.getCodigo().trim().isEmpty()) {
            return ResultadoRegistro.PRODUCTO_INVALIDO;
        }
        if (precio <= 0) {
            return ResultadoRegistro.PRECIO_INVALIDO;
        }
        if (registros.containsKey(producto.getCodigo())) {
            return ResultadoRegistro.CODIGO_DUPLICADO;
        }
        
        registros.put(producto.getCodigo(), new RegistroInterno(producto, precio, 0));
        return ResultadoRegistro.PRODUCTO_REGISTRADO;
    }

    @Override
    public Optional<ProductoInventario> buscarProducto(String codigo) {
        if (!registros.containsKey(codigo)) return Optional.empty();
        RegistroInterno registro = registros.get(codigo);
        return Optional.of(new ProductoInventario(registro.producto, registro.precio, registro.existencias));
    }

    @Override
    public ResultadoReabastecimiento reabastecer(String codigo, int cantidad) {
        if (cantidad <= 0) return ResultadoReabastecimiento.CANTIDAD_INVALIDA;
        if (!registros.containsKey(codigo)) return ResultadoReabastecimiento.PRODUCTO_NO_ENCONTRADO;
        
        registros.get(codigo).existencias += cantidad;
        return ResultadoReabastecimiento.REABASTECIMIENTO_REALIZADO;
    }

    @Override
    public ResultadoEntrega despachar(String codigo) {
        if (!registros.containsKey(codigo)) return ResultadoEntrega.PRODUCTO_NO_ENCONTRADO;
        
        RegistroInterno registro = registros.get(codigo);
        if (registro.existencias <= 0) return ResultadoEntrega.SIN_EXISTENCIAS;
        
        registro.existencias--;
        return ResultadoEntrega.PRODUCTO_PREPARADO;
    }
}
