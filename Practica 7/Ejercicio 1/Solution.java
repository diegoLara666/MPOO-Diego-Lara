import java.io.*;
import java.util.*;

enum TipoDestino { PLANETA, SATELITE_NATURAL }
enum TipoRecurso { DRON, SATELITE_ARTIFICIAL, CARGA, TRIPULANTE }
enum TipoMision { EXPLORACION, RESCATE, TRANSPORTE }
enum Capacidad { COMUNICABLE, RASTREABLE, OPERABLE, ESCANEABLE }
enum EstadoValidacion { OK, FALLIDA }
enum EstadoPaso { OK, NO_EJECUTADO }
enum ResultadoObjetivo { ESCANEO_COMPLETADO, RESCATE_COMPLETADO, CARGA_ENTREGADA, NO_EJECUTADO }

final class DestinoDTO {
    private final TipoDestino tipo; private final String codigo; private final String nombre; private final double gravedad; private final String datoEspecifico;
    public DestinoDTO(TipoDestino tipo, String codigo, String nombre, double gravedad, String datoEspecifico) {
        this.tipo = tipo; this.codigo = codigo; this.nombre = nombre; this.gravedad = gravedad; this.datoEspecifico = datoEspecifico;
    }
    public TipoDestino getTipo() { return tipo; } public String getCodigo() { return codigo; } public String getNombre() { return nombre; }
    public double getGravedad() { return gravedad; } public String getDatoEspecifico() { return datoEspecifico; }
}

final class NaveDTO {
    private final String id; private final String nombre; private final String posicion; private final boolean operativa;
    public NaveDTO(String id, String nombre, String posicion, boolean operativa) {
        this.id = id; this.nombre = nombre; this.posicion = posicion; this.operativa = operativa;
    }
    public String getId() { return id; } public String getNombre() { return nombre; } public String getPosicion() { return posicion; } public boolean isOperativa() { return operativa; }
}

abstract class RecursoDTO {
    private final TipoRecurso tipo; private final String id;
    protected RecursoDTO(TipoRecurso tipo, String id) { this.tipo = tipo; this.id = id; }
    public TipoRecurso getTipo() { return tipo; } public String getId() { return id; }
}

final class DronDTO extends RecursoDTO {
    private final String modelo; private final String posicion; private final boolean operativo;
    public DronDTO(String id, String modelo, String posicion, boolean operativo) { super(TipoRecurso.DRON, id); this.modelo = modelo; this.posicion = posicion; this.operativo = operativo; }
    public String getModelo() { return modelo; } public String getPosicion() { return posicion; } public boolean isOperativo() { return operativo; }
}

final class SateliteArtificialDTO extends RecursoDTO {
    private final String nombre; private final String posicion; private final boolean operativo;
    public SateliteArtificialDTO(String id, String nombre, String posicion, boolean operativo) { super(TipoRecurso.SATELITE_ARTIFICIAL, id); this.nombre = nombre; this.posicion = posicion; this.operativo = operativo; }
    public String getNombre() { return nombre; } public String getPosicion() { return posicion; } public boolean isOperativo() { return operativo; }
}

final class CargaDTO extends RecursoDTO {
    private final String descripcion; private final double peso; private final String posicion;
    public CargaDTO(String id, String descripcion, double peso, String posicion) { super(TipoRecurso.CARGA, id); this.descripcion = descripcion; this.peso = peso; this.posicion = posicion; }
    public String getDescripcion() { return descripcion; } public double getPeso() { return peso; } public String getPosicion() { return posicion; }
}

final class TripulanteDTO extends RecursoDTO {
    private final String nombre; private final String posicion;
    public TripulanteDTO(String id, String nombre, String posicion) { super(TipoRecurso.TRIPULANTE, id); this.nombre = nombre; this.posicion = posicion; }
    public String getNombre() { return nombre; } public String getPosicion() { return posicion; }
}

final class MisionDTO {
    private final String id; private final String nombre; private final TipoMision tipo; private final List<String> idsRecursos;
    public MisionDTO(String id, String nombre, TipoMision tipo, List<String> idsRecursos) { this.id = id; this.nombre = nombre; this.tipo = tipo; this.idsRecursos = new ArrayList<String>(idsRecursos); }
    public String getId() { return id; } public String getNombre() { return nombre; } public TipoMision getTipo() { return tipo; } public List<String> getIdsRecursos() { return new ArrayList<String>(idsRecursos); }
}

