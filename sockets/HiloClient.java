package sockets;
import java.io.*;
import java.net.*;

public class HiloClient extends Thread {
  private String host = null;
  private Integer port = null;
  
  public HiloClient(String host, Integer port) {
    this.host = host;
    this.port = port;
  }
  
  public void run() {
    Socket clientSocket;
    Console console = System.console();

    if(console == null) {
      System.err.println("No hay consola.");
      System.exit(1);
    }

    try {
      String str;
      do {
        clientSocket = new Socket(host, port);
        console.printf("Escribe un mensaje: \n");
        str = console.readLine();
  
        DataOutputStream outputData = new DataOutputStream(clientSocket.getOutputStream());
        outputData.writeUTF(str);
        outputData.close();
        clientSocket.close();
      } while (!str.equals("Salir"));

    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
