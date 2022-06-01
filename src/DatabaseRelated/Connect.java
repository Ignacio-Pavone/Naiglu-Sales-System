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

    public Employee loginUser(char[] passwordField2, String emailField) {
        String email = emailField;
        char [] password = passwordField2;
        Employee aux= authenticate(email, password);
        return aux;
    }

    private Employee authenticate(String email, char[] password) {
        Connection con;
        PreparedStatement ps;
        ResultSet rs;
        Employee em = null;
        try {
            String sql = "SELECT * FROM usuarios WHERE correo=? AND password=?";
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, String.valueOf(password));
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