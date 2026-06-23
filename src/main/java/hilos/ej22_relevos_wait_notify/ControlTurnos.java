package hilos.ej22_relevos_wait_notify;

class ControlTurnos {
    private int turno = 0;

    // Imprime el texto cuando llega su turno.
    public synchronized void imprimir(int miTurno, String texto) throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            while (turno != miTurno) {
                wait();
            }
            System.out.println(texto);
            turno = (turno + 1) % 3;
            notifyAll();
        }
    }
}
