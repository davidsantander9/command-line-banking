package bank;

import java.util.HashMap;

public class AdministradorCuentasHabientes {

    //final private ArrayList<CuentaHabiente> cuentasHabientes = new ArrayList<>();
    final private HashMap<String, CuentaHabiente> mapaCuentaHabiente = new HashMap<>();
    //final private HashMap<String, Cliente> mapaCliente = new HashMap<>();
    final private AdministradorProducto adm;
    private Configuracion conf;

    public AdministradorCuentasHabientes() {
        this.conf = new Configuracion();
        conf.setMaxLineaCreditoPorIngresoMensual(4.0);
        adm = new AdministradorProducto(conf);
    }

    public void agregarCuentaHabiente(Cliente cliente){
        if(mapaCuentaHabiente.get(cliente.getNumCliente()) == null){
            mapaCuentaHabiente.put(cliente.getNumCliente(), new CuentaHabiente(cliente, adm));
        }else{
            System.out.println("Number client already exists");
        }
    }

    public void agregarProducto(String numCliente, ProductoFinanciero product){
        try{
            mapaCuentaHabiente.get(numCliente).agregarProducto(product);
        }catch(IndexOutOfBoundsException e){
            System.err.println("Error");
        }
    }

    public void mostrarInfoCuentasHabiente(){
        System.out.println();
        System.out.println("*************************************************");
        if(mapaCuentaHabiente != null){
            for(CuentaHabiente cuenta: mapaCuentaHabiente.values()){
                cuenta.mostrarInformaciónCuenta();
            }
            System.out.println("*************************************************");
            System.out.println();
        }
    }

    public void mostrarProductosCliente(String numCliente){
        mapaCuentaHabiente.get(numCliente).mostrarProductos();
    }


    public void retiro(String numCliente, String id, double amount){
        mapaCuentaHabiente.get(numCliente).retiroProducto(id, amount);
    }

    public void deposito(String numCliente, String id, double amount){
        mapaCuentaHabiente.get(numCliente).depositoProducto(id, amount);
    }

    public void corte(String numCliente, String id){
        mapaCuentaHabiente.get(numCliente).corteProducto(id);
    }

    public void imprimirEstadoCuenta(String numCliente, String id){
        mapaCuentaHabiente.get(numCliente).imprimirEstadoCuentaProducto(id);
    }

    public void cancelarProductos(String numCliente){
        mapaCuentaHabiente.get(numCliente).cancelarProductos();
    }

    public void cancelarCuentaHabiente(String numCliente) {
        try {
            if (productosCancelables(numCliente)) {
                mapaCuentaHabiente.remove(numCliente);
            }
        } catch (IndexOutOfBoundsException exception) {
            System.out.println("Error");
        }
    }

    public boolean productosCancelables(String numClientex){
        try{
            return adm.puedeCancelar(mapaCuentaHabiente.get(numClientex).getCliente());
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

    public void tranferencia(String numClienteOrigen, String idOrigen, String numClienteDestino, String idDestino, double amount){
        if(!numClienteDestino.equals(numClienteOrigen)){
            mapaCuentaHabiente.get(numClienteOrigen).retiroProducto(idOrigen, amount);
            mapaCuentaHabiente.get(numClienteDestino).depositoProducto(idDestino, amount);
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
