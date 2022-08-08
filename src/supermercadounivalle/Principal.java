
package supermercadounivalle;

import controlador.SuperMercadoController;

/**
 * MiniProyecto 4 - SuperMercado Univalle
 * @author Juan Sebastian Getial Getial <202124644>
 * @author Mauricio Muñoz Gutierrez <202123687>
 * @author Santiago Torres Carvajal <2140010>
 * @profesor Luis Yovany Romo Portilla
 * Clase principal
 */
public class Principal {
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        SuperMercadoController superMercadoController = 
                new SuperMercadoController();
        superMercadoController.attachShutDownHook();
    }
    
}
