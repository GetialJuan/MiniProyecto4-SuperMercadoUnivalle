
package modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MiniProyecto 4 - SuperMercado Univalle
 * @author Juan Sebastian Getial Getial <202124644>
 * @author Mauricio Muñoz Gutierrez <202123687>
 * @author Santiago Torres Carvajal <>
 * @profesor Luis Yovany Romo Portilla
 * Clase que representa el supermercado
 */
public class SuperMercado {
    
    private ArrayList<Cliente> clientes;
    private ArrayList<Proveedor> proveedores;
    private ArrayList<Producto> productos;
    private ArrayList<HashMap<String,String>> carritoSuper;
    private ArrayList<Factura> ventas;
    private ArrayList<Factura> compras;
    private int clienteSeleccionado;
    
    
    
    public SuperMercado(){
        clientes = new ArrayList<>();
        proveedores = new ArrayList<>();
        productos = new ArrayList<>();
        carritoSuper = new ArrayList<>();
        ventas = new ArrayList<>();
        compras = new ArrayList<>();
        clienteSeleccionado = 0;

        restaurarDatos();
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

    public ArrayList<Factura> getVentas() {
        return ventas;
    }
    
    public ArrayList<Factura> getCompras(){
        return compras;
    }
   
    public int getIndiceProducto(String cualProducto){
        int indice = 0;
        for(Producto p : productos){
            if(p.getNombre().equalsIgnoreCase(cualProducto)){
                break;
            }
            indice++;
        }
        return indice;
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
            int precio = Integer.parseInt(producto.get("precio"));
            int cantidad = Integer.parseInt(producto.get("cantidad"));
            
            total += (precio * cantidad);
        }
        
        return total;
    }
    
    public void agregarVenta(Factura factura){
        ventas.add(factura);
    }
    
    public void agregarCompra(Factura factura){
        compras.add(factura);
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
        HashMap<String,String> map = new HashMap<>();
        map.put("nombre", nombre);
        map.put("precio", precio);
        map.put("cantidad", "1");
        return map;
    }
    
    public void aNadirProductoCarrito(HashMap<String,String> map){
        boolean nuevo = false;
        for(HashMap<String,String> mapCarrito : carritoSuper){
            if(mapCarrito.get("nombre").equals(map.get("nombre"))){
                nuevo = true;
                int cantidadActual = Integer.parseInt(mapCarrito.get("cantidad"));
                int nuevaCantidad = cantidadActual + 1;
                mapCarrito.put("cantidad", Integer.toString(nuevaCantidad));
                break;
            }
        }
        if(!nuevo){
            carritoSuper.add(map);
        }
    }
    
    public void limpiarCarritoSuper(){
        carritoSuper.clear();
    }
    
    public void eliminarProductoCarrito(int indice){
        carritoSuper.remove(indice);
    }
    
    public int totalCarritoSuper(){
        int total = 0;
        int cantidad = 0;
        int precio = 0;
        for(HashMap<String,String> mapCarrito : carritoSuper){
            cantidad = Integer.parseInt(mapCarrito.get("cantidad"));
            precio = Integer.parseInt(mapCarrito.get("precio"));
            total += (precio * cantidad);
        }
        return total;
    }
    
    public void aNadirProducto(Producto producto){
        boolean nuevo = true;
        int i = 0;
        for(Producto p : productos){
            if(producto.getNombre().equals(p.getNombre()) && 
                    producto.getPrecio() == p.getPrecio()){
                nuevo = false;
                Producto nuevoP = p;
                nuevoP.setCantidad(nuevoP.getCantidad() + producto.getCantidad());
                productos.remove(i);
                productos.add(i, nuevoP);
                break;
            }else{
                i++;
            }
        }
        if(nuevo){
            productos.add(producto);
        }
    }
    
