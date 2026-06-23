package hilos.ej10_sludador_thread;

class HiloSaludador extends Thread {
    private final int id;

    // Constructor: guarda los datos necesarios.
    public HiloSaludador(int id) {
        this.id = id;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        try {
            Thread.sleep(id * 500L);
            System.out.println("Hilo " + id + ": hola mundo");
        } catch (InterruptedException e) {
            System.out.println("Hilo " + id + " interrumpido");
        }
    }
}
