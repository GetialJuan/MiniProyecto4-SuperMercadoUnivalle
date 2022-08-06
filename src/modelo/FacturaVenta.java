
package modelo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * MiniProyecto 4 - SuperMercado Univalle
 * @author Juan Sebastian Getial Getial <202124644>
 * @author Mauricio Muñoz Gutierrez <202123687>
 * @author Santiago Torres Carvajal <>
 * @profesor Luis Yovany Romo Portilla
 * Clase que representa a una factura de venta
 */
public class FacturaVenta {
    private String nombreCliente;
    private String iDCliente;
    private final ArrayList<HashMap<String,String>> carrito;
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
