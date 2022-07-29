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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Juan
 */
public class VentanaCarritoCliente extends JFrame{
    //tablas
    private JTable tblProductos;
    private DefaultTableModel mdTProductos;
    private JScrollPane spProductos;
    
    private JTable tblCarrito;
    private DefaultTableModel mdTCarrito;
    private JScrollPane spCarrito;
    
    //botones
    private ArrayList<JButton> botones;
    
    private Container contPrincipal;

    public VentanaCarritoCliente() {
        setSize(500, 500);
        setVisible(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Carrito Compras");
        setResizable(false);
        iniciarComponenentes();
    }
    
    private void iniciarComponenentes() {
        //botones
        JButton agregarAlCarrito = new JButton("+");
        agregarAlCarrito.setBounds(10, 360, 100, 20);
        
        JButton removerDelCarrito = new JButton("-");
        removerDelCarrito.setBounds(260, 360, 100, 20);
        
        JButton efectuarCompra = new JButton("Efectuar Compra");
        efectuarCompra.setBounds(300, 10, 200, 20);
        
        botones = new ArrayList<>();
        botones.add(efectuarCompra);
        botones.add(removerDelCarrito);
        botones.add(agregarAlCarrito);
        
        //tablas
        mdTProductos = new DefaultTableModel();
        mdTProductos.addColumn("Nombre");
        mdTProductos.addColumn("Precio");
        mdTProductos.addColumn("Cantidad");
        tblProductos = new JTable();
        tblProductos.setModel(mdTProductos);
        spProductos = new JScrollPane(tblProductos);
        spProductos.setBounds(10, 50, 200, 300);
        
        mdTCarrito = new DefaultTableModel();
        mdTCarrito.addColumn("Nombre");
        mdTCarrito.addColumn("Precio");
        mdTCarrito.addColumn("Cantidad");
        tblCarrito = new JTable();
        tblCarrito.setModel(mdTCarrito);
        spCarrito = new JScrollPane(tblCarrito);
        spCarrito.setBounds(260, 50, 200, 300);
        
        
        
        contPrincipal = getContentPane();
        contPrincipal.setLayout(null);
        for(JButton btn : botones){
            contPrincipal.add(btn);
        }
        contPrincipal.add(spProductos);
        contPrincipal.add(spCarrito);
    }
    
    public void agregarListenersBtns(ActionListener aL){
        for(JButton btn : botones){
            btn.addActionListener(aL);
        }
    }
}
