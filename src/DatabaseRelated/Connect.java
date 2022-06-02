package DatabaseRelated;

import UserRelated.Employee;

import javax.swing.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public Employee addEmployee (String name, String paswwordField2, String emailField){
        Employee newEmployee = new Employee(name,paswwordField2, emailField);
        return newEmployee;
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

    public boolean register(Employee employee) {
        String sql = "INSERT INTO usuarios (nombre, correo, password, esAdmin) VALUES (?,?,?,?)";
        Connection con;
        PreparedStatement ps;
        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getPassword());
            ps.setBoolean(4, employee.isAdmin());
            ps.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEmail(String correo) {
        Pattern pat = null;
        Matcher mat = null;
        pat = Pattern.compile("^[\\w\\-\\_\\+]+(\\.[\\w\\-\\_]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$");
        mat = pat.matcher(correo);
        return mat.find();
    }
}