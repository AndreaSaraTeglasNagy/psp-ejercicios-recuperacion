package hilos.ej13_contador_sin_sincronizar;

class Incrementador implements Runnable {
    private final ContadorCompartido contador;
    private final int veces;

    // Constructor: guarda los datos necesarios.
    public Incrementador(ContadorCompartido contador, int veces) {
        this.contador = contador;
        this.veces = veces;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        for (int i = 0; i < veces; i++) {
            contador.valor++; // no es seguro
        }
    }
}
