package hilos.ej17_buffer_circular;

import java.util.Random;

class BufferNumeros {
    private final int[] datos;
    private int posicionLeer = 0;
    private int posicionEscribir = 0;
    private int cantidad = 0;

    // Constructor: guarda los datos necesarios.
    public BufferNumeros(int capacidad) {
        datos = new int[capacidad];
    }

    // Añade un dato compartido y avisa a otros hilos.
    public synchronized void meter(int valor) throws InterruptedException {
        while (cantidad == datos.length) {
            wait();
        }
        datos[posicionEscribir] = valor;
        posicionEscribir = (posicionEscribir + 1) % datos.length;
        cantidad++;
        System.out.println("Metido " + valor + " | cantidad=" + cantidad);
        notifyAll();
    }

    // Obtiene un dato compartido cuando está disponible.
    public synchronized int sacar() throws InterruptedException {
        while (cantidad == 0) {
            wait();
        }
        int valor = datos[posicionLeer];
        posicionLeer = (posicionLeer + 1) % datos.length;
        cantidad--;
        System.out.println("Sacado " + valor + " | cantidad=" + cantidad);
        notifyAll();
        return valor;
    }
}
