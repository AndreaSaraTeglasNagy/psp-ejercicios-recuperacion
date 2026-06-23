package sockets_tcp.ej27_tcp_eco_multihilo;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class ServidorTcpEcoMultihilo {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws IOException {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Servidor TCP eco multihilo.");
        int puerto = pedirEntero(teclado, "Puerto para escuchar", 6001);
        iniciarServidor(puerto);
    }

    // Pide un número entero permitiendo un valor por defecto.
    private static int pedirEntero(Scanner teclado, String mensaje, int defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine();
        return texto.isBlank() ? defecto : Integer.parseInt(texto);
    }

    // Inicia el servidor y atiende clientes.
    private static void iniciarServidor(int puerto) throws IOException {
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en puerto " + puerto);
            while (true) {
                new Thread(new HiloClienteEco(servidor.accept())).start();
            }
        }
    }
}
