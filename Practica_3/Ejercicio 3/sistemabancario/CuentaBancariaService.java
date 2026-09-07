
package sistemabancario;

public class CuentaBancariaService {
    
    private CuentaBancaria[] cuentas;

    public CuentaBancariaService() {
        cuentas = new CuentaBancaria[]{
            new CuentaBancaria("111", "Diego", 5000.0, true),
            new CuentaBancaria("222", "Luis", 3000.0, true),
            new CuentaBancaria("333", "Ana", 1000.0, false)
        };
    }

    private CuentaBancaria buscarCuenta(String numeroCuenta) {
        for (CuentaBancaria cuenta : cuentas) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                return cuenta;
            }
        }
        return null; 
    }

    public boolean transferir(String cuentaOrigen, String cuentaDestino, double cantidad) {
        if (cantidad <= 0) return false; 
        if (cuentaOrigen.equals(cuentaDestino)) return false; 

        CuentaBancaria origen = buscarCuenta(cuentaOrigen);
        CuentaBancaria destino = buscarCuenta(cuentaDestino);

        if (origen == null || destino == null) return false; 
        if (origen.getSaldo() < cantidad) return false; 

        origen.restarSaldo(cantidad);
        destino.sumarSaldo(cantidad);
        return true;
    }

    public boolean depositar(String cuentaDestino, double cantidad) {
        if (cantidad <= 0) return false;
        
        CuentaBancaria destino = buscarCuenta(cuentaDestino);
        if (destino == null) return false;

        destino.sumarSaldo(cantidad);
        return true;
    }

    public boolean retirar(String cuentaOrigen, double cantidad) {
        if (cantidad <= 0) return false;
        
        CuentaBancaria origen = buscarCuenta(cuentaOrigen);
        if (origen == null) return false;
        if (origen.getSaldo() < cantidad) return false; 

        origen.restarSaldo(cantidad);
        return true;
    }
}