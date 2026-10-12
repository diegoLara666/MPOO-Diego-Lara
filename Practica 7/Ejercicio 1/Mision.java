import java.util.*;

abstract class Mision {
    protected final String id;
    protected final String nombre;
    protected final Nave nave;
    protected final DestinoEspacial destino;
    protected final List<Recurso> recursosAsignados;

    public Mision(String id, String nombre, Nave nave, DestinoEspacial destino, List<Recurso> recursosAsignados) {
        this.id = id; this.nombre = nombre; this.nave = nave; this.destino = destino; this.recursosAsignados = recursosAsignados;
    }

    public final EjecucionMisionDTO ejecutarMision() {
        EstadoValidacion validacion = validarMision();
        if (validacion == EstadoValidacion.FALLIDA) {
            return new EjecucionMisionDTO(EstadoValidacion.FALLIDA, EstadoPaso.NO_EJECUTADO, EstadoPaso.NO_EJECUTADO, ResultadoObjetivo.NO_EJECUTADO, EstadoPaso.NO_EJECUTADO, EstadoPaso.NO_EJECUTADO);
        }
        EstadoPaso preparacion = prepararRecursos();
        EstadoPaso viaje = iniciarViaje();
        ResultadoObjetivo objetivo = ejecutarObjetivo();
        EstadoPaso finalizacion = finalizarOperacion();
        EstadoPaso reporte = generarReporte();

        return new EjecucionMisionDTO(validacion, preparacion, viaje, objetivo, finalizacion, reporte);
    }

    protected EstadoValidacion validarMision() {
        if (!nave.estaOperativo()) return EstadoValidacion.FALLIDA;
        return validarRecursosEspecificos() ? EstadoValidacion.OK : EstadoValidacion.FALLIDA;
    }
    protected abstract boolean validarRecursosEspecificos();
    protected EstadoPaso prepararRecursos() { return EstadoPaso.OK; }
    protected EstadoPaso iniciarViaje() { return EstadoPaso.OK; }
    protected abstract ResultadoObjetivo ejecutarObjetivo();
    protected EstadoPaso finalizarOperacion() { return EstadoPaso.OK; }
    protected EstadoPaso generarReporte() { return EstadoPaso.OK; }
}

class MisionExploracion extends Mision {
    public MisionExploracion(String id, String nombre, Nave nave, DestinoEspacial destino, List<Recurso> recursosAsignados) {
        super(id, nombre, nave, destino, recursosAsignados);
    }
    @Override protected boolean validarRecursosEspecificos() {
        for (Recurso r : recursosAsignados) {
            if (r instanceof Escaneable) return true;
        }
        return false;
    }
    @Override protected ResultadoObjetivo ejecutarObjetivo() { return ResultadoObjetivo.ESCANEO_COMPLETADO; }
}

class MisionRescate extends Mision {
    public MisionRescate(String id, String nombre, Nave nave, DestinoEspacial destino, List<Recurso> recursosAsignados) {
        super(id, nombre, nave, destino, recursosAsignados);
    }
    @Override protected boolean validarRecursosEspecificos() {
        for (Recurso r : recursosAsignados) {
            if (r instanceof Tripulante) return true;
        }
        return false;
    }
    @Override protected ResultadoObjetivo ejecutarObjetivo() { return ResultadoObjetivo.RESCATE_COMPLETADO; }
}

class MisionTransporte extends Mision {
    public MisionTransporte(String id, String nombre, Nave nave, DestinoEspacial destino, List<Recurso> recursosAsignados) {
        super(id, nombre, nave, destino, recursosAsignados);
    }
    @Override protected boolean validarRecursosEspecificos() {
        for (Recurso r : recursosAsignados) {
            if (r instanceof Carga) return true;
        }
        return false;
    }
    @Override protected ResultadoObjetivo ejecutarObjetivo() { return ResultadoObjetivo.CARGA_ENTREGADA; }
}
