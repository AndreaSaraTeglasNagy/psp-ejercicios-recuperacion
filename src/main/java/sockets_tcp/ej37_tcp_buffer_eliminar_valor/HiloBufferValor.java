package sockets_tcp.ej37_tcp_buffer_eliminar_valor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class HiloBufferValor implements Runnable {
    private final Socket socket;
    private final BufferCircularBusqueda buffer;

    public HiloBufferValor(Socket socket, BufferCircularBusqueda buffer) {
        this.socket = socket;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try (socket;
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            salida.println("Comandos: METER n | SACAR | ELIMINAR n | SUMA | VER | TAM | SALIR");

            String linea;
            while ((linea = entrada.readLine()) != null) {
                String[] partes = linea.trim().split("\\s+");
                String comando = partes[0].toUpperCase();

                if (comando.equals("METER") && partes.length == 2) {
                    int valor = Integer.parseInt(partes[1]);
                    buffer.meter(valor);
                    salida.println("OK METIDO " + valor);

                } else if (comando.equals("SACAR")) {
                    int valor = buffer.sacarPrimero();
                    salida.println("OK SACADO_PRIMERO " + valor);

                } else if (comando.equals("ELIMINAR") && partes.length == 2) {
                    int valor = Integer.parseInt(partes[1]);
                    boolean eliminado = buffer.eliminarValor(valor);
                    if (eliminado) {
                        salida.println("OK ELIMINADO " + valor);
                    } else {
                        salida.println("NO_ENCONTRADO " + valor);
                    }

                } else if (comando.equals("SUMA")) {
                    salida.println("SUMA " + buffer.getSumaTotal());

                } else if (comando.equals("VER")) {
                    salida.println("BUFFER " + buffer.verContenido());

                } else if (comando.equals("TAM")) {
                    salida.println("TAM " + buffer.getCantidad());

                } else if (comando.equals("SALIR")) {
                    salida.println("ADIOS");
                    break;

                } else {
                    salida.println("ERROR Comando no valido");
                }
            }
        } catch (Exception e) {
            System.err.println("Cliente desconectado del servidor de buffer.");
        }
    }
}
