package hilos.ej14_contador_sincronizado;

class ContadorSeguro {
    private int valor = 0;

    // Método auxiliar del ejercicio.
    public synchronized void incrementar() {
        valor++;
    }

    // Devuelve el valor solicitado.
    public synchronized int getValor() {
        return valor;
    }
}
