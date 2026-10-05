import java.util.*;

class RegularDescuentoStrategy implements DescuentoStrategy {
    @Override
    public double calcularDescuento(double subtotal) {
        return 0.0;
    }
}

class FrecuenteDescuentoStrategy implements DescuentoStrategy {
    @Override
    public double calcularDescuento(double subtotal) {
        return subtotal * 0.10;
    }
}

class MayoreoDescuentoStrategy implements DescuentoStrategy {
    @Override
    public double calcularDescuento(double subtotal) {
        if (subtotal >= 5000.0) {
            return subtotal * 0.15;
        }
        return 0.0;
    }
}

public class PedidoServiceImpl implements PedidoService {
    private final ProductoRepository productoRepository;

    public PedidoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public EstadoAgregarProducto agregarProducto(Pedido pedido, int idProducto, int cantidad) {
        if (!productoRepository.existe(idProducto)) {
            return EstadoAgregarProducto.PRODUCTO_NO_EXISTE;
        }
        if (cantidad <= 0) {
            return EstadoAgregarProducto.CANTIDAD_INVALIDA;
        }
        Producto p = productoRepository.buscarPorId(idProducto);
        pedido.agregarProducto(p, cantidad);
        return EstadoAgregarProducto.AGREGADO;
    }

    @Override
    public DescuentoStrategy SelectorDescuento(TipoDescuento tipoDescuento) {
        if (tipoDescuento == TipoDescuento.FRECUENTE) {
            return new FrecuenteDescuentoStrategy();
        } else if (tipoDescuento == TipoDescuento.MAYOREO) {
            return new MayoreoDescuentoStrategy();
        }
        return new RegularDescuentoStrategy();
    }

    @Override
    public ResumenPedidoDTO confirmarPedido(Pedido pedido, TipoDescuento tipoDescuento) {
        DescuentoStrategy strategy = SelectorDescuento(tipoDescuento);
        pedido.setDiscount(strategy);

        double subtotal = 0.0;
        List<DetallePedidoDTO> detalles = new ArrayList<>();

        for (ElementoPedido ep : pedido.getElementos()) {
            subtotal += ep.getSubtotal();
            detalles.add(new DetallePedidoDTO(
                ep.getProducto().getId(),
                ep.getProducto().getNombre(),
                ep.getCantidad()
            ));
        }

        double descuento = strategy.calcularDescuento(subtotal);
        double total = subtotal - descuento;

        return new ResumenPedidoDTO(detalles, subtotal, descuento, total);
    }
}
