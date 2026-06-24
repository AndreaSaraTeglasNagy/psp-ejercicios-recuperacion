package sockets_tcp.ej40_tcp_pipeline_transformador;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorConsumidorPipeline {

    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Servidor consumidor para pipeline.");
        int puerto = pedirEntero(teclado, "Puerto", 6401);

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor consumidor pipeline escuchando en " + puerto);
            while (true) {
                Socket socket = servidor.accept();
                new Thread(() -> atender(socket)).start();
            }
        }
    }

    private static void atender(Socket socket) {
        int suma = 0;
        int cantidad = 0;

        try (socket;
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            String linea;
            while ((linea = entrada.readLine()) != null) {
                if (linea.startsWith("@consumir_valor#") && linea.endsWith("@")) {
                    String numero = linea.replace("@consumir_valor#", "").replace("@", "");
                    int valor = Integer.parseInt(numero);
                    suma += valor;
                    cantidad++;
                    salida.println("@valor_consumido#" + valor + "#suma#" + suma + "#cantidad#" + cantidad + "@");
                } else if (linea.equals("@salir#@")) {
                    salida.println("@adios#suma_final#" + suma + "#cantidad#" + cantidad + "@");
                    break;
                } else {
                    salida.println("@error#comando_no_valido@");
                }
            }
        } catch (Exception e) {
            System.err.println("Cliente consumidor pipeline desconectado.");
        }
    }

    private static int pedirEntero(Scanner teclado, String mensaje, int defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine();
        return texto.isBlank() ? defecto : Integer.parseInt(texto);
    }
}
