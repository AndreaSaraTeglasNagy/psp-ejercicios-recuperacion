package sockets_tcp.ej30_tcp_acumulador;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class ServidorAcumulador {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws IOException {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Servidor TCP acumulador.");
        int puerto = pedirEntero(teclado, "Puerto para escuchar", 6004);
        iniciarServidor(puerto);
    }

    // Pide un número entero permitiendo un valor por defecto.
    private static int pedirEntero(Scanner teclado, String mensaje, int defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine();
        return texto.isBlank() ? defecto : Integer.parseInt(texto);
    }

    // Inicia el servidor y comparte el acumulado entre clientes.
    private static void iniciarServidor(int puerto) throws IOException {
        AtomicInteger acumulado = new AtomicInteger(0);
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor acumulador iniciado en puerto " + puerto);
            while (true) {
                new Thread(new HiloAcumulador(servidor.accept(), acumulado)).start();
            }
        }
    }
}
