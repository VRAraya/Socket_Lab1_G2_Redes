package sockets;

//Lado del Servidor

import java.io.*;
import java.util.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.*;
  
// Clase Servidor
public class Server
{
  // Vector para almacenar los usuarios activos
  static Vector<ClientHandler> ar = new Vector<>();

  // Contador de clientes
  static int i = 0;
  public static void main(String[] args) throws IOException  
  {
    Console console = System.console();

    if (console == null) {
      System.err.println("No hay consola.");
      System.exit(1);
    }

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy");
    
    //Solicita puerto a escuchar
    console.printf("Configuración del servidor\n");
    console.printf("Ingrese el puerto a escuchar: ");
    Integer port = Integer.parseInt(console.readLine());

    // Server escuchará en el puerto port
    ServerSocket serverSocket=null;

    // Se intenta realizar la conexion
    try {
      serverSocket = new ServerSocket(port);
      console.printf("["+ dtf.format(LocalDateTime.now()) +"] ");
      console.printf("Servidor escuchando en el puerto "+ port +"\n");
    } catch (IOException e) {
      console.printf("No se puede escuchar el puerto "+ port);
      System.exit(1);
    }

    Socket s = null;
    // Corre en loop infinito para escuchar solicitudes de clientes

    while (true)
    {
      // Aceptar la solicitud entrante
      s = serverSocket.accept();

      // Se declaran los flujos de entrada y salida para trabajar con ellos
      DataInputStream inStream = new DataInputStream(s.getInputStream()); 
      DataOutputStream outStream = new DataOutputStream(s.getOutputStream()); 

      // Recibe el nickname del cliente
      String name = null;
      try {
        name = inStream.readUTF();
      } catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
      }

      //Se notifica dentro del servidor que se ha conectado un usuario
      console.printf("["+ dtf.format(LocalDateTime.now()) +"] " + "Se ha conectado " + name + " con IP " + s.getInetAddress() + "\n");

      //Se notifica a todos los usuarios activos que un cliente se ha conectado
      for (ClientHandler mc : Server.ar) {
        if (mc.isloggedin==true) {
          mc.outStream.writeUTF("["+ dtf.format(LocalDateTime.now()) +"] " + "Se ha conectado "+ name + " con IP"+ s.getInetAddress() +"\n");
          break;
        }
      }

      // Se crea un nuevo manejador de clientes para encargarse de este. 
      ClientHandler mtch = new ClientHandler(s, name, inStream, outStream); 
  
      // Crear un nuevo hilo con este objeto 
      Thread t = new Thread(mtch);

      // Se agrega a la lista de usuarios activos
      ar.add(mtch); 
  
      // Se inicia el hilo
      t.start(); 

      // Se suma 1 al conteo de usuarios
      i++; 

    }
  }
}
