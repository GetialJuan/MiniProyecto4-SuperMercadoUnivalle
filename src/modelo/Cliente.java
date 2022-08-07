
package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * MiniProyecto 4 - SuperMercado Univalle
 * @author Juan Sebastian Getial Getial <202124644>
 * @author Mauricio Muñoz Gutierrez <202123687>
 * @author Santiago Torres Carvajal <>
 * @profesor Luis Yovany Romo Portilla
 * Clase que representa a un cliente
 */
public class Cliente implements Serializable{
    
    private String nombre;
    private String iD;
    private ArrayList<HashMap<String,String>> carrito;

    public Cliente(String nombre, String iD) {
        this.nombre = nombre;
        this.iD = iD;
        carrito = new ArrayList<>();
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getNombre() {
        return nombre;
    }

    public String getiD() {
        return iD;
    }

    public ArrayList<HashMap<String, String>> getCarrito() {
        return carrito;
    }
    
    public void agregarProductoAlCarrito(String cualProducto, int precio){
        HashMap<String,String> producto = new HashMap<>();
        int cantidad = 1;
        int indice = 0;
        boolean encontrado = false;
        for(HashMap<String, String> p : carrito){
            if(p.get("nombre").equals(cualProducto)){
                encontrado = true;
                cantidad = Integer.parseInt(p.get("cantidad")) + 1;
                break;
            }
            indice++;
        }
        
        producto.put("nombre", cualProducto);
        producto.put("precio", Integer.toString(precio));
        producto.put("cantidad", Integer.toString(cantidad));
        
        if(encontrado){
            carrito.set(indice, producto);
        }
        else{
            carrito.add(producto);
        }
    }
    
    public void eliminarProducto(int cualProducto){
        carrito.remove(cualProducto);
    }
    
    public void limpiarCarrito(){
        carrito.clear();
    }
    
}
