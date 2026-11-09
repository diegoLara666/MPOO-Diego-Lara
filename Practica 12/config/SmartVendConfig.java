package mx.unam.fi.smartvend.config;

import mx.unam.fi.smartvend.domain.Inventario;
import mx.unam.fi.smartvend.domain.Monedero;
import mx.unam.fi.smartvend.infrastructure.InventarioImpl;
import mx.unam.fi.smartvend.infrastructure.MonederoEfectivo;
import mx.unam.fi.smartvend.infrastructure.MonederoTarjeta;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmartVendConfig {

    @Bean
    public Inventario inventario() {
        return new InventarioImpl();
    }

    @Bean(name = "monederoEfectivo")
    public Monedero monederoEfectivo() {
        return new MonederoEfectivo(500.0);
    }

    @Bean(name = "monederoTarjeta")
    public Monedero monederoTarjeta() {
        return new MonederoTarjeta(250.0);
    }
}
