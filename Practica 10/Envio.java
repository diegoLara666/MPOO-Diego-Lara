public abstract class Envio implements ServicioEnvio {
    protected String destinatario;
    protected double peso;
    protected double distancia;

    public Envio(String destinatario, double peso, double distancia) {
        this.destinatario = destinatario;
        this.peso = peso;
        this.distancia = distancia;
    }
}
