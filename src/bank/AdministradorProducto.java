package bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdministradorProducto {
    private Configuracion conf;
    private Map<String, HashMap<String,ProductoFinanciero>> mapaProductos = new HashMap<>();

    public AdministradorProducto(Configuracion conf) {
        this.conf = conf;
    }

    public void agregarProducto(Cliente cliente, ProductoFinanciero producto, String id) {
        HashMap<String, ProductoFinanciero> productos = mapaProductos.get(cliente.getNumCliente());
        if(productos == null) {
            productos = new HashMap<>();
            mapaProductos.put(cliente.getNumCliente(), productos);
        }
        if(producto instanceof TarjetaCredito) {
            double ingresoMensual = cliente.getIngresoMensual();
            double lineaCredito = ((TarjetaCredito) producto).getLineaCredito();
            if(lineaCredito > ingresoMensual * conf.getMaxLineaCreditoPorIngresoMensual()) {
                System.out.println("Linea de credito excesiva para este cliente");
                return;
            }
        }
        if(producto instanceof CuentaInversion){
            boolean tieneCuentaCheques = false;
            for(ProductoFinanciero productoCliente: productos.values()){
                if(productoCliente instanceof CuentaCheques){
                    tieneCuentaCheques = true;
                    break;
                }
            }
            if(!tieneCuentaCheques){
                System.out.println("Alta del producto rechazada");
                System.out.println("Para poder tener una cuenta de inversión, es requisito haber abierto antes, una cuenta de cheques.");
                return;
            }
        }
        if(productos.get(id) == null){
            productos.put(id, producto);
            System.out.println("Alta del producto aprobada");
        }else{
            System.out.println("Ya existe el id del producto");
        }
    }

    public HashMap<String ,ProductoFinanciero> getProductos(String numCliente) {
        HashMap productos = mapaProductos.get(numCliente);
        //if(productos == null)
        //    System.out.println("\t El cliente no tiene productos asignados");
        return productos;
    }

    public boolean puedeCancelar(Cliente cliente) {
        HashMap<String, ProductoFinanciero> productos = getProductos(cliente.getNumCliente());
        boolean resultado = true;
        for(ProductoFinanciero pf : productos.values()) {
            if(pf.getSaldo() != 0.0) {
                resultado = false;
                pf.imprimirEstadoCuenta();
            }
        }
        return resultado;
    }

}
