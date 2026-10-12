class DestinoMapperImpl implements DestinoMapper {
    @Override public DestinoEspacial toEntity(DestinoDTO dto) {
        if (dto.getTipo() == TipoDestino.PLANETA) {
            return new Planeta(dto.getCodigo(), dto.getNombre(), dto.getGravedad(), Boolean.parseBoolean(dto.getDatoEspecifico()));
        } else {
            return new SateliteNatural(dto.getCodigo(), dto.getNombre(), dto.getGravedad(), dto.getDatoEspecifico());
        }
    }
}

class NaveMapperImpl implements NaveMapper {
    @Override public Nave toEntity(NaveDTO dto) {
        return new Nave(dto.getId(), dto.getNombre(), dto.getPosicion(), dto.isOperativa());
    }
}

class RecursoMapperImpl implements RecursoMapper {
    @Override public Recurso toEntity(RecursoDTO dto) {
        if (dto instanceof DronDTO) {
            DronDTO d = (DronDTO) dto;
            return new Dron(d.getId(), d.getModelo(), d.getPosicion(), d.isOperativo());
        } else if (dto instanceof SateliteArtificialDTO) {
            SateliteArtificialDTO s = (SateliteArtificialDTO) dto;
            return new SateliteArtificial(s.getId(), s.getNombre(), s.getPosicion(), s.isOperativo());
        } else if (dto instanceof CargaDTO) {
            CargaDTO c = (CargaDTO) dto;
            return new Carga(c.getId(), c.getDescripcion(), c.getPeso(), c.getPosicion());
        } else if (dto instanceof TripulanteDTO) {
            TripulanteDTO t = (TripulanteDTO) dto;
            return new Tripulante(t.getId(), t.getNombre(), t.getPosicion());
        }
        return null;
    }
}
