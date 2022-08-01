/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.FacturaVenta;
import modelo.Producto;
import modelo.Proveedor;
import modelo.SuperMercado;
import vista.VentanaCompra;
import vista.VentanaInicio;
import vista.VentanaNuevoCliente;
import vista.VentanaNuevoProducto;
import vista.VentanaDatosProveedor;
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
    VentanaDatosProveedor ventanaDProveedor;
    VentanaCompra ventanaCompra;

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
    
    //////////////////////////////VentanaDProducto///////////////
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
                int fila = ventanaProveedores.getFilaTabla();
                if(fila != -1){
                    if(ventanaCompra != null){
                        ventanaCompra.show();
                    }
                    else{
                        ventanaCompra = new VentanaCompra();
                        ventanaCompra.
                                agregarListenersBtns(
                                        new ManejadorDeEventosCompra());
                    }
                    ventanaProveedores.dispose();
                }else{
                    ventanaProveedores.mensajesEmergentes("Comprar");
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("Nuevo Proveedor")){
                try {
                    ventanaDProveedor
                            .setTitulo("Nuevo Proveedor");
                    ventanaDProveedor
                            .show();
                    ventanaProveedores.dispose();
                }
                catch (NullPointerException npe){
                    ventanaProveedores.dispose();
                    ventanaDProveedor
                            = new VentanaDatosProveedor();
                    ventanaDProveedor
                            .
                            agregarListenersBtns(new ManejadorDeEventosNuevoProveedor());
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("Modificar Proveedor")){
                int fila = ventanaProveedores.getFilaTabla();
                if(fila != -1){
                    Proveedor p;
                    ventanaProveedores.dispose();
                     if(ventanaDProveedor
                             != null){
                        ventanaDProveedor
                                .show();
                    }
                    else{
                        ventanaDProveedor
                                = new VentanaDatosProveedor();
                        ventanaDProveedor
                                .
                            agregarListenersBtns(new ManejadorDeEventosNuevoProveedor());
                    }
                    p = superMercado.getProveedor(fila);
                    ventanaDProveedor
                            .limpiarCampos();
                    ventanaDProveedor
                            .setTxtNombre(p.getNombre());
                    ventanaDProveedor
                            .setTxtTelefono(p.getTelefono());
                    ventanaDProveedor
                            .setCbCategoria(p.getCategoria());
                    for(HashMap<String,String> map : p.getProductos()){
                        String nombre = "";
                        String precio = "";
                        for(Map.Entry<String, String> entry : map.entrySet()){
                            if(entry.getKey().equalsIgnoreCase("Nombre")){
                                nombre = entry.getValue();
                            }
                            else if(entry.getKey().equalsIgnoreCase("Precio")){
                                precio = entry.getValue();
                            }                                              
                        }
                        ventanaDProveedor
                                .aNadirTablaProductos(nombre, precio);
                    }
                    ventanaDProveedor
                            .setTitulo("Modificar Proveedor");
                    ventanaDProveedor
                            .show();
                }else{
                    ventanaProveedores.mensajesEmergentes("SelecModificar");
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
    
    //////////////////////// ventanaDProveedor /////////////////////////
    class ManejadorDeEventosNuevoProveedor implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("Adicionar")){
                String nombre = ventanaDProveedor
                        .getTxtNombreProducto();
                String precio = ventanaDProveedor
                        .getTxtPrecio();
                if(nombre.equals("")){
                    ventanaDProveedor
                            .mensajesEmergentes("NombreP");
                }else if(precio.equals("")){
                    ventanaDProveedor
                            .mensajesEmergentes("Precio");
                }else{
                    try{
                        Integer.parseInt(precio);
                        DefaultTableModel modeloTbl = ventanaDProveedor
                                .getModeloTabla();
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
                            ventanaDProveedor
                                    .mensajesEmergentes("Repetido");
                        }else{
                            ventanaDProveedor
                                    .aNadirTablaProductos(nombre, precio);
                            ventanaDProveedor
                                    .limpiarCamposProducto();
                        }
                    }catch(NumberFormatException ne){
                        ventanaDProveedor
                                .mensajesEmergentes("NumPrecio");
                    }
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("Eliminar")){
                if(ventanaDProveedor
                        .getFilaTabla() == -1){
                    ventanaDProveedor
                            .mensajesEmergentes("Fila"); 
                }
                else{
                    int fila = ventanaDProveedor
                            .getFilaTabla();
                    ventanaDProveedor
                            .eliminarProducto(fila);
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("Aceptar")){
                int fila = ventanaProveedores.getFilaTabla();
                String nombre = ventanaDProveedor
                        .getTxtNombre();
                String telefono = ventanaDProveedor
                        .getTxtTelefono();
                String categoria = ventanaDProveedor
                        .getCbCategoria();
                if(nombre.equals("")){
                    ventanaDProveedor
                            .mensajesEmergentes("Nombre"); 
                }
                else if(telefono.equals("")){
                    ventanaDProveedor
                            .mensajesEmergentes("Telefono"); 
                }
                else if(categoria.equals("")){
                    ventanaDProveedor
                            .mensajesEmergentes("Categoria"); 
                }
                else if(ventanaDProveedor
                        .getModeloTabla().getRowCount() == 0){
                    ventanaDProveedor
                            .mensajesEmergentes("SinProducto"); 
                }else{
                    try{
                        Long.parseLong(telefono);
                        DefaultTableModel modeloTbl = ventanaDProveedor
                                .getModeloTabla();
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
                        if(ventanaDProveedor
                                .getLblTitulo().equalsIgnoreCase("Nuevo Proveedor")){
                            System.out.println("entra n");
                            superMercado.agregarProveedor(proveedor);
                            ventanaDProveedor
                                    .mensajesEmergentes("Nuevo"); 
                        }
                        else if(ventanaDProveedor
                                .getLblTitulo().equalsIgnoreCase("Modificar Proveedor")){
                            System.out.println("entra m");
                            superMercado.modificarProveedor(fila,proveedor);
                            ventanaDProveedor
                                    .mensajesEmergentes("Modificado"); 
                        }                      
                        ventanaDProveedor
                                .dispose();
                        ventanaProveedores.setTablaProveedores(superMercado.
                            getProveedores());
                        ventanaDProveedor
                                .limpiarCampos();
                        ventanaProveedores.show();
                    }catch(NumberFormatException ne){
                        ventanaDProveedor
                                .mensajesEmergentes("NumTelefono"); 
                    }
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("Cancelar")){
                ventanaDProveedor
                        .dispose();
                ventanaDProveedor
                        .limpiarCampos();
                ventanaDProveedor
                        .limpiarTablaProductos();
                ventanaProveedores.setTablaProveedores(superMercado.
                        getProveedores());
                ventanaProveedores.show();
            }
            
        }   
    }
    
    class ManejadorDeEventosCompra implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("Agregar a Compra")){
                System.out.println("btn Agregar");
            }
            else if(e.getActionCommand().equalsIgnoreCase("Cancelar Compra")){
                ventanaCompra.dispose();
                ventanaProveedores.show();
            }
            else if(e.getActionCommand().equalsIgnoreCase("Eliminar item seleccionado")){
                System.out.println("Btn Eliminar");
            }
            else if(e.getActionCommand().equalsIgnoreCase("Finalizar")){
                System.out.println("Btn Finalizar");
            }
        }      
    }
}
