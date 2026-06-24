package sockets_tcp.ej39_tcp_reto_calculo_multicliente;

class EstadisticasCalculo {
    private int clientesConectados = 0;
    private int operacionesRealizadas = 0;

    public synchronized void nuevoCliente() {
        clientesConectados++;
    }

    public synchronized void nuevaOperacion() {
        operacionesRealizadas++;
    }

    public synchronized String resumen() {
        return "clientes=" + clientesConectados + "#operaciones=" + operacionesRealizadas;
    }
}
