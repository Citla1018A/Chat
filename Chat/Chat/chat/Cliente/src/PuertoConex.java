/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 *
 * @author Citlalli
 */
public class PuertoConex extends Thread {
    static Socket connection;
    DataOutputStream outStream;
    BufferedReader inStream;
    
    JTextPane pane;
    JTextField field;
    JButton btn;
    JTextPane panell;
    String nombr;
    String IP;
    Integer puerto;
    
    public PuertoConex(JTextPane pane, JTextField field, JButton btn, JTextPane panell, String nombrecito, String ip, Integer puerto) {
        
        this.pane=pane;
        this.field=field;
        this.btn=btn;
        this.panell=panell;
        nombr=nombrecito;
        IP=ip;
        puerto=puerto;
        
        String destination = IP;
        int port = puerto;
        
        try {
            connection = new Socket(destination, port);
        } catch (UnknownHostException ex) {
            error("Servidor desconocido");
        } catch (IOException ex){
            error("Error E/S: al crear el socket");
        }
        try {
            inStream = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            outStream = new DataOutputStream(connection.getOutputStream());
        } catch (IOException e) {
            error("Error E/S: obteniendo el flujo");
        }
        System.out.println("Conectando a: " + destination + " puerto " + 
                port + ".");
    }
    
    public void displayDestinationParameters() {
        InetAddress destAddress = connection.getInetAddress();
        String name = destAddress.getHostName();
        byte[] ipAddress = destAddress.getAddress();
        int port = connection.getPort();
        displayParameters("servidor destino ", name, ipAddress, port);
    }
    
    public void displayLocalParameters() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
            error("Error obteniendo informacion del servidor local");
        }
        String name = localAddress.getHostName();
        byte[] ipAddress = localAddress.getAddress();
        int port = connection.getLocalPort();
        displayParameters("servidor local ", name, ipAddress, port);
    }
    
    public void displayParameters(String s, String name,
            byte[] ipAddress, int port) {
        
        System.out.println(s +  name + ".");
        System.out.println("IP " + s);
        for (int i = 0; i < ipAddress.length; i++) {
            System.out.print((ipAddress[i] + 256) % 256 + ".");
        }
        System.out.println();
        System.out.println("numero de puerto " + s + port + ".");
    }
    
    ActionListener enviar = new ActionListener(){
        
        public void actionPerformed(ActionEvent evt){
            String sendLine = field.getText();
            try {
                outStream.writeBytes(nombr + ": " + sendLine);
                outStream.write(13);
                outStream.write(10);
                outStream.flush();
            } catch (IOException ex) {
                
            }
            
        }
    };
    
    public void chat() {
        
        btn.addActionListener(enviar);
        
        try {
            outStream.writeBytes("-" + nombr);
            outStream.write(13);
            outStream.write(10);
            outStream.flush();
        } catch (IOException ex) {
                
        }
        
        System.out.println(nombr);
        
        boolean finished = false;
        do {
            try {
                char inByte;
                String palabra="";
                while((inByte = (char) inStream.read()) != '\n') {
                    
                    palabra = palabra + inByte; 
                    
                }
                
                if("salir".equals(palabra)){
                    
                    finished = true;
                }
                
                if(palabra.length()>0 && palabra.charAt(1) == '-'){
                   
                   if(palabra.charAt(0) == '*'){
                        panell.setText("Usuarios:");
                   }
                   
                   panell.setText(panell.getText() + "\n" + palabra + "\n");
                }else{
                    pane.setText(pane.getText() + palabra + "\n");
                    field.setText("");
                }

            } catch (IOException e) {
                error("Conexion Finalizada");
            }
        } while (!finished);
    }
    
    public static void shutdown() {
        try {
            connection.close();
        } catch (IOException e) {
            error("Error E/S cerrando");
        }
    }
    
    public static void error(String s) {
        System.out.println(s);
        System.exit(0);
    }
    
    public void run(){
        displayDestinationParameters();
        displayLocalParameters();
        chat();
    }
}

