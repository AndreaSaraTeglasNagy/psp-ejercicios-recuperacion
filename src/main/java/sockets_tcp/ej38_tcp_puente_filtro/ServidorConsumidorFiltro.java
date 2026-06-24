package sockets_tcp.ej38_tcp_puente_filtro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorConsumidorFiltro {

    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Servidor consumidor compatible con @consumir_valor#NNN@.");
        int puerto = pedirEntero(teclado, "Puerto", 6201);

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor consumidor escuchando en " + puerto);
            while (true) {
                Socket socket = servidor.accept();
                new Thread(() -> atender(socket)).start();
            }
        }
    }

    private static void atender(Socket socket) {
        try (socket;
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            String linea;
            while ((linea = entrada.readLine()) != null) {
                if (linea.startsWith("@consumir_valor#") && linea.endsWith("@")) {
                    String numero = linea.replace("@consumir_valor#", "").replace("@", "");
                    salida.println("@valor_consumido#" + numero + "@");
                } else if (linea.equals("@salir#@")) {
                    salida.println("@adios#@");
                    break;
                } else {
                    salida.println("@error#comando_no_valido@");
                }
            }
        } catch (Exception e) {
            System.err.println("Cliente del consumidor desconectado.");
        }
    }

    private static int pedirEntero(Scanner teclado, String mensaje, int defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine();
        return texto.isBlank() ? defecto : Integer.parseInt(texto);
    }
}
