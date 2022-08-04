/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author Juan
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
