package sockets_tcp.ej31_tcp_cola_numeros;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class HiloCola implements Runnable {
    private final Socket socket;
    private final ColaCompartida cola;

    // Constructor: guarda los datos necesarios.
    public HiloCola(Socket socket, ColaCompartida cola) {
        this.socket = socket;
        this.cola = cola;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        try (socket;
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            salida.println("Comandos: METER n | SACAR | TAM | SALIR");
            String linea;
            while ((linea = entrada.readLine()) != null) {
                String[] partes = linea.trim().split("\\s+");
                String comando = partes[0].toUpperCase();

                if (comando.equals("METER") && partes.length == 2) {
                    cola.meter(Integer.parseInt(partes[1]));
                    salida.println("OK");
                } else if (comando.equals("SACAR")) {
                    salida.println("NUMERO " + cola.sacar());
                } else if (comando.equals("TAM")) {
                    salida.println("TAM " + cola.tamanyo());
                } else if (comando.equals("SALIR")) {
                    salida.println("ADIOS");
                    break;
                } else {
                    salida.println("ERROR");
                }
            }
        } catch (Exception e) {
            System.err.println("Cliente cola desconectado");
        }
    }
}
