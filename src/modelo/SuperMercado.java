/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author Juan
 */
public class SuperMercado {
    
    private ArrayList<Cliente> clientes;
    private ArrayList<Proveedor> proveedores;
    private ArrayList<Producto> productos; 
    
    public SuperMercado(){
        clientes = new ArrayList<>();
        proveedores = new ArrayList<>();
        productos = new ArrayList<>();
    }
    
    public void agregarCliente(String nombre, String iD){
        clientes.add(new Cliente(nombre, iD));
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }
    
}
