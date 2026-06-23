package hilos.ej14_contador_sincronizado;

class HiloIncrementador implements Runnable {
    private final ContadorSeguro contador;
    private final int veces;

    // Constructor: guarda los datos necesarios.
    public HiloIncrementador(ContadorSeguro contador, int veces) {
        this.contador = contador;
        this.veces = veces;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        for (int i = 0; i < veces; i++) {
            contador.incrementar();
        }
    }
}
