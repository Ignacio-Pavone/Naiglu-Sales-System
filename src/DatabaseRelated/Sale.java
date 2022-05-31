package DatabaseRelated;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Sale implements Serializable {
    private double operationNumber;
    private String customerName;
    private double totalAmmount;
    private LocalDateTime date;
    private String dateFormatted;
    private boolean invoiced;
    private String customerId;

    public Sale(){
        this.invoiced = false;
    }

    public Sale(boolean invoiced){
        this.invoiced = invoiced;
    }


    public Sale(String customerName, double totalAmmount) {

        this.operationNumber = Math.floor((Math.random() * 500 + 1));
        this.customerName = customerName;
        this.totalAmmount = totalAmmount;
        date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.dateFormatted = date.format(formatter);
    }

    public Sale(String nameAux, double amount, String id) {
        this.operationNumber = Math.floor((Math.random() * 500 + 1));
        this.customerName = nameAux;
        this.totalAmmount = totalAmmount;
        date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.dateFormatted = date.format(formatter);
        this.totalAmmount = amount;
        this.customerId = id;

    }

    public Sale(double operationNumber, String name, double totalAmmount) {
        this.operationNumber = operationNumber;
        customerName = name;
        this.totalAmmount = totalAmmount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getCustomerId() {
        return customerId;
    }

    public boolean isInvoiced() {
        return invoiced;
    }

    public void setInvoiced(boolean invoice) {
        this.invoiced = invoice;
    }

    public String getDateFormatted() {
        return dateFormatted;
    }

    public Double getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(Double operationNumber) {
        this.operationNumber = operationNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getTotalAmmount() {
        return totalAmmount;
    }

    public void setTotalAmmount(double totalAmmount) {
        this.totalAmmount = totalAmmount;
    }

    public boolean compareTo(double d){
        if (this.getOperationNumber() == d) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationNumber);
    }

    @Override
    public String toString() {
        return "Sell{" +
                "operationNumber=" + operationNumber +
                ", customerName='" + customerName + '\'' +
                ", totalAmmount=" + totalAmmount +
                ", date=" + date +
                ", dateFormatted='" + dateFormatted + '\'' +
                '}';
    }
}
