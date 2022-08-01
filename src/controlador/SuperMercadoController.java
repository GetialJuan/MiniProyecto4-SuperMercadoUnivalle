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
import javax.swing.table.DefaultTableModel;
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
    
    ///////////////////////// Ventana Proveedor /////////////////////////////
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
                int fila = ventanaProveedores.getFilaTabla();
                if(fila != -1){
                    if(ventanaProveedores.mensajeEliminarProveedor() == 0){
                        superMercado.eliminarProveedor(fila);
                        ventanaProveedores.setTablaProveedores(superMercado.getProveedores());
                    }
                }else{
                    ventanaProveedores.mensajesEmergentes("SelecEliminar");
                }
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
    
    //////////////////////// VentanaNuevoProveedores /////////////////////////
    class ManejadorDeEventosNuevoProveedor implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("Adicionar")){
                String nombre = ventanaNuevoProveedor.getNombreProducto();
                String precio = ventanaNuevoProveedor.getPrecio();
                if(nombre.equals("")){
                    ventanaNuevoProveedor.mensajesEmergentes("NombreP");
                }else if(precio.equals("")){
                    ventanaNuevoProveedor.mensajesEmergentes("Precio");
                }else{
                    try{
                        Integer.parseInt(precio);
                        DefaultTableModel modeloTbl = ventanaNuevoProveedor.getModeloTabla();
                        boolean repetido = false;
                        boolean n = false;
                        boolean p = false;
                        int filas = modeloTbl.getRowCount();
                        for(int i = 0; i < filas; i++){
                            if(nombre.equals(modeloTbl.getValueAt(i, 0))){
                                n = true;
                            }
                            if(precio.equals(modeloTbl.getValueAt(i, 1))){
                                p = true;
                            }
                            if(n && p){
                                repetido = true;
                            }else{
                                n = false;
                                p = false;
                            }
                        }
                        if(repetido){
                            ventanaNuevoProveedor.mensajesEmergentes("Repetido");
                        }else{
                            ventanaNuevoProveedor.aNadirTablaProductos(nombre, precio);
                        }
                    }catch(NumberFormatException ne){
                        ventanaNuevoProveedor.mensajesEmergentes("NumPrecio");
                    }
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("Eliminar")){
                if(ventanaNuevoProveedor.getFilaTabla() == -1){
                    ventanaNuevoProveedor.mensajesEmergentes("Fila"); 
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
                    ventanaNuevoProveedor.mensajesEmergentes("Nombre"); 
                }
                else if(telefono.equals("")){
                    ventanaNuevoProveedor.mensajesEmergentes("Telefono"); 
                }
                else if(categoria.equals("")){
                    ventanaNuevoProveedor.mensajesEmergentes("Categoria"); 
                }
                else if(ventanaNuevoProveedor.getModeloTabla().getRowCount() == 0){
                    ventanaNuevoProveedor.mensajesEmergentes("SinProducto"); 
                }else{
                    try{
                        Long.parseLong(telefono);
                        DefaultTableModel modeloTbl = ventanaNuevoProveedor.getModeloTabla();
                        ArrayList<HashMap<String,String>> productos = new ArrayList<>();
                        int filas = modeloTbl.getRowCount();
                        for(int i = 0; i < filas; i++){
                            HashMap <String,String> auxMap = new HashMap();
                            auxMap.put("nombre", (String)modeloTbl.getValueAt(i, 0));
                            auxMap.put("precio", (String)modeloTbl.getValueAt(i, 1));
                            auxMap.put("categoria", categoria);
                            productos.add(auxMap);
                        }
                        Proveedor proveedor = new Proveedor(nombre,telefono,categoria,productos);
                        superMercado.agregarProveedor(proveedor);
                        ventanaNuevoProveedor.mensajesEmergentes("Nuevo"); 
                        ventanaNuevoProveedor.dispose();
                        ventanaProveedores.setTablaProveedores(superMercado.
                            getProveedores());
                        ventanaNuevoProveedor.limpiarCampos();
                        ventanaProveedores.show();
                    }catch(NumberFormatException ne){
                        ventanaNuevoProveedor.mensajesEmergentes("NumTelefono"); 
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
