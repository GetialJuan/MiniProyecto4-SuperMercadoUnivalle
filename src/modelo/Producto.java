
package modelo;

import java.io.Serializable;

/**
 * MiniProyecto 4 - SuperMercado Univalle
 * @author Juan Sebastian Getial Getial <202124644>
 * @author Mauricio Muñoz Gutierrez <202123687>
 * @author Santiago Torres Carvajal <2140010>
 * @profesor Luis Yovany Romo Portilla
 * Clase que representa un producto
 */
public class Producto implements Serializable{
    
    private String nombre;
    private int cantidad;
    private int precio;
    private String categoria;

    public Producto(String nombre, int cantidad, int precio, String categoria) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getPrecio() {
        return precio;
    }
    
    public String getCategoria() {
        return categoria;
    }

    public boolean reducirUnaUnidad(){
        if(cantidad>0){
            cantidad--;
            return true;
        }
        else{
            return false;
        }
    }
    
    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }
}
