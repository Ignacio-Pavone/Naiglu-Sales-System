package DatabaseRelated;

import UserRelated.Employee;

import javax.swing.*;
import java.sql.*;

public class Connect {

    Connection con;
    public static Employee employee;

    public Connection getConnection() {
        try {
            String myBD = "jdbc:mysql://localhost:3306/proyecto final";
            con = DriverManager.getConnection(myBD, "root", "");
            return con;
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public void loginUser(JPasswordField passwordField2, JTextField emailField) {
        String email = emailField.getText();
        String password = String.valueOf(passwordField2.getPassword());
        employee = authenticate(email, password);
        if (employee != null) {

            JOptionPane.showMessageDialog(null, "Hello " + employee.getName());
            MainMenu nuevo = new MainMenu(null);
            nuevo.setEmployee(employee);
            nuevo.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect email or password.", "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private Employee authenticate(String email, String password) {
        Employee em = null;
        Connection con;
        PreparedStatement ps;
        ResultSet rs;

        try {
            String sql = "SELECT * FROM usuarios WHERE correo=? AND password=?";
            con = getConnection();
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