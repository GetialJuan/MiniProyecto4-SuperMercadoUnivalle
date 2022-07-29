/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Juan
 */
public class Cliente {
    
    private String nombre;
    private String iD;
    private ArrayList<HashMap<String,String>> carrito;

    public Cliente(String nombre, String iD) {
        this.nombre = nombre;
        this.iD = iD;
        carrito = new ArrayList<>();
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
    
    public void agregarProductoAlCarrito(String cualProducto, String cantidad){
        HashMap<String,String> producto = new HashMap<>();
        producto.put("nombre", cualProducto);
        producto.put("cantidad", cantidad);
        
        carrito.add(producto);
    }
    
}
