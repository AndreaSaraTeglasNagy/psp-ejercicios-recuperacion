package sockets_tcp.ej38_tcp_puente_filtro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class HiloProductorFiltro extends Thread {
    private final String ip;
    private final int puerto;
    private final int totalGenerar;
    private final int valorProhibido;
    private final BufferCircularCerrable buffer;

    public HiloProductorFiltro(String ip, int puerto, int totalGenerar, int valorProhibido, BufferCircularCerrable buffer) {
        this.ip = ip;
        this.puerto = puerto;
        this.totalGenerar = totalGenerar;
        this.valorProhibido = valorProhibido;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(ip, puerto);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            for (int i = 0; i < totalGenerar; i++) {
                salida.println("@generar_valor#@");
                String respuesta = entrada.readLine();
                int valor = extraerValor(respuesta);

                if (valor == valorProhibido) {
                    System.out.println("Productor: descartado valor prohibido " + valor);
                } else {
                    buffer.meter(valor);
                    System.out.println("Productor: guardado " + valor);
                }
            }

            buffer.cerrar();
            salida.println("@salir#@");
            System.out.println("Productor: " + entrada.readLine());

        } catch (Exception e) {
            System.out.println("Error en productor filtro: " + e.getMessage());
            buffer.cerrar();
        }
    }

    private int extraerValor(String respuesta) {
        if (respuesta == null || !respuesta.startsWith("@valor_generado#") || !respuesta.endsWith("@")) {
            throw new IllegalArgumentException("Respuesta no valida: " + respuesta);
        }
        return Integer.parseInt(respuesta.replace("@valor_generado#", "").replace("@", ""));
    }
}
