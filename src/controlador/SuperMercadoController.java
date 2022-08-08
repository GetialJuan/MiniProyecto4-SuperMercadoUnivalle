
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import modelo.SuperMercado;
import vista.VentanaClientes;
import vista.VentanaCompra;
import vista.VentanaDatosCliente;
import vista.VentanaDatosProveedor;
import vista.VentanaInicio;
import vista.VentanaDatosProducto;
import vista.VentanaProductos;
import vista.VentanaProveedores;
import vista.VentanaRegistro;
import vista.VentanaValidacionCliente;
import vista.VentanaVenta;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.Factura;
import modelo.Producto;
import modelo.Proveedor;

/**
 * MiniProyecto 4 - SuperMercado Univalle
 * @author Juan Sebastian Getial Getial <202124644>
 * @author Mauricio Muñoz Gutierrez <202123687>
 * @author Santiago Torres Carvajal <2140010>
 * @profesor Luis Yovany Romo Portilla
 * Clase controladora
 */
public class SuperMercadoController {
    
    //modelos
    private SuperMercado superMercado;
    
    //ventanas
    VentanaInicio ventanaInicio;
    
    VentanaValidacionCliente ventanaValidacionCliente;
    VentanaClientes ventanaClientes;
    VentanaVenta ventanaVenta;
    VentanaDatosCliente ventanaNuevoCliente;
    VentanaDatosCliente ventanaModificarCliente;
    
    VentanaProductos ventanaProductos;
    VentanaDatosProducto ventanaNuevoProducto;
    VentanaDatosProducto ventanaModificarProducto;
    
    VentanaProveedores ventanaProveedores;
    
    VentanaRegistro ventanaRegistro;
    
    VentanaDatosProveedor ventanaDProveedor;
    VentanaCompra ventanaCompra;
    
    //Registro (venta o compra)
    ArrayList<Factura> registrosVentas = new ArrayList<>();
    ArrayList<Factura> registrosCompras = new ArrayList<>();
    int numRegistro = 0;
    

    public SuperMercadoController() {
        
        superMercado = new SuperMercado();
        ventanaInicio = new VentanaInicio();
        ventanaInicio.agregarListenersBtns(new ManejadorDeEventosMenu());
    }
    
    /////////////////////////ventanaInicio////////////////////////////////////
    class ManejadorDeEventosMenu implements ActionListener {

        @Override
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("clientes")){
                ventanaInicio.dispose();
                try {
                    ventanaValidacionCliente.show();
                }
                catch (NullPointerException npe){
                    ventanaValidacionCliente = new VentanaValidacionCliente();
                    ventanaValidacionCliente.
                            agregarListenersBtns(new ManejadorDeEventosValidacionCliente());
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("productos")){
                ventanaInicio.dispose();
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
            else if(e.getActionCommand().equalsIgnoreCase("ventas")){
                if(ventanaRegistro != null){
                    ventanaRegistro.show();
                }
                else{
                    ventanaRegistro = new VentanaRegistro();
                    ventanaRegistro.
                            agregarListenersBnts(
                                    new ManejadorDeEventosRegistro());
                }
                ventanaRegistro.setLblTitulo("Registro de Ventas");
                ventanaRegistro.setDatos("", "");
                registrosVentas = superMercado.getVentas();
                if(!superMercado.getVentas().isEmpty()){
                    ventanaRegistro.cambiarRegistro(superMercado.
                            getVentas().get(numRegistro));
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("compras")){
                if(ventanaRegistro != null){
                    ventanaRegistro.show();
                }
                else{
                    ventanaRegistro = new VentanaRegistro();                
                    ventanaRegistro.
                            agregarListenersBnts(
                                    new ManejadorDeEventosRegistro());
                }
                ventanaRegistro.setLblTitulo("Registro de Compras");
                ventanaRegistro.setDatos("", "");
                registrosCompras = superMercado.getCompras();
                if(!superMercado.getCompras().isEmpty()){                  
                    ventanaRegistro.cambiarRegistro(superMercado.
                            getCompras().get(numRegistro));
                }
            }
            ventanaInicio.dispose();
        }
        
    }
    
    ////////////////////ventanaValidacionCliente//////////////////////////////
    class ManejadorDeEventosValidacionCliente implements ActionListener {

        @Override
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("iniciar venta")){
                if(!superMercado.
                        buscarCliente(ventanaValidacionCliente.getTxtCedula())){
                    ventanaValidacionCliente.mensajeNoEncontrado();
                }
                else{
                
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
                    ventanaNuevoCliente = new VentanaDatosCliente();
                    ventanaNuevoCliente.
                            agregarListenersBtns(new ManejadorDeEventosNuevoCliente());
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("ver clientes")){
                ventanaValidacionCliente.dispose();
                if(ventanaClientes != null){
                    ventanaClientes.show();
                }
                else{
                    ventanaClientes = new VentanaClientes();
                    ventanaClientes.agregarListenersBtns(
                            new ManejadorDeEventosClientes());
                }
                ventanaClientes.limpiarTablaClientes();
                ventanaClientes.setTablaClientes(
                superMercado.getClientes());
            }
            else if(e.getActionCommand().equalsIgnoreCase("regresar")){
                ventanaValidacionCliente.dispose();
                ventanaInicio.show();
            }
        }
        
        
    }
    
