import java.time.LocalDate;

public class Refresco extends Producto {
    public Refresco(String codigo, String nombre, LocalDate fechaCaducidad) {
        super(codigo, nombre, fechaCaducidad, CategoriaProducto.BEBIDA);
    }
}
