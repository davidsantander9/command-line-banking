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

    public void mostrarInformaciónCuenta(){
        int numeroCuentas = 0;
        if(this.getProductos() != null){
            numeroCuentas = this.getProductos().size();
        }
        System.out.printf("client %s with number %s has %d accounts \n", this.getCliente().getNombre(), this.getCliente().getNumCliente(), numeroCuentas);
    }

    public void mostrarProductos(){
        HashMap<String ,ProductoFinanciero> productos = this.getProductos();
        if(productos != null){
            System.out.println();
            System.out.println("*************************************************");
            for(ProductoFinanciero producto: productos.values()){
                String textClass = producto.getClass().toString();
                String tipoProducto = textClass.substring(textClass.indexOf("."));
                System.out.println("id: " + producto.getId() + " type of product: " + tipoProducto + " balance " + producto.getSaldo() );
            }
            System.out.println("*************************************************");
            System.out.println();
        }else{
            System.out.println("No info");
        }
    }

    public ProductoFinanciero getProduct(String id){
        try{
            HashMap<String ,ProductoFinanciero> productos = this.getProductos();
            return productos.get(id);
        } catch (NullPointerException exception){
            System.err.println("Error");
            return null;
        }
    }

    public void retiroProducto(String id, double amount){
        ProductoFinanciero producto = getProduct(id);
        if(producto != null){
            if(producto instanceof TarjetaCredito){
                ((TarjetaCredito) producto).cargarTarjeta(amount);
            }else if(producto instanceof CuentaBancaria) {
                ((CuentaBancaria) producto).reducirFondos(amount);
            }
        }
    }

    public void depositoProducto(String id, double amount){
        ProductoFinanciero producto = getProduct(id);
        if(producto != null){
            if(producto instanceof TarjetaCredito){
                ((TarjetaCredito) producto).pagarTarjeta(amount);
            }else if(producto instanceof CuentaBancaria) {
                ((CuentaBancaria) producto).agregarFondos(amount);
            }
        }
    }

    public void corteProducto(String id){
        ProductoFinanciero producto = getProduct(id);
        if(producto != null){
            if(producto instanceof CuentaInversion){
                ((CuentaInversion) producto).aplicarCorte();
            }
        }
    }

    public void imprimirEstadoCuentaProducto(String id){
        ProductoFinanciero producto = getProduct(id);
        if(producto != null){
            System.out.println("*** " + this.getCliente().getNombre() + " ***");
            producto.imprimirEstadoCuenta();
        }
    }

    public void cancelarProductos(){
        try{
            if(puedeCancelar()){
                administrador.eliminarProductos(this.cliente);
            }
        }catch (IndexOutOfBoundsException exception){
            System.out.println("Error");
        }
    }

    public boolean puedeCancelar(){
        return administrador.puedeCancelar(cliente);
    }

}
