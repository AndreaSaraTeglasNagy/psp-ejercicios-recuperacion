package hilos.ej11_saludador_runnable;

class Saludador implements Runnable {
    private final int id;

    // Constructor: guarda los datos necesarios.
    public Saludador(int id) {
        this.id = id;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        try {
            Thread.sleep(id * 500L);
            System.out.println("Saludador " + id + ": hola mundo");
        } catch (InterruptedException e) {
            System.out.println("Saludador " + id + " interrumpido");
        }
    }
}
