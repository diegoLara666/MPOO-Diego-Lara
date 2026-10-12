import java.util.*;

class MisionFactoryServiceImpl implements MisionFactoryService {
    @Override public Mision crearMision(MisionDTO dto, Nave nave, DestinoEspacial destino, Map<String, Recurso> recursos) {
        List<Recurso> recursosAsignados = new ArrayList<>();
        for (String id : dto.getIdsRecursos()) {
            if (recursos.containsKey(id)) {
                recursosAsignados.add(recursos.get(id));
            }
        }
        switch (dto.getTipo()) {
            case EXPLORACION: return new MisionExploracion(dto.getId(), dto.getNombre(), nave, destino, recursosAsignados);
            case RESCATE: return new MisionRescate(dto.getId(), dto.getNombre(), nave, destino, recursosAsignados);
            case TRANSPORTE: return new MisionTransporte(dto.getId(), dto.getNombre(), nave, destino, recursosAsignados);
            default: throw new IllegalArgumentException("Tipo de mision desconocido");
        }
    }
}

class CapacidadServiceImpl implements CapacidadService {
    @Override public List<Capacidad> obtenerCapacidades(Object entidad) {
        List<Capacidad> capacidades = new ArrayList<>();
        if (entidad instanceof Comunicable) capacidades.add(Capacidad.COMUNICABLE);
        if (entidad instanceof Rastreable) capacidades.add(Capacidad.RASTREABLE);
        if (entidad instanceof Operable) capacidades.add(Capacidad.OPERABLE);
        if (entidad instanceof Escaneable) capacidades.add(Capacidad.ESCANEABLE);
        return capacidades;
    }
}
