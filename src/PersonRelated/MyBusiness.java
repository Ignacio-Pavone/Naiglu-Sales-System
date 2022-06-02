package PersonRelated;

public class MyBusiness extends Entity implements I_SaleAndPurchase{
    private double balance;

    public MyBusiness() {
    }

    public MyBusiness(String name, String taxpayerID, String phoneNumber) {
        super(name, taxpayerID, phoneNumber);
        balance = 0;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String generateDataForBills() {
        return "+ Business name: "+ this.getName() + "," +
                "+ Taxpayer ID:" + this.getTaxpayerID() + "," +
                "+ Phone number: "+this.getPhoneNumber() + "," +
                "+ Balance: "+this.getBalance();
    }

}
