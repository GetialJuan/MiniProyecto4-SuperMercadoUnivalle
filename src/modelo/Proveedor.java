
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
 * Clase que representa un proveedor
 */
public class Proveedor implements Serializable{
    private String nombre;
    private String telefono;
    private String categoria;
    private ArrayList<HashMap<String,String>> productos;

    public Proveedor(String nombre, String telefono, String categoria, ArrayList<HashMap<String, String>> productos) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.categoria = categoria;
        this.productos = productos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public ArrayList<HashMap<String, String>> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<HashMap<String, String>> productos) {
        this.productos = productos;
    }

}
