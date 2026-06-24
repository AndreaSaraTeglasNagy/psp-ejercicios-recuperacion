package sockets_tcp.ej38_tcp_puente_filtro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class HiloConsumidorFiltro extends Thread {
    private final String ip;
    private final int puerto;
    private final BufferCircularCerrable buffer;

    public HiloConsumidorFiltro(String ip, int puerto, BufferCircularCerrable buffer) {
        this.ip = ip;
        this.puerto = puerto;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        int procesados = 0;

        try (Socket socket = new Socket(ip, puerto);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            while (true) {
                Integer valor = buffer.sacar();
                if (valor == null) {
                    break;
                }

                String comando = "@consumir_valor#" + valor + "@";
                salida.println(comando);
                String respuesta = entrada.readLine();
                comprobarRespuesta(respuesta, valor);
                procesados++;
                System.out.println("Consumidor: consumido " + valor);
            }

            salida.println("@salir#@");
            System.out.println("Consumidor: " + entrada.readLine());
            System.out.println("Consumidor: total procesados " + procesados);

        } catch (Exception e) {
            System.out.println("Error en consumidor filtro: " + e.getMessage());
        }
    }

    private void comprobarRespuesta(String respuesta, int valor) {
        String esperada = "@valor_consumido#" + valor + "@";
        if (!esperada.equals(respuesta)) {
            throw new IllegalArgumentException("Respuesta no valida: " + respuesta);
        }
    }
}
