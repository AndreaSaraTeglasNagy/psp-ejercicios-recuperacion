package sockets_tcp.ej31_tcp_cola_numeros;

import java.util.LinkedList;
import java.util.Queue;

class ColaCompartida {
    private final Queue<Integer> cola = new LinkedList<>();
    private final int maximo;

    // Constructor: guarda los datos necesarios.
    public ColaCompartida(int maximo) {
        this.maximo = maximo;
    }

    // Añade un dato compartido y avisa a otros hilos.
    public synchronized void meter(int numero) throws InterruptedException {
        while (cola.size() == maximo) {
            wait();
        }
        cola.add(numero);
        notifyAll();
    }

    // Obtiene un dato compartido cuando está disponible.
    public synchronized int sacar() throws InterruptedException {
        while (cola.isEmpty()) {
            wait();
        }
        int numero = cola.remove();
        notifyAll();
        return numero;
    }

    // Devuelve el tamaño actual de la cola.
    public synchronized int tamanyo() {
        return cola.size();
    }
}
