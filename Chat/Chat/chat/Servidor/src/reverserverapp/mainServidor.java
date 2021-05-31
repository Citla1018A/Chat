/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package reverserverapp;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Citlalli
 */
public class mainServidor {
    
    
    
    public static void main (String args[]){
        try{
            ServerSocket server = new ServerSocket(1234); 
            int localPort = server.getLocalPort();
            System.out.println("Servidor escuchando en puerto: "+localPort+".");
            
            ArrayList<PrintStream> outStream = new ArrayList<>();
            ArrayList<String> user = new ArrayList<>();
            
            Integer i=0;
            
            

            while(true){
            
                Socket conect = server.accept();
                Conexion c = new Conexion(server, conect,i,outStream,user);
                c.start();
                i++;
                user = c.getUser();
                
            }
            
        }catch (IOException ex){
            System.out.println("exepcion : IOExeption .");
        }
        
        
    }
    
}


    class ReverseString{
        String s;
        public ReverseString(String in){
            int len = in.length();
            char outChars[] = new char[len];
            for (int i = 0; i < outChars.length; i++) {
                 outChars[len-1-i]= in.charAt(i);
                
            }
            s= String.valueOf(outChars);
        }
        
        public String getString(){
            return s;
        }
        
        
    }

