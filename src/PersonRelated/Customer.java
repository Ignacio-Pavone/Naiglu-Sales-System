package PersonRelated;

public class Customer extends Entity implements I_SaleAndPurchase{
    private String category;

    public Customer() {
    }

    @Override
    public String generateDataForBills() {
        return this.getName() +
                "\n" +
                this.getTaxpayerID() +
                "\n" +
                this.getPhoneNumber() +
                "\n" +
                this.getCategory() +
                "\n";

    }

    public String getCategory() {
        return category;
    }
}
