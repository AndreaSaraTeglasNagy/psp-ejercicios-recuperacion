package sockets_tcp.ej40_tcp_pipeline_transformador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class ServidorGeneradorPipeline {

    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Servidor generador para pipeline.");
        int puerto = pedirEntero(teclado, "Puerto", 6400);

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor generador pipeline escuchando en " + puerto);
            while (true) {
                Socket socket = servidor.accept();
                new Thread(() -> atender(socket)).start();
            }
        }
    }

    private static void atender(Socket socket) {
        Random random = new Random();

        try (socket;
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            String linea;
            while ((linea = entrada.readLine()) != null) {
                if (linea.equals("@generar_valor#@")) {
                    int valor = 1 + random.nextInt(50);
                    salida.println("@valor_generado#" + valor + "@");
                } else if (linea.equals("@salir#@")) {
                    salida.println("@adios#@");
                    break;
                } else {
                    salida.println("@error#comando_no_valido@");
                }
            }
        } catch (Exception e) {
            System.err.println("Cliente generador pipeline desconectado.");
        }
    }

    private static int pedirEntero(Scanner teclado, String mensaje, int defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine();
        return texto.isBlank() ? defecto : Integer.parseInt(texto);
    }
}
