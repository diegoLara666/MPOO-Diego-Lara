package mx.unam.fi.smartvend.application;

import mx.unam.fi.smartvend.domain.*;
import mx.unam.fi.smartvend.api.dto.RegistroProductoDTO;
import mx.unam.fi.smartvend.api.dto.CompraDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class SmartVendService {
    private final Inventario inventario;
    private final Monedero monederoEfectivo;
    private final Monedero monederoTarjeta;

    public SmartVendService(
            Inventario inventario,
            @Qualifier("monederoEfectivo") Monedero monederoEfectivo,
            @Qualifier("monederoTarjeta") Monedero monederoTarjeta) {
        this.inventario = inventario;
        this.monederoEfectivo = monederoEfectivo;
        this.monederoTarjeta = monederoTarjeta;
    }

    public boolean registrarProducto(RegistroProductoDTO dto) {
        Producto p = new Producto(dto.codigo(), dto.nombre(), dto.fechaCaducidad(), dto.categoria());
        return inventario.registrarProducto(p, dto.precio());
    }

    public Optional<ProductoInventario> buscarProducto(String codigo) {
        return inventario.buscarProducto(codigo);
    }

    public boolean reabastecer(String codigo, int cantidad) {
        return inventario.reabastecer(codigo, cantidad);
    }

    public ResultadoCompra comprar(CompraDTO dto) {
        Optional<ProductoInventario> opt = inventario.buscarProducto(dto.codigoProducto());
        
        if (opt.isEmpty()) {
            return new ResultadoCompra(EstadoCompra.PRODUCTO_NO_ENCONTRADO, null, dto.cantidadRecibida());
        }
        
        ProductoInventario item = opt.get();
        if (item.existencias() <= 0) {
            return new ResultadoCompra(EstadoCompra.SIN_EXISTENCIAS, item.producto(), dto.cantidadRecibida());
        }
        
        Monedero monedero = (dto.metodoPago() == MetodoPago.TARJETA) ? monederoTarjeta : monederoEfectivo;
        ResultadoCobro resCobro = monedero.cobrar(item.precio(), dto.cantidadRecibida());
        
        if (resCobro.estado() == EstadoCobro.MONTO_INVALIDO) {
            return new ResultadoCompra(EstadoCompra.PAGO_INSUFICIENTE, item.producto(), resCobro.cambio());
        }
        if (resCobro.estado() == EstadoCobro.SIN_FONDOS_SUFICIENTES) {
            return new ResultadoCompra(EstadoCompra.SIN_FONDOS_SUFICIENTES, item.producto(), resCobro.cambio());
        }
        
        inventario.despachar(item.producto().getCodigo());
        return new ResultadoCompra(EstadoCompra.VENTA_REALIZADA, item.producto(), resCobro.cambio());
    }

    public double getFondosEfectivo() { return monederoEfectivo.consultarFondos(); }
    public double getFondosTarjeta() { return monederoTarjeta.consultarFondos(); }
}
