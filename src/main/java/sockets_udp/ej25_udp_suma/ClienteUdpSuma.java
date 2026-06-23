package sockets_udp.ej25_udp_suma;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClienteUdpSuma {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Cliente UDP suma.");
        String hostTexto = pedirTexto(teclado, "Host del servidor", "localhost");
        int puerto = pedirEntero(teclado, "Puerto del servidor", 5002);
        int a = pedirEntero(teclado, "Primer número", 5);
        int b = pedirEntero(teclado, "Segundo número", 7);

        pedirSuma(hostTexto, puerto, a, b);
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

    // Envía la petición de suma y muestra la respuesta.
    private static void pedirSuma(String hostTexto, int puerto, int a, int b) throws Exception {
        InetAddress host = InetAddress.getByName(hostTexto);
        String mensaje = "@sumar#" + a + "#" + b + "@";

        try (DatagramSocket socket = new DatagramSocket()) {
            byte[] datos = mensaje.getBytes(StandardCharsets.UTF_8);
            socket.send(new DatagramPacket(datos, datos.length, host, puerto));

            byte[] buffer = new byte[1400];
            DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length);
            socket.receive(respuesta);

            System.out.println(new String(respuesta.getData(), 0, respuesta.getLength(), StandardCharsets.UTF_8));
        }
    }
}
