package PersonRelated;

public class MyBusiness extends Entity implements I_SaleAndPurchase{
    private int balance;

    public MyBusiness() {
    }

    public MyBusiness(String name, String taxpayerID, String phoneNumber) {
        super(name, taxpayerID, phoneNumber);
    }

    @Override
    public String generateDataForBills() {
        return "+ Business name: "+ this.getName() + "," +
                "+ Taxpayer ID:" + this.getTaxpayerID() + "," +
                "+ Phone number: "+this.getPhoneNumber() + ",";
    }

}
