
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Citlalli
 */
public class Conexion extends JDialog {
    
    private JTextField boxUser;
    private JTextField boxIp;
    private JTextField boxPuerto;
    
    JTextPane p;
    JTextField f;
    JButton b;
    JTextPane u;
    
    ActionListener acept = new ActionListener(){
        
        public void actionPerformed(ActionEvent evt){
            setVisible(false);
            String name = boxUser.getText();
            String IP = boxIp.getText();
            int puerto = Integer.parseInt(boxPuerto.getText());
            new PuertoConex(p,f,b,u,name,IP,puerto).start();
            
        }
    };
    
    ActionListener cancel = new ActionListener(){
        
        public void actionPerformed(ActionEvent evt){
            setVisible(false);
        }
    };
    
    
    public Conexion(JFrame f, JTextPane p, JTextField f1, JButton b, JTextPane u){
        
        super(f,"Conectarse a:", true);
        super.setLayout(null);
        super.setSize(400, 200);
        
        this.p=p;
        this.f=f1;
        this.b=b;
        this.u=u;
        
        
        JLabel txtUser = new JLabel("Nombre de Usuario: ");
        txtUser.setBounds(10, 10, 150, 20);
        super.add(txtUser);
        
        boxUser = new JTextField("Anonimo");
        boxUser.setBounds(170, 10, 150, 20);
        super.add(boxUser);
        
        JLabel txtIp = new JLabel("Numero IP: ");
        txtIp.setBounds(10, 40, 150, 20);
        super.add(txtIp);
        
        boxIp = new JTextField("127.0.0.1");
        boxIp.setBounds(170, 40, 150, 20);
        super.add(boxIp);
        
        JLabel txtPuerto = new JLabel("Numero de puerto: ");
        txtPuerto.setBounds(10, 70, 150, 20);
        super.add(txtPuerto);
        
        boxPuerto = new JTextField("1234");
        boxPuerto.setBounds(170, 70, 150, 20);
        super.add(boxPuerto);
        
        JButton aceptar = new JButton("Aceptar");
        aceptar.setBounds(230, 120, 120, 20);
        aceptar.addActionListener(acept);
        super.add(aceptar);
        
        JButton cancelar = new JButton("Cancelar");
        cancelar.setBounds(50, 120, 120, 20);
        cancelar.addActionListener(cancel);
        super.add(cancelar);
        
        super.setVisible(true);
        
    }
    
}
