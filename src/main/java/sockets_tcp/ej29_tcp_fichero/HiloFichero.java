package sockets_tcp.ej29_tcp_fichero;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

class HiloFichero implements Runnable {
    private final Socket socket;

    // Constructor: guarda los datos necesarios.
    public HiloFichero(Socket socket) {
        this.socket = socket;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        try (socket;
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            String nombreFichero = entrada.readLine();
            Path ruta = Path.of(nombreFichero);

            if (!Files.exists(ruta)) {
                salida.println("@error#no_existe@");
            } else {
                for (String linea : Files.readAllLines(ruta)) {
                    salida.println(linea);
                }
                salida.println("@fin@");
            }
        } catch (IOException e) {
            System.err.println("Error enviando fichero");
        }
    }
}
