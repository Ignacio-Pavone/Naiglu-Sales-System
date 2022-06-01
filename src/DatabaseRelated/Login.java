package DatabaseRelated;

import UserRelated.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;
import java.sql.*;

public class Login extends JDialog {
    private JPasswordField passwordField2;
    private JButton enterButton;
    private JButton cancelButton;
    private JPanel loginPanel;
    private JTextField email;
    private JButton registerButton;
    private JLabel systemIcon;
    private JButton xButton;
    public static Employee employee;
    final Point offset = new Point();

    Connect connect = new Connect();

    public Login(JFrame parent) {
        super(parent);
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(550, 450));
        setModal(true);
        setUndecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        systemIcon.setForeground(Color.green);

        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu menu = new MainMenu(null);

                Employee aux = connect.loginUser(passwordField2.getPassword(), email.getText());
                if (aux == null){
                    JOptionPane.showMessageDialog(null,"Login Incorrecto");
                }else{
                    JOptionPane.showMessageDialog(null,"Welcome Again " + aux.getName());
                    dispose();
                    menu.setVisible(true);
                    menu.setEmployee(aux);
                }

            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(final MouseEvent e) {
                offset.setLocation(e.getPoint());
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(final MouseEvent e) {
                setLocation(e.getXOnScreen() - offset.x, e.getYOnScreen() - offset.y);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegister();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);


    }

    private void showRegister() {
        Register nuevo = new Register(null);
        nuevo.setVisible(true);
    }

}
