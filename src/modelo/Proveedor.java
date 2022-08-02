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
public class Proveedor {
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
