package hilos.ej22_relevos_wait_notify;

class HiloLetra implements Runnable {
    private final ControlTurnos control;
    private final int turno;
    private final String letra;

    // Constructor: guarda los datos necesarios.
    public HiloLetra(ControlTurnos control, int turno, String letra) {
        this.control = control;
        this.turno = turno;
        this.letra = letra;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        try {
            control.imprimir(turno, letra);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