    //////////////////////ventanaClientes///////////////////////////////////
    class ManejadorDeEventosClientes implements ActionListener {

        @Override
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("modificar cliente")){
                int cliente = ventanaClientes.getFilaTablaClientes();
                if(cliente != -1){
                    Cliente clienteAModificar = superMercado.getClientes().
                            get(cliente);
                    if(ventanaModificarCliente != null){
                        ventanaModificarCliente.show();
                    }
                    else{
                        ventanaModificarCliente = new VentanaDatosCliente();
                        ventanaModificarCliente.agregarListenersBtns(
                                new ManejadorDeEventosModificarCliente());
                    }
                    ventanaModificarCliente.setDatosCliente(
                            clienteAModificar.getNombre(),
                            clienteAModificar.getiD());
                    ventanaClientes.dispose();
                    
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("eliminar cliente")){
                int cliente = ventanaClientes.getFilaTablaClientes();
                if(cliente != -1){
                    superMercado.getClientes().remove(cliente);
                    ventanaClientes.limpiarTablaClientes();
                    ventanaClientes.setTablaClientes(
                    superMercado.getClientes());
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("iniciar venta")){
                int cliente = ventanaClientes.getFilaTablaClientes();
                if(cliente != -1){
                    superMercado.setClienteSeleccionado(cliente);
                    ventanaClientes.dispose();
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
                    
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("regresar")){
                ventanaClientes.dispose();
                ventanaValidacionCliente.show();
            }
        }
        
    }
    
    /////////////////////////ventanaModicarCliente////////////////////////////
    class ManejadorDeEventosModificarCliente implements ActionListener {

        @Override
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("aceptar")){
                int clienteAModificar = ventanaClientes.getFilaTablaClientes();
                if(ventanaModificarCliente.advertencia()){
                    //se modifca el cliente
                    superMercado.getClientes().get(clienteAModificar).setNombre(
                    ventanaModificarCliente.getTxtNombre());
                    superMercado.getClientes().get(clienteAModificar).setiD(
                    ventanaModificarCliente.getTxtCedula());

                    ventanaModificarCliente.dispose();
                    ventanaClientes.limpiarTablaClientes();
                    ventanaClientes.setTablaClientes(
                    superMercado.getClientes());
                    ventanaClientes.show();
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("cancelar")){
                ventanaModificarCliente.dispose();
                ventanaClientes.show();
            }
        }
        
    }
    
    ///////////////////////ventanaNuevoCliente///////////////////////////////
    class ManejadorDeEventosNuevoCliente implements ActionListener {

