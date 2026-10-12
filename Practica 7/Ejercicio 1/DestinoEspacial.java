abstract class DestinoEspacial {
    private final String codigo;
    private final String nombre;
    private final double gravedad;
    protected DestinoEspacial(String codigo, String nombre, double gravedad) {
        this.codigo = codigo; this.nombre = nombre; this.gravedad = gravedad;
    }
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getGravedad() { return gravedad; }
}

class Planeta extends DestinoEspacial {
    private final boolean tieneAtmosfera;
    public Planeta(String codigo, String nombre, double gravedad, boolean tieneAtmosfera) {
        super(codigo, nombre, gravedad); this.tieneAtmosfera = tieneAtmosfera;
    }
    public boolean tieneAtmosfera() { return tieneAtmosfera; }
}

class SateliteNatural extends DestinoEspacial {
    private final String orbitaA;
    public SateliteNatural(String codigo, String nombre, double gravedad, String orbitaA) {
        super(codigo, nombre, gravedad); this.orbitaA = orbitaA;
    }
    public String getOrbitaA() { return orbitaA; }
}
