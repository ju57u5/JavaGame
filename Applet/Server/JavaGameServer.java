import java.io.File;
import java.io.*;
import java.net.*;


public class JavaGameServer {
  ServerSocket serverSocket;
  
  public static void main(String[] args) {
    System.out.println("Gestartet!");
    new JavaGameServer();
  } // end of main
  
  public JavaGameServer() {
    try {
      serverSocket = new ServerSocket(8080);
    } catch(Exception e) {
      e.printStackTrace();
    } 
    
    while (true) { 
      try {
        Socket socket = serverSocket.accept();
        DataInputStream dIn = new DataInputStream(socket.getInputStream());
        System.out.println("Connected");
        int score = dIn.readInt();
        String name = dIn.readUTF();
        
        for (int c=1;c<=10;c++) {
          BufferedReader brver = new BufferedReader(new FileReader(new File(c+".txt")));
          String currentscore = brver.readLine();
          int currentIScore = Integer.parseInt(currentscore);
          brver.close();
          System.out.println("Aktueller Score: "+currentIScore+" Platz:"+c);
          if (score > currentIScore) {
            for (int cou=10;cou>c;cou-- ) {
              
              BufferedReader br = new BufferedReader(new FileReader(new File((cou-1)+".txt")));
              int altIScore = Integer.parseInt(br.readLine());
              String altname = br.readLine();
              brver.close();
              
              PrintWriter writer = new PrintWriter((cou)+".txt", "UTF-8");
              writer.println(""+altIScore);
              writer.println(altname);
              writer.close();
              
            } // end of for
            
            System.out.println("Neuer Score!!!!!!!!!:"+score+" Auf Platz:"+c);
            PrintWriter writer = new PrintWriter(c+".txt", "UTF-8");
            writer.println(""+score);
            writer.println(name);
            writer.close();
            
            
            break;
          } // end of if
          
        } // end of for
        
        
      } catch(Exception e) {
        e.printStackTrace();
      } 
    } // end of while
    
  }  
} // end of class JavaGameServer
