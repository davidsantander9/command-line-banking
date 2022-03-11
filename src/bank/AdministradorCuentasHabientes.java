package bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdministradorCuentasHabientes {

    private ArrayList<CuentaHabiente> cuentasHabientes = new ArrayList<>();
    private HashMap<String, Cliente> clientes = new HashMap<>();
    private Configuracion conf = new Configuracion();;
    private AdministradorProducto adm;

    public AdministradorCuentasHabientes() {
        conf.setMaxLineaCreditoPorIngresoMensual(4.0);
        adm = new AdministradorProducto(conf);
    }

    public void agregarCuentaHabiente(Cliente cliente){
        if(clientes.get(cliente.getNumCliente()) == null){
            clientes.put(cliente.getNumCliente(), cliente);
            cuentasHabientes.add(new CuentaHabiente(cliente, adm));
        }else{
            System.out.println("Number client already exists");
        }
    }

    public void agregarProducto(int index, ProductoFinanciero product){
        cuentasHabientes.get(index).agregarProducto(product);
    }

    public void mostrarInfoCuentasHabiente(){
        for(int i = 0; i < cuentasHabientes.size(); i++){
            CuentaHabiente cuenta  = cuentasHabientes.get(i);
            String nombre = cuenta.getCliente().getNombre();
            String numCliente = cuenta.getCliente().getNumCliente();

            int numeroCuentas = 0;
            if(cuenta.getProductos() != null){
                numeroCuentas = cuenta.getProductos().size();
            }
            System.out.printf("%d. client %s with number %s has %d accounts \n", i, nombre, numCliente, numeroCuentas);
        }
    }

    public void mostraCuentasClientes(int index){
        HashMap<String ,ProductoFinanciero> productos = cuentasHabientes.get(index).getProductos();
        for(ProductoFinanciero producto: productos.values()){
            String textClass = producto.getClass().toString();
            String tipoProducto = textClass.substring(textClass.indexOf(".") + 1);
            System.out.println("id " + producto.getId() + " type of product: " + tipoProducto);
        }
    }

    public static CuentaInversion getCuentaInversion(String id, double balance, double interesAlCorte, double impuesto){
        return new CuentaInversion(id, balance, interesAlCorte, impuesto);
    }

    public static CuentaCheques getCuentaCheques(String id, double balanceInicial, double comisionRetiro){
        return new CuentaCheques(id, balanceInicial, comisionRetiro);
    }

    public static TarjetaCredito getTarjetaCredito(String id, double lineaCredito){
        return new TarjetaCredito(id, lineaCredito);
    }

}
