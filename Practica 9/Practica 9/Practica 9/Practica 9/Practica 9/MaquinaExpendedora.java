import java.util.Optional;

public class MaquinaExpendedora implements MaquinaDespachadora {
    private final Inventario inventario;
    private final Monedero monedero;

    private MaquinaExpendedora(Builder builder) {
        this.inventario = builder.inventario;
        this.monedero = builder.monedero;
    }

    @Override
    public ResultadoRegistro registrarProducto(RegistroProductoDTO dto) {
        Producto producto;
        if (dto.getCategoria() == CategoriaProducto.BEBIDA) {
            if (dto.getNombre().toLowerCase().contains("jugo")) {
                producto = new Jugo(dto.getCodigo(), dto.getNombre(), dto.getFechaCaducidad());
            } else {
                producto = new Refresco(dto.getCodigo(), dto.getNombre(), dto.getFechaCaducidad());
            }
        } else {
            if (dto.getNombre().toLowerCase().contains("galleta")) {
                producto = new Galleta(dto.getCodigo(), dto.getNombre(), dto.getFechaCaducidad());
            } else {
                producto = new Fritura(dto.getCodigo(), dto.getNombre(), dto.getFechaCaducidad());
            }
        }
        return inventario.registrarProducto(producto, dto.getPrecio());
    }

    @Override
    public Optional<ProductoInventario> buscarProducto(String codigo) {
        return inventario.buscarProducto(codigo);
    }

    @Override
    public ResultadoReabastecimiento reabastecer(ReabastecimientoDTO solicitud) {
        return inventario.reabastecer(solicitud.getCodigo(), solicitud.getCantidad());
    }

    @Override
    public ResultadoCompra comprar(CompraDTO solicitud) {
        Optional<ProductoInventario> opt = inventario.buscarProducto(solicitud.getCodigoProducto());
        if (opt.isEmpty()) return new ResultadoCompra(EstadoCompra.PRODUCTO_NO_ENCONTRADO, null, solicitud.getCantidadRecibida());
        
        ProductoInventario item = opt.get();
        if (item.getExistencias() <= 0) return new ResultadoCompra(EstadoCompra.SIN_EXISTENCIAS, item.getProducto(), solicitud.getCantidadRecibida());
        
        ResultadoCobro resCobro = monedero.cobrar(item.getPrecio(), solicitud.getCantidadRecibida());
        
        if (resCobro.getEstado() == EstadoCobro.MONTO_INVALIDO) return new ResultadoCompra(EstadoCompra.PAGO_INSUFICIENTE, item.getProducto(), resCobro.getCambio());
        if (resCobro.getEstado() == EstadoCobro.SIN_FONDOS_SUFICIENTES) return new ResultadoCompra(EstadoCompra.SIN_FONDOS_SUFICIENTES, item.getProducto(), resCobro.getCambio());
        
        inventario.despachar(item.getProducto().getCodigo());
        return new ResultadoCompra(EstadoCompra.VENTA_REALIZADA, item.getProducto(), resCobro.getCambio());
    }

    @Override
    public double consultarFondos() {
        return monedero.consultarFondos();
    }

    public static class Builder {
        private Inventario inventario;
        private Monedero monedero;

        public Builder setInventario(Inventario inventario) {
            this.inventario = inventario;
            return this;
        }

        public Builder setMonedero(Monedero monedero) {
            this.monedero = monedero;
            return this;
        }

        public MaquinaExpendedora build() {
            if (this.inventario == null || this.monedero == null) throw new IllegalStateException("Faltan componentes");
            return new MaquinaExpendedora(this);
        }
    }
}
