package sockets_tcp.ej40_tcp_pipeline_transformador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class HiloConsumidorPipeline extends Thread {
    private final String ip;
    private final int puerto;
    private final BufferCircularCerrable bufferEntrada;

    public HiloConsumidorPipeline(String ip, int puerto, BufferCircularCerrable bufferEntrada) {
        this.ip = ip;
        this.puerto = puerto;
        this.bufferEntrada = bufferEntrada;
    }

    @Override
    public void run() {
        int enviados = 0;

        try (Socket socket = new Socket(ip, puerto);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            while (true) {
                Integer valor = bufferEntrada.sacar();
                if (valor == null) {
                    break;
                }

                String comando = "@consumir_valor#" + valor + "@";
                salida.println(comando);
                String respuesta = entrada.readLine();
                System.out.println("Consumidor: enviado " + valor + " | respuesta " + respuesta);
                enviados++;
            }

            salida.println("@salir#@");
            System.out.println("Consumidor: " + entrada.readLine());
            System.out.println("Consumidor: total enviados " + enviados);

        } catch (Exception e) {
            System.out.println("Error en consumidor pipeline: " + e.getMessage());
        }
    }
}
