/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author Juan
 */
public class VentanaMenu extends JFrame{
    
    private ArrayList<JButton> botones;
    private Container contPrincipal;

    public VentanaMenu() {
        setSize(300, 300);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Menu");
        setResizable(false);
        iniciarComponenentes();
    }
    
    private void iniciarComponenentes(){
        
        JButton btnProveedor = new JButton("proveedor");
        btnProveedor.setBounds(11,101,278,61); 
        
        JButton btnCliente = new JButton("cliente");
        btnCliente.setBounds(10,230,280,61); 
        
        JButton btnProductos = new JButton("productos");
        btnProductos.setBounds(10,164,280,64);
        
        botones = new ArrayList<>();
        botones.add(btnCliente);
        botones.add(btnProductos);
        botones.add(btnProveedor);
        
        contPrincipal = getContentPane();
        for(JButton btn : botones){
            contPrincipal.add(btn);
        }
    }
    
    public void AgregarListenersBtns(ActionListener aL){
        for(JButton btn : botones){
            btn.addActionListener(aL);
        }
    }
    
    
    
}
