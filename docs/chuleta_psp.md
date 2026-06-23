# Chuleta rápida PSP

## Hilo básico

```java
class MiHilo implements Runnable {
    public void run() {
        System.out.println("trabajando");
    }
}

Thread h = new Thread(new MiHilo());
h.start();
h.join();
```

## synchronized

```java
public synchronized void metodoSeguro() {
    contador++;
}
```

## wait / notifyAll

```java
public synchronized int consumir() throws InterruptedException {
    while (!hayDato) {
        wait();
    }
    int dato = valor;
    hayDato = false;
    notifyAll();
    return dato;
}
```

Usa siempre `while`, no `if`, porque el hilo puede despertar y que la condición siga sin cumplirse.

## ProcessBuilder básico

```java
ProcessBuilder pb = new ProcessBuilder("grep", "ERROR", "log.txt");
pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
pb.redirectError(ProcessBuilder.Redirect.INHERIT);
Process p = pb.start();
p.waitFor();
```

## Escribir a la entrada de un proceso

```java
Process p = new ProcessBuilder("wc").start();
BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
bw.write("hola mundo");
bw.newLine();
bw.close();
p.waitFor();
```

## Leer salida de un proceso

```java
BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
String linea;
while ((linea = br.readLine()) != null) {
    System.out.println(linea);
}
```

## Servidor TCP multihilo

```java
ServerSocket servidor = new ServerSocket(5000);
while (true) {
    Socket socket = servidor.accept();
    new Thread(new HiloCliente(socket)).start();
}
```

## Cliente TCP

```java
Socket socket = new Socket("localhost", 5000);
BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
out.println("hola");
System.out.println(in.readLine());
```

## UDP enviar

```java
DatagramSocket socket = new DatagramSocket();
byte[] datos = "hola".getBytes(StandardCharsets.UTF_8);
DatagramPacket p = new DatagramPacket(datos, datos.length, InetAddress.getByName("localhost"), 5000);
socket.send(p);
```

## UDP recibir

```java
byte[] buffer = new byte[1400];
DatagramPacket p = new DatagramPacket(buffer, buffer.length);
socket.receive(p);
String msg = new String(p.getData(), 0, p.getLength(), StandardCharsets.UTF_8);
```

## Cosas que suelen restar puntos

- No cerrar sockets o streams.
- No hacer `flush()` al escribir al proceso hijo.
- Usar `if` en vez de `while` con `wait()`.
- Olvidar `join()` y que el `main` termine antes.
- No crear un hilo por cliente en servidores TCP multicliente.
- Modificar variables compartidas sin `synchronized` o `AtomicInteger`.
- Leer solo `stdout` y olvidarse de `stderr` en procesos que escriben errores.
