package UserRelated;

public class Employee extends User{
    private boolean admin;


    public Employee() {
    }

    public Employee(String email, String password) {
        super(email, password);
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return super.toString() + "Employee{" +
                "admin=" + admin +
                '}';
    }
}
