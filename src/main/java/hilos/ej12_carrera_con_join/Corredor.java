package hilos.ej12_carrera_con_join;

import java.util.Random;

class Corredor implements Runnable {
    private final String nombre;
    private final Random random = new Random();

    // Constructor: guarda los datos necesarios.
    public Corredor(String nombre) {
        this.nombre = nombre;
    }

    // Ejecuta la tarea del hilo.
    @Override
    public void run() {
        for (int metro = 10; metro <= 100; metro += 10) {
            try {
                Thread.sleep(random.nextInt(300, 900));
                System.out.println(nombre + " va por el metro " + metro);
            } catch (InterruptedException e) {
                return;
            }
        }
        System.out.println(nombre + " ha llegado a meta");
    }
}
