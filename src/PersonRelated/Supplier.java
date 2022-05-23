package PersonRelated;

import java.util.Objects;

public class Supplier extends Entity implements I_SaleAndPurchase{
    private String workingArea;

    public Supplier() {
    }

    public String getWorkingArea() {
        return workingArea;
    }


    @Override
    public int hashCode() {
        return 1;
    }

    public void setWorkingArea(String workingArea) {
        this.workingArea = workingArea;
    }

    @Override
    public String generateDataForBills() {
        return this.getName() +
                "\n" +
                this.getTaxpayerID() +
                "\n" +
                this.getPhoneNumber() +
                "\n" +
                this.getWorkingArea() +
                "\n";
    }

    @Override
    public String toString() {
        return super.toString() + "|| Working area: " + workingArea;
    }
}

