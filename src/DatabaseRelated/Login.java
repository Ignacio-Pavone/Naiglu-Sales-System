package DatabaseRelated;

import UserRelated.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JDialog {
    private JPasswordField passwordField2;
    private JButton enterButton;
    private JButton cancelButton;
    private JPanel loginPanel;
    private JTextField email;
    private JButton registerButton;
    public static User user;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Connect cn = new Connect();

    public Login(JFrame parent) {
        super(parent);
        setTitle("DatabaseRelated.Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(600, 550));
        setModal(true);

        setUndecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegister();
            }
        });
        setVisible(true);
    }

    private void loginUser() {
        String email = Login.this.email.getText();
        String password = String.valueOf(passwordField2.getPassword());
        user = authenticate(email, password);
        if (user != null) {
            dispose();
            JOptionPane.showMessageDialog(null, "Welcome " + user.name);
            Inventory nuevo = new Inventory(null);
            nuevo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(Login.this,
                    "Incorrect email or password.", "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showRegister() {
        Register nuevo = new Register(null);
        nuevo.setVisible(true);
    }

    private User authenticate(String email, String password) {
        User user = null;
        try {
            String sql = "SELECT * FROM usuarios WHERE correo=? AND password=?";
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new User();
                user.ID = rs.getInt("id");
                user.name = rs.getString("nombre");
                user.email = rs.getString("correo");
                user.password = rs.getString("password");
            }
            rs.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
