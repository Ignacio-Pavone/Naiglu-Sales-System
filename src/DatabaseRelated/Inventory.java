package DatabaseRelated;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;

public class Inventory extends JDialog {

    private JPanel products1;
    private JTabbedPane tabbedPane1;
    private JButton exitButton;
    private JButton button2;
    private JTable tablaProductos;
    private JTextField codeField;
    private JTextField nameField;
    private JTextField stockField;
    private JTextField priceField;
    private JButton ADDButton;
    private JButton SALIRButton;
    private Inventory productData;
    DefaultTableModel modelo = new DefaultTableModel();

    private HashMap<String, Product> productList = new HashMap<>();

    public Inventory(JFrame parent) {
        super(parent);
        setMinimumSize(new Dimension(400, 450));
        setContentPane(products1);
        setModal(true);
        setUndecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        SALIRButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarProducto();
            }
        });
        tabbedPane1.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });
        tablaProductos.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                listarProductos();
            }
        });
    }

    private void cargarProducto() {
        try {
            Product data = new Product();
            data.setId(codeField.getText());
            data.setName(nameField.getText());
            data.setStock(Integer.parseInt(stockField.getText()));
            data.setPrice(Double.parseDouble(priceField.getText()));
            productList.put(data.getId(), data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarProductos() {
        Object[] ob = new Object[4];
        modelo = (DefaultTableModel) tablaProductos.getModel();
        productList.forEach((k, v) -> {
            ob[0] = productList.get(k);
            ob[1] = productList.get(v.getName());
            ob[2] = productList.get(v.getStock());
            ob[3] = productList.get(v.getPrice());
            modelo.addRow(ob);
            TableColumnModel columnas = tablaProductos.getColumnModel();
            columnas.getColumn(0).setMinWidth(50);
        });
    }
}






