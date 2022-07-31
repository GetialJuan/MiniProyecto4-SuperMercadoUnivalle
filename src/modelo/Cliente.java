/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author storr
 */
public class Cliente {
    private String nombre, cedula;
    private ArrayList<Producto> carrito;

    public Cliente(String nombre, String cedula, ArrayList carrito) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.carrito = carrito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public ArrayList getCarrito() {
        return carrito;
    }

    public void setCarrito(ArrayList carrito) {
        this.carrito = carrito;
    }
    
    
}
