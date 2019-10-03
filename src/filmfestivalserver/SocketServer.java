/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filmfestivalserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * establish new connection thread to client
 * 
 * @author admin
 */
public class SocketServer {
    int portNum = 1234;
    ServerSocket serverSocket = null;
    Scanner sc = new Scanner(System.in);
    
    public void runServer() {
        
        try {
            serverSocket = new ServerSocket(portNum);
            System.out.println("Server has started successfully");
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        while(true){
            try {
                Socket clientSocket = serverSocket.accept();
                new Thread(new FilmRunnable(clientSocket,serverSocket)).start();
                if (sc.nextLine().equals("quit")){
                System.exit(0);
                }
                
            } catch (IOException e) {
            System.out.println(e.getMessage());  
                              
            }
        }
        
    }
}
