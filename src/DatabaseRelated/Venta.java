package DatabaseRelated;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class Venta {
    private double numero;
    private String nombreCliente;
    private double totalAmmount;
    private LocalDateTime date;
    private String fecha;
    private boolean facturado;

    public Venta(){
        this.facturado = false;
    }


    public Venta(String nombreCliente, double totalAmmount) {

        this.numero = Math.floor((Math.random() * 500 + 1));
        this.nombreCliente = nombreCliente;
        this.totalAmmount = totalAmmount;
        date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.fecha = date.format(formatter);
    }

    public boolean isFacturado() {
        return facturado;
    }

    public void setFacturado(boolean facturado) {
        this.facturado = facturado;
    }

    public String getFecha() {
        return fecha;
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

    @Override
    public String toString() {
        return "Venta{" +
                "numero=" + numero +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", totalAmmount=" + totalAmmount +
                ", date=" + date +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
