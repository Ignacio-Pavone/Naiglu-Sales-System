package UserRelated;

public class Employee extends User{
    private boolean admin;


    public Employee() {
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
