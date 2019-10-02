package sockets;

//Lado del Servidor
  
import java.io.*; 
import java.util.*; 
import java.net.*; 
  
// Clase Servidor
public class Server  
{ 
  
    // Vector para almacenar los usuarios activos
    static Vector<ClientHandler> ar = new Vector<>(); 
      
    // Contador de clientes
    static int i = 0; 
  
    public static void main(String[] args) throws IOException  
    { 
        // Server escuchará en el puerto 1234
        ServerSocket serverSocket = new ServerSocket(1234); 
          
        Socket s; 
          
        // Corre en loop infinito para escuchar solicitudes de clientes

        while (true)  
        { 
            // Aceptar la solicitud entrante
            s = serverSocket.accept(); 
  
            System.out.println("Se ha conectado un cliente : " + s); 
              
            // Se declaran los flujos de entrada y salida para trabajar con ellos
            DataInputStream inStream = new DataInputStream(s.getInputStream()); 
            DataOutputStream outStream = new DataOutputStream(s.getOutputStream()); 
              
            // Se crea un nuevo manejador de clientes para encargarse de este. 
            ClientHandler mtch = new ClientHandler(s,"client " + i, inStream, outStream); 
  
            // Crear un nuevo hilo con este objeto 
            Thread t = new Thread(mtch); 
              
            System.out.println("Se ingresa a la lista de usuarios activos"); 
  
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
  
