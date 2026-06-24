package sockets_tcp.ej37_tcp_buffer_eliminar_valor;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class ServidorBufferEliminarValor {

    public static void main(String[] args) throws IOException {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Servidor TCP con buffer circular y eliminacion por valor.");
        int puerto = pedirEntero(teclado, "Puerto para escuchar", 6100);
        int capacidad = pedirEntero(teclado, "Capacidad del buffer", 10);

        BufferCircularBusqueda buffer = new BufferCircularBusqueda(capacidad);

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en puerto " + puerto);
            while (true) {
                new Thread(new HiloBufferValor(servidor.accept(), buffer)).start();
            }
        }
    }

    private static int pedirEntero(Scanner teclado, String mensaje, int defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine();
        return texto.isBlank() ? defecto : Integer.parseInt(texto);
    }
}
