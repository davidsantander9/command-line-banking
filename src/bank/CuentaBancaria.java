package bank;

public abstract class CuentaBancaria implements ProductoFinanciero {
    private double balance;

    public CuentaBancaria(double balanceInicial) {
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

    public abstract void imprimirEstadoCuenta();
}