        @Override
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("aceptar")){
                if(ventanaNuevoCliente.advertencia()){
                    //se agrega al cliente
                    superMercado.
                            agregarCliente(ventanaNuevoCliente.getTxtNombre(), 
                                    ventanaNuevoCliente.getTxtCedula());

                    superMercado.setClienteSeleccionado(
                    superMercado.getClientes().size()-1);

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
            }
            else if(e.getActionCommand().equalsIgnoreCase("cancelar")){
                ventanaNuevoCliente.dispose();
            }
        }
        
    }
    
    ////////////////////////////////ventanaVenta//////////////////////////////
    class ManejadorDeEventosVenta implements ActionListener {

        @Override
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("agregar a venta")){
                
                //se obtiene el producto seleccionado
                String nombreProducto = ventanaVenta.getProductoElegido();
                
                if(!nombreProducto.equalsIgnoreCase("")){
                    int indiceProducto = superMercado.
                            getIndiceProducto(nombreProducto);
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
                        ventanaVenta.mensaje("Producto agotado");
                    }
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("cancelar venta")){
                ventanaVenta.dispose();
                superMercado.cancelarVenta(ventanaVenta.getProductosInfo());
                ventanaVenta.setTotal(0);
                ventanaVenta.limpiarTablaCarrito();
                ventanaVenta.dispose();
                ventanaInicio.show();
            }
            else if(e.getActionCommand().equalsIgnoreCase("eliminar item seleccionado")){
                int itemSeleccionado = ventanaVenta.getFilaTblCarrito();
                if(itemSeleccionado != -1){
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
                if(ventanaVenta.advertencia()){
                    Cliente cliente = superMercado.getClientes().
                        get(superMercado.getClienteSeleccionado());

                    ArrayList<HashMap<String,String>> carrito = cliente.getCarrito();
                    ArrayList<HashMap<String,String>> carritoCopia = new ArrayList<>();
                    //se copia el carrito
                    for(HashMap<String,String> p : carrito){
                        @SuppressWarnings("unchecked")
                        HashMap<String,String> pN = (HashMap<String,String>)p.clone();
                        carritoCopia.add(pN);
                    }

                    //se agrega la venta
                    superMercado.agregarVenta(new Factura(
                            cliente.getNombre(), cliente.getiD(), 
                            carritoCopia, 
                            superMercado.getTotalCarritoCliente()));

                    //se vacia el crrito de lcliente
                    cliente.limpiarCarrito();

                    //Se cierrra la ventana
                    ventanaVenta.setTotal(0);
                    ventanaVenta.limpiarTablaCarrito();
                    ventanaVenta.dispose();
                    ventanaInicio.show();
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("comboBoxChanged")){
                ventanaVenta.setCboxProductos(
                superMercado.getProductos());
            }
        }
        
    }
    
    ///////////////////////////////VentanaProductos///////////////////////////
    class ManejadorDeEventosProductos implements ActionListener {

        @Override
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("regresar")){
                ventanaProductos.limpiarTablaProductos();
                ventanaProductos.dispose();
                ventanaInicio.show();
            }
            else if(e.getActionCommand().equalsIgnoreCase("eliminar producto")){
                String productoSeleccionado = ventanaProductos.
                        getProductoSeleccionado();
                if("".equals(productoSeleccionado)){
                    ventanaProductos.mensajeSelecProducto();
                }
                else{
                    int productoAEliminar = superMercado.
                            getIndiceProducto(productoSeleccionado);
                    superMercado.getProductos().remove(productoAEliminar);
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
                    ventanaNuevoProducto = new VentanaDatosProducto();
                    ventanaNuevoProducto.
                            agregarListenersBtns(
                                    new ManejadorDeEventosNuevoProducto());
                }
                ventanaProductos.dispose();
            }
            else if(e.getActionCommand().equalsIgnoreCase("modificar producto")){
                String productoSeleccionado = ventanaProductos.
                        getProductoSeleccionado();
                if("".equals(productoSeleccionado)){
                    ventanaProductos.mensajeSelecProducto();
                }
                else{
                    int productoAModificar = superMercado.
                            getIndiceProducto(productoSeleccionado);
                    if(ventanaModificarProducto != null){
                        ventanaModificarProducto.show();
                    }
                    else{
                        ventanaModificarProducto = new VentanaDatosProducto();
                        ventanaModificarProducto.
                                agregarListenersBtns(
                                        new ManejadorDeEventosModificarProducto());
                    }
                    ventanaModificarProducto.setTxtNombre(
                    superMercado.getProductos().get(productoAModificar).getNombre());
                    ventanaModificarProducto.setTxtPrecio(
                    superMercado.getProductos().get(productoAModificar).getPrecio()+"");
                    ventanaModificarProducto.setTxtCategoria(
                    superMercado.getProductos().get(productoAModificar).getCategoria());
                    ventanaProductos.dispose();
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("comboBoxChanged")){
                ventanaProductos.limpiarTablaProductos();
                ventanaProductos.setTablaProductos(superMercado.
                        getProductos());
            }
        }
        
    }
    
    ////////////////////////////VentanaModificarProducto///////////////////////
    class ManejadorDeEventosModificarProducto implements ActionListener {

        @Override
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("aceptar")){
                if(ventanaModificarProducto.advertencia()){
                    //se modifica el producto
                    int producto = superMercado.getIndiceProducto(
                    ventanaProductos.getProductoSeleccionado());

                    superMercado.getProductos().set(producto,new Producto(
                            ventanaModificarProducto.getTxtNombre(), 
                            superMercado.getProductos().get(producto).getCantidad(),
                    Integer.parseInt(ventanaModificarProducto.getTxtPrecio()), 
                            ventanaModificarProducto.getTxtCategoria()));

                    //se cierra y abre ventanas
                    ventanaModificarProducto.dispose();
                    ventanaProductos.limpiarTablaProductos();
                    ventanaProductos.setTablaProductos(
                    superMercado.getProductos());
                    ventanaProductos.show();
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("cancelar")){
                ventanaModificarProducto.dispose();
                ventanaProductos.show();
            }
        }
        
    } 
    
    //////////////////////////////VentanaNuevoProducto//////////////////////
    class ManejadorDeEventosNuevoProducto implements ActionListener {

        @Override
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("aceptar")){
                if(ventanaNuevoProducto.advertencia()){
                    //se añade el producto nuevo
                    superMercado.getProductos().add(new Producto(
                            ventanaNuevoProducto.getTxtNombre(), 0,
                    Integer.parseInt(ventanaNuevoProducto.getTxtPrecio()), 
                            ventanaNuevoProducto.getTxtCategoria()));

                    //se cierra y abre ventanas
                    ventanaNuevoProducto.dispose();
                    ventanaProductos.limpiarTablaProductos();
                    ventanaProductos.setTablaProductos(
                    superMercado.getProductos());
                    ventanaProductos.show();
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("cancelar")){
                ventanaNuevoProducto.dispose();
                ventanaProductos.show();
            }
        }
        
    }
    
    ////////////////////////////////VentanaRegistro/////////////////
    class ManejadorDeEventosRegistro implements ActionListener {
        @Override
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("siguiente")){
                numRegistro++;
                try{
                    if(ventanaRegistro.getLblTitulo().equalsIgnoreCase("Registro de Ventas")){
                        Factura factura = registrosVentas.get(numRegistro);
                        ventanaRegistro.cambiarRegistro(factura);
                    }else{
                        Factura factura = registrosCompras.get(numRegistro);
                        ventanaRegistro.cambiarRegistro(factura);
                    }
                }catch(IndexOutOfBoundsException ai){
                    numRegistro--;
                    ventanaRegistro.mensajeRegistros();
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("anterior")){
                numRegistro--;
                try{
                    if(ventanaRegistro.getLblTitulo().equalsIgnoreCase("Registro de Ventas")){
                        Factura factura = registrosVentas.get(numRegistro);
                        ventanaRegistro.cambiarRegistro(factura);
                    }else{
                        Factura factura = registrosCompras.get(numRegistro);
                        ventanaRegistro.cambiarRegistro(factura);
                    }
                }catch(IndexOutOfBoundsException ai){
                    numRegistro++;
                    ventanaRegistro.mensajeRegistros();
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("regresar")){
                ventanaRegistro.dispose();
                ventanaRegistro.setLblTotal("");
                ventanaInicio.show();
                numRegistro = 0;
            }
        }
        
    }
    
    ///////////////////////// Ventana Proveedor /////////////////////////////
    class ManejadorDeEventosProveedores implements ActionListener{
        @Override
        @SuppressWarnings("deprecation")
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
                    Proveedor p = superMercado.getProveedor(fila);
                    String nombre = p.getNombre();
                    String categoria = p.getCategoria();
                    ArrayList<String> productos = new ArrayList<>();
                    for(HashMap<String,String> map : p.getProductos()){
                        for(Map.Entry<String, String> entry : map.entrySet()){
                            String nombreP = "";
                            if(entry.getKey().equalsIgnoreCase("Nombre")){
                                nombreP = entry.getValue();
                                productos.add(nombreP);
                            }
                        }    
                    }
                    ventanaCompra.mostrarProveedor(nombre, categoria, productos);
                    ventanaCompra.setLblTotal("0");
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
        @SuppressWarnings("deprecation")
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
                            @SuppressWarnings("unchecked")
                            HashMap <String,String> auxMap = new HashMap();
                            auxMap.put("nombre", (String)modeloTbl.getValueAt(i, 0));
                            auxMap.put("precio", (String)modeloTbl.getValueAt(i, 1));
                            auxMap.put("categoria", categoria);
                            productos.add(auxMap);
                        }
                        Proveedor proveedor = new Proveedor(nombre,telefono,categoria,productos);
                        if(ventanaDProveedor
                                .getLblTitulo().equalsIgnoreCase("Nuevo Proveedor")){
                            superMercado.agregarProveedor(proveedor);
                            ventanaDProveedor
                                    .mensajesEmergentes("Nuevo"); 
                        }
                        else if(ventanaDProveedor
                                .getLblTitulo().equalsIgnoreCase("Modificar Proveedor")){
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
    
    ////////////////////////////////VentanaCompra////////////////////
    class ManejadorDeEventosCompra implements ActionListener{
        @Override
        @SuppressWarnings({"deprecation", "unchecked"})
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("Agregar a Compra")){
                int numP = ventanaProveedores.getFilaTabla();
                String producto = ventanaCompra.getCboxProductos();
                Proveedor p = superMercado.getProveedor(numP);
                int cantidad = ventanaCompra.getSpnCantidad();
                for(HashMap<String,String> map : p.getProductos()){
                    if(map.get("nombre").equalsIgnoreCase(producto)){
                        HashMap<String,String> mapProducto;
                        mapProducto = superMercado.generarMap(map.get("nombre"), 
                                map.get("precio"), Integer.toString(cantidad));
                        superMercado.aNadirProductoCarrito(mapProducto, cantidad);
                    }
                }
                ventanaCompra.setTablaCarrito(superMercado.getCarritoSuper());
                ventanaCompra.setLblTotal(Integer.toString(superMercado.totalCarritoSuper()));
            }
            else if(e.getActionCommand().equalsIgnoreCase("Cancelar Compra")){
                if(ventanaCompra.mensajeCancelarCompra() == 0){
                    ventanaCompra.dispose();
                    superMercado.limpiarCarritoSuper();
                    ventanaCompra.limpiarTablaProductos();
                    ventanaCompra.reiniciarSpnCantidad();
                    ventanaProveedores.show();
                }
                
            }
            else if(e.getActionCommand().equalsIgnoreCase("Eliminar item seleccionado")){
                int fila = ventanaCompra.getFilaTabla();
                if(fila != -1){
                    if(ventanaCompra.mensajeEliminarProducto() == 0){
                        superMercado.eliminarProductoCarrito(fila);
                        ventanaCompra.setTablaCarrito(superMercado.getCarritoSuper());
                        ventanaCompra.setLblTotal(Integer.toString(superMercado.totalCarritoSuper()));
                    }
                }else{
                    ventanaCompra.mensajesEmergentes("Eliminar");
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("Finalizar")){
                if(ventanaCompra.mensajeRelizarCompra() == 0){
                    if(ventanaCompra.advertencia()){
                
                        ArrayList<HashMap<String,String>> carrito;
                        carrito = superMercado.getCarritoSuper();
                        String categoria = ventanaCompra.getTxtCategoria();
                        for(HashMap<String,String> map : carrito){
                            String nombre = map.get("nombre");
                            int cantidad = Integer.parseInt(map.get("cantidad"));
                            int precio = Integer.parseInt(map.get("precio"));
                            Producto p = new Producto(nombre,cantidad,precio,categoria);
                            superMercado.aNadirProducto(p);
                        }                    
                        int numP = ventanaProveedores.getFilaTabla();                    
                        Proveedor p = superMercado.getProveedor(numP);                    
                        @SuppressWarnings("unchecked")
                        ArrayList<HashMap<String,String>> carritoClone;
                        carritoClone = (ArrayList<HashMap<String,String>>)
                                superMercado.getCarritoSuper().clone();
                        ventanaCompra.limpiarTablaProductos();
                        ventanaCompra.mensajesEmergentes("Comprar");
                        superMercado.agregarCompra(new Factura(
                                p.getNombre(), p.getTelefono(), 
                                carritoClone, 
                                superMercado.totalCarritoSuper()));
                        ventanaCompra.reiniciarSpnCantidad();
                        ventanaCompra.dispose();
                        ventanaProveedores.show();
                        superMercado.limpiarCarritoSuper();
                    }
                }
            }            
        }      
    }
    
    ///Metodo que se ejecuta al finalizar el programa///////
    public void attachShutDownHook(){
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
              superMercado.guardarDatos();
            }
        } );
    }
}
