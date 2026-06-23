package sockets_udp.ej25_udp_suma;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServidorUdpSuma {
    private static final Pattern PATRON = Pattern.compile("@sumar#(-?\\d+)#(-?\\d+)@");

    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Servidor UDP suma.");
        int puerto = pedirEntero(teclado, "Puerto para escuchar", 5002);
        iniciarServidor(puerto);
    }

    // Pide un número entero permitiendo un valor por defecto.
    private static int pedirEntero(Scanner teclado, String mensaje, int defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine();
        return texto.isBlank() ? defecto : Integer.parseInt(texto);
    }

    // Inicia el servidor UDP que suma dos números.
    private static void iniciarServidor(int puerto) throws Exception {
        try (DatagramSocket socket = new DatagramSocket(puerto)) {
            System.out.println("Servidor UDP suma iniciado en puerto " + puerto);

            while (true) {
                byte[] buffer = new byte[1400];
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                socket.receive(paquete);

                String mensaje = new String(paquete.getData(), 0, paquete.getLength(), StandardCharsets.UTF_8);
                Matcher matcher = PATRON.matcher(mensaje);

                String respuesta;
                if (matcher.matches()) {
                    int a = Integer.parseInt(matcher.group(1));
                    int b = Integer.parseInt(matcher.group(2));
                    respuesta = "@resultado#" + (a + b) + "@";
                } else {
                    respuesta = "@error#formato@";
                }

                byte[] datos = respuesta.getBytes(StandardCharsets.UTF_8);
                socket.send(new DatagramPacket(datos, datos.length, paquete.getAddress(), paquete.getPort()));
            }
        }
    }
}
