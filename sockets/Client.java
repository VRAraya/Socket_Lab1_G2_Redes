package sockets;

//Lado del Cliente

import java.io.*; 
import java.net.*; 
import java.util.Scanner; 

public class Client 
{ 
	final static int portServer = 1234; 

	public static void main(String args[]) throws UnknownHostException, IOException 
	{ 
		Scanner scn = new Scanner(System.in); 
		
		// Obteniendo la ip (en este ejemplo, se obtiene la ip de localhost - CAMBIAR -)
		InetAddress ipServer = InetAddress.getByName("localhost"); 
		
		// Estableciendo la conexión
		Socket s = new Socket(ipServer, portServer); 
		
		// Se declaran los flujos de entrada y salida
		DataInputStream inStream = new DataInputStream(s.getInputStream()); 
		DataOutputStream outStream = new DataOutputStream(s.getOutputStream()); 

		// Hilo para enviar mensajes
		Thread sendMessage = new Thread(new Runnable() 
		{ 
			@Override
			public void run() { 
				while (true) { 

					// Leer el mensaje a entregar por consola
					String message = scn.nextLine(); 
					
					try { 
						// escribe en el flujo de salida el mensaje
						outStream.writeUTF(message); 
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
						// Lee el mensaje enviado a este cliente (Se muestra por consola)
						String message = inStream.readUTF(); 
						System.out.println(message); 
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
