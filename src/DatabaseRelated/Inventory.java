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
    private JLabel nameText;
    private JPanel stocktxt;
    private JLabel pricetxt;
    private JLabel codetxt;
    private JLabel stockLabel;
    private JButton DELETEButton;
    private JTable tablaCarrito;
    private JButton SALIRButton1;
    private JTable listaProductosCliente;
    private JButton salirListaUsuario;
    private JButton addCarroButton;
    private JTextField cantUsuario;
    private JButton CONFIRMARButton;
    private JButton CANCELARButton;
    private JScrollPane tabl;
    private JLabel cantValueLabel;
    private JButton deleteCarritoButton;
    private JButton SALIRButton2;
    private JTextField cantField;
    private JButton REFRESHButton;
    private Inventory productData;
    private int seleccionFila;


    private HashMap<String, Product> productList = new HashMap<>();
    private HashMap<String, Product> shopList = new HashMap<>();

    public Inventory(JFrame parent) {
        super(parent);

        tableStyle();
        setMinimumSize(new Dimension(600, 550));
        setContentPane(products1);
        setModal(true);
        setUndecorated(false);
        labelStyle();
        tableStyle();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        listarProductos();
        listarProductosCliente();
        listarCarrito();
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
                listarProductosCliente();
                emptyTextsAdd();
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
                } else {
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
        DELETEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarProductoLista();
            }
        });

        salirListaUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        addCarroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               añadiralCarro();
            }
        });
        deleteCarritoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProductoCarrito();
            }
        });
    }

    private void borrarProductoLista (){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        String id = updateID.getText();
        if (!id.equals("")) {
            dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                deleteProduct(id);
                listarProductos();
                listarProductosCliente();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un Producto");
        }
    }

    private void añadiralCarro (){
        seleccionFila = listaProductosCliente.getSelectedRow();
        if (seleccionFila != -1) {
            datosProductoCarro();
            listarCarrito();
            listarProductos();
            listarProductosCliente();
            cantUsuario.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }
    }

    private void eliminarProductoCarrito (){
        seleccionFila = tablaCarrito.getSelectedRow();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int nuevoStock = 0;
        if (seleccionFila != -1){
            dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                String id = String.valueOf(tablaCarrito.getValueAt(seleccionFila, 0));
                String nombre = String.valueOf(tablaCarrito.getValueAt(seleccionFila, 1));
                int stock = Integer.parseInt(String.valueOf(tablaCarrito.getValueAt(seleccionFila, 2)));
                double precio = Double.parseDouble(String.valueOf(tablaCarrito.getValueAt(seleccionFila, 3)));
                nuevoStock = stockProducto(id) + stock;
                System.out.println(nuevoStock);
                Product aux = new Product(id, nombre, nuevoStock, precio);
                productList.put(aux.getId(), aux);
                listarCarrito();
                listarProductos();
                listarProductosCliente();
                DefaultTableModel modelo = (DefaultTableModel) tablaCarrito.getModel();
                modelo.removeRow(seleccionFila);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Seleccione una fila");
        }

    }

    private int stockProducto (String id){
        int stock = 0;
        for (Map.Entry<String, Product> entry : productList.entrySet()){
            if (productList.containsKey(id)){
                stock = productList.get(id).getStock();
            }
        }
        return stock;
    }

    private void emptyTextsAdd (){
        codeField.setText("");
        nameField.setText("");
        stockField.setText("");
        priceField.setText("");
    }

    private void labelStyle (){
        nameText.setForeground(Color.WHITE);
        codetxt.setForeground(Color.WHITE);
        pricetxt.setForeground(Color.WHITE);
        stockLabel.setForeground(Color.WHITE);
        cantValueLabel.setForeground(Color.WHITE);
    }

    private void tableStyle (){
        listaProductosCliente.getTableHeader().setFont( new Font( "Consolas" , Font.BOLD, 12 ));
        tablaCarrito.getTableHeader().setFont( new Font( "Consolas" , Font.BOLD, 12 ));
        tablaProductos.getTableHeader().setFont( new Font( "Consolas" , Font.BOLD, 12 ));
    }

    private void deleteProduct(String id) {
        if (productList.containsKey(id)) {
            productList.remove(id);
            limpiarLabels();
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un Producto");
        }
    }

    private void datosProductoCarro (){
        int nuevoStock = 0;
        String id = String.valueOf(listaProductosCliente.getValueAt(seleccionFila, 0));
        String nombre = String.valueOf(listaProductosCliente.getValueAt(seleccionFila, 1));
        int stock = Integer.parseInt(String.valueOf(listaProductosCliente.getValueAt(seleccionFila, 2)));
        double precio = Double.parseDouble(String.valueOf(listaProductosCliente.getValueAt(seleccionFila, 3)));
        int newStock = Integer.parseInt(cantUsuario.getText());
        if (newStock> 0 && newStock <= stock){
            nuevoStock = stock-newStock;
            Product aux = new Product(id,nombre,newStock,precio);
            Product aux2 = new Product(id,nombre,nuevoStock,precio);
            shopList.put(aux.getId(),aux);
            productList.put(aux2.getId(),aux2);
        }else{
            JOptionPane.showMessageDialog(null, "Stock Insuficiente");
        }

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
            Product aux = new Product(id, name, stock, price);
            productList.put(aux.getId(), aux);
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un producto a modificar");
        }
        limpiarLabels();
        listarProductosCliente();
        listarProductos();
    }

    private void limpiarLabels() {
        updateID.setText("");
        updateName.setText("");
        updateStock.setText("");
        updatePrice.setText("");
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
                new Object[]{"Codigo", "Name", "Stock", "Precio"}, 0) {
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

    private void listarCarrito() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Codigo", "Name", "Cantidad", "Precio"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Map.Entry<String, Product> entry : shopList.entrySet()) {
            model.addRow(new Object[]{entry.getKey(),entry.getValue().getName(), entry.getValue().getStock(), entry.getValue().getPrice()});
        }
        tablaCarrito.setModel(model);
    }

    private void listarProductosCliente() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Codigo", "Name", "Stock", "Precio"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Map.Entry<String, Product> entry : productList.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue().getName(), entry.getValue().getStock(), entry.getValue().getPrice()});
        }
        listaProductosCliente.setModel(model);
    }
}






