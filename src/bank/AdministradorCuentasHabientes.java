package bank;

import java.util.ArrayList;

public class AdministradorCuentasHabientes {

    private ArrayList<CuentaHabiente> cuentasHabientes = new ArrayList<>();
    private Configuracion conf;
    private AdministradorProducto adm;

    public AdministradorCuentasHabientes() {
        conf = new Configuracion();
        conf.setMaxLineaCreditoPorIngresoMensual(4.0);
        adm = new AdministradorProducto(conf);
    }

    public void agregarCuentaHabiente(Cliente cliente){
        CuentaHabiente cuentaHabiente = new CuentaHabiente(cliente, adm);
        cuentasHabientes.add(cuentaHabiente);
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
            System.out.printf("%d. El cliente %s con numero %s tiene %d cuentas \n", i, nombre, numCliente, numeroCuentas);
        }
    }

    public static CuentaInversion getCuentaInversion(){
        return new CuentaInversion(1000, 0.05, .15);
    }

    public static CuentaCheques getCuentaCheques(){
        return new CuentaCheques(100, 5.0);
    }

    public static TarjetaCredito getTarjetaCredito(){
        return new TarjetaCredito(12000);
    }

}
