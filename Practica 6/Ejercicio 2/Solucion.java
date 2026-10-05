import java.io.*;
import java.util.*;

enum AccionPedido { AGREGAR, CONFIRMAR }
enum TipoDescuento { REGULAR, FRECUENTE, MAYOREO }
enum EstadoAgregarProducto { AGREGADO, PRODUCTO_NO_EXISTE, CANTIDAD_INVALIDA }

final class ProductoDTO {
    private final String id;
    private final String nombre;
    private final String precio;
    public ProductoDTO(String id, String nombre, String precio) {
        this.id = id; this.nombre = nombre; this.precio = precio;
    }
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getPrecio() { return precio; }
}

final class DetallePedidoDTO {
    private final int idProducto;
    private final String nombre;
    private final int cantidad;
    public DetallePedidoDTO(int idProducto, String nombre, int cantidad) {
        this.idProducto = idProducto; this.nombre = nombre; this.cantidad = cantidad;
    }
    public int getIdProducto() { return idProducto; }
    public String getNombre() { return nombre; }
    public int getCantidad() { return cantidad; }
}

final class ResumenPedidoDTO {
    private final List<DetallePedidoDTO> detalles;
    private final double subtotal;
    private final double descuento;
    private final double total;
    public ResumenPedidoDTO(List<DetallePedidoDTO> detalles, double subtotal, double descuento, double total) {
        this.detalles = detalles; this.subtotal = subtotal; this.descuento = descuento; this.total = total;
    }
    public List<DetallePedidoDTO> getDetalles() { return detalles; }
    public double getSubtotal() { return subtotal; }
    public double getDescuento() { return descuento; }
    public double getTotal() { return total; }
}

interface DescuentoStrategy {
    double calcularDescuento(double subtotal);
}

interface Discountable {
    void setDiscount(DescuentoStrategy descuentoStrategy);
}

interface ProductoRepository {
    boolean guardar(ProductoDTO producto);
    Producto buscarPorId(int idProducto);
    boolean existe(int idProducto);
}

interface PedidoService {
    EstadoAgregarProducto agregarProducto(Pedido pedido, int idProducto, int cantidad);
    DescuentoStrategy SelectorDescuento(TipoDescuento tipoDescuento);
    ResumenPedidoDTO confirmarPedido(Pedido pedido, TipoDescuento tipoDescuento);
}

public class Solution {
    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String productos = br.readLine();
        String operaciones = br.readLine();

        ProductoRepository productoRepository = new ProductoRepositoryImpl();
        PedidoService pedidoService = new PedidoServiceImpl(productoRepository);
        Pedido pedido = Pedido.empty();

        if (productos != null && !productos.isBlank() && !productos.equals("-")) {
            String[] registros = productos.split(";");
            for (String registro : registros) {
                String[] datos = registro.split("\\|", 3);
                ProductoDTO productoDTO = new ProductoDTO(datos[0].trim(), datos[1].trim(), datos[2].trim());
                productoRepository.guardar(productoDTO);
            }
        }

        StringBuilder salida = new StringBuilder();

        if (operaciones != null && !operaciones.isBlank() && !operaciones.equals("-")) {
            String[] listaOperaciones = operaciones.split(";");
            for (String operacion : listaOperaciones) {
                String[] datos = operacion.split("\\|");
                AccionPedido accion = AccionPedido.valueOf(datos[0].trim());

                if (accion == AccionPedido.AGREGAR) {
                    int idProducto = Integer.parseInt(datos[1].trim());
                    int cantidad = Integer.parseInt(datos[2].trim());
                    EstadoAgregarProducto estado = pedidoService.agregarProducto(pedido, idProducto, cantidad);
                    salida.append("AGREGAR ").append(idProducto).append(" ").append(estado).append("\n");
                }
                else if (accion == AccionPedido.CONFIRMAR) {
                    TipoDescuento tipoDescuento = TipoDescuento.valueOf(datos[1].trim());
                    ResumenPedidoDTO resumen = pedidoService.confirmarPedido(pedido, tipoDescuento);
                    if (resumen == null) {
                        throw new IllegalStateException("confirmarPedido no debe regresar null");
                    }
                    imprimirResumen(salida, resumen);
                }
            }
        }
        System.out.print(salida.toString());
    }

    private static void imprimirResumen(StringBuilder salida, ResumenPedidoDTO resumen) {
        List<DetallePedidoDTO> detalles = resumen.getDetalles();
        if (detalles == null) {
            throw new IllegalStateException("La lista de detalles no debe ser null");
        }
        for (DetallePedidoDTO detalle : detalles) {
            salida.append("ITEM ").append(detalle.getIdProducto()).append(" ")
                  .append(detalle.getNombre()).append(" ").append(detalle.getCantidad()).append("\n");
        }
        salida.append(String.format(Locale.US, "RESUMEN %.2f %.2f %.2f",
                resumen.getSubtotal(), resumen.getDescuento(), resumen.getTotal()));
    }
}
