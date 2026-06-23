package mixtos.ej33_servidor_procesos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class HiloProcesos implements Runnable {
    private final Socket socket;

    // Constructor: guarda los datos necesarios.
    public HiloProcesos(Socket socket) {
        this.socket = socket;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        try (socket;
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            salida.println("Comandos: FECHA | JAVA | GREP texto fichero | SALIR");
            String linea;
            while ((linea = entrada.readLine()) != null) {
                if (linea.equalsIgnoreCase("SALIR")) {
                    salida.println("ADIOS");
                    salida.println("@fin@");
                    break;
                }

                List<String> comando = crearComando(linea);
                if (comando == null) {
                    salida.println("ERROR comando no permitido");
                    salida.println("@fin@");
                } else {
                    ejecutarProceso(comando, salida);
                    salida.println("@fin@");
                }
            }
        } catch (IOException e) {
            System.err.println("Cliente desconectado");
        }
    }

    // Crea el comando del sistema según la petición.
    private List<String> crearComando(String linea) {
        String[] partes = linea.trim().split("\\s+");
        if (partes.length == 0) return null;

        List<String> comando = new ArrayList<>();
        if (partes[0].equalsIgnoreCase("FECHA")) {
            comando.add("date");
        } else if (partes[0].equalsIgnoreCase("JAVA")) {
            comando.add("java");
            comando.add("-version");
        } else if (partes[0].equalsIgnoreCase("GREP") && partes.length == 3) {
            comando.add("grep");
            comando.add(partes[1]);
            comando.add(partes[2]);
        } else {
            return null;
        }
        return comando;
    }

    // Ejecuta el proceso y muestra su resultado.
    private void ejecutarProceso(List<String> comando, PrintWriter salida) {
        ProcessBuilder pb = new ProcessBuilder(comando);
        pb.redirectErrorStream(true);

        try {
            Process proceso = pb.start();
            try (BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))) {
                String linea;
                while ((linea = lector.readLine()) != null) {
                    salida.println(linea);
                }
            }
            salida.println("codigo=" + proceso.waitFor());
        } catch (IOException | InterruptedException e) {
            salida.println("ERROR ejecutando proceso");
        }
    }
}
