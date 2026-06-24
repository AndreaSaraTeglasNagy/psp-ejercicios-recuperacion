package sockets_tcp.ej39_tcp_reto_calculo_multicliente;

import java.net.ServerSocket;
import java.util.Scanner;

public class ServidorRetoCalculo {

    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);
        System.out.println("Servidor TCP reto calculo multicliente.");
        int puerto = pedirEntero(teclado, "Puerto", 6300);

        EstadisticasCalculo estadisticas = new EstadisticasCalculo();

        try (ServerSocket servidor = new ServerSocket(puerto)) {
            System.out.println("Servidor reto calculo escuchando en " + puerto);
            while (true) {
                new Thread(new HiloRetoCalculo(servidor.accept(), estadisticas)).start();
            }
        }
    }

    private static int pedirEntero(Scanner teclado, String mensaje, int defecto) {
        System.out.print(mensaje + " [" + defecto + "]: ");
        String texto = teclado.nextLine();
        return texto.isBlank() ? defecto : Integer.parseInt(texto);
    }
}
