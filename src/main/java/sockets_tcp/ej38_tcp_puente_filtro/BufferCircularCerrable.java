package sockets_tcp.ej38_tcp_puente_filtro;

class BufferCircularCerrable {
    private final int[] datos;
    private int posicionLeer = 0;
    private int posicionEscribir = 0;
    private int cantidad = 0;
    private boolean cerrado = false;

    public BufferCircularCerrable(int capacidad) {
        datos = new int[capacidad];
    }

    public synchronized void meter(int valor) throws InterruptedException {
        while (cantidad == datos.length && !cerrado) {
            wait();
        }

        if (cerrado) {
            throw new IllegalStateException("No se puede meter porque el buffer esta cerrado.");
        }

        datos[posicionEscribir] = valor;
        posicionEscribir = (posicionEscribir + 1) % datos.length;
        cantidad++;
        notifyAll();
    }

    public synchronized Integer sacar() throws InterruptedException {
        while (cantidad == 0 && !cerrado) {
            wait();
        }

        if (cantidad == 0 && cerrado) {
            return null;
        }

        int valor = datos[posicionLeer];
        posicionLeer = (posicionLeer + 1) % datos.length;
        cantidad--;
        notifyAll();
        return valor;
    }

    public synchronized void cerrar() {
        cerrado = true;
        notifyAll();
    }
}
