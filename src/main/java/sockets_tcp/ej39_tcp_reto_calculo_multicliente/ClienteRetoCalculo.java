package sockets_tcp.ej39_tcp_reto_calculo_multicliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteRetoCalculo {

    public static void main(String[] args) throws Exception {
        Scanner teclado = new Scanner(System.in);

        System.out.print("IP [127.0.0.1]: ");
        String ip = teclado.nextLine();
        if (ip.isBlank()) {
            ip = "127.0.0.1";
        }

        System.out.print("Puerto [6300]: ");
        String textoPuerto = teclado.nextLine();
        int puerto = textoPuerto.isBlank() ? 6300 : Integer.parseInt(textoPuerto);

        try (Socket socket = new Socket(ip, puerto);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println(entrada.readLine());
            System.out.println("Ejemplos: @sumar#5#7@ | @factorial#5@ | @primo#17@ | @stats#@ | @salir#@");

            String linea;
            do {
                System.out.print("> ");
                linea = teclado.nextLine();
                salida.println(linea);
                System.out.println(entrada.readLine());
            } while (!linea.equals("@salir#@"));
        }
    }
}
