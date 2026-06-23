package sockets_udp.ej24_udp_eco;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClienteUdpEco {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Cliente UDP eco.");
        String hostTexto = pedirTexto(teclado, "Host del servidor", "localhost");
        int puerto = pedirEntero(teclado, "Puerto del servidor", 5001);
        String mensaje = pedirTexto(teclado, "Mensaje", "hola mundo");

        enviarMensaje(hostTexto, puerto, mensaje);
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

    // Envía un mensaje UDP y muestra el eco recibido.
    private static void enviarMensaje(String hostTexto, int puerto, String mensaje) throws Exception {
        InetAddress host = InetAddress.getByName(hostTexto);

        try (DatagramSocket socket = new DatagramSocket()) {
            byte[] datos = mensaje.getBytes(StandardCharsets.UTF_8);
            socket.send(new DatagramPacket(datos, datos.length, host, puerto));

            byte[] buffer = new byte[1400];
            DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length);
            socket.receive(respuesta);

            String texto = new String(respuesta.getData(), 0, respuesta.getLength(), StandardCharsets.UTF_8);
            System.out.println("Eco: " + texto);
        }
    }
}
