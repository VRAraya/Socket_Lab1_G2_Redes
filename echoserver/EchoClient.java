package echoserver;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class EchoClient {
  static int cantDatos = 100;
  static String[] servidores = { "localhost" };
  
  static ArrayList<String> operador = new ArrayList<String>();
  static ArrayList<String> num1 = new ArrayList<String>();
  static ArrayList<String> num2 = new ArrayList<String>();
  static String[] operadores = { "+", "-", "*", "/" };

  public static void main(String[] args) throws IOException {
    Random random = new Random();
    for (int x = 0; x < cantDatos; x++) {
      operador.add(operadores[random.nextInt(4)]);
      num1.add("" + random.nextInt());
      num2.add("" + random.nextInt());
    }
    
    String fromUser;
    int servidorActual = 0;
    int maxServidores = servidores.length;
    
    for (int x = 0; x < cantDatos; x++) {
      fromUser = operador.get(x) + " " + num1.get(x) + " " + num2.get(x);
      HiloClient hc=new HiloClient(servidores[servidorActual], fromUser);
      hc.start();
      
      servidorActual++;
      if (servidorActual == maxServidores) {
        servidorActual = 0;
      }
    }
  }
}