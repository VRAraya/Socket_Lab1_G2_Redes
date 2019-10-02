package sockets;

import java.io.*; 
import java.util.*; 
import java.net.*; 

// Clase Manejador de cliente 
class ClientHandler implements Runnable  
{ 
    Scanner scn = new Scanner(System.in); 
    private String name; 
    final DataInputStream inStream; 
    final DataOutputStream outStream; 
    Socket s; 
    boolean isloggedin; 
      
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
  
        String received; 
        while (true)  
        { 
            try
            { 
                // Recibe el string
                received = inStream.readUTF(); 
                  
                System.out.println(received); 
                  
                if(received.equals("Salir")){ 
                    this.isloggedin=false; 
                    this.s.close(); 
                    break; 
                } 
                  
                // Rompe el string en dos partes, mensaje y receptor
                StringTokenizer st = new StringTokenizer(received, "#"); 
                String MsgToSend = st.nextToken(); 
                String recipient = st.nextToken(); 
  
                // Buscar el receptor dentro de los usuarios activos
                // ar es el vector donde se guardan los usuarios activos
                for (ClientHandler mc : Server.ar)  
                { 
                    // si el receptor es encontrado y está conectado, se escribe en su flujo de salida
                    if (mc.name.equals(recipient) && mc.isloggedin==true)  
                    { 
                        mc.outStream.writeUTF(this.name+" : "+MsgToSend); 
                        break; 
                    } 
                } 
            } catch (IOException e) { 
                  
                e.printStackTrace(); 
            } 
              
        } 
        try
        { 
            // Cerrando los recursos
            this.inStream.close(); 
            this.outStream.close(); 
              
        }catch(IOException e){ 
            e.printStackTrace(); 
        } 
    } 
} 
