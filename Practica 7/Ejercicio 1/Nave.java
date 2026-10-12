class Nave implements Comunicable, Rastreable, Operable {
    private final String id;
    private final String nombre;
    private final String posicion;
    private final boolean operativa;

    public Nave(String id, String nombre, String posicion, boolean operativa) {
        this.id = id; this.nombre = nombre; this.posicion = posicion; this.operativa = operativa;
    }
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    @Override public String comunicar() { return "Nave comunicando"; }
    @Override public String obtenerPosicion() { return posicion; }
    @Override public boolean estaOperativo() { return operativa; }
}
