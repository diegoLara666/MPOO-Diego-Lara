import java.io.*;
import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

/* =====================================================
 * TIPOS PROPORCIONADOS POR HACKERRANK
 * ===================================================== */
enum TipoReembolso {
    ALIMENTOS,
    TRANSPORTE,
    HOSPEDAJE
}

enum ResultadoValidacion {
    APROBADO,
    SIN_COMPROBANTE,
    FUERA_DE_PERIODO,
    EXCEDE_LIMITE,
    REQUIERE_AUTORIZACION
}

/* =====================================================
 * DTO DE ENTRADA
 * ===================================================== */
final class ReembolsoInputDTO {
    private final TipoReembolso tipo;
    private final String folio;
    private final String empleado;
    private final double monto;
    private final LocalDate fechaGasto;
    private final boolean tieneComprobante;
    private final boolean autorizadoGerente;
    private final LocalDate fechaEvaluacion;

    public ReembolsoInputDTO(TipoReembolso tipo, String folio, String empleado, double monto, 
                             LocalDate fechaGasto, boolean tieneComprobante, 
                             boolean autorizadoGerente, LocalDate fechaEvaluacion) {
        this.tipo = tipo;
        this.folio = folio;
        this.empleado = empleado;
        this.monto = monto;
        this.fechaGasto = fechaGasto;
        this.tieneComprobante = tieneComprobante;
        this.autorizadoGerente = autorizadoGerente;
        this.fechaEvaluacion = fechaEvaluacion;
    }

    public TipoReembolso getTipo() { return tipo; }
    public String getFolio() { return folio; }
    public String getEmpleado() { return empleado; }
    public double getMonto() { return monto; }
    public LocalDate getFechaGasto() { return fechaGasto; }
    public boolean tieneComprobante() { return tieneComprobante; }
    public boolean estaAutorizadoGerente() { return autorizadoGerente; }
    public LocalDate getFechaEvaluacion() { return fechaEvaluacion; }
}


final class ReembolsoOutputDTO {
    private final String folio;
    private final TipoReembolso tipo;
    private final ResultadoValidacion resultado;

    public ReembolsoOutputDTO(String folio, TipoReembolso tipo, ResultadoValidacion resultado) {
        this.folio = folio;
        this.tipo = tipo;
        this.resultado = resultado;
    }

    public String getFolio() { return folio; }
    public TipoReembolso getTipo() { return tipo; }
    public ResultadoValidacion getResultado() { return resultado; }
}


abstract class Reembolso {
    private String folio;
    private String empleado;
    private double monto;
    private LocalDate fechaGasto;
    private boolean tieneComprobante;
    private boolean autorizadoGerente;

    public Reembolso(String folio, String empleado, double monto) {
        this.folio = folio;
        this.empleado = empleado;
        this.monto = monto;
    }

    public Reembolso(String folio, String empleado, double monto, LocalDate fechaGasto, 
                     boolean tieneComprobante, boolean autorizadoGerente) {
        this(folio, empleado, monto);
        this.fechaGasto = fechaGasto;
        this.tieneComprobante = tieneComprobante;
        this.autorizadoGerente = autorizadoGerente;
    }

    public String getFolio() { return folio; }
    public String getEmpleado() { return empleado; }
    public double getMonto() { return monto; }
    public LocalDate getFechaGasto() { return fechaGasto; }
    public boolean tieneComprobante() { return tieneComprobante; }
    public boolean estaAutorizadoGerente() { return autorizadoGerente; }

    public abstract double getLimitePermitido();
    public abstract TipoReembolso getTipo();
}

class ReembolsoAlimentos extends Reembolso {
    public ReembolsoAlimentos(String folio, String empleado, double monto, LocalDate fechaGasto, 
                              boolean tieneComprobante, boolean autorizadoGerente) {
        super(folio, empleado, monto, fechaGasto, tieneComprobante, autorizadoGerente);
    }
    @Override
    public double getLimitePermitido() { return 1000.0; }
    @Override
    public TipoReembolso getTipo() { return TipoReembolso.ALIMENTOS; }
}

class ReembolsoTransporte extends Reembolso {
    public ReembolsoTransporte(String folio, String empleado, double monto, LocalDate fechaGasto, 
                               boolean tieneComprobante, boolean autorizadoGerente) {
        super(folio, empleado, monto, fechaGasto, tieneComprobante, autorizadoGerente);
    }
    @Override
    public double getLimitePermitido() { return 1500.0; }
    @Override
    public TipoReembolso getTipo() { return TipoReembolso.TRANSPORTE; }
}

class ReembolsoHospedaje extends Reembolso {
    public ReembolsoHospedaje(String folio, String empleado, double monto, LocalDate fechaGasto, 
                              boolean tieneComprobante, boolean autorizadoGerente) {
        super(folio, empleado, monto, fechaGasto, tieneComprobante, autorizadoGerente);
    }
    @Override
    public double getLimitePermitido() { return 4000.0; }
    @Override
    public TipoReembolso getTipo() { return TipoReembolso.HOSPEDAJE; }
}


abstract class RegistroReembolso {
    public abstract Reembolso crearReembolso(ReembolsoInputDTO dto);
}

class RegistroAlimentos extends RegistroReembolso {
    @Override
    public Reembolso crearReembolso(ReembolsoInputDTO dto) {
        return new ReembolsoAlimentos(dto.getFolio(), dto.getEmpleado(), dto.getMonto(), 
                                      dto.getFechaGasto(), dto.tieneComprobante(), dto.estaAutorizadoGerente());
    }
}

