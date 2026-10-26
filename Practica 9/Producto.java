import java.time.LocalDate;

public abstract class Producto {
    protected final String codigo;
    protected final String nombre;
    protected final LocalDate fechaCaducidad;
    protected final CategoriaProducto categoria;

    public Producto(String codigo, String nombre, LocalDate fechaCaducidad, CategoriaProducto categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fechaCaducidad = fechaCaducidad;
        this.categoria = categoria;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public LocalDate getFechaCaducidad() { return fechaCaducidad; }
    public CategoriaProducto getCategoria() { return categoria; }
}
