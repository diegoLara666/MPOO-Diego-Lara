import java.time.LocalDate;

public class Jugo extends Producto {
    public Jugo(String codigo, String nombre, LocalDate fechaCaducidad) {
        super(codigo, nombre, fechaCaducidad, CategoriaProducto.BEBIDA);
    }
}
