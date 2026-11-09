package mx.unam.fi.smartvend.api.controller;

import mx.unam.fi.smartvend.api.dto.*;
import mx.unam.fi.smartvend.application.SmartVendService;
import mx.unam.fi.smartvend.domain.ProductoInventario;
import mx.unam.fi.smartvend.domain.ResultadoCompra;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class SmartVendController {

    private final SmartVendService service;

    public SmartVendController(SmartVendService service) {
        this.service = service;
    }

    @PostMapping("/productos")
    public ResponseEntity<String> registrarProducto(@RequestBody RegistroProductoDTO dto) {
        boolean exito = service.registrarProducto(dto);
        return exito ? ResponseEntity.ok("Producto registrado") : ResponseEntity.badRequest().body("Error al registrar");
    }

    @GetMapping("/productos/{codigo}")
    public ResponseEntity<ProductoInventario> buscarProducto(@PathVariable String codigo) {
        Optional<ProductoInventario> opt = service.buscarProducto(codigo);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/productos/{codigo}/existencias")
    public ResponseEntity<String> reabastecer(@PathVariable String codigo, @RequestBody ReabastecerDTO dto) {
        boolean exito = service.reabastecer(codigo, dto.cantidad());
        return exito ? ResponseEntity.ok("Reabastecimiento exitoso") : ResponseEntity.badRequest().body("Error al reabastecer");
    }

    @PostMapping("/producto/{codigo}/comprar")
    public ResponseEntity<ResultadoCompra> comprar(@PathVariable String codigo, @RequestBody CompraDTO dto) {
        CompraDTO solicitud = new CompraDTO(codigo, dto.metodoPago(), dto.cantidadRecibida());
        ResultadoCompra res = service.comprar(solicitud);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/fondos")
    public ResponseEntity<FondosDTO> consultarFondos() {
        double efectivo = service.getFondosEfectivo();
        double tarjeta = service.getFondosTarjeta();
        return ResponseEntity.ok(new FondosDTO(efectivo, tarjeta, efectivo + tarjeta));
    }
}
