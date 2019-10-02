package sockets;
import java.io.*;
import java.net.Socket;

public class HiloServer extends Thread {
  private Socket clientSocket = null;

  public HiloServer(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public void run() {
    // PrintWriter out=null;
    // BufferedReader in=null;
    // long ini = System.currentTimeMillis();
    try {
      DataInputStream inputData = new DataInputStream(clientSocket.getInputStream());

      String messageText = inputData.readUTF();

      System.out.println(messageText + "\n");

      clientSocket.close();
      // out = new PrintWriter(clientSocket.getOutputStream(), true);
      // in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      // String inputLine, outputLine;

      // while ((inputLine = in.readLine()) != null) {
      //   outputLine = "Computador dice: " + inputLine;
      //   //Solo repetimos lo que enviamos
      //   // outputLine = "" + Server.getResultado(inputLine);
      //   out.println(outputLine);
      //   if (inputLine.equals("Bye."))
      //     break;
      // }
      // if (out!=null)
      //   out.close();
      // if (in!=null)
      //   in.close();
      // clientSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    // long fin = System.currentTimeMillis();
    // System.out.println("Tiempo Ejecución Hilo " + (fin - ini) + "ms");
  }
}