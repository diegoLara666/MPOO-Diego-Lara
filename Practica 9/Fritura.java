import java.time.LocalDate;

public class Fritura extends Producto {
    public Fritura(String codigo, String nombre, LocalDate fechaCaducidad) {
        super(codigo, nombre, fechaCaducidad, CategoriaProducto.SNACK);
    }
}
