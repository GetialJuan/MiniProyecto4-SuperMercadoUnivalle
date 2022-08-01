/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Cliente;
import modelo.FacturaVenta;
import modelo.SuperMercado;
import vista.VentanaInicio;
import vista.VentanaNuevoCliente;
import vista.VentanaNuevoProveedor;
import vista.VentanaProductos;
import vista.VentanaProveedores;
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
    
    VentanaProductos ventanaProductos;
    VentanaProveedores ventanaProveedores;
    VentanaNuevoProveedor ventanaNuevoProveedor;

    public SuperMercadoController() {
        
        superMercado = new SuperMercado();
        
        ventanaInicio = new VentanaInicio();
        ventanaInicio.agregarListenersBtns(new ManejadorDeEventosMenu());
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
                if(ventanaProductos != null){
                    ventanaProductos.show();
                }
                else{
                    ventanaProductos = new VentanaProductos();
                    ventanaProductos.
                            agregarListenersBtns(new ManejadorDeEventosProductos());
                }
                ventanaProductos.setTablaProductos(superMercado.
                            getProductos());
            }
            else if(e.getActionCommand().equalsIgnoreCase("proveedores")){
                try {
                    ventanaProveedores.show();
                    ventanaInicio.dispose();
                }
                catch (NullPointerException npe){
                    ventanaInicio.dispose();
                    ventanaProveedores = new VentanaProveedores();
                    ventanaProveedores.
                            agregarListenersBtns(new ManejadorDeEventosProveedores());
                }
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
                    //se guarda una copia del estado actual de los datos
                    superMercado.setCopias();
                
                    //se abre ventanVenta
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
            else if(e.getActionCommand().equalsIgnoreCase("regresar")){
                ventanaValidacionCliente.dispose();
            }
        }
        
        
    }
    
    //ventanaNuevoCliente
    class ManejadorDeEventosNuevoCliente implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("agregar")){
                
                //se agrega al cliente
                superMercado.
                        agregarCliente(ventanaNuevoCliente.getTxtNombre(), 
                                ventanaNuevoCliente.getTxtCedula());
                
                //se guarda una copia del estado actual de los datos
                superMercado.setCopias();
                
                superMercado.setClienteSeleccionadoNuevo();
                
                //se abre la ventanaVenta
                if(ventanaVenta != null){
                    ventanaVenta.show();
                }
                else{
                    ventanaVenta = new VentanaVenta();
                    ventanaVenta.
                            agregarListenersBtns(new ManejadorDeEventosVenta());
                }
                //se establcen los producto en la ventanVenta
                ventanaVenta.setCboxProductos(superMercado.
                        getProductos());
                ventanaValidacionCliente.dispose();
            }
            else if(e.getActionCommand().equalsIgnoreCase("cancelar")){
                ventanaNuevoCliente.dispose();
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
                    if(superMercado.getProductos().get(indiceProducto).
                            reducirUnaUnidad()){
                        //se agrega el producto al carrito del cliente
                        int cliente = superMercado.getClienteSeleccionado();

                        superMercado.getClientes().get(cliente).
                                agregarProductoAlCarrito(
                                        superMercado.getProductos().
                                                get(indiceProducto).getNombre(), 
                                        superMercado.getProductos().
                                                get(indiceProducto).getPrecio());

                        //Se actulizan las tablas y datos de la ventana
                        ventanaVenta.limpiarTablaCarrito();
                        ventanaVenta.setTablaCarrito(superMercado.
                                getClientes().get(cliente).getCarrito());
                        ventanaVenta.setTotal(superMercado.
                                getTotalCarritoCliente());
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Producto agotado");
                    }
                    
                    
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("cancelar venta")){
                superMercado.restablecerDatos();
                ventanaVenta.dispose();
                superMercado.cancelarVenta(ventanaVenta.getProductosInfo());
                ventanaVenta.setTotal(0);
                ventanaVenta.limpiarTablaCarrito();
                ventanaVenta.dispose();
                ventanaInicio.show();
            }
            else if(e.getActionCommand().equalsIgnoreCase("eliminar item seleccionado")){
                int itemSeleccionado = ventanaVenta.getFilaTblCarrito();
                if(itemSeleccionado == -1){
                    JOptionPane.showMessageDialog(null, "Seleccione un item");
                }
                else{
                    int cliente = superMercado.getClienteSeleccionado();
                    ArrayList<String> producto = ventanaVenta.
                            getProductoInfo(itemSeleccionado);
                    //se elimina el prodeucto del carrito
                    superMercado.getClientes().get(cliente).eliminarProducto(itemSeleccionado);
                    
                    //se reestablce 3el prducto
                    superMercado.restablecerProducto(producto);
                    
                    //Se actulizan las tablas y datos de la ventana
                    ventanaVenta.limpiarTablaCarrito();
                    ventanaVenta.setTablaCarrito(superMercado.
                            getClientes().get(cliente).getCarrito());
                    ventanaVenta.setTotal(superMercado.
                                getTotalCarritoCliente());
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("finalizar")){
                JOptionPane.showMessageDialog(null, "Se registro la venta");
                
                Cliente cliente = superMercado.getClientes().
                        get(superMercado.getClienteSeleccionado());
                
                //se agrega la venta
                superMercado.agregarVenta(new FacturaVenta(
                        cliente.getNombre(), cliente.getiD(), 
                        cliente.getCarrito(), 
                        superMercado.getTotalCarritoCliente()));
                
                //Se cierrra la ventana
                ventanaVenta.setTotal(0);
                ventanaVenta.limpiarTablaCarrito();
                ventanaVenta.dispose();
                ventanaInicio.show();
            }
        }
        
    }
    
    ///////////////////////////////VentanaProductos///////////////////////////
    class ManejadorDeEventosProductos implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("regresar")){
                ventanaProductos.limpiarTablaProductos();
                ventanaProductos.dispose();
                ventanaInicio.show();
            }
            else if(e.getActionCommand().equalsIgnoreCase("eliminar producto")){
                int productoSeleccionado = ventanaProductos.
                        getProductoSeleccionado();
                if(productoSeleccionado == -1){
                    JOptionPane.showMessageDialog(null, "Seleccione un "
                            + "producto");
                }
                else{
                    superMercado.getProductos().remove(productoSeleccionado);
                    ventanaProductos.limpiarTablaProductos();
                    ventanaProductos.setTablaProductos(superMercado.
                            getProductos());
                }
            }
        }
        
    }
    //ventanaProveedor
    class ManejadorDeEventosProveedores implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("Comprar Producto")){
                System.out.println("Btn ComprarProducto");
            }
            else if(e.getActionCommand().equalsIgnoreCase("Nuevo Proveedor")){
                try {
                    ventanaNuevoProveedor.show();
                    ventanaProveedores.dispose();
                }
                catch (NullPointerException npe){
                    ventanaProveedores.dispose();
                    ventanaNuevoProveedor = new VentanaNuevoProveedor();
                    ventanaNuevoProveedor.
                            agregarListenersBtns(new ManejadorDeEventosNuevoProveedor());
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("Eliminar Proveedor")){
                System.out.println("Btn EliminarProveedor");
            }
            else if(e.getActionCommand().equalsIgnoreCase("Regresar")){
                try {
                    ventanaProveedores.dispose();
                    ventanaInicio.show();
                }
                catch (NullPointerException npe){
                    ventanaProveedores.dispose();
                    ventanaInicio = new VentanaInicio();
                    ventanaInicio.
                            agregarListenersBtns(new ManejadorDeEventosProveedores());
                }
            }
        }
        
    }
    
    class ManejadorDeEventosNuevoProveedor implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("Agregar")){
                System.out.println("Btn Agregar");
            }
            else if(e.getActionCommand().equalsIgnoreCase("Cancelar")){
                ventanaNuevoProveedor.dispose();
                ventanaProveedores.show();
            }
        }
        
    }
}
