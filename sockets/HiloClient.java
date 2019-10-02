package sockets;
import java.io.*;
import java.net.*;
import java.util.Date;

public class HiloClient extends Thread {
  private String ip = null;
  private Integer port = null;
  private String nick = null;
  
  public HiloClient(String ip, Integer port, String nick) {
    this.ip = ip;
    this.port = port;
    this.nick = nick;
  }
  
  public void run() {
    Socket clientSocket;
    Console console = System.console();

    if(console == null) {
      System.err.println("No hay consola.");
      System.exit(1);
    }

    try {
      clientSocket = new Socket(ip, port);
      String message;
      SendingPackage data = new SendingPackage();
      Date date = new Date();
      data.setNick(nick);
      data.setIp(ip);
      ObjectOutputStream dataPackage = new ObjectOutputStream(clientSocket.getOutputStream());


      do {
        console.printf("Escribe un mensaje: \n");
        message = console.readLine();

        data.setMessage(message);
        data.setDate(date);
        dataPackage.writeObject(data);

      } while (!message.equals("Salir"));

      clientSocket.close();
      System.out.println("Cliente cerró conexión");

    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("[ERROR]: ");
      System.out.println(e.getMessage());
    }
  }
}
