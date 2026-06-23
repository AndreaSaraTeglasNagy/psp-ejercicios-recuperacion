package hilos.ej16_productor_consumidor_simple;

import java.util.Random;

class ContenedorSimple {
    private int valor;
    private boolean lleno = false;

    // Añade un dato compartido y avisa a otros hilos.
    public synchronized void producir(int nuevoValor) throws InterruptedException {
        while (lleno) {
            wait();
        }
        valor = nuevoValor;
        lleno = true;
        System.out.println("Producido: " + valor);
        notifyAll();
    }

    // Obtiene un dato compartido cuando está disponible.
    public synchronized int consumir() throws InterruptedException {
        while (!lleno) {
            wait();
        }
        int resultado = valor;
        lleno = false;
        System.out.println("Consumido: " + resultado);
        notifyAll();
        return resultado;
    }
}
