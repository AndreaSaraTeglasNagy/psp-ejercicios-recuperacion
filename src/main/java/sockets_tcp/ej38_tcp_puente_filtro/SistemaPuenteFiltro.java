package sockets_tcp.ej38_tcp_puente_filtro;

public class SistemaPuenteFiltro {

    public static void main(String[] args) {
        if (args.length != 6) {
            System.out.println("Uso:");
            System.out.println("java sockets_tcp.ej38_tcp_puente_filtro.SistemaPuenteFiltro <ipProd> <puertoProd> <ipCons> <puertoCons> <totalGenerar> <valorProhibido>");
            return;
        }

        String ipProductor = args[0];
        int puertoProductor = Integer.parseInt(args[1]);
        String ipConsumidor = args[2];
        int puertoConsumidor = Integer.parseInt(args[3]);
        int totalGenerar = Integer.parseInt(args[4]);
        int valorProhibido = Integer.parseInt(args[5]);

        BufferCircularCerrable buffer = new BufferCircularCerrable(10);

        HiloProductorFiltro productor = new HiloProductorFiltro(ipProductor, puertoProductor, totalGenerar, valorProhibido, buffer);
        HiloConsumidorFiltro consumidor = new HiloConsumidorFiltro(ipConsumidor, puertoConsumidor, buffer);

        productor.start();
        consumidor.start();

        try {
            productor.join();
            consumidor.join();
            System.out.println("Sistema puente filtro finalizado correctamente.");
        } catch (InterruptedException e) {
            System.out.println("Main interrumpido.");
            Thread.currentThread().interrupt();
        }
    }
}
