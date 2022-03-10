package bank;

public class TarjetaCredito implements ProductoFinanciero {

    private String id;
    private double lineaCredito;
    private double saldo;

    public TarjetaCredito(String id, double lineaCredito) {
        this.id = id;
        this.lineaCredito = lineaCredito;
        this.saldo = 0;
    }

    public double getLineaCredito() {
        return lineaCredito;
    }

    public double getSaldo() {
        return saldo;
    }

    @Override
    public void imprimirEstadoCuenta() {
        System.out.println("Estado de Cuenta - Tarjeta de Credito");
        System.out.println("Saldo: " + saldo);
        System.out.println("Línea de crédito: " + lineaCredito);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public void pagarTarjeta(double importe) {
        saldo += importe;
    }

    public void cargarTarjeta(double importe) {
        if(saldo - importe < lineaCredito * -1)
            System.out.println("Linea de credito insuficiente");
        else
            saldo -= importe;
    }

}
