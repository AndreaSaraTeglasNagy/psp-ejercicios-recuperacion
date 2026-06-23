package sockets_tcp.ej27_tcp_eco_multihilo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class HiloClienteEco implements Runnable {
    private final Socket socket;

    // Constructor: guarda los datos necesarios.
    public HiloClienteEco(Socket socket) {
        this.socket = socket;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        try (socket;
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            salida.println("Conectado. Escribe salir para cerrar.");
            String linea;
            while ((linea = entrada.readLine()) != null) {
                if (linea.equalsIgnoreCase("salir")) {
                    salida.println("adios");
                    break;
                }
                salida.println("eco desde " + Thread.currentThread().getName() + ": " + linea);
            }
        } catch (IOException e) {
            System.err.println("Cliente desconectado con error");
        }
    }
}
