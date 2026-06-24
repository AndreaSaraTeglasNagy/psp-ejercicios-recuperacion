package sockets_tcp.ej37_tcp_buffer_eliminar_valor;

class BufferCircularBusqueda {
    private final int[] datos;
    private int posicionLeer = 0;
    private int posicionEscribir = 0;
    private int cantidad = 0;
    private int sumaTotal = 0;

    public BufferCircularBusqueda(int capacidad) {
        datos = new int[capacidad];
    }

    public synchronized void meter(int valor) throws InterruptedException {
        while (cantidad == datos.length) {
            wait();
        }

        datos[posicionEscribir] = valor;
        posicionEscribir = (posicionEscribir + 1) % datos.length;
        cantidad++;
        sumaTotal += valor;

        notifyAll();
    }

    public synchronized int sacarPrimero() throws InterruptedException {
        while (cantidad == 0) {
            wait();
        }

        int valor = datos[posicionLeer];
        posicionLeer = (posicionLeer + 1) % datos.length;
        cantidad--;
        sumaTotal -= valor;

        notifyAll();
        return valor;
    }

    public synchronized boolean eliminarValor(int valorBuscado) {
        int posicionLogica = -1;

        for (int i = 0; i < cantidad; i++) {
            int posicionFisica = (posicionLeer + i) % datos.length;
            if (datos[posicionFisica] == valorBuscado) {
                posicionLogica = i;
                break;
            }
        }

        if (posicionLogica == -1) {
            return false;
        }

        for (int i = posicionLogica; i < cantidad - 1; i++) {
            int actual = (posicionLeer + i) % datos.length;
            int siguiente = (posicionLeer + i + 1) % datos.length;
            datos[actual] = datos[siguiente];
        }

        posicionEscribir = (posicionEscribir - 1 + datos.length) % datos.length;
        cantidad--;
        sumaTotal -= valorBuscado;

        notifyAll();
        return true;
    }

    public synchronized int getCantidad() {
        return cantidad;
    }

    public synchronized int getSumaTotal() {
        return sumaTotal;
    }

    public synchronized String verContenido() {
        if (cantidad == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < cantidad; i++) {
            int posicionFisica = (posicionLeer + i) % datos.length;
            sb.append(datos[posicionFisica]);
            if (i < cantidad - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
