abstract class Recurso {
    private final String id;
    protected Recurso(String id) { this.id = id; }
    public String getId() { return id; }
}

class Dron extends Recurso implements Comunicable, Rastreable, Operable, Escaneable {
    private final String modelo;
    private final String posicion;
    private final boolean operativo;
    public Dron(String id, String modelo, String posicion, boolean operativo) {
        super(id); this.modelo = modelo; this.posicion = posicion; this.operativo = operativo;
    }
    @Override public String comunicar() { return "Dron comunicando"; }
    @Override public String obtenerPosicion() { return posicion; }
    @Override public boolean estaOperativo() { return operativo; }
    @Override public String escanear() { return "Dron escaneando"; }
}

class SateliteArtificial extends Recurso implements Comunicable, Rastreable, Operable, Escaneable {
    private final String nombre;
    private final String posicion;
    private final boolean operativo;
    public SateliteArtificial(String id, String nombre, String posicion, boolean operativo) {
        super(id); this.nombre = nombre; this.posicion = posicion; this.operativo = operativo;
    }
    @Override public String comunicar() { return "Satelite comunicando"; }
    @Override public String obtenerPosicion() { return posicion; }
    @Override public boolean estaOperativo() { return operativo; }
    @Override public String escanear() { return "Satelite escaneando"; }
}

class Carga extends Recurso implements Rastreable {
    private final String descripcion;
    private final double peso;
    private final String posicion;
    public Carga(String id, String descripcion, double peso, String posicion) {
        super(id); this.descripcion = descripcion; this.peso = peso; this.posicion = posicion;
    }
    @Override public String obtenerPosicion() { return posicion; }
}

class Tripulante extends Recurso implements Comunicable, Rastreable {
    private final String nombre;
    private final String posicion;
    public Tripulante(String id, String nombre, String posicion) {
        super(id); this.nombre = nombre; this.posicion = posicion;
    }
    @Override public String comunicar() { return "Tripulante comunicando"; }
    @Override public String obtenerPosicion() { return posicion; }
}
