package DatabaseRelated;

import UserRelated.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.Serializable;
import java.sql.*;

public class Login extends JDialog{
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
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Connect cn = new Connect();

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

                loginUser();
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

    private void loginUser() {
        String email = Login.this.email.getText();
        String password = String.valueOf(passwordField2.getPassword());
        employee = authenticate(email, password);
        if (employee != null) {
            dispose();
            JOptionPane.showMessageDialog(null, "Hello " + employee.getName());
            MainMenu nuevo = new MainMenu(null);
            nuevo.setEmployee(employee);
            nuevo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(Login.this, "Incorrect email or password.", "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showRegister() {
        Register nuevo = new Register(null);
        nuevo.setVisible(true);
    }

    private Employee authenticate(String email, String password) {
        Employee em = null;
        try {
            String sql = "SELECT * FROM usuarios WHERE correo=? AND password=?";
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                em = new Employee();
                em.setID(rs.getInt("id"));
                em.setName(rs.getString("nombre"));
                em.setEmail(rs.getString("correo"));
                em.setPassword(rs.getString("password"));
                em.setAdmin(rs.getBoolean("esAdmin"));
            }
            rs.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return em;
    }
}
