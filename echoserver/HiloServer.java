package echoserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HiloServer extends Thread {
  private Socket clientSocket = null;
  
  public HiloServer(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }
  
  public void run() {
    PrintWriter out=null;
    BufferedReader in=null;
    long ini = System.currentTimeMillis();
    try {
      out = new PrintWriter(clientSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      String inputLine, outputLine;

      while ((inputLine = in.readLine()) != null) {
        outputLine = "Computador dice: " + inputLine;
        //Solo repetimos lo que enviamos
        // outputLine = "" + EchoServer.getResultado(inputLine);
        out.println(outputLine);
        if (inputLine.equals("Bye."))
          break;
      }
      if (out!=null)
        out.close();
      if (in!=null)
        in.close();
      clientSocket.close();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    long fin = System.currentTimeMillis();
    System.out.println("Tiempo Ejecución Hilo " + (fin - ini) + "ms");
  }
}