import java.util.*;

public class ProductoRepositoryImpl implements ProductoRepository {
    private final Map<Integer, Producto> inventario = new HashMap<>();

    public ProductoRepositoryImpl() {}

    @Override
    public boolean guardar(ProductoDTO producto) {
        int id = Integer.parseInt(producto.getId());
        double precio = Double.parseDouble(producto.getPrecio());
        inventario.put(id, new Producto(id, producto.getNombre(), precio));
        return true;
    }

    @Override
    public Producto buscarPorId(int idProducto) {
        return inventario.get(idProducto);
    }

    @Override
    public boolean existe(int idProducto) {
        return inventario.containsKey(idProducto);
    }
}
