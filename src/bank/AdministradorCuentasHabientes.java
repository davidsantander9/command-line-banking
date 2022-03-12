package bank;

import handlers.PropertyHandler;

import java.util.HashMap;

public class AdministradorCuentasHabientes {

    final private HashMap<String, CuentaHabiente> mapaCuentaHabiente = new HashMap<>();
    final private AdministradorProducto adm;
    private Configuracion conf;
    private static final String PROP_MAX_CREDIT_LINE = "system.max.credit.line.per.monthly.income";

    public AdministradorCuentasHabientes() {
        this.conf = new Configuracion();
        adm = new AdministradorProducto(conf);
        try{
            PropertyHandler.load("/application-default.properties", "application.properties");
            double maxCreditLine = Double.parseDouble(PropertyHandler.getStringProperty(PROP_MAX_CREDIT_LINE));
            conf.setMaxLineaCreditoPorIngresoMensual(maxCreditLine);
        }catch (Exception e) {
            System.err.printf("%s: %s%n", e.getClass().getName(), e.getMessage());
            conf.setMaxLineaCreditoPorIngresoMensual(4.0);
        }
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
            if(mapaCuentaHabiente.get(numCliente) != null)
                mapaCuentaHabiente.get(numCliente).agregarProducto(product);
        }catch(NullPointerException e){
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
        }else{
            System.err.println("Error");
        }
    }

    public void mostrarProductosCliente(String numCliente){
        if(mapaCuentaHabiente.get(numCliente) != null)
            mapaCuentaHabiente.get(numCliente).mostrarProductos();
        else
            System.err.println("Error");
    }


    public void retiro(String numCliente, String id, double amount){
        if(mapaCuentaHabiente.get(numCliente) != null)
            mapaCuentaHabiente.get(numCliente).retiroProducto(id, amount);
        else
            System.err.println("Error");
    }

    public void deposito(String numCliente, String id, double amount){
        if(mapaCuentaHabiente != null)
            mapaCuentaHabiente.get(numCliente).depositoProducto(id, amount);
        else
            System.err.println("Error");
    }

    public void corte(String numCliente, String id){
        if(mapaCuentaHabiente != null)
            mapaCuentaHabiente.get(numCliente).corteProducto(id);
        else
            System.err.println("Error");
    }

    public void imprimirEstadoCuenta(String numCliente, String id){
        if(mapaCuentaHabiente != null)
            mapaCuentaHabiente.get(numCliente).imprimirEstadoCuentaProducto(id);
        else
            System.err.println("Error");
    }

    public void cancelarProductos(String numCliente){
        if(mapaCuentaHabiente != null)
            mapaCuentaHabiente.get(numCliente).cancelarProductos();
        else
            System.err.println("Error");
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

    public void changeLineadeCreditoMaximaPorIngresoMensual(){
        try{
            PropertyHandler.load("/application-default.properties", "application.properties");
            double maxCreditLine = Double.parseDouble(PropertyHandler.getStringProperty(PROP_MAX_CREDIT_LINE));
            conf.setMaxLineaCreditoPorIngresoMensual(maxCreditLine);
        }catch (Exception e) {
            System.err.printf("%s: %s%n", e.getClass().getName(), e.getMessage());
            conf.setMaxLineaCreditoPorIngresoMensual(4.0);
        }
    }

    public void setImpuesto(double impuesto){
        CuentaInversion.setIMPUESTO(impuesto);
    }

    public void tranferencia(String numClienteOrigen, String idOrigen, String numClienteDestino, String idDestino, double amount){
        if(!numClienteDestino.equals(numClienteOrigen)){
            if(mapaCuentaHabiente.get(numClienteOrigen) != null && mapaCuentaHabiente.get(numClienteDestino) != null){
                mapaCuentaHabiente.get(numClienteOrigen).retiroProducto(idOrigen, amount);
                mapaCuentaHabiente.get(numClienteDestino).depositoProducto(idDestino, amount);
            } else
                System.err.println("Error");
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
