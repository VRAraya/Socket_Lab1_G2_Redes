package echoserver;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class HiloClient extends Thread {
  private String host = null;
  private String operacion = null;
  
  public HiloClient(String host,String operacion) {
    this.host = host;
    this.operacion = operacion;
  }
  
  public void run() {
    long ini = System.currentTimeMillis();
    Socket clientSocket;
    try {
      clientSocket = new Socket(host, 4444);
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
      BufferedReader in =new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      out.println(operacion);
      out.flush();
      String fromServer = in.readLine();
      
      long fin = System.currentTimeMillis();
      System.out.println("Cliente: " + operacion);
      System.out.println("Server " + host + ": " + fromServer);
      System.out.println("Tiempo Ejecución Hilo " + (fin - ini) + "ms");

      in.close();
      out.close();
      clientSocket.close();
    } catch (UnknownHostException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
