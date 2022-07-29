/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.VentanaCliente;
import vista.VentanaMenu;

/**
 *
 * @author Juan
 */
public class SuperMercadoController {
    VentanaMenu ventanaMenu;
    VentanaCliente ventanaCliente;

    public SuperMercadoController() {
        ventanaMenu = new VentanaMenu();
        ventanaMenu.AgregarListenersBtns(new ManejadorDeEventosMenu());
    }
    
    class ManejadorDeEventosMenu implements ActionListener {

        @Override
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
    
    class ManejadorDeEventosCliente implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("siguiente")){
                System.out.println("btn siguinete");
            }
        }
        
    }
}
