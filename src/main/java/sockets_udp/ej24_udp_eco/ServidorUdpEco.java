package sockets_udp.ej24_udp_eco;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ServidorUdpEco {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Servidor UDP eco.");
        int puerto = pedirEntero(teclado, "Puerto para escuchar", 5001);
        iniciarServidor(puerto);
    }

    // Pide un número entero permitiendo un valor por defecto.
    private static int pedirEntero(Scanner teclado, String mensaje, int defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine();
        return texto.isBlank() ? defecto : Integer.parseInt(texto);
    }

    // Inicia el servidor UDP de eco.
    private static void iniciarServidor(int puerto) throws Exception {
        try (DatagramSocket socket = new DatagramSocket(puerto)) {
            System.out.println("Servidor eco UDP en puerto " + puerto);

            while (true) {
                byte[] buffer = new byte[1400];
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                socket.receive(paquete);

                String mensaje = new String(paquete.getData(), 0, paquete.getLength(), StandardCharsets.UTF_8);
                System.out.println("Recibido: " + mensaje);

                DatagramPacket respuesta = new DatagramPacket(paquete.getData(), paquete.getLength(), paquete.getAddress(), paquete.getPort());
                socket.send(respuesta);
            }
        }
    }
}
