package sockets;

import java.io.*;
//import java.net.*;

public class Client {
  public static void main(String[] args) throws IOException {
    int inputPort;
    String inputIP;
    Console console = System.console();

    if(console == null) {
      System.err.println("No hay consola.");
      System.exit(1);
    }

    console.printf("Configuración de conexión a servidor \n");
    console.printf("Por favor introduzca la IP del servidor donde se conectará: \n");
    inputIP = console.readLine();

    console.printf("Por favor introduzca el puerto: \n");
    inputPort = Integer.parseInt(console.readLine());

    HiloClient hc=new HiloClient(inputIP, inputPort);
    hc.start();
  }
}