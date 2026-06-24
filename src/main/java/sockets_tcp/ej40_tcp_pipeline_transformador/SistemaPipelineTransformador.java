package sockets_tcp.ej40_tcp_pipeline_transformador;

public class SistemaPipelineTransformador {

    public static void main(String[] args) {
        if (args.length != 6) {
            System.out.println("Uso:");
            System.out.println("java sockets_tcp.ej40_tcp_pipeline_transformador.SistemaPipelineTransformador <ipProd> <puertoProd> <ipCons> <puertoCons> <totalValores> <multiplicador>");
            return;
        }

        String ipProductor = args[0];
        int puertoProductor = Integer.parseInt(args[1]);
        String ipConsumidor = args[2];
        int puertoConsumidor = Integer.parseInt(args[3]);
        int totalValores = Integer.parseInt(args[4]);
        int multiplicador = Integer.parseInt(args[5]);

        BufferCircularCerrable bufferOriginales = new BufferCircularCerrable(10);
        BufferCircularCerrable bufferTransformados = new BufferCircularCerrable(10);

        HiloProductorPipeline productor = new HiloProductorPipeline(ipProductor, puertoProductor, totalValores, bufferOriginales);
        HiloTransformadorPipeline transformador = new HiloTransformadorPipeline(bufferOriginales, bufferTransformados, multiplicador);
        HiloConsumidorPipeline consumidor = new HiloConsumidorPipeline(ipConsumidor, puertoConsumidor, bufferTransformados);

        productor.start();
        transformador.start();
        consumidor.start();

        try {
            productor.join();
            transformador.join();
            consumidor.join();
            System.out.println("Pipeline transformador finalizado correctamente.");
        } catch (InterruptedException e) {
            System.out.println("Main interrumpido.");
            Thread.currentThread().interrupt();
        }
    }
}
