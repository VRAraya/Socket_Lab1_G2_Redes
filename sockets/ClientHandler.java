package sockets;

import java.io.*;
import java.util.*;
import java.net.*;
import java.text.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Clase Manejador de cliente 
class ClientHandler implements Runnable  
{
  private String name; 
  final DataInputStream inStream; 
  final DataOutputStream outStream; 
  Socket s; 
  boolean isloggedin;
  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");

  // constructor 
  public ClientHandler(Socket s, String name, DataInputStream inStream, DataOutputStream outStream) { 
    this.inStream = inStream; 
    this.outStream = outStream; 
    this.name = name; 
    this.s = s; 
    this.isloggedin=true;
  }
  
  @Override
  public void run() {
    Console console = System.console();

    if (console == null) {
      System.err.println("No hay consola.");
      System.exit(1);
    }

    String received;
    while (true) {
      try
      {
        // Recibe el string
        received = inStream.readUTF();
        console.printf("["+ dtf.format(LocalDateTime.now()) +"] "+ name + ": " + received+ "\n");

        if (received.equals("Salir")) {
          
          //Se notifica dentro del servidor que se ha desconectado un usuario
          this.isloggedin=false;
          this.s.close();
          console.printf("["+ dtf.format(LocalDateTime.now()) +"] " + name + " se ha desconectado.\n");

          //Se notifica a todos los usuarios activos que un cliente se ha desconectado
          for (ClientHandler mc : Server.ar) {
            if (mc.isloggedin==true) {
              mc.outStream.writeUTF("["+ dtf.format(LocalDateTime.now()) +"] " + name + " se ha desconectado.\n");
              break;
            }
          }
          break;
        }

        // Buscar todos los usuarios activos en el vector ar,
        // y envía el mensaje a todos ellos.
        for (ClientHandler mc : Server.ar) {
          // si el receptor es encontrado y está conectado, se escribe en su flujo de salida
          if (!mc.name.equals(name) && mc.isloggedin==true) {
            mc.outStream.writeUTF("["+ dtf.format(LocalDateTime.now()) +"] "+ name +" : "+ received+"\n");
            break;
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
      }
    }
    try {
      // Cerrando los recursos
      this.inStream.close();
      this.outStream.close();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }
}
