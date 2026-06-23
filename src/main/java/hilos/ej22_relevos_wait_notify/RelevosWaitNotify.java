package hilos.ej22_relevos_wait_notify;

public class RelevosWaitNotify {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) {
        ControlTurnos control = new ControlTurnos();
        new Thread(new HiloLetra(control, 0, "A")).start();
        new Thread(new HiloLetra(control, 1, "B")).start();
        new Thread(new HiloLetra(control, 2, "C")).start();
    }
}
