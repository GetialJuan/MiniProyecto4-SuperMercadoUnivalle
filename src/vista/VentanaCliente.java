/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.Container;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

/**
 *
 * @author Juan
 */
public class VentanaCliente extends JFrame{
    
    private Container contPrincipal;
    private JTextField txtID;
    private JTextField txtNombre;
    private JButton btnSiguiente;

    public VentanaCliente() {
        setSize(400, 400);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Cliente");
        setResizable(false);
        iniciarComponenentes();
    }
    
    private void iniciarComponenentes(){
        
        txtID = new JTextField("ID");
        txtID.setBounds(10, 10, 200, 20);
        
        txtNombre = new JTextField("Nombre");
        txtNombre.setBounds(10, 40, 200, 20);
        
        btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setBounds(10, 70, 200, 40);
        
        contPrincipal = getContentPane();
        contPrincipal.setLayout(null);
        contPrincipal.add(txtID);
        contPrincipal.add(txtNombre);
        contPrincipal.add(btnSiguiente);
    }
    
    public void setListenerBtnSiguiente(ActionListener aL){
        btnSiguiente.addActionListener(aL);
    }
    
    public String getTxtNombre(){
        return txtNombre.getText();
    }
    
    public String getTxtID(){
        return txtID.getText();
    }
}
