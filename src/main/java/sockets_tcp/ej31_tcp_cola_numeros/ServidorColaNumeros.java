package sockets_tcp.ej31_tcp_cola_numeros;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class ServidorColaNumeros {
    // Punto de entrada del ejercicio.
    public static void main(String[] args) throws IOException {
        Scanner teclado = new Scanner(System.in);

        System.out.println("Servidor TCP cola números.");
        int puerto = pedirEntero(teclado, "Puerto para escuchar", 6005);
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
        ColaCompartida cola = new ColaCompartida(5);
        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en puerto " + puerto);
            while (true) {
                new Thread(new HiloCola(servidor.accept(), cola)).start();
            }
        }
    }
}
