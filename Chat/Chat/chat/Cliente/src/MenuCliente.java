
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
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
public class MenuCliente extends JPanel {

    private JFrame f;
    static ButtonGroup grupoBotones;
    JPanel p = new JPanel();
    JTextPane conversacion;
    JTextPane usuarios;
    JTextField texto;
    JButton enviar;
    
    static class TipoMenu{
      TipoMenu(int i){}  
    };
    
    static final TipoMenu mi = new TipoMenu(1);
    static final TipoMenu cb = new TipoMenu(2);
    static final TipoMenu rb = new TipoMenu(3);
    
    
    ActionListener connect = new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            new Conexion(f,conversacion,texto,enviar,usuarios);
        }
    };
    
    ActionListener disconnect = new ActionListener(){  
        public void actionPerformed(ActionEvent evt){
            PuertoConex.shutdown();
        }
    };
    
    
    public Object Archivo[][] = {
        {"Archivo", new Character('A')},
        {"Conectarse",mi, new Character('N'),connect},
        {"Desconectarse",mi, new Character('b'),disconnect},
    };
    
    
    
    
    public Object barraMenu[] = {Archivo};  
    
    static public JMenuBar creaMenuBarra( Object barraMenuDato[] ){
        JMenuBar barraMenu = new JMenuBar();
        for(int i=0; i<barraMenuDato.length;i++){
            barraMenu.add( creaMenu( (Object[][])barraMenuDato[i]) );
        }
        return (barraMenu);
    }
    
    static public JMenu creaMenu( Object[][] menuDato){
        JMenu menu = new JMenu();
        menu.setText( (String)menuDato[0][0] );
        menu.setMnemonic( ((Character)menuDato[0][1]).charValue() );
        grupoBotones = new ButtonGroup();
        for (int i = 1; i < menuDato.length; i++) {
            if(menuDato[i][0] == null){
                menu.add( new JSeparator() );
            }else{
                menu.add( creaMenuItem( menuDato[i] ) );
            }
        }
        return (menu);
    }
    
    static public JMenuItem creaMenuItem( Object[] dato ){
        JMenuItem m = null;
        TipoMenu tipo = (TipoMenu)dato[1];
        
        if(tipo == mi){
            m=new JMenuItem();
        }else if(tipo == cb){
            m=new JCheckBoxMenuItem();
        }else if(tipo == rb){
            m= new JRadioButtonMenuItem();
            grupoBotones.add(m);
        }
        m.setText((String)dato[0]);
        m.setMnemonic(((Character)dato[2]).charValue());
        m.addActionListener((ActionListener)dato[3]);
        return (m);
    }
    
    public MenuCliente(JFrame f1){
        f=f1;
        setLayout( new BorderLayout());
        add( creaMenuBarra( barraMenu ),BorderLayout.NORTH);
        p.setLayout(null);
        
        add(p);
        f.setResizable(false);
        
        JPanel pane = new JPanel();
        pane.setSize(500, 500);
        pane.setBounds(20, 30, 400, 400);
        pane.setLayout(null);
        pane.setPreferredSize(new Dimension(300,1000));       
        
        JScrollPane scroll = new JScrollPane(pane,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(20, 30, 400, 400);
        
        conversacion = new JTextPane();
        conversacion.setBounds(0, 0, 400, 1000);
        pane.add(conversacion);
        
        f.getContentPane().add(scroll);
        
        usuarios = new JTextPane();
        usuarios.setBounds(430, 30, 100, 400);
        usuarios.setText("Usuarios:");
        f.add(usuarios);

        texto = new JTextField();
        texto.setBounds(20, 450, 300, 30);
        f.add(texto);

        enviar = new JButton("ENVIAR :");
        enviar.setBounds(330, 450, 100, 30);
        f.add(enviar);
        
    }
    
}
