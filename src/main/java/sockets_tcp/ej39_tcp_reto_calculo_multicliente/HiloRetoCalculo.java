package sockets_tcp.ej39_tcp_reto_calculo_multicliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class HiloRetoCalculo implements Runnable {
    private final Socket socket;
    private final EstadisticasCalculo estadisticas;

    public HiloRetoCalculo(Socket socket, EstadisticasCalculo estadisticas) {
        this.socket = socket;
        this.estadisticas = estadisticas;
    }

    @Override
    public void run() {
        estadisticas.nuevoCliente();

        try (socket;
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            salida.println("Protocolo: @sumar#a#b@ | @factorial#n@ | @primo#n@ | @stats#@ | @salir#@");

            String linea;
            while ((linea = entrada.readLine()) != null) {
                if (linea.startsWith("@sumar#") && linea.endsWith("@")) {
                    String cuerpo = limpiar(linea, "@sumar#");
                    String[] partes = cuerpo.split("#");
                    int a = Integer.parseInt(partes[0]);
                    int b = Integer.parseInt(partes[1]);
                    estadisticas.nuevaOperacion();
                    salida.println("@resultado#" + (a + b) + "@");

                } else if (linea.startsWith("@factorial#") && linea.endsWith("@")) {
                    int n = Integer.parseInt(limpiar(linea, "@factorial#"));
                    estadisticas.nuevaOperacion();
                    salida.println("@resultado#" + factorial(n) + "@");

                } else if (linea.startsWith("@primo#") && linea.endsWith("@")) {
                    int n = Integer.parseInt(limpiar(linea, "@primo#"));
                    estadisticas.nuevaOperacion();
                    salida.println("@resultado#" + esPrimo(n) + "@");

                } else if (linea.equals("@stats#@")) {
                    salida.println("@stats#" + estadisticas.resumen() + "@");

                } else if (linea.equals("@salir#@")) {
                    salida.println("@adios#@");
                    break;

                } else {
                    salida.println("@error#comando_no_valido@");
                }
            }
        } catch (Exception e) {
            System.err.println("Cliente reto calculo desconectado.");
        }
    }

    private String limpiar(String linea, String prefijo) {
        return linea.replace(prefijo, "").replace("@", "");
    }

    private long factorial(int n) {
        if (n < 0 || n > 20) {
            throw new IllegalArgumentException("Factorial solo permite valores entre 0 y 20.");
        }

        long resultado = 1;
        for (int i = 2; i <= n; i++) {
            resultado *= i;
        }
        return resultado;
    }

    private boolean esPrimo(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
