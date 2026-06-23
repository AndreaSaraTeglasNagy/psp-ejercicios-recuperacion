package hilos.ej20_biblioteca_libros;

class Biblioteca {
    private int librosDisponibles;

    // Constructor: guarda los datos necesarios.
    public Biblioteca(int librosDisponibles) {
        this.librosDisponibles = librosDisponibles;
    }

    // Método auxiliar del ejercicio.
    public synchronized void cogerLibro(String estudiante) throws InterruptedException {
        while (librosDisponibles == 0) {
            System.out.println(estudiante + " espera un libro");
            wait();
        }
        librosDisponibles--;
        System.out.println(estudiante + " coge un libro. Quedan " + librosDisponibles);
    }

    // Devuelve un libro y libera el recurso.
    public synchronized void devolverLibro(String estudiante) {
        librosDisponibles++;
        System.out.println(estudiante + " devuelve un libro. Quedan " + librosDisponibles);
        notifyAll();
    }
}
