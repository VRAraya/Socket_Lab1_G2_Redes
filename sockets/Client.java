package sockets;

//Lado del Cliente

import java.io.*; 
import java.net.*; 
import java.util.*; 
import java.text.*;

public class Client 
{ 
	public static void main(String args[]) throws UnknownHostException, IOException 
	{ 
    Scanner scn = new Scanner(System.in);
    DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");

    System.out.println("---Bienvenido a la sala de chat--- ");

    // Obteniendo la ip
    System.out.print("Ingrese la ip del servidor de la sala de chat: ");
    String ipServer = scn.nextLine();

    // Obteniendo el puerto de envío
    System.out.print("Ingrese el puerto: ");
    Integer portServer = scn.nextInt();
		
    // Estableciendo la conexión
    Socket s = null; 
    try {
      s = new Socket(ipServer, portServer);
      System.out.println("Socket conectado.");
    } catch (IOException e) {
      System.err.println("No se puede construir el socket hacia el server"+ipServer+"con puerto "+ portServer);
      System.exit(1);
    }
    System.out.print("Ingrese su nickname para entrar al chat: ");
    String nick = scn.next();
    System.out.println("---CHAT---");
		
		// Se declaran los flujos de entrada y salida
    DataInputStream inStream = new DataInputStream(s.getInputStream()); 
    DataOutputStream outStream = new DataOutputStream(s.getOutputStream()); 

		// Hilo para enviar mensajes
    Thread sendMessage = new Thread(new Runnable()  
    { 
        @Override
        public void run() { 
            while (true) { 

                // Lee el mensaje a enviar
                String msg = scn.nextLine(); 
                  
                try { 
                    // write on the output stream 
                    outStream.writeUTF(msg); 
                } catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            } 
        } 
    }); 
		
		// Hilo para leer mensajes
    Thread readMessage = new Thread(new Runnable()  
    { 
      @Override
      public void run() { 
        while (true) {
          try {
            // read the message sent to this client
            String msg = inStream.readUTF();
            System.out.println(msg);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }); 

		sendMessage.start(); 
		readMessage.start(); 

	} 
} 
