
package modelo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * MiniProyecto 4 - SuperMercado Univalle
 * @author Juan Sebastian Getial Getial <202124644>
 * @author Mauricio Muñoz Gutierrez <202123687>
 * @author Santiago Torres Carvajal <>
 * @profesor Luis Yovany Romo Portilla
 * Clase que representa a una factura de venta
 */
public class Factura{
    private String nombre;
    private String id;
    private final ArrayList<HashMap<String,String>> carrito;
    private int total;

    public Factura(String nombre, String id, 
            ArrayList<HashMap<String, String>> carrito, int total) {
        this.nombre = nombre;
        this.id = id;
        this.carrito = carrito;
        this.total = total;
    }

    public String getNombre() {
        return nombre;
    }

    public String getId() {
        return id;
    }

    public ArrayList<HashMap<String, String>> getCarrito() {
        return carrito;
    }

    public int getTotal() {
        return total;
    }
    
}
