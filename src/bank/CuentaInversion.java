package bank;

public class CuentaInversion extends CuentaBancaria {
    public static double IMPUESTO = .15;
    private double interesAlCorte;

    public CuentaInversion(double balanceInicial, double interesAlCorte, double impuesto) {
        super(balanceInicial);
        this.interesAlCorte = interesAlCorte;
        CuentaInversion.IMPUESTO = impuesto;
    }

    public void aplicarCorte() {
        double interesBruto = getBalance() * interesAlCorte;
        double interesNeto = interesBruto - interesBruto*IMPUESTO;
        agregarFondos(interesNeto);
    }

    @Override
    public void imprimirEstadoCuenta() {
        System.out.println("Estado de Cuenta de Inversión ...");
        System.out.println("Balance: " + getBalance());
        System.out.println("Tasa de Interés: " + interesAlCorte);
    }

    public static double getIMPUESTO() {
        return IMPUESTO;
    }

    public static void setIMPUESTO(double IMPUESTO) {
        CuentaInversion.IMPUESTO = IMPUESTO;
    }

}
