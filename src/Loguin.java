
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Loguin extends JDialog {
    private JPasswordField passwordField2;
    private JButton ingresarButton;
    private JButton cancelarButton;
    private JPanel loguinPanel;
    private JTextField mail;
    private JButton registrarseButton;
    public static Usuario user;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();

    public Loguin(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loguinPanel);
        setMinimumSize(new Dimension(400, 450));
        setModal(true);
        setUndecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = mail.getText();
                String password = String.valueOf(passwordField2.getPassword());
                user = autenticar(email, password);
                if (user != null) {
                    dispose();
                    JOptionPane.showMessageDialog(null,"Bienvenido/a " + user.name);
                    Productos nuevo = new Productos(null);
                    nuevo.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(Loguin.this,
                            "Usuario o Contraseña Incorrecto", "Intenta de nuevo",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        registrarseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registro registro = new Registro(null);
                registro.setVisible(true);
            }
        });

        setVisible(true);
    }

    private Usuario autenticar(String email, String password) {
        Usuario user = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/proyecto final";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            String sql = "SELECT * FROM usuarios WHERE correo=? AND password=?";
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new Usuario();
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
