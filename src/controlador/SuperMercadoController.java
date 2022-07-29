/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.SuperMercado;
import vista.VentanaCarritoCliente;
import vista.VentanaCliente;
import vista.VentanaMenu;

/**
 *
 * @author Juan
 */
public class SuperMercadoController {
    
    //modelos
    private SuperMercado superMercado;
    
    //ventanas
    VentanaMenu ventanaMenu;
    VentanaCliente ventanaCliente;
    VentanaCarritoCliente ventanaCarritoCliente;

    public SuperMercadoController() {
        
        superMercado = new SuperMercado();
        
        ventanaMenu = new VentanaMenu();
        ventanaMenu.AgregarListenersBtns(new ManejadorDeEventosMenu());
    }
    
    //ventanaMenu
    class ManejadorDeEventosMenu implements ActionListener {

        @Override
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("cliente")){
                try {
                    ventanaCliente.show();
                }
                catch (NullPointerException npe){
                    ventanaCliente = new VentanaCliente();
                    ventanaCliente.
                            setListenerBtnSiguiente(new ManejadorDeEventosCliente());
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("productos")){
                System.out.println("btn porducto");
            }
            else if(e.getActionCommand().equalsIgnoreCase("proveedor")){
                System.out.println("btn proveedor");
            }
        }
        
    }
    
    //ventanaCliente
    class ManejadorDeEventosCliente implements ActionListener {

        @Override
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("siguiente")){
                superMercado.
                        agregarCliente(ventanaCliente.getTxtNombre(), 
                                ventanaCliente.getTxtID());
                
                if(ventanaCarritoCliente != null){
                    ventanaCarritoCliente.show();
                }
                else{
                    ventanaCarritoCliente = new VentanaCarritoCliente();
                    ventanaCarritoCliente.
                            agregarListenersBtns(new ManejadorDeEventosCarritoCliente());
                }
                ventanaCarritoCliente.setTablaProductos(superMercado.
                        getProductos());
                ventanaCliente.dispose();
            }
        }
        
    }
    
    //ventanaCarritoCliente
    class ManejadorDeEventosCarritoCliente implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("+")){
                int indiceProducto = ventanaCarritoCliente.getFilaProductos();
                if(indiceProducto == -1){
                    JOptionPane.showMessageDialog(null, "Seleccione un producto");
                }
                else
                {
                    
                    superMercado.getProductos().get(indiceProducto).
                            reducirUnaUnidad();
                    
                    //se agrega el producto al carrito del cliente
                    superMercado.getClientes().get(0).
                            agregarProductoAlCarrito(
                                    superMercado.getProductos().
                                            get(indiceProducto).getNombre(), 
                                    superMercado.getProductos().
                                            get(indiceProducto).getPrecio());
                    
                    //Se actulizan las tablas
                    ventanaCarritoCliente.limpiarTablas();
                    ventanaCarritoCliente.setTablaCarrito(superMercado.
                            getClientes().get(0).getCarrito());
                    ventanaCarritoCliente.setTablaProductos(superMercado.
                            getProductos());
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("-")){
                System.out.println("btn remover del carrito");
            }
            else if(e.getActionCommand().equalsIgnoreCase("efectuar compra")){
                System.out.println("btn efectuar compra");
            }
        }
        
    }
}
