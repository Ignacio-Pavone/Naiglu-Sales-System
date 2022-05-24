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

    public String getCategory() {
        return category;
    }
    @Override
    public String generateDataForBills() {
        return "+ Customer name name: "+ this.getName() + "," +
                "+ Taxpayer ID:" + this.getTaxpayerID() + "," +
                "+ Phone number: "+this.getPhoneNumber() + ","+
                "+ Category " + this.getCategory() +",";
    }
}
