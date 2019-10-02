package sockets;

import java.io.*;
//import java.net.*;

public class Client {
  public static void main(String[] args) throws IOException {
    int inputPort;
    String inputIp;
    String nickName;
    Console console = System.console();

    if(console == null) {
      System.err.println("No hay consola.");
      System.exit(1);
    }

    console.printf("Configuración de conexión a servidor \n");
    console.printf("Introduzca la IP del servidor donde se conectará: \n");
    inputIp = console.readLine();

    console.printf("Indique el puerto: \n");
    inputPort = Integer.parseInt(console.readLine());

    console.printf("Ingrese su nickname: \n");
    nickName = console.readLine();

    HiloClient hc=new HiloClient(inputIp, inputPort, nickName);
    hc.start();
  }
}