package sockets;
import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HiloServer extends Thread {
  private Socket clientSocket = null;

  public HiloServer(Socket clientSocket) {
    this.clientSocket = clientSocket;
  }

  public void run() {
    DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
  
    // PrintWriter out=null;
    // BufferedReader in=null;
    // long ini = System.currentTimeMillis();
    try {
      String nick, ip, message;
      Date date = new Date();
      SendingPackage packageReceived;

      ObjectInputStream dataPackage = new ObjectInputStream(clientSocket.getInputStream());
      packageReceived = (SendingPackage) dataPackage.readObject();
      
      nick = packageReceived.getNick();
      ip=packageReceived.getIp();
      message=packageReceived.getMessage();

      System.out.print("["+hourdateFormat.format(date)+"] "+ nick +": ");
      System.out.println(message + "\n");

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
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    // long fin = System.currentTimeMillis();
    // System.out.println("Tiempo Ejecución Hilo " + (fin - ini) + "ms");
  }
}