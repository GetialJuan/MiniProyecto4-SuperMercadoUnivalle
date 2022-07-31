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
    private ArrayList<Cliente> copiaClientes;
    private ArrayList<Proveedor> proveedores;
    private ArrayList<Producto> productos;
    private ArrayList<Producto> copiaProductos;
    private int clienteSeleccionado;
    
    public SuperMercado(){
        clientes = new ArrayList<>();
        proveedores = new ArrayList<>();
        productos = new ArrayList<>();
        clienteSeleccionado = 0;
        
        //productos momentaneos para pruebas (Se debe borrar luego)
        productos.add(new Producto("lechuga", 10, 1000));
        productos.add(new Producto("leche", 5, 2000));
        productos.add(new Producto("Doritos", 20, 1500));
        productos.add(new Producto("Cebolla", 3, 500));
        
        //clientes momentaneos para pruebas (Se debe borrar luego)
        clientes.add(new Cliente("juan", "123456"));
        clientes.add(new Cliente("nauj", "654321"));
        clientes.add(new Cliente("anju", "111222"));
    }
    
    public void agregarCliente(String nombre, String iD){
        clientes.add(new Cliente(nombre, iD));
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
    
    public int getClienteSeleccionado(){
        return clienteSeleccionado;
    }
    
    public void setCopias(){
        copiaClientes = clientes;
        copiaProductos = productos;
    }
    
    public void restablecerDatos() {
        clientes = copiaClientes;
        productos = copiaProductos;
    }
    
    public void restablecerProducto(String producto){
        Producto pr = null;
        for(Producto p : copiaProductos){
            if(p.getNombre().equalsIgnoreCase(producto)){
                pr = p;
                break;
            }
        }
        int indice = 0;
        for(Producto p : productos){
            if(p.getNombre().equalsIgnoreCase(producto)){
                break;
            }
            else{
                indice++;
            }
            
        }
        System.out.println(indice);
        productos.set(indice, pr);
    }
    
    public void setClienteSeleccionadoNuevo(){
        clienteSeleccionado = 0;
    }
    
    public boolean buscarCliente(String iD){
        boolean encontrado = false;
        int indice = 0;
        for(Cliente c : clientes){
            if(c.getiD().equals(iD)){
                encontrado = true;
                break;
            }
            indice++;
        }
        clienteSeleccionado = indice;
        return encontrado;
    }
    
}
