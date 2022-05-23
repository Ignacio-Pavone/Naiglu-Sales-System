package DatabaseRelated;

public class Venta {
    private double numero;
    private String nombreCliente;
    private double totalAmmount;

    public Venta(String nombreCliente, double totalAmmount) {

        this.numero = Math.floor((Math.random() * 500 + 1));
        this.nombreCliente = nombreCliente;
        this.totalAmmount = totalAmmount;
    }

    public Double getNumero() {
        return numero;
    }

    public void setNumero(Double numero) {
        this.numero = numero;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public double getTotalAmmount() {
        return totalAmmount;
    }

    public void setTotalAmmount(double totalAmmount) {
        this.totalAmmount = totalAmmount;
    }
}
