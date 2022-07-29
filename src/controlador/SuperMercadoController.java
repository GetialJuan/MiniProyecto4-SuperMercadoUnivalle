/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.SuperMercado;
import vista.VentanaCarritoCliente;
import vista.VentanaCliente;
import vista.VentanaMenu;

/**
 *
 * @author Juan
 */
public class SuperMercadoController {
    
    //modelos
    private SuperMercado superMercado;
    
    //ventanas
    VentanaMenu ventanaMenu;
    VentanaCliente ventanaCliente;
    VentanaCarritoCliente ventanaCarritoCliente;

    public SuperMercadoController() {
        
        superMercado = new SuperMercado();
        
        ventanaMenu = new VentanaMenu();
        ventanaMenu.AgregarListenersBtns(new ManejadorDeEventosMenu());
    }
    
    //ventanaMenu
    class ManejadorDeEventosMenu implements ActionListener {

        @Override
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("cliente")){
                try {
                    ventanaCliente.show();
                }
                catch (NullPointerException npe){
                    ventanaCliente = new VentanaCliente();
                    ventanaCliente.
                            setListenerBtnSiguiente(new ManejadorDeEventosCliente());
                }
            }
            else if(e.getActionCommand().equalsIgnoreCase("productos")){
                System.out.println("btn porducto");
            }
            else if(e.getActionCommand().equalsIgnoreCase("proveedor")){
                System.out.println("btn proveedor");
            }
        }
        
    }
    
    //ventanaCliente
    class ManejadorDeEventosCliente implements ActionListener {

        @Override
        @SuppressWarnings("deprecation")
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("siguiente")){
                superMercado.
                        agregarCliente(ventanaCliente.getTxtNombre(), 
                                ventanaCliente.getTxtID());
                
                if(ventanaCarritoCliente != null){
                    ventanaCarritoCliente.show();
                }
                else{
                    ventanaCarritoCliente = new VentanaCarritoCliente();
                }
                ventanaCliente.dispose();
            }
        }
        
    }
}
