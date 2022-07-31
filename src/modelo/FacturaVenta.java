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
public class FacturaVenta {
    private String nombreCliente;
    private String iDCliente;
    private ArrayList<HashMap<String,String>> carrito;
    private int totalVenta;

    public FacturaVenta(String nombreCliente, String iDCliente, 
            ArrayList<HashMap<String, String>> carrito, int totalVenta) {
        this.nombreCliente = nombreCliente;
        this.iDCliente = iDCliente;
        this.carrito = carrito;
        this.totalVenta = totalVenta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getiDCliente() {
        return iDCliente;
    }

    public ArrayList<HashMap<String, String>> getCarrito() {
        return carrito;
    }

    public int getTotalVenta() {
        return totalVenta;
    }
    
}