final class EjecucionMisionDTO {
    private final EstadoValidacion validacion; private final EstadoPaso preparacion; private final EstadoPaso viaje; private final ResultadoObjetivo objetivo; private final EstadoPaso finalizacion; private final EstadoPaso reporte;
    public EjecucionMisionDTO(EstadoValidacion validacion, EstadoPaso preparacion, EstadoPaso viaje, ResultadoObjetivo objetivo, EstadoPaso finalizacion, EstadoPaso reporte) {
        this.validacion = validacion; this.preparacion = preparacion; this.viaje = viaje; this.objetivo = objetivo; this.finalizacion = finalizacion; this.reporte = reporte;
    }
    public EstadoValidacion getValidacion() { return validacion; } public EstadoPaso getPreparacion() { return preparacion; } public EstadoPaso getViaje() { return viaje; }
    public ResultadoObjetivo getObjetivo() { return objetivo; } public EstadoPaso getFinalizacion() { return finalizacion; } public EstadoPaso getReporte() { return reporte; }
}

interface DestinoMapper { DestinoEspacial toEntity(DestinoDTO dto); }
interface NaveMapper { Nave toEntity(NaveDTO dto); }
interface RecursoMapper { Recurso toEntity(RecursoDTO dto); }
interface MisionFactoryService { Mision crearMision(MisionDTO dto, Nave nave, DestinoEspacial destino, Map<String, Recurso> recursos); }
interface CapacidadService { List<Capacidad> obtenerCapacidades(Object entidad); }

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String destinoInput = br.readLine();
        String naveInput = br.readLine();
        String recursosInput = br.readLine();
        String misionInput = br.readLine();
        String resultado = procesarMision(destinoInput, naveInput, recursosInput, misionInput);
        System.out.print(resultado);
    }

    public static String procesarMision(String destinoInput, String naveInput, String recursosInput, String misionInput) {
        DestinoDTO destinoDTO = parseDestino(destinoInput);
        NaveDTO naveDTO = parseNave(naveInput);
        List<RecursoDTO> recursosDTO = parseRecursos(recursosInput);
        MisionDTO misionDTO = parseMision(misionInput);

        DestinoMapper destinoMapper = new DestinoMapperImpl();
        NaveMapper naveMapper = new NaveMapperImpl();
        RecursoMapper recursoMapper = new RecursoMapperImpl();
        MisionFactoryService factory = new MisionFactoryServiceImpl();
        CapacidadService capacidadService = new CapacidadServiceImpl();

        DestinoEspacial destino = destinoMapper.toEntity(destinoDTO);
        Nave nave = naveMapper.toEntity(naveDTO);
        Map<String, Recurso> recursos = mapearRecursos(recursosDTO, recursoMapper);

        Mision mision = factory.crearMision(misionDTO, nave, destino, recursos);
        EjecucionMisionDTO ejecucion = mision.ejecutarMision();

        return construirSalida(destino, nave, recursos, misionDTO, ejecucion, capacidadService);
    }

    private static Map<String, Recurso> mapearRecursos(List<RecursoDTO> recursosDTO, RecursoMapper mapper) {
        Map<String, Recurso> recursos = new LinkedHashMap<String, Recurso>();
        for (RecursoDTO dto : recursosDTO) {
            Recurso recurso = mapper.toEntity(dto);
            recursos.put(recurso.getId(), recurso);
        }
        return recursos;
    }

    private static String construirSalida(DestinoEspacial destino, Nave nave, Map<String, Recurso> recursos, MisionDTO misionDTO, EjecucionMisionDTO ejecucion, CapacidadService capacidadService) {
        List<String> salida = new ArrayList<String>();
        salida.add("MISION " + misionDTO.getId());
        salida.add("TIPO " + misionDTO.getTipo());
        salida.add(construirDestino(destino));
        salida.add(construirCapacidades(nave.getId(), nave, capacidadService));

        for (String id : misionDTO.getIdsRecursos()) {
            Recurso recurso = recursos.get(id);
            if (recurso != null) {
                salida.add(construirCapacidades(recurso.getId(), recurso, capacidadService));
            }
        }

        if (ejecucion.getValidacion() == EstadoValidacion.FALLIDA) {
            salida.add("VALIDACION FALLIDA");
            salida.add("MISION ABORTADA");
            return String.join("\n", salida);
        }

        salida.add("VALIDACION OK");
        if (ejecucion.getPreparacion() == EstadoPaso.OK) salida.add("PREPARACION OK");
        if (ejecucion.getViaje() == EstadoPaso.OK) salida.add("VIAJE " + destino.getNombre());
        if (ejecucion.getObjetivo() != ResultadoObjetivo.NO_EJECUTADO) salida.add("OBJETIVO " + ejecucion.getObjetivo().name());
        if (ejecucion.getFinalizacion() == EstadoPaso.OK) salida.add("FINALIZACION OK");
        if (ejecucion.getReporte() == EstadoPaso.OK) salida.add("REPORTE COMPLETADO");
        return String.join("\n", salida);
    }

    private static String construirDestino(DestinoEspacial destino) {
        if (destino instanceof Planeta) {
            Planeta planeta = (Planeta) destino;
            return "DESTINO PLANETA " + planeta.getNombre() + " ATMOSFERA " + (planeta.tieneAtmosfera() ? "SI" : "NO");
        }
        if (destino instanceof SateliteNatural) {
            SateliteNatural satelite = (SateliteNatural) destino;
            return "DESTINO SATELITE_NATURAL " + satelite.getNombre() + " ORBITA " + satelite.getOrbitaA();
        }
        throw new IllegalArgumentException("Destino no valido");
    }

    private static String construirCapacidades(String id, Object entidad, CapacidadService servicio) {
        List<Capacidad> capacidades = servicio.obtenerCapacidades(entidad);
        List<String> nombres = new ArrayList<String>();
        for (Capacidad capacidad : capacidades) nombres.add(capacidad.name());
        return "CAPACIDADES " + id + " " + String.join(",", nombres);
    }

    private static DestinoDTO parseDestino(String input) {
        String[] d = input.split("\\|", 5);
        return new DestinoDTO(TipoDestino.valueOf(d[0].trim()), d[1].trim(), d[2].trim(), Double.parseDouble(d[3].trim()), d[4].trim());
    }

    private static NaveDTO parseNave(String input) {
        String[] d = input.split("\\|", 4);
        return new NaveDTO(d[0].trim(), d[1].trim(), d[2].trim(), Boolean.parseBoolean(d[3].trim()));
    }

    private static List<RecursoDTO> parseRecursos(String input) {
        List<RecursoDTO> recursos = new ArrayList<RecursoDTO>();
        if (input == null || input.trim().isEmpty() || input.equals("-")) return recursos;
        String[] registros = input.split(";");
        for (String registro : registros) {
            String[] d = registro.split("\\|");
            TipoRecurso tipo = TipoRecurso.valueOf(d[0].trim());
            switch (tipo) {
                case DRON: recursos.add(new DronDTO(d[1].trim(), d[2].trim(), d[3].trim(), Boolean.parseBoolean(d[4].trim()))); break;
                case SATELITE_ARTIFICIAL: recursos.add(new SateliteArtificialDTO(d[1].trim(), d[2].trim(), d[3].trim(), Boolean.parseBoolean(d[4].trim()))); break;
                case CARGA: recursos.add(new CargaDTO(d[1].trim(), d[2].trim(), Double.parseDouble(d[3].trim()), d[4].trim())); break;
                case TRIPULANTE: recursos.add(new TripulanteDTO(d[1].trim(), d[2].trim(), d[3].trim())); break;
            }
        }
        return recursos;
    }

    private static MisionDTO parseMision(String input) {
        String[] d = input.split("\\|", 4);
        List<String> ids = new ArrayList<String>();
        if (d.length == 4 && !d[3].trim().isEmpty() && !d[3].equals("-")) {
            String[] recursos = d[3].split(",");
            for (String id : recursos) ids.add(id.trim());
        }
        return new MisionDTO(d[0].trim(), d[1].trim(), TipoMision.valueOf(d[2].trim()), ids);
    }
}
