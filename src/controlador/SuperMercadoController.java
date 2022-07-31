/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.SuperMercado;
import vista.VentanaInicio;
import vista.VentanaNuevoCliente;
import vista.VentanaValidacionCliente;
import vista.VentanaVenta;

/**
 *
 * @author Juan
 */
public class SuperMercadoController {
    
    //modelos
    private SuperMercado superMercado;
    
    //ventanas
    VentanaInicio ventanaInicio;
    VentanaValidacionCliente ventanaValidacionCliente;
    VentanaVenta ventanaVenta;
    VentanaNuevoCliente ventanaNuevoCliente;

    public SuperMercadoController() {
        
        superMercado = new SuperMercado();
        
        ventanaInicio = new VentanaInicio();
        ventanaInicio.AgregarListenersBtns(new ManejadorDeEventosMenu());
    }
    
    //ventanaIncio
    class ManejadorDeEventosMenu implements ActionListener {

        @Override
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("clientes")){
                try {
                    ventanaValidacionCliente.show();
                }
                catch (NullPointerException npe){
                    ventanaValidacionCliente = new VentanaValidacionCliente();
                    ventanaValidacionCliente.
                            agregarListenersBtns(new ManejadorDeEventosCliente());
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
    
    //ventanaValidacionCliente
    class ManejadorDeEventosCliente implements ActionListener {

        @Override
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("iniciar venta")){
                if(!superMercado.
                        buscarCliente(ventanaValidacionCliente.getTxtCedula())){
                    JOptionPane.showMessageDialog(null, 
                            "No se encontro el cliente");
                }
                else{
                   if(ventanaVenta != null){
                    ventanaVenta.show();
                    }
                    else{
                        ventanaVenta = new VentanaVenta();
                        ventanaVenta.
                                agregarListenersBtns(new ManejadorDeEventosVenta());
                    }
                    ventanaVenta.setCboxProductos(superMercado.
                            getProductos());
                    ventanaValidacionCliente.dispose(); 
                }
                
            }
            else if(e.getActionCommand().equalsIgnoreCase("nuevo cliente")){
                if(ventanaNuevoCliente != null){
                    ventanaNuevoCliente.show();
                }
                else{
                    ventanaNuevoCliente = new VentanaNuevoCliente();
                    ventanaNuevoCliente.
                            agregarListenersBtns(new ManejadorDeEventosNuevoCliente());
                }
            }
        }
        
        
    }
    
    //ventanaNuevoCliente
    class ManejadorDeEventosNuevoCliente implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("agregar")){
                superMercado.
                        agregarCliente(ventanaNuevoCliente.getTxtNombre(), 
                                ventanaNuevoCliente.getTxtCedula());
                superMercado.setClienteSeleccionadoNuevo();
                if(ventanaVenta != null){
                    ventanaVenta.show();
                }
                else{
                    ventanaVenta = new VentanaVenta();
                    ventanaVenta.
                            agregarListenersBtns(new ManejadorDeEventosVenta());
                }
                ventanaVenta.setCboxProductos(superMercado.
                        getProductos());
                ventanaValidacionCliente.dispose();
            }
        }
        
    }
    
    //ventanaVenta
    class ManejadorDeEventosVenta implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("agregar a venta")){
                int indiceProducto = ventanaVenta.getProductoSeleccionado();
                if(indiceProducto == -1){
                    JOptionPane.showMessageDialog(null, "Seleccione un producto");
                }
                else
                {
                    
                    superMercado.getProductos().get(indiceProducto).
                            reducirUnaUnidad();
                    
                    //se agrega el producto al carrito del cliente
                    int cliente = superMercado.getClienteSeleccionado();
                    
                    superMercado.getClientes().get(cliente).
                            agregarProductoAlCarrito(
                                    superMercado.getProductos().
                                            get(indiceProducto).getNombre(), 
                                    superMercado.getProductos().
                                            get(indiceProducto).getPrecio());
                    
                    //Se actulizan las tablas
                    ventanaVenta.limpiarTablaCarrito();
                    ventanaVenta.setTablaCarrito(superMercado.
                            getClientes().get(cliente).getCarrito());
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
