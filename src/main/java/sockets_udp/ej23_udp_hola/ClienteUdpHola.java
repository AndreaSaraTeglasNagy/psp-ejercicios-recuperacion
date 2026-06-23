package sockets_udp.ej23_udp_hola;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClienteUdpHola {
    private static final int MAX_BYTES = 1400;

    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Cliente UDP hola.");
        String hostTexto = pedirTexto(teclado, "Host del servidor", "localhost");
        int puerto = pedirEntero(teclado, "Puerto del servidor", 5000);
        String nombre = pedirTexto(teclado, "Tu nombre", "Alumno");

        enviarSaludo(hostTexto, puerto, nombre);
    }

    // Pide un texto permitiendo un valor por defecto.
    private static String pedirTexto(Scanner teclado, String mensaje, String defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine();
        return texto.isBlank() ? defecto : texto;
    }

    // Pide un número entero permitiendo un valor por defecto.
    private static int pedirEntero(Scanner teclado, String mensaje, int defecto) {
        String texto = pedirTexto(teclado, mensaje, String.valueOf(defecto));
        return Integer.parseInt(texto);
    }

    // Envía un saludo UDP y espera respuesta.
    private static void enviarSaludo(String hostTexto, int puerto, String nombre) throws Exception {
        InetAddress host = InetAddress.getByName(hostTexto);

        try (DatagramSocket socket = new DatagramSocket()) {
            String saludo = "@hola#" + nombre + "@";
            byte[] datos = saludo.getBytes(StandardCharsets.UTF_8);
            DatagramPacket paquete = new DatagramPacket(datos, datos.length, host, puerto);
            socket.send(paquete);

            byte[] buffer = new byte[MAX_BYTES];
            DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length);
            socket.receive(respuesta);

            String texto = new String(respuesta.getData(), 0, respuesta.getLength(), StandardCharsets.UTF_8);
            System.out.println("Respuesta: " + texto);
        }
    }
}
