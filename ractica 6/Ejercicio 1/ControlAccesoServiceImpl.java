import java.util.*;

public class ControlAccesoServiceImpl implements ControlAccesoService {

    private static final Map<Integer, Asistente> registrados = new LinkedHashMap<>();
    private static final Set<Integer> dentro = new HashSet<>();

    @Override
    public boolean registrarAsistente(AsistenteDTO asistente) {
        int id = Integer.parseInt(asistente.getId());
        if (!registrados.containsKey(id)) {
            TipoAsistente tipo = TipoAsistente.valueOf(asistente.getTipo());
            registrados.put(id, new Asistente(id, asistente.getNombre(), tipo));
            return true;
        }
        return false;
    }

    @Override
    public EstadoEntrada registrarEntrada(int idAsistente) {
        if (!registrados.containsKey(idAsistente)) {
            return EstadoEntrada.NO_REGISTRADO;
        }
        if (dentro.contains(idAsistente)) {
            return EstadoEntrada.YA_DENTRO;
        }
        if (dentro.size() >= AFORO_MAXIMO) {
            return EstadoEntrada.AFORO_COMPLETO;
        }
        dentro.add(idAsistente);
        return EstadoEntrada.AUTORIZADO;
    }

    @Override
    public EstadoSalida registrarSalida(int idAsistente) {
        if (!registrados.containsKey(idAsistente)) {
            return EstadoSalida.NO_REGISTRADO;
        }
        if (!dentro.contains(idAsistente)) {
            return EstadoSalida.NO_ESTA_DENTRO;
        }
        dentro.remove(idAsistente);
        return EstadoSalida.AUTORIZADA;
    }

    @Override
    public List<ResultadoEntradaDTO> registrarEntradasMasivas(List<Integer> identificadores) {
        List<ResultadoEntradaDTO> resultados = new ArrayList<>();
        for (int id : identificadores) {
            if (dentro.size() >= AFORO_MAXIMO) {
                break;
            }
            EstadoEntrada estado = registrarEntrada(id);
            resultados.add(new ResultadoEntradaDTO(id, estado));
        }
        return resultados;
    }

    @Override
    public ReporteDTO generarReporte() {
        int totalRegistrados = registrados.size();
        int aforoActual = dentro.size();
        int disponibles = AFORO_MAXIMO - aforoActual;
        int alumnos = 0;
        int profesores = 0;
        int invitados = 0;

        for (int id : dentro) {
            Asistente a = registrados.get(id);
            if (a.getTipo() == TipoAsistente.ALUMNO) alumnos++;
            else if (a.getTipo() == TipoAsistente.PROFESOR) profesores++;
            else if (a.getTipo() == TipoAsistente.INVITADO) invitados++;
        }

        double ocupacion = ((double) aforoActual / AFORO_MAXIMO) * 100.0;

        return new ReporteDTO(totalRegistrados, disponibles, aforoActual, alumnos, profesores, invitados, ocupacion);
    }
}
