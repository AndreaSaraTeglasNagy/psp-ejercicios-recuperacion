package sockets_tcp.ej40_tcp_pipeline_transformador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class HiloProductorPipeline extends Thread {
    private final String ip;
    private final int puerto;
    private final int totalValores;
    private final BufferCircularCerrable bufferSalida;

    public HiloProductorPipeline(String ip, int puerto, int totalValores, BufferCircularCerrable bufferSalida) {
        this.ip = ip;
        this.puerto = puerto;
        this.totalValores = totalValores;
        this.bufferSalida = bufferSalida;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(ip, puerto);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            for (int i = 0; i < totalValores; i++) {
                salida.println("@generar_valor#@");
                String respuesta = entrada.readLine();
                int valor = extraerValor(respuesta);
                bufferSalida.meter(valor);
                System.out.println("Productor: generado " + valor);
            }

            bufferSalida.cerrar();
            salida.println("@salir#@");
            System.out.println("Productor: " + entrada.readLine());

        } catch (Exception e) {
            System.out.println("Error en productor pipeline: " + e.getMessage());
            bufferSalida.cerrar();
        }
    }

    private int extraerValor(String respuesta) {
        if (respuesta == null || !respuesta.startsWith("@valor_generado#") || !respuesta.endsWith("@")) {
            throw new IllegalArgumentException("Respuesta no valida: " + respuesta);
        }
        return Integer.parseInt(respuesta.replace("@valor_generado#", "").replace("@", ""));
    }
}
