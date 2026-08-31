
package battlerobots;


    public class Bateria {
    private String tipo;
    private int nivelCarga;

    public Bateria(String tipo, int nivelCarga) {
        this.tipo = tipo; // Cumpliendo el uso de this[cite: 1]
        this.nivelCarga = nivelCarga;
    }

    public int getNivelCarga() { return nivelCarga; }
    public void setNivelCarga(int nivelCarga) { this.nivelCarga = nivelCarga; }
    public String getTipo() { return tipo; }
}

