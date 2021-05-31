/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package reverserverapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Citlalli
 */
public class Conexion extends Thread {
    private static Socket conect;
    private static ArrayList<PrintStream> outStream = new ArrayList<>();
    private static ArrayList<String> user = new ArrayList<>();
    private boolean finished;
    private ServerSocket server;
    BufferedReader inStream;
    private static Integer num;

    public ArrayList<String> getUser() {
        return user;
    }

    public void setUser(ArrayList<String> user) {
        this.user = user;
    }
    
    public Conexion(ServerSocket server, Socket conect, Integer i, ArrayList<PrintStream> outStream, ArrayList<String> n) throws IOException{
        this.server = server;
        this.conect=conect;
        num=i;
        this.outStream=outStream;
        user = n;
        
        
        String destName = conect.getInetAddress().getHostName();
        int destPort= conect.getPort();
        System.out.println("Conexion a "+destName+" en puerto "+destPort+".");

        inStream = new BufferedReader(new InputStreamReader(conect.getInputStream()));
        outStream.add(new PrintStream(conect.getOutputStream()));
        finished = false;
    }
    
    @Override
    public void run(){
        int fr=0;
        do {
            try {
                String inLine = inStream.readLine(); 
                if("salir".equals(inLine)){
                    finished = true;
                }
                if(fr==0){
                    user.add(inLine);
                    fr++;
                    char a = ' ';
                    for (int i = 0; i < user.size(); i++) {
                        for (int j = 0; j < outStream.size(); j++) {
                            if(i==0){
                                a='*';
                            }
                            outStream.get(j).println(a+user.get(i));
                            outStream.get(j).write(10);
                            outStream.get(j).flush();
                        }
                        a=' ';
                    }
                }else{
                    if(inLine!=null){
                        for (int j = 0; j < outStream.size(); j++) {
                            outStream.get(j).println(inLine);
                            outStream.get(j).write(10);
                            outStream.get(j).flush();
                        }
                    }
                }
                
            } catch (IOException ex) {}
            
        } while (!finished);
        try {
            cerrar();
        } catch (IOException ex) {

        }
    }
    
    public static void cerrar() throws IOException{
        outStream.get(num).close();
        user.remove(num);
        conect.close();
        System.out.println("Desconectado");
    }
    
}
