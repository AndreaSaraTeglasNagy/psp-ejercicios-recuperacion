package mixtos.ej34_servidor_mayusculas_proceso;

import java.io.*;
import java.net.Socket;

class HiloMayusculasCliente implements Runnable {
    private final Socket socket;

    // Constructor: guarda los datos necesarios.
    public HiloMayusculasCliente(Socket socket) {
        this.socket = socket;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        ProcessBuilder pb = new ProcessBuilder("java", "-cp", System.getProperty("java.class.path"), "mixtos.ej34_servidor_mayusculas_proceso.HijoMayusculas");

        try (socket;
             BufferedReader clienteIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter clienteOut = new PrintWriter(socket.getOutputStream(), true)) {

            Process hijo = pb.start();

            try (BufferedWriter hijoIn = new BufferedWriter(new OutputStreamWriter(hijo.getOutputStream()));
                 BufferedReader hijoOut = new BufferedReader(new InputStreamReader(hijo.getInputStream()))) {

                clienteOut.println("Escribe texto. SALIR para cerrar.");
                String linea;
                while ((linea = clienteIn.readLine()) != null) {
                    if (linea.equalsIgnoreCase("SALIR")) {
                        clienteOut.println("ADIOS");
                        break;
                    }

                    hijoIn.write(linea);
                    hijoIn.newLine();
                    hijoIn.flush();

                    clienteOut.println(hijoOut.readLine());
                }
            }

            hijo.destroy();
        } catch (IOException e) {
            System.err.println("Error con cliente mayúsculas");
        }
    }
}
