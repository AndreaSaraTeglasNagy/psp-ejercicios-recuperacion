package mixtos.ej36_udp_servidor_con_pool;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorUdpPool {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Servidor UDP con pool de hilos.");
        int puerto = pedirEntero(teclado, "Puerto para escuchar", 7200);
        iniciarServidor(puerto);
    }

    // Pide un número entero permitiendo un valor por defecto.
    private static int pedirEntero(Scanner teclado, String mensaje, int defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine();
        return texto.isBlank() ? defecto : Integer.parseInt(texto);
    }

    // Inicia el servidor UDP con pool de hilos.
    private static void iniciarServidor(int puerto) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(4);

        try (DatagramSocket socket = new DatagramSocket(puerto)) {
            System.out.println("Servidor UDP con pool iniciado en puerto " + puerto);

            while (true) {
                byte[] buffer = new byte[1400];
                DatagramPacket paquete = new DatagramPacket(buffer, buffer.length);
                socket.receive(paquete);

                byte[] datos = java.util.Arrays.copyOf(paquete.getData(), paquete.getLength());
                InetAddress direccion = paquete.getAddress();
                int puertoCliente = paquete.getPort();

                pool.submit(() -> responder(socket, datos, direccion, puertoCliente));
            }
        }
    }

    // Atiende la petición recibida por el cliente.
    private static void responder(DatagramSocket socket, byte[] datos, InetAddress direccion, int puertoCliente) {
        try {
            String mensaje = new String(datos, StandardCharsets.UTF_8);
            String respuesta = Thread.currentThread().getName() + " procesó: " + mensaje.toUpperCase();
            byte[] salida = respuesta.getBytes(StandardCharsets.UTF_8);

            synchronized (socket) {
                socket.send(new DatagramPacket(salida, salida.length, direccion, puertoCliente));
            }
        } catch (Exception e) {
            System.err.println("Error respondiendo UDP");
        }
    }
}
