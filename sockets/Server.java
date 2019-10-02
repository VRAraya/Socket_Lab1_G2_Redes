package sockets;

//Lado del Servidor
  
import java.io.*; 
import java.util.*; 
import java.net.*; 
import java.text.*;
  
// Clase Servidor
public class Server
{
  // Vector para almacenar los usuarios activos
  static Vector<ClientHandler> ar = new Vector<>();

  // Contador de clientes
  static int i = 0;
  public static void main(String[] args) throws IOException  
  {
    Scanner scn = new Scanner(System.in);
    Date date = new Date();
    DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
    
    System.out.println("Configuración del servidor");
    System.out.println("Ingrese el puerto a escuchar: ");
    Integer port = scn.nextInt();

    // Server escuchará en el puerto port
    ServerSocket serverSocket=null;

    try {
      serverSocket = new ServerSocket(port);
      System.out.print("["+ hourdateFormat.format(date) +"]: ");
      System.out.println("Socket del servidor creado. \n");
    } catch (IOException e) {
      System.err.println("No se puede escuchar el puerto "+ port);
      System.exit(1);
    }

    Socket s = null;
    // Corre en loop infinito para escuchar solicitudes de clientes

    while (true)
    {
      // Aceptar la solicitud entrante
      s = serverSocket.accept();

      //Se notifica dentro del servidor que se ha conectado un usuario
      System.out.print("["+ hourdateFormat.format(date) +"]: ");
      System.out.println("Se ha conectado un cliente : " + s); 
      
      // Se declaran los flujos de entrada y salida para trabajar con ellos
      DataInputStream inStream = new DataInputStream(s.getInputStream()); 
      DataOutputStream outStream = new DataOutputStream(s.getOutputStream()); 

      // Se crea un nuevo manejador de clientes para encargarse de este. 
      ClientHandler mtch = new ClientHandler(s,"client " + i, inStream, outStream); 
  
      // Crear un nuevo hilo con este objeto 
      Thread t = new Thread(mtch);

      // Se agrega a la lista de usuarios activos
      ar.add(mtch); 
  
      // Se inicia el hilo
      t.start(); 
  
      // Se suma 1 al conteo de usuarios
      // se usa sólo para nombrar usuarios, pero puede ser reemplazado
      // por cualquier esquema de nombres
      i++; 

    }
  }
}
