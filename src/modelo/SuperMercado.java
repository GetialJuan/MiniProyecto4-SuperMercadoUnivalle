/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author Juan
 */
public class SuperMercado {
    
    private ArrayList<Cliente> clientes;
    private ArrayList<Proveedor> proveedores;
    private ArrayList<Producto> productos;
    private ArrayList<HashMap<String,String>> carritoSuper;
    private ArrayList<FacturaVenta> ventas;
    private int clienteSeleccionado;
    
    public SuperMercado(){
        clientes = new ArrayList<>();
        proveedores = new ArrayList<>();
        productos = new ArrayList<>();
        carritoSuper = new ArrayList<>();
        ventas = new ArrayList<>();
        clienteSeleccionado = 0;
        
        //productos momentaneos para pruebas (Se debe borrar luego)
        productos.add(new Producto("lechuga", 10, 1000));
        productos.add(new Producto("leche", 5, 2000));
        productos.add(new Producto("Doritos", 20, 1500));
        productos.add(new Producto("Cebolla", 3, 500));
        
        //clientes momentaneos para pruebas (Se debe borrar luego)
        clientes.add(new Cliente("juan", "123456"));
        clientes.add(new Cliente("nauj", "654321"));
        clientes.add(new Cliente("anju", "111222"));
        
        //proveedores momentaneos para pruebas (Se debe borrar luego)
        ArrayList <HashMap<String,String>> productos = new ArrayList<>();
        HashMap<String,String> auxMap = new HashMap(); 
        auxMap.put("Nombre","Pollo");
        auxMap.put("Precio", "16000");
        auxMap.put("Categoria", "Cárnicos");
        productos.add(auxMap);
        auxMap = new HashMap();
        auxMap.put("Nombre","Res");
        auxMap.put("Precio", "25000");
        auxMap.put("Categoria", "Cárnicos");
        productos.add(auxMap);
        proveedores.add(new Proveedor("Mauro", "123456", "Cárnicos",productos));
        
        productos = new ArrayList<>();
        auxMap = new HashMap();
        auxMap.put("Nombre","Tomates");
        auxMap.put("Precio", "3000");
        auxMap.put("Categoria", "Frutas y Verduras");
        productos.add(auxMap);
        auxMap = new HashMap();
        auxMap.put("Nombre","Papas");
        auxMap.put("Precio", "2000");
        auxMap.put("Categoria", "Frutas y Verduras");
        productos.add(auxMap);
        proveedores.add(new Proveedor("Juanito", "78910", "Frutas y verduras", productos));
        
        productos = new ArrayList<>();
        auxMap = new HashMap();
        auxMap.put("Nombre","Ron");
        auxMap.put("Precio", "150000");
        auxMap.put("Categoria", "Licores");
        productos.add(auxMap);
        auxMap = new HashMap();
        auxMap.put("Nombre","Vodka");
        auxMap.put("Precio", "60000");
        auxMap.put("Categoria", "Licores");
        productos.add(auxMap);
        proveedores.add(new Proveedor("Carlos", "11121314", "Licores", productos));
    }
    
    public void agregarCliente(String nombre, String iD){
        clientes.add(new Cliente(nombre, iD));
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
    
    public ArrayList<Proveedor> getProveedores(){
        return proveedores;
    }
    
    public ArrayList<HashMap<String,String>> getCarritoSuper(){
        return carritoSuper;
    }
    
    public int getClienteSeleccionado(){
        return clienteSeleccionado;
    }

    public ArrayList<FacturaVenta> getVentas() {
        return ventas;
    }
    
    
    public void setCopias(){
        ///falta
    }
    
    public void restablecerDatos() {
        //falta
    }    
        
    public void cancelarVenta(ArrayList< ArrayList<String>> productos){
        for(ArrayList<String> p : productos){
            restablecerProducto(p);
        }
        clientes.get(clienteSeleccionado).limpiarCarrito();
    }
    
    public void restablecerProducto(ArrayList<String> producto){
        String nombre = producto.get(0);
        int cantidad = Integer.parseInt(producto.get(1));
        
        for(Producto p : productos){
            if(p.getNombre().equals(nombre)){
                int cantidadP = p.getCantidad();
                p.setCantidad(cantidad + cantidadP);
                break;
            }
        }
    }
    
    public void setClienteSeleccionado(int n){
        clienteSeleccionado = n;
    }
    
    public boolean buscarCliente(String iD){
        boolean encontrado = false;
        int indice = 0;
        for(Cliente c : clientes){
            if(c.getiD().equals(iD)){
                encontrado = true;
                break;
            }
            indice++;
        }
        clienteSeleccionado = indice;
        return encontrado;
    }
    
    public int getTotalCarritoCliente(){
        int total = 0;
        
        ArrayList<HashMap<String,String>> carrito = 
                clientes.get(clienteSeleccionado).getCarrito();
        for(HashMap<String,String> producto : carrito){
            int presio = Integer.parseInt(producto.get("presio"));
            int cantidad = Integer.parseInt(producto.get("cantidad"));
            
            total += (presio * cantidad);
        }
        
        return total;
    }
    
    public void agregarVenta(FacturaVenta fv){
        ventas.add(fv);
    }
    
    public void agregarProveedor(Proveedor proveedor){
        proveedores.add(proveedor);
    }
    
    public void eliminarProveedor(int indice){
        proveedores.remove(indice);
    }
    
    public Proveedor getProveedor(int indice){
        return proveedores.get(indice);
    }
    
    public void modificarProveedor(int indice, Proveedor proveedor){
        proveedores.remove(indice);
        if(indice >= proveedores.size()){
            proveedores.add(proveedor);
        }else{
            proveedores.add(indice, proveedor);
        }
    }
    
    public HashMap<String,String> generarMap(String nombre, String precio){
        HashMap<String,String> map = new HashMap();
        map.put("Nombre", nombre);
        map.put("Precio", precio);
        map.put("Cantidad", "1");
        return map;
    }
    
    public void aNadirProductoCarrito(HashMap<String,String> map){
        boolean nuevo = false;
        for(HashMap<String,String> mapCarrito : carritoSuper){
            if(mapCarrito.get("Nombre").equals(map.get("Nombre"))){
                nuevo = true;
                int cantidadActual = Integer.parseInt(mapCarrito.get("Cantidad"));
                int nuevaCantidad = cantidadActual + 1;
                mapCarrito.put("Cantidad", Integer.toString(nuevaCantidad));
                break;
            }
        }
        if(!nuevo){
            carritoSuper.add(map);
        }
    }
    
    public void limpiarCarritoSuper(){
        for(int i = 0; i < carritoSuper.size(); i = 0){
            carritoSuper.remove(i);
        }
    }
}
