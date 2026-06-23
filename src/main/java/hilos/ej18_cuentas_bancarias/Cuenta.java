package hilos.ej18_cuentas_bancarias;

class Cuenta {
    private final int id;
    private int saldo;

    // Constructor: guarda los datos necesarios.
    public Cuenta(int id, int saldo) {
        this.id = id;
        this.saldo = saldo;
    }

    // Devuelve el valor solicitado.
    public int getId() {
        return id;
    }

    // Devuelve el valor solicitado.
    public synchronized int getSaldo() {
        return saldo;
    }

    // Transfiere saldo entre cuentas de forma segura.
    public void transferirA(Cuenta destino, int cantidad) {
        Cuenta primera = this.id < destino.id ? this : destino;
        Cuenta segunda = this.id < destino.id ? destino : this;

        synchronized (primera) {
            synchronized (segunda) {
                if (saldo >= cantidad) {
                    saldo -= cantidad;
                    destino.saldo += cantidad;
                    System.out.printf("Cuenta %d pasa %d a cuenta %d%n", id, cantidad, destino.id);
                }
            }
        }
    }
}
