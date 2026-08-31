
package battlerobots;

public class Arma {
    private String tipo; 
    private int poderDano;

    public Arma(String tipo, int poderDano) {
        this.tipo = tipo;
        this.poderDano = poderDano;
    }

   
    public String getTipo() { return tipo; }
    public int getPoderDano() { return poderDano; }
    
    
    public void setPoderDano(int poderDano) { this.poderDano = poderDano; }
}