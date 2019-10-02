package sockets;
import java.net.*;
import java.io.*;

public class Server {
  static ServerSocket serverSocket = null;
  public static void main(String[] args) throws IOException {
    Console console = System.console();

    if(console == null) {
      System.err.println("No hay consola.");
      System.exit(1);
    }

    console.printf("Configuración de servidor \n");
    console.printf("Introduzca un puerto donde escuchar: \n");

    int inputPort;
    inputPort = Integer.parseInt(console.readLine());

    try {
      serverSocket = new ServerSocket(inputPort);
      console.printf("Socket del servidor creado. \n");
    } 
    catch (IOException e) {
      System.err.println("Could not listen on port: "+ inputPort);
      System.exit(1);
    }
    Socket clientSocket = null;

    while (true){
      clientSocket = serverSocket.accept();
      HiloServer hs = new HiloServer(clientSocket);
      hs.start();
      // console.printf("Cliente conectado. \n");
    }
  }
}