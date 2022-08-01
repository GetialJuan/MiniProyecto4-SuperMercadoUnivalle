/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import modelo.Cliente;
import modelo.FacturaVenta;
import modelo.Producto;
import modelo.Proveedor;
import modelo.SuperMercado;
import vista.VentanaInicio;
import vista.VentanaNuevoCliente;
import vista.VentanaNuevoProducto;
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
    VentanaNuevoProducto ventanaNuevoProducto;
    
    VentanaProveedores ventanaProveedores;
    VentanaNuevoProveedor ventanaNuevoProveedor;

    public SuperMercadoController() {
        
        superMercado = new SuperMercado();
        
        ventanaInicio = new VentanaInicio();
        ventanaInicio.agregarListenersBtns(new ManejadorDeEventosMenu());
    }
    
    //ventanaInicio
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
                if(ventanaProveedores != null) {
                    ventanaProveedores.show();
                }
                else{
                    ventanaProveedores = new VentanaProveedores();
                    ventanaProveedores.
                            agregarListenersBtns(new ManejadorDeEventosProveedores());
                }
                ventanaProveedores.setTablaProveedores(superMercado.
                        getProveedores());
            }
            ventanaInicio.dispose();
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
            else if(e.getActionCommand().equalsIgnoreCase("nuevo producto")){
                if(ventanaNuevoProducto != null){
                    ventanaNuevoProducto.show();
                }
                else{
                    ventanaNuevoProducto = new VentanaNuevoProducto();
                    ventanaNuevoProducto.
                            agregarListenersBtns(
                                    new ManejadorDeEventosNuevoProducto());
                }
                ventanaProductos.dispose();
            }
        }
        
    }
    
    //////////////////////////////VentanaNuevoProducto///////////////
    class ManejadorDeEventosNuevoProducto implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("agregar")){
                //se añade el producto nuevo
                superMercado.getProductos().add(new Producto(
                        ventanaNuevoProducto.getTxtNombre(), 0,
                Integer.parseInt(ventanaNuevoProducto.getTxtPresio())));
                
                //se cierra y abre ventanas
                ventanaNuevoProducto.dispose();
                ventanaProductos.limpiarTablaProductos();
                ventanaProductos.setTablaProductos(
                superMercado.getProductos());
            }
            else if(e.getActionCommand().equalsIgnoreCase("cancelar")){
                ventanaNuevoProducto.dispose();
            }
            ventanaProductos.show();
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
            if(e.getActionCommand().equalsIgnoreCase("Adicionar")){
                String nombre = ventanaNuevoProveedor.getNombreProducto();
                String precio = ventanaNuevoProveedor.getPrecio();
                if(nombre.equals("")){
                    JOptionPane.showMessageDialog
                            (ventanaNuevoProveedor, "Introduzca un nombre");
                }else if(precio.equals("")){
                    JOptionPane.showMessageDialog
                            (ventanaNuevoProveedor, "Introduzca un precio");
                }else{
                    try{
                        Integer.parseInt(precio);
                        if(ventanaNuevoProveedor.verificarRepetido(nombre,precio)){
                            JOptionPane.showMessageDialog
                            (ventanaNuevoProveedor, "Ya existe un producto con ese precio y nombre");
                        }else{
                            ventanaNuevoProveedor.aNadirTablaProductos(nombre, precio);
                        }
                    }catch(NumberFormatException ne){
                        JOptionPane.showMessageDialog
                            (ventanaNuevoProveedor, "Introduzca un número en precio");
                    }
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("Eliminar")){
                if(ventanaNuevoProveedor.getFilaTabla() == -1){
                    JOptionPane.showMessageDialog
                            (ventanaNuevoProveedor, "Seleccione una fila");
                }
                else{
                    int fila = ventanaNuevoProveedor.getFilaTabla();
                    ventanaNuevoProveedor.eliminarProducto(fila);
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("Agregar")){
                String nombre = ventanaNuevoProveedor.getNombre();
                String telefono = ventanaNuevoProveedor.getTelefono();
                String categoria = ventanaNuevoProveedor.getCategoria();
                if(nombre.equals("")){
                    JOptionPane.showMessageDialog
                            (ventanaNuevoProveedor, "Digite un nombre al proveedor");
                }
                else if(telefono.equals("")){
                    JOptionPane.showMessageDialog
                            (ventanaNuevoProveedor, "Digite un teléfono al proveedor");
                }
                else if(categoria.equals("")){
                    JOptionPane.showMessageDialog
                            (ventanaNuevoProveedor, "Seleccione una categoría para el proveedor");
                }
                else if(ventanaNuevoProveedor.tablaVacia()){
                    JOptionPane.showMessageDialog
                            (ventanaNuevoProveedor, "Introduzca al menos un producto");
                }else{
                    try{
                        Long.parseLong(telefono);
                        ArrayList<HashMap<String,String>> productos = 
                                ventanaNuevoProveedor.getProductosTabla();
                        Proveedor proveedor = new Proveedor(nombre,telefono,categoria,productos);
                        superMercado.agregarProveedor(proveedor);
                        JOptionPane.showMessageDialog(ventanaNuevoProveedor, "Se ha añadido el nuevo proveedor");
                        ventanaNuevoProveedor.dispose();
                        ventanaProveedores.setTablaProveedores(superMercado.
                            getProveedores());
                        ventanaNuevoProveedor.limpiarCampos();
                        ventanaProveedores.show();
                    }catch(NumberFormatException ne){
                        JOptionPane.showMessageDialog
                            (ventanaNuevoProveedor, "Introduzca un número en Teléfono");
                    }
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("Cancelar")){
                ventanaNuevoProveedor.dispose();
                ventanaNuevoProveedor.limpiarCampos();
                ventanaProveedores.setTablaProveedores(superMercado.
                        getProveedores());
                ventanaProveedores.show();
            }
            
        }
        
    }
}
