package DatabaseRelated;

import UserRelated.Employee;
import UserRelated.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

    public Register(JFrame parent) {
        super(parent);
        setTitle("DatabaseRelated.Register");
        setContentPane(register);
        setMinimumSize(new Dimension(600, 550));
        setModal(true);
        setLocationRelativeTo(null);
        setUndecorated(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);



        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerButtonLogic();
            }
            });
        }

        public void registerButtonLogic () {
            Employee employee = new Employee();
            if (!nameTextField.getText().isEmpty() && isEmail(emailTextField.getText()) && passwordTextField.getPassword().length > 0) {
                employee.setName(nameTextField.getText());
                employee.setEmail(emailTextField.getText());
                employee.setPassword(passwordTextField.getText());
                Registrarse(employee);
            } else {
                JOptionPane.showMessageDialog(null, "ERROR");
            }
        }


    public boolean Registrarse(Employee employee) {
        String sql = "INSERT INTO usuarios (nombre, correo, password, esAdmin) VALUES (?,?,?,?)";
        try {
            con = connection.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getPassword());
            ps.setBoolean(4,employee.isAdmin());
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
}
