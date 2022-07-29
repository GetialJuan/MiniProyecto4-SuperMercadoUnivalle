/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.VentanaMenu;

/**
 *
 * @author Juan
 */
public class SuperMercadoController {
    VentanaMenu ventanaMenu;

    public SuperMercadoController() {
        ventanaMenu = new VentanaMenu();
        ventanaMenu.AgregarListenersBtns(new ManejadorDeEventosMenu());
    }
    
    class ManejadorDeEventosMenu implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equalsIgnoreCase("cliente")){
                System.out.println("btn cliente");
            }
            else if(e.getActionCommand().equalsIgnoreCase("productos")){
                System.out.println("btn porducto");
            }
            else if(e.getActionCommand().equalsIgnoreCase("proveedor")){
                System.out.println("btn proveedor");
            }
        }
        
    }
}
