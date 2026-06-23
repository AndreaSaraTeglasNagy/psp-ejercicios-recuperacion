package hilos.ej20_biblioteca_libros;

public class BibliotecaLibros {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws InterruptedException {
        Biblioteca biblioteca = new Biblioteca(2);
        Thread[] estudiantes = new Thread[6];

        for (int i = 0; i < estudiantes.length; i++) {
            estudiantes[i] = new Thread(new Estudiante("Estudiante " + (i + 1), biblioteca));
            estudiantes[i].start();
        }

        for (Thread estudiante : estudiantes) estudiante.join();
    }
}
