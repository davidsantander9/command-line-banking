package bank;

public abstract class CuentaBancaria implements ProductoFinanciero {
    private double balance;
    private String id;

    public CuentaBancaria(String id, double balanceInicial) {
        this.id = id;
        this.balance = balanceInicial;
    }

    public double getBalance() {
        return balance;
    }

    public void agregarFondos(double importe) {
        this.balance += importe;
    }

    public void reducirFondos(double importe) {
        if(importe > balance)
            System.out.println("Fondos insuficientes");
        else
            this.balance -= importe;
    }

    @Override
    public double getSaldo() {
        return balance;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public abstract void imprimirEstadoCuenta();

}
