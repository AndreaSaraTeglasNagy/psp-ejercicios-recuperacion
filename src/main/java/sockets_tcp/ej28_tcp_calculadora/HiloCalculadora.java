package sockets_tcp.ej28_tcp_calculadora;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class HiloCalculadora implements Runnable {
    private final Socket socket;

    // Constructor: guarda los datos necesarios.
    public HiloCalculadora(Socket socket) {
        this.socket = socket;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        try (socket;
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            salida.println("Comandos: SUMA a b | RESTA a b | MULT a b | DIV a b | SALIR");
            String linea;
            while ((linea = entrada.readLine()) != null) {
                if (linea.equalsIgnoreCase("SALIR")) {
                    salida.println("ADIOS");
                    break;
                }
                salida.println(resolver(linea));
            }
        } catch (IOException e) {
            System.err.println("Error con cliente");
        }
    }

    // Resuelve la operación recibida.
    private String resolver(String linea) {
        try {
            String[] partes = linea.trim().split("\\s+");
            String operacion = partes[0].toUpperCase();
            double a = Double.parseDouble(partes[1]);
            double b = Double.parseDouble(partes[2]);

            return switch (operacion) {
                case "SUMA" -> "RESULTADO " + (a + b);
                case "RESTA" -> "RESULTADO " + (a - b);
                case "MULT" -> "RESULTADO " + (a * b);
                case "DIV" -> b == 0 ? "ERROR division por cero" : "RESULTADO " + (a / b);
                default -> "ERROR operacion desconocida";
            };
        } catch (Exception e) {
            return "ERROR formato incorrecto";
        }
    }
}
