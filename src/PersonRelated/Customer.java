package PersonRelated;

public class Customer extends Entity implements I_SaleAndPurchase{
    private String category;

    public Customer() {
    }

    public Customer(String name, String taxpayerID, String phoneNumber, String category) {
        super(name, taxpayerID, phoneNumber);
        this.category = category;
    }

    public void setCategory(String category) {
        this.category = category;
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
