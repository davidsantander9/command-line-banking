package bank;

import java.util.HashMap;

public class CuentaHabiente {
    private Cliente cliente;
    private AdministradorProducto administrador;

    public CuentaHabiente(Cliente cliente, AdministradorProducto administrador) {
        this.cliente = cliente;
        this.administrador = administrador;
    }

    public AdministradorProducto getAdministrador() {
        return administrador;
    }

    public void setAdministrador(AdministradorProducto administrador) {
        this.administrador = administrador;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void agregarProducto(ProductoFinanciero producto){
        administrador.agregarProducto(cliente, producto, producto.getId());
    }

    public HashMap getProductos(){
        return administrador.getProductos(cliente.getNumCliente());
    }

    public void eliminarProductos() {

    };

    public boolean puedeCancelar(){
        return administrador.puedeCancelar(cliente);
    }

}
