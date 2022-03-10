package bank;

public class Cliente {
    private String nombre;
    private String numCliente;
    private double ingresoMensual;

    public Cliente(String nombre, String numCliente, double ingresoMensual) {
        this.nombre = nombre;
        this.numCliente = numCliente;
        this.ingresoMensual = ingresoMensual;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumCliente() {
        return numCliente;
    }

    public double getIngresoMensual() {
        return ingresoMensual;
    }

}
