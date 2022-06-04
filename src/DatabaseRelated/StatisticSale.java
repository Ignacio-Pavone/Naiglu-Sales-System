package DatabaseRelated;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class StatisticSale implements Serializable {
    private String date;
    private Double totalInvoices;
    private int invoiceAmount;

    public StatisticSale(String date, Double totalInvoices, int invoiceAmount) {
        this.date = date;
        this.totalInvoices = totalInvoices;
        this.invoiceAmount = invoiceAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getTotalInvoices() {
        return totalInvoices;
    }

    public void setTotalInvoices(Double totalInvoices) {
        this.totalInvoices = totalInvoices;
    }

    public int getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(int invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    @Override
    public String toString() {
        return "StatisticSale{" +
                "date='" + date + '\'' +
                ", totalInvoices=" + totalInvoices +
                ", invoiceAmount=" + invoiceAmount +
                '}';
    }
}
