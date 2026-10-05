import java.util.*;

public class Pedido implements Discountable {
    private final Map<Integer, ElementoPedido> elementos = new LinkedHashMap<>();
    private DescuentoStrategy descuentoStrategy;

    private Pedido() {}

    public static Pedido empty() {
        return new Pedido();
    }

    @Override
    public void setDiscount(DescuentoStrategy descuentoStrategy) {
        this.descuentoStrategy = descuentoStrategy;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        if (elementos.containsKey(producto.getId())) {
            elementos.get(producto.getId()).addCantidad(cantidad);
        } else {
            elementos.put(producto.getId(), new ElementoPedido(producto, cantidad));
        }
    }

    public Collection<ElementoPedido> getElementos() {
        return elementos.values();
    }
}