class RegistroTransporte extends RegistroReembolso {
    @Override
    public Reembolso crearReembolso(ReembolsoInputDTO dto) {
        return new ReembolsoTransporte(dto.getFolio(), dto.getEmpleado(), dto.getMonto(), 
                                       dto.getFechaGasto(), dto.tieneComprobante(), dto.estaAutorizadoGerente());
    }
}

class RegistroHospedaje extends RegistroReembolso {
    @Override
    public Reembolso crearReembolso(ReembolsoInputDTO dto) {
        return new ReembolsoHospedaje(dto.getFolio(), dto.getEmpleado(), dto.getMonto(), 
                                      dto.getFechaGasto(), dto.tieneComprobante(), dto.estaAutorizadoGerente());
    }
}


abstract class ValidadorReembolso {
    protected ValidadorReembolso siguiente;

    public void setSiguiente(ValidadorReembolso siguiente) {
        this.siguiente = siguiente;
    }

    public abstract ResultadoValidacion validar(Reembolso reembolso, LocalDate fechaEvaluacion);
}

class ValidadorComprobante extends ValidadorReembolso {
    @Override
    public ResultadoValidacion validar(Reembolso reembolso, LocalDate fechaEvaluacion) {
        if (!reembolso.tieneComprobante()) {
            return ResultadoValidacion.SIN_COMPROBANTE;
        }
        if (siguiente != null) {
            return siguiente.validar(reembolso, fechaEvaluacion);
        }
        return ResultadoValidacion.APROBADO;
    }
}

class ValidadorPeriodo extends ValidadorReembolso {
    @Override
    public ResultadoValidacion validar(Reembolso reembolso, LocalDate fechaEvaluacion) {
        long dias = ChronoUnit.DAYS.between(reembolso.getFechaGasto(), fechaEvaluacion);
        if (dias > 30) {
            return ResultadoValidacion.FUERA_DE_PERIODO;
        }
        if (siguiente != null) {
            return siguiente.validar(reembolso, fechaEvaluacion);
        }
        return ResultadoValidacion.APROBADO;
    }
}

class ValidadorLimite extends ValidadorReembolso {
    @Override
    public ResultadoValidacion validar(Reembolso reembolso, LocalDate fechaEvaluacion) {
        if (reembolso.getMonto() > reembolso.getLimitePermitido()) {
            return ResultadoValidacion.EXCEDE_LIMITE;
        }
        if (siguiente != null) {
            return siguiente.validar(reembolso, fechaEvaluacion);
        }
        return ResultadoValidacion.APROBADO;
    }
}

class ValidadorGerencia extends ValidadorReembolso {
    @Override
    public ResultadoValidacion validar(Reembolso reembolso, LocalDate fechaEvaluacion) {
        if (reembolso.getMonto() > 2000 && !reembolso.estaAutorizadoGerente()) {
            return ResultadoValidacion.REQUIERE_AUTORIZACION;
        }
        if (siguiente != null) {
            return siguiente.validar(reembolso, fechaEvaluacion);
        }
        return ResultadoValidacion.APROBADO;
    }
}


class Result {
    public static ReembolsoOutputDTO procesarReembolso(ReembolsoInputDTO input) {
        RegistroReembolso factory = null;
        
        switch (input.getTipo()) {
            case ALIMENTOS:
                factory = new RegistroAlimentos();
                break;
            case TRANSPORTE:
                factory = new RegistroTransporte();
                break;
            case HOSPEDAJE:
                factory = new RegistroHospedaje();
                break;
        }
        
        Reembolso reembolso = factory.crearReembolso(input);

        ValidadorReembolso valComprobante = new ValidadorComprobante();
        ValidadorReembolso valPeriodo = new ValidadorPeriodo();
        ValidadorReembolso valLimite = new ValidadorLimite();
        ValidadorReembolso valGerencia = new ValidadorGerencia();

        valComprobante.setSiguiente(valPeriodo);
        valPeriodo.setSiguiente(valLimite);
        valLimite.setSiguiente(valGerencia);

        ResultadoValidacion resultado = valComprobante.validar(reembolso, input.getFechaEvaluacion());

        return new ReembolsoOutputDTO(reembolso.getFolio(), reembolso.getTipo(), resultado);
    }
}

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String lineaFecha = br.readLine();
        LocalDate fechaEvaluacion = LocalDate.parse(lineaFecha.trim());

        String lineaSolicitud = br.readLine();
        String[] datos = lineaSolicitud.split("\\|");

        TipoReembolso tipo = TipoReembolso.valueOf(datos[0].trim());
        String folio = datos[1].trim();
        String empleado = datos[2].trim();
        double monto = Double.parseDouble(datos[3].trim());
        LocalDate fechaGasto = LocalDate.parse(datos[4].trim());
        boolean tieneComprobante = Boolean.parseBoolean(datos[5].trim());
        boolean autorizadoGerente = Boolean.parseBoolean(datos[6].trim());

        ReembolsoInputDTO input = new ReembolsoInputDTO(
            tipo, folio, empleado, monto, fechaGasto, 
            tieneComprobante, autorizadoGerente, fechaEvaluacion
        );

        ReembolsoOutputDTO output = Result.procesarReembolso(input);

        System.out.println("REEMBOLSO " + output.getFolio());
        System.out.println("TIPO " + output.getTipo());
        System.out.println("RESULTADO " + output.getResultado());
    }
}
