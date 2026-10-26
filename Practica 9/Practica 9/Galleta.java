import java.time.LocalDate;

public class Galleta extends Producto {
    public Galleta(String codigo, String nombre, LocalDate fechaCaducidad) {
        super(codigo, nombre, fechaCaducidad, CategoriaProducto.SNACK);
    }
}
