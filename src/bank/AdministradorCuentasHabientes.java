package bank;

import java.util.ArrayList;
import java.util.HashMap;

public class AdministradorCuentasHabientes {

    final private ArrayList<CuentaHabiente> cuentasHabientes = new ArrayList<>();
    final private HashMap<String, Cliente> clientes = new HashMap<>();
    final private AdministradorProducto adm;
    private Configuracion conf;

    public AdministradorCuentasHabientes() {
        this.conf = new Configuracion();
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
        try{
            cuentasHabientes.get(index).agregarProducto(product);
        }catch(IndexOutOfBoundsException e){
            System.err.println("Error");
        }
    }

    public void mostrarInfoCuentasHabiente(){
        System.out.println();
        System.out.println("*************************************************");
        for(int i = 0; i < cuentasHabientes.size(); i++){
            CuentaHabiente cuenta  = cuentasHabientes.get(i);
            System.out.print(i + " ");
            cuenta.mostrarInformaciónCuenta();
        }
        System.out.println("*************************************************");
        System.out.println();
    }

    public void mostrarProductosCliente(int index){
        cuentasHabientes.get(index).mostrarProductos();
    }


    public void retiro(int index, String id, double amount){
        cuentasHabientes.get(index).retiroProducto(id, amount);
    }

    public void deposito(int index, String id, double amount){
        cuentasHabientes.get(index).depositoProducto(id, amount);
    }

    public void corte(int index, String id){
        cuentasHabientes.get(index).corteProducto(id);
    }

    public void imprimirEstadoCuenta(int index, String id){
        cuentasHabientes.get(index).imprimirEstadoCuentaProducto(id);
    }

    public void cancelarProductos(int index){
        cuentasHabientes.get(index).cancelarProductos();
    }

    public void cancelarCuentaHabiente(int index) {
        try {
            if (productosCancelables(index)) {
                cuentasHabientes.remove(index);
            }
        } catch (IndexOutOfBoundsException exception) {
            System.out.println("Error");
        }
    }

    public boolean productosCancelables(int index){
        try{
            return adm.puedeCancelar(cuentasHabientes.get(index).getCliente());
        }catch (IndexOutOfBoundsException exception){
            return false;
        }
    }

    public void changeLineadeCreditoMaximaPorIngresoMensual(double maxLineaCredito){
        this.conf.setMaxLineaCreditoPorIngresoMensual(maxLineaCredito);
    }

    public void setImpuesto(double impuesto){
        CuentaInversion.setIMPUESTO(impuesto);
    }

    public void tranferencia(int indexOrigen, String idOrigen, int indexDestino, String idDestino, Double ammount){
        if(indexOrigen != indexDestino){
            cuentasHabientes.get(indexOrigen).retiroProducto(idOrigen, ammount);
            cuentasHabientes.get(indexDestino).depositoProducto(idDestino, ammount);
        }
    }

    public static CuentaInversion getCuentaInversion(String id, double balance, double interesAlCorte){
        return new CuentaInversion(id, balance, interesAlCorte);
    }

    public static CuentaCheques getCuentaCheques(String id, double balanceInicial, double comisionRetiro){
        return new CuentaCheques(id, balanceInicial, comisionRetiro);
    }

    public static TarjetaCredito getTarjetaCredito(String id, double lineaCredito){
        return new TarjetaCredito(id, lineaCredito);
    }

    public static Cliente getCliente(String nombreCliente,String numCliente,double ingresosMensuales){
        return new Cliente(nombreCliente, numCliente, ingresosMensuales);
    }
}
