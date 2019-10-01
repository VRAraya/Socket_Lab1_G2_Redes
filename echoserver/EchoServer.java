package echoserver;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class EchoServer {
  static ServerSocket serverSocket = null;
  public static void main(String[] args) throws IOException {
    System.out.println("Configuración de creación de socket: ");
    System.out.println ("Por favor introduzca un puerto:");

    int puertoEntrada;
    Scanner entradaEscaner = new Scanner(System.in); // Creación de un objeto Scanner
    puertoEntrada = entradaEscaner.nextInt(); //Invocamos un método sobre un objeto Scanner

    try {
      serverSocket = new ServerSocket(puertoEntrada);
      System.out.println("Socket creado");
    } catch (IOException e) {
      System.err.println("Could not listen on port: "+ puertoEntrada);
      System.exit(1);
    }
    Socket clientSocket = null;

    while (true){
      clientSocket = serverSocket.accept();
      HiloServer hs = new HiloServer(clientSocket);
      hs.start();
      System.out.println("Cliente conectado");
    }
  }
  
  // public static int getResultado(String linea) {
  //   int resultado=0;
  //   String[] operadores = linea.split("\\s");

  //   if (operadores[0].equalsIgnoreCase("+")) {
  //     resultado=Integer.parseInt(operadores[1])+Integer.parseInt(operadores[2]);
  //   }
  //   if (operadores[0].equalsIgnoreCase("-")) {
  //     resultado=Integer.parseInt(operadores[1])-Integer.parseInt(operadores[2]);
  //   }
  //   if (operadores[0].equalsIgnoreCase("*")) {
  //     resultado=Integer.parseInt(operadores[1])*Integer.parseInt(operadores[2]);
  //   }
  //   if (operadores[0].equalsIgnoreCase("/")) {
  //     resultado=Integer.parseInt(operadores[1])/Integer.parseInt(operadores[2]);
  //   }
  //   return resultado;
  // }
}