package sockets_udp.ej23_udp_hola;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServidorUdpHola {
    private static final int MAX_BYTES = 1400;
    private static final Pattern PATRON = Pattern.compile("@hola#(.+)@");

    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Servidor UDP hola.");
        int puerto = pedirEntero(teclado, "Puerto para escuchar", 5000);
        String nombreServidor = pedirTexto(teclado, "Nombre del servidor", "Servidor");

        iniciarServidor(puerto, nombreServidor);
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

    // Inicia el servidor UDP y responde a cada cliente.
    private static void iniciarServidor(int puerto, String nombreServidor) throws Exception {
        try (DatagramSocket socket = new DatagramSocket(puerto)) {
            System.out.println("Servidor UDP escuchando en puerto " + puerto);

            while (true) {
                byte[] buffer = new byte[MAX_BYTES];
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                socket.receive(paquete);

                String mensaje = new String(paquete.getData(), 0, paquete.getLength(), StandardCharsets.UTF_8);
                Matcher matcher = PATRON.matcher(mensaje);

                if (matcher.matches()) {
                    System.out.println("Saludo de " + matcher.group(1));
                } else {
                    System.out.println("Mensaje incorrecto: " + mensaje);
                }

                String respuesta = "@hola#" + nombreServidor + "@";
                byte[] datos = respuesta.getBytes(StandardCharsets.UTF_8);
                DatagramPacket paqueteRespuesta = new DatagramPacket(datos, datos.length, paquete.getAddress(), paquete.getPort());
                socket.send(paqueteRespuesta);
            }
        }
    }
}
