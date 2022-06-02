package DatabaseRelated;

import UserRelated.Employee;

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
    Connect connection = new Connect();

    public Register(JFrame parent) {
        super(parent);
        setContentPane(register);
        setMinimumSize(new Dimension(600, 550));
        setModal(true);
        setLocationRelativeTo(null);
        setUndecorated(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee newEmployee = registerButtonLogic();
                if (newEmployee != null){
                    if (connection.register(newEmployee)){
                        JOptionPane.showMessageDialog(null, "Registration Successful");
                    }else{
                        JOptionPane.showMessageDialog(null, "Error");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Error");
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    public Employee registerButtonLogic() {
        Employee employee = new Employee();
        if (!nameTextField.getText().isEmpty() && connection.isEmail(emailTextField.getText()) && passwordTextField.getPassword().length > 0) {
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            String password = passwordTextField.getText();
            employee = connection.addEmployee(name,email,password);
        } else {
            JOptionPane.showMessageDialog(null, "ERROR");
        }
        return employee;
    }
}
