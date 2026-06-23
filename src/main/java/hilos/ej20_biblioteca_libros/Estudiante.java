package hilos.ej20_biblioteca_libros;

import java.util.Random;

class Estudiante implements Runnable {
    private final String nombre;
    private final Biblioteca biblioteca;
    private final Random random = new Random();

    // Constructor: guarda los datos necesarios.
    public Estudiante(String nombre, Biblioteca biblioteca) {
        this.nombre = nombre;
        this.biblioteca = biblioteca;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        try {
            biblioteca.cogerLibro(nombre);
            Thread.sleep(random.nextInt(500, 1500));
            biblioteca.devolverLibro(nombre);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
