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

        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);

            salida.println("Protocolo: @sumar#a#b@ | @factorial#n@ | @primo#n@ | @stats#@ | @salir#@");

            String linea;
            while ((linea = entrada.readLine()) != null) {

                try {
                    procesarComando(linea, salida);

                    if (linea.equals("@salir#@")) {
                        break;
                    }

                } catch (Exception e) {
                    salida.println("@error#" + e.getMessage() + "@");
                }
            }

            entrada.close();
            salida.close();
            socket.close();

        } catch (Exception e) {
            System.err.println("Cliente reto calculo desconectado: " + e.getMessage());
        }
    }

    private void procesarComando(String linea, PrintWriter salida) {

        if (linea.equals("@salir#@")) {
            salida.println("@adios#@");
            return;
        }

        if (linea.equals("@stats#@")) {
            salida.println("@stats#" + estadisticas.resumen() + "@");
            return;
        }

        if (linea.startsWith("@sumar#") && linea.endsWith("@")) {
            procesarSuma(linea, salida);
            return;
        }

        if (linea.startsWith("@factorial#") && linea.endsWith("@")) {
            procesarFactorial(linea, salida);
            return;
        }

        if (linea.startsWith("@primo#") && linea.endsWith("@")) {
            procesarPrimo(linea, salida);
            return;
        }

        salida.println("@error#comando_no_valido@");
    }

    private void procesarSuma(String linea, PrintWriter salida) {
        String cuerpo = quitarPrefijoYSufijo(linea, "@sumar#");
        String[] partes = cuerpo.split("#");

        if (partes.length != 2) {
            salida.println("@error#formato_suma_incorrecto_usa_@sumar#a#b@@");
            return;
        }

        int a = Integer.parseInt(partes[0]);
        int b = Integer.parseInt(partes[1]);

        estadisticas.nuevaOperacion();
        salida.println("@resultado#" + (a + b) + "@");
    }

    private void procesarFactorial(String linea, PrintWriter salida) {
        String cuerpo = quitarPrefijoYSufijo(linea, "@factorial#");
        int n = Integer.parseInt(cuerpo);

        if (n < 0 || n > 20) {
            salida.println("@error#factorial_solo_entre_0_y_20@");
            return;
        }

        estadisticas.nuevaOperacion();
        salida.println("@resultado#" + factorial(n) + "@");
    }

    private void procesarPrimo(String linea, PrintWriter salida) {
        String cuerpo = quitarPrefijoYSufijo(linea, "@primo#");
        int n = Integer.parseInt(cuerpo);

        estadisticas.nuevaOperacion();
        salida.println("@resultado#" + esPrimo(n) + "@");
    }

    private String quitarPrefijoYSufijo(String linea, String prefijo) {
        String cuerpo = linea.substring(prefijo.length(), linea.length() - 1);

        if (cuerpo.length() == 0) {
            throw new IllegalArgumentException("faltan_datos");
        }

        return cuerpo;
    }

    private long factorial(int n) {
        long resultado = 1;

        for (int i = 2; i <= n; i++) {
            resultado = resultado * i;
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
