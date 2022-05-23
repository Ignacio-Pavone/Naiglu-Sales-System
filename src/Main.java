import DatabaseRelated.Login;
import UserRelated.User;

public class Main {

    public static void main(String[] args) {
        Login login = new Login(null);
        User user = Login.employee;
        if (user != null) {
            System.out.println("ID " + user.getID());
            System.out.println("Successful authentication: " + user.getName());
            System.out.println("Email: " + user.getEmail());
        }
        else {
            System.out.println("Cancelled");
        }
    }
}
