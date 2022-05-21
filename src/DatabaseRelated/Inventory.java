package DatabaseRelated;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

public class Inventory extends JDialog {

    private JPanel products1;
    private JTabbedPane tabbedPane1;
    private JButton exitButton;
    private JButton modificarButton;
    private JTable tablaProductos;
    private JTextField codeField;
    private JTextField nameField;
    private JTextField stockField;
    private JTextField priceField;
    private JButton ADDButton;
    private JButton SALIRButton;
    private JTextField updateID;
    private JTextField updatePrice;
    private JTextField updateName;
    private JTextField updateStock;
    private JButton updateButton;
    private Inventory productData;
    private int seleccionFila;


    private HashMap<String, Product> productList = new HashMap<>();

    public Inventory(JFrame parent) {
        super(parent);
        setMinimumSize(new Dimension(600, 550));
        setContentPane(products1);
        setModal(true);
        setUndecorated(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        listarProductos();
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
                listarProductos();
                tablaProductos.setRowSelectionAllowed(true);
                tablaProductos.setColumnSelectionAllowed(false);


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
        modificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionFila = tablaProductos.getSelectedRow();
                if (seleccionFila != -1) {
                    datosFila();
                }else{
                    JOptionPane.showMessageDialog(null, "Seleccione una fila");
                }




            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProducto();
            }
        });
    }

    private void datosFila() {

        String id = String.valueOf(tablaProductos.getValueAt(seleccionFila, 0));
        String nombre = String.valueOf(tablaProductos.getValueAt(seleccionFila, 1));
        int stock = Integer.parseInt(String.valueOf(tablaProductos.getValueAt(seleccionFila, 2)));
        double precio = Double.parseDouble(String.valueOf(tablaProductos.getValueAt(seleccionFila, 3)));
        updateID.setText(id);
        updateName.setText(nombre);
        updateStock.setText(String.valueOf(stock));
        updatePrice.setText(String.valueOf(precio));
    }

    private void actualizarProducto() {
        String id = updateID.getText();
        if (!id.equals("")) {
            String name = updateName.getText();
            int stock = Integer.parseInt(updateStock.getText());
            Double price = Double.parseDouble(updatePrice.getText());
            if (!id.equals("")) {
                Product aux = new Product(id, name, stock, price);
                productList.put(aux.getId(), aux);
            }
        }
        updateID.setText("");
        updateName.setText("");
        updateStock.setText("");
        updatePrice.setText("");
        listarProductos();
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
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID", "Name", "Stock", "Precio"}, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Map.Entry<String, Product> entry : productList.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue().getName(), entry.getValue().getStock(), entry.getValue().getPrice()});
        }
        tablaProductos.setModel(model);
    }
}






