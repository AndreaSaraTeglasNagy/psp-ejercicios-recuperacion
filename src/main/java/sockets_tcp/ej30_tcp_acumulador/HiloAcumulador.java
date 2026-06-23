package sockets_tcp.ej30_tcp_acumulador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

class HiloAcumulador implements Runnable {
    private final Socket socket;
    private final AtomicInteger acumulado;

    // Constructor: guarda los datos necesarios.
    public HiloAcumulador(Socket socket, AtomicInteger acumulado) {
        this.socket = socket;
        this.acumulado = acumulado;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        try (socket;
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            salida.println("Comandos: ADD n | GET | RESET | SALIR");
            String linea;
            while ((linea = entrada.readLine()) != null) {
                String[] partes = linea.trim().split("\\s+");
                String comando = partes[0].toUpperCase();

                if (comando.equals("ADD") && partes.length == 2) {
                    int valor = Integer.parseInt(partes[1]);
                    salida.println("ACUMULADO " + acumulado.addAndGet(valor));
                } else if (comando.equals("GET")) {
                    salida.println("ACUMULADO " + acumulado.get());
                } else if (comando.equals("RESET")) {
                    acumulado.set(0);
                    salida.println("ACUMULADO 0");
                } else if (comando.equals("SALIR")) {
                    salida.println("ADIOS");
                    break;
                } else {
                    salida.println("ERROR");
                }
            }
        } catch (Exception e) {
            System.err.println("Cliente acumulador desconectado");
        }
    }
}
