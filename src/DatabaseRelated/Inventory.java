package DatabaseRelated;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;

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
                tablaProductos = new JTable(toTableModel());
                JPanel p = new JPanel();
                p.add(tablaProductos);
                JFrame f = new JFrame();
                f.add(p);
                f.setSize(500, 500);
                f.setVisible(true);
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

    private TableModel toTableModel() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Name", "Stock", "Precio"}, 0);
        for (Map.Entry<String, Product> entry : productList.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue().getName(), entry.getValue().getStock(), entry.getValue().getPrice()});
        }
        return model;
    }

    /*
    private void listarProductos() {
        Object[][] ob = {
                {"2", "Juan", 20, 500},
        };
        tablaProductos.setModel(new DefaultTableModel(
                ob,
                new String[]{"ID", "NOMBRE", "STOCK", "PRECIO"}));
    }

     */
}






