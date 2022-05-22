import DatabaseRelated.Login;
import UserRelated.User;

public class Main {

    public static void main(String[] args) {
        Login login = new Login(null);
        User user = Login.user;
        if (user != null) {
            System.out.println("ID " + user.ID);
            System.out.println("Successful authentication: " + user.name);
            System.out.println("Email: " + user.email);
        }
        else {
            System.out.println("Cancelled");
        }
    }
}
