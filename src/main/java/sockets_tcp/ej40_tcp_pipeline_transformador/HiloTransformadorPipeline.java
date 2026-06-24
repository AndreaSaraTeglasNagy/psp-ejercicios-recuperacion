package sockets_tcp.ej40_tcp_pipeline_transformador;

class HiloTransformadorPipeline extends Thread {
    private final BufferCircularCerrable bufferEntrada;
    private final BufferCircularCerrable bufferSalida;
    private final int multiplicador;

    public HiloTransformadorPipeline(BufferCircularCerrable bufferEntrada, BufferCircularCerrable bufferSalida, int multiplicador) {
        this.bufferEntrada = bufferEntrada;
        this.bufferSalida = bufferSalida;
        this.multiplicador = multiplicador;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Integer valor = bufferEntrada.sacar();
                if (valor == null) {
                    break;
                }

                int transformado = valor * multiplicador;
                bufferSalida.meter(transformado);
                System.out.println("Transformador: " + valor + " -> " + transformado);
            }
        } catch (Exception e) {
            System.out.println("Error en transformador: " + e.getMessage());
        } finally {
            bufferSalida.cerrar();
        }
    }
}
