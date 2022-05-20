import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends JDialog {


    private JTextField nombretxt;
    private JPasswordField passtxt;
    private JButton cancelarButton;
    private JPanel registro;
    private JTextField mailtxt;
    private JButton registroButton;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();


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

    public Registro(JFrame parent) {
        super(parent);
        setTitle("Registro");
        setContentPane(registro);
        setMinimumSize(new Dimension(400, 450));
        setModal(true);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        registroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuario user = new Usuario();
                if (!nombretxt.getText().isEmpty() && isEmail(mailtxt.getText()) && passtxt.getPassword().length > 0) {
                    user.setName(nombretxt.getText());
                    user.setEmail(mailtxt.getText());
                    user.setPassword(passtxt.getText());
                    Registrarse(user);
                } else {
                    JOptionPane.showMessageDialog(null, "ERROR");
                }
            }
        });
    }

    public boolean Registrarse(Usuario user) {
        String sql = "INSERT INTO usuarios (nombre, correo, password) VALUES (?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            JOptionPane.showMessageDialog(null, "REGISTRADO CON EXITO");
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