    private void guardar(String rutaArchivo, String objetoAGuardar){
        File archivo = new File(rutaArchivo);
        archivo.delete();
        try {
            archivo.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(SuperMercado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try (FileOutputStream fos = new FileOutputStream(archivo);
            ObjectOutputStream oos = new ObjectOutputStream(fos); ){
            
            if(objetoAGuardar.equals("productos")){
                for(Producto p : productos){
                    oos.writeObject(p);
                }
            }
            else if(objetoAGuardar.equals("clientes")){
                for(Cliente c : clientes){
                    oos.writeObject(c);
                }
            }
            if(objetoAGuardar.equals("proveedores")){
                for(Proveedor pr : proveedores){
                    oos.writeObject(pr);
                }
            }
            if(objetoAGuardar.equals("compras")){
                for(Factura compra : compras){
                    oos.writeObject(compra);
                }
            }
            if(objetoAGuardar.equals("ventas")){
                for(Factura venta : ventas){
                    oos.writeObject(venta);
                }
            }
            
            fos.close();
            oos.close();
        } catch (FileNotFoundException ex ) {
            System.out.println("no se encontro el archivo 1");
        } catch (IOException ex){
            System.out.println("no se encontro el archivo");
        }
    }
    
    private void restaurar(String rutaArchivo, String objetoARestaurar){
        File archivo = new File(rutaArchivo);
        try (FileInputStream fis = new FileInputStream(archivo);
             ObjectInputStream ois = new ObjectInputStream(fis);) {

            while(fis.available() > 0){
                if(objetoARestaurar.equals("productos")){
                    Producto p = (Producto)ois.readObject();
                    this.productos.add(p);
                }
                else if(objetoARestaurar.equals("clientes")){
                    Cliente c = (Cliente)ois.readObject();
                    this.clientes.add(c);
                }
                if(objetoARestaurar.equals("proveedores")){
                    Proveedor pr = (Proveedor)ois.readObject();
                    this.proveedores.add(pr);
                }
                if(objetoARestaurar.equals("ventas")){
                    Factura venta = (Factura)ois.readObject();
                    this.ventas.add(venta);
                }
                if(objetoARestaurar.equals("compras")){
                    Factura compra = (Factura)ois.readObject();
                    this.compras.add(compra);
                }
            }
            
            fis.close();
            if(ois != null){
                ois.close();
            }
            
        } catch (FileNotFoundException | ClassNotFoundException ex) {
            System.out.println("No se encontro el archivo");
        } catch (IOException ex){}
    }
    
    private void restaurarDatos(){
        String rutaAbsoluta = new File("").getAbsolutePath();
        
        restaurar(rutaAbsoluta.concat("\\src\\ArchivosPersistentes\\"
                + "Productos.dat"), "productos");
        
        restaurar(rutaAbsoluta.concat("\\src\\ArchivosPersistentes\\"
                + "Clientes.dat"), "clientes");
        
        restaurar(rutaAbsoluta.concat("\\src\\ArchivosPersistentes\\"
                + "Proveedores.dat"), "proveedores");
        
        restaurar(rutaAbsoluta.concat("\\src\\ArchivosPersistentes\\"
                + "Ventas.dat"), "ventas");
        
        restaurar(rutaAbsoluta.concat("\\src\\ArchivosPersistentes\\"
                + "Compras.dat"), "compras");
    }
    
    public void guardarDatos(){
        String rutaAbsoluta = new File("").getAbsolutePath();
        
        guardar(rutaAbsoluta.concat("\\src\\ArchivosPersistentes\\"
                + "Productos.dat"), "productos");
        
        guardar(rutaAbsoluta.concat("\\src\\ArchivosPersistentes\\"
                + "Clientes.dat"), "clientes");
        
        guardar(rutaAbsoluta.concat("\\src\\ArchivosPersistentes\\"
                + "Proveedores.dat"), "proveedores");
        
        guardar(rutaAbsoluta.concat("\\src\\ArchivosPersistentes\\"
                + "Ventas.dat"), "ventas");
        
        guardar(rutaAbsoluta.concat("\\src\\ArchivosPersistentes\\"
                + "Compras.dat"), "compras");
    }
}
