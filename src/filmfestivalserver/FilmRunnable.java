/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filmfestivalserver;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Zdenek
 */
public class FilmRunnable implements Runnable {

    protected Socket clientSocket = null;
    protected ServerSocket serverSocket = null;
              ServerDAO dbCon = new ServerDAO();
    
    public FilmRunnable(Socket clientSocket,ServerSocket serverSocket) {
        
        this.serverSocket = serverSocket;
        this.clientSocket = clientSocket;
        System.out.println("Klient se připojil z adresy " + clientSocket.getInetAddress().getHostAddress() + ".");

    }

    public void run() {

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
           // PrintWriter os = new PrintWriter(clientSocket.getOutputStream(), true);
            PrintStream os = new PrintStream(clientSocket.getOutputStream(), true);
            
            while (true) {
            String text = in.readLine();

            System.out.println(text);

            if (text.equals("show")) {
                dbCon.getFestProgram(os);
            }
            if (text.equals("add")) {
                String text1 = in.readLine();
                String text2 = in.readLine();
                String text3 = in.readLine();
                String text4 = in.readLine();

                dbCon.addMovie(text1, text2, text3, text4);

                
            }
            if (text.equals("update")) {

                String text1 = in.readLine();
                String text2 = in.readLine();
                String text3 = in.readLine();
                String text4 = in.readLine();
                String text5 = in.readLine();

                dbCon.updateMovie(text1, text2, text3, text4, text5);
               
                
            }

            if (text.equals("delete")) {

                String text1 = in.readLine();

                dbCon.deleteMovie(text1); 
                
               
            }

            if (text.equals("quit")) {

                
                //serverSocket.close();
                clientSocket.close();
                System.out.println("client quit");
                break;
            }}
           
        } catch (IOException e) {
            e.printStackTrace();
            try {
                serverSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(FilmRunnable.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(FilmRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
