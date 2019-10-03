package sockets;

//Lado del Cliente

import java.io.*; 
import java.net.*; 

public class Client 
{ 
	public static void main(String args[]) throws UnknownHostException, IOException 
	{ 
    Console console = System.console();

    if (console == null) {
      System.err.println("No hay consola.");
      System.exit(1);
    }

    console.printf("---Bienvenido a la sala de chat--- \n");

    // Obteniendo la ip
    console.printf("Ingrese la ip del servidor de la sala de chat: ");
    String ipServer = console.readLine();

    // Obteniendo el puerto de envío
    System.out.print("Ingrese el puerto: ");
    Integer portServer = Integer.parseInt(console.readLine());

    // Estableciendo la conexión
    Socket s = null; 
    try {
      s = new Socket(ipServer, portServer);
      console.printf("Socket conectado.\n");
    } catch (IOException e) {
      System.err.println("No se puede construir el socket hacia el server"+ipServer+"con puerto "+ portServer);
      System.exit(1);
    }

    // Se declaran los flujos de entrada y salida
    DataInputStream inStream = new DataInputStream(s.getInputStream()); 
    DataOutputStream outStream = new DataOutputStream(s.getOutputStream()); 

    // Se solicita ingresar un nickname para identificación dentro del chat
    console.printf("Ingrese su nickname para entrar al chat: ");
    String nick = console.readLine();
    try { 
      // Escribiendo en el flujo
      outStream.writeUTF(nick); 
    } catch (IOException e) { 
      e.printStackTrace(); 
    }

    console.printf("---CHAT---\n");

    // Hilo para enviar mensajes
    Thread sendMessage = new Thread(new Runnable()  
    { 
        @Override
        public void run() { 
            while (true) { 
                // Lee el mensaje a enviar
                console.printf("Escribe tu mensaje: ");
                String msg = console.readLine(); 
                  
                try { 
                    // Escribiendo en el flujo
                    outStream.writeUTF(msg); 
                } catch (IOException e) { 
                    System.exit(1);
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
            // Leer el mensaje recibido
            String msg = inStream.readUTF();
            console.printf(msg);
          } catch (IOException e) {
            System.exit(1);
          }
        }
      }
    }); 

		sendMessage.start(); 
		readMessage.start(); 

	} 
} 
