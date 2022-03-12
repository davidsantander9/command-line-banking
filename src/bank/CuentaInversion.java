package bank;

public class CuentaInversion extends CuentaBancaria {
    public static double IMPUESTO = .15;
    private double interesAlCorte;

    public CuentaInversion(String id, double balanceInicial, double interesAlCorte) {
        super(id, balanceInicial);
        this.interesAlCorte = interesAlCorte;
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
