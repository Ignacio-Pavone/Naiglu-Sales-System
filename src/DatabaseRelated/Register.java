package DatabaseRelated;

import DatabaseRelated.Connect;
import UserRelated.Usuario;
import com.sun.java.accessibility.util.GUIInitializedListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends JDialog {
    private JTextField nameTextField;
    private JPasswordField passwordTextField;
    private JButton cancelButton;
    private JPanel register;
    private JTextField emailTextField;
    private JButton registerButton;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;


    Connect connection = new Connect();

    public boolean isEmail(String correo) {
        Pattern pat = null;
        Matcher mat = null;
        pat = Pattern.compile("^[\\w\\-\\_\\+]+(\\.[\\w\\-\\_]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$");
        mat = pat.matcher(correo);
        if (mat.find()) {
            return true;
        } else {
            return false;
        }
    }

    public Register(JFrame parent) {
        super(parent);
        setTitle("DatabaseRelated.Register");
        setContentPane(register);
        setMinimumSize(new Dimension(400, 450));
        setModal(true);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerButtonLogic();
            }
            });
        }

        public void registerButtonLogic () {
            Usuario user = new Usuario();
            if (!nameTextField.getText().isEmpty() && isEmail(emailTextField.getText()) && passwordTextField.getPassword().length > 0) {
                user.setName(nameTextField.getText());
                user.setEmail(emailTextField.getText());
                user.setPassword(passwordTextField.getText());
                Registrarse(user);
            } else {
                JOptionPane.showMessageDialog(null, "ERROR");
            }
        }


    public boolean Registrarse(Usuario user) {
        String sql = "INSERT INTO usuarios (nombre, correo, password) VALUES (?,?,?)";
        try {
            con = connection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            JOptionPane.showMessageDialog(null, "SUCCESSFUL REGISTRATION");
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }
}
