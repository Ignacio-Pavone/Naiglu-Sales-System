package PersonRelated;

public class MyBusiness extends Entity implements I_SaleAndPurchase{
    private int balance;

    public MyBusiness() {
    }

    @Override
    public String generateDataForBills() {
        return this.getName() +
                "\n" +
                this.getTaxpayerID() +
                "\n" +
                this.getPhoneNumber() +
                "\n";
    }

}
