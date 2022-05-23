package DatabaseRelated;

import UserRelated.Employee;
import UserRelated.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inventory extends JDialog {

    private JPanel products1;
    private JTabbedPane sellTable;
    private JButton exitButton;
    private JButton modifyButton;
    private JTable productsTable;
    private JTextField codeField;
    private JTextField nameField;
    private JTextField stockField;
    private JTextField priceField;
    private JButton addButton;
    private JButton exitingButton;
    private JTextField updateID;
    private JTextField updatePrice;
    private JTextField updateName;
    private JTextField updateStock;
    private JButton updateButton;
    private JLabel nameText;
    private JPanel addProducts;
    private JLabel pricetxt;
    private JLabel codetxt;
    private JLabel stockLabel;
    private JButton deleteButton;
    private JTable cartTable;
    private JButton exitbutton1;
    private JTable clientProductList;
    private JButton exitUserList;
    private JButton addToCartButton;
    private JTextField userAmmount;
    private JButton CONFIRMPURCHASEButton;
    private JButton CANCELButton;
    private JScrollPane tabl;
    private JLabel ammountValueLabel;
    private JButton deleteCartElement;
    private JLabel textFinalPrice;
    private JPanel adminPanel;
    private JLabel finalPrice;
    private JButton exitButton2;
    private JTextField cantField;
    private JButton refreshButton;
    private Inventory productData;
    private JTable ventasTable;
    private JPanel addSells;
    private JButton generarFacturaButton;
    private int rowSelection;
    private double ammountAcc;
    private Employee employee = new Employee();
    private HashMap<String, Product> productList = new HashMap<>();
    private HashMap<String, Product> shopList = new HashMap<>();
    private ArrayList<Venta> listaVentass = new ArrayList<>();


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
        listProducts();
        listClientProducts();
        listCart();
        listaVentas();
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(employee);
                dispose();
            }
        });
        exitingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
                listProducts();
                listClientProducts();
                clearTextFields();
                productsTable.setRowSelectionAllowed(true);
                productsTable.setColumnSelectionAllowed(false);

            }
        });
        sellTable.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                if (!employee.isAdmin()) {
                    sellTable.remove(adminPanel);
                    sellTable.remove(addProducts);
                    sellTable.remove(addSells);
                }
            }
        });
        productsTable.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

            }
        });
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rowSelection = productsTable.getSelectedRow();
                if (rowSelection != -1) {
                    rowData();
                } else {
                    JOptionPane.showMessageDialog(null, "Select a row");
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProductFromList();
            }
        });

        exitUserList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addToCart();

            }
        });
        deleteCartElement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProductFromCart();
            }
        });
        CONFIRMPURCHASEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmPruchase();
            }
        });
    }

    private void confirmPruchase() {
        Venta nueva = new Venta();
        if (tableHaveData()) {
            System.out.println("hola");
            Double ammount = ammountAcc;
            nueva = new Venta(employee.getName(), ammount);
            if (!sellExist(nueva)) {
                listaVentass.add(nueva);
                textFinalPrice.setText("Total Price");
                shopList.clear();
            }
        }
        listCart();
        listaVentas();
    }

    private boolean tableHaveData() {
        boolean flag = false;
        if (shopList.size() > 0 && !flag) {
            System.out.println(shopList.size());
            flag = true;
        }
        return flag;
    }

    private boolean sellExist(Venta aux) {
        Boolean flag = false;
        for (int i = 0; i < listaVentass.size(); i++) {
            if (listaVentass.get(i).getNumero().equals(aux.getNumero()) && !flag) {
                flag = true;
            }
        }
        return flag;
    }

    protected void setEmployee(Employee employee) {
        this.employee = employee;
    }

    private void deleteProductFromList() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        String id = updateID.getText();
        if (!id.equals("")) {
            dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                deleteProduct(id);
                listProducts();
                listClientProducts();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a product");
        }
    }

    private void addToCart() {
        rowSelection = clientProductList.getSelectedRow();
        if (rowSelection != -1) {
            cartProductsData();
            listCart();
            listProducts();
            listClientProducts();
            userAmmount.setText("");
            setTotalPrice();
        } else {
            JOptionPane.showMessageDialog(null, "Select a row");
        }

    }

    private void setTotalPrice() {
        double totalprice = 0;
        for (int i = 0; i < cartTable.getRowCount(); i++) {
            totalprice = totalprice + Double.parseDouble(String.valueOf(cartTable.getValueAt(i, 4)));
        }
        textFinalPrice.setVisible(true);
        textFinalPrice.setText("Final price: " + totalprice);
        ammountAcc = totalprice;
    }

    private void deleteProductFromCart() {

        int row = 0;
        row = cartTable.getSelectedRow();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int newStock = 0;
        if (row != -1) {
            dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                String id = String.valueOf(cartTable.getValueAt(row, 0));
                String name = String.valueOf(cartTable.getValueAt(row, 1));
                int stock = Integer.parseInt(String.valueOf(cartTable.getValueAt(row, 2)));
                double price = Double.parseDouble(String.valueOf(cartTable.getValueAt(row, 3)));
                newStock = productStock(id) + stock;
                Product aux = new Product(id, name, newStock, price);
                deleteProductShop(id);
                productList.put(aux.getId(), aux);
                listProducts();
                listClientProducts();
                listCart();
                setTotalPrice();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a row");
        }


    }

    private int productStockShopList(String id) {
        int stock = 0;
        for (Map.Entry<String, Product> entry : shopList.entrySet()) {
            if (shopList.containsKey(id)) {
                stock = shopList.get(id).getStock();
            }
        }
        return stock;
    }

    private int productStock(String id) {
        int stock = 0;
        for (Map.Entry<String, Product> entry : productList.entrySet()) {
            if (productList.containsKey(id)) {
                stock = productList.get(id).getStock();
            }
        }
        return stock;
    }

    private void clearTextFields() {
        codeField.setText("");
        nameField.setText("");
        stockField.setText("");
        priceField.setText("");
    }

    private void labelStyle() {
        textFinalPrice.setForeground(Color.WHITE);
        nameText.setForeground(Color.WHITE);
        codetxt.setForeground(Color.WHITE);
        pricetxt.setForeground(Color.WHITE);
        stockLabel.setForeground(Color.WHITE);
        ammountValueLabel.setForeground(Color.WHITE);
    }

    private void tableStyle() {
        clientProductList.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        cartTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        productsTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        ventasTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        ventasTable.getTableHeader().setBackground(Color.GRAY);
        productsTable.getTableHeader().setBackground(Color.GRAY);
        cartTable.getTableHeader().setBackground(Color.GRAY);
        clientProductList.getTableHeader().setBackground(Color.GRAY);
    }

    private void deleteProductShop(String id) {
        if (shopList.containsKey(id)) {
            shopList.remove(id);
        }
    }

    private void deleteProduct(String id) {
        if (productList.containsKey(id)) {
            productList.remove(id);
            cleanLabels();
        } else {
            JOptionPane.showMessageDialog(null, "Select a product");
        }
    }

    private void cartProductsData() {
        int auxStock = 0;
        String id = String.valueOf(clientProductList.getValueAt(rowSelection, 0));
        String nombre = String.valueOf(clientProductList.getValueAt(rowSelection, 1));
        int stock = Integer.parseInt(String.valueOf(clientProductList.getValueAt(rowSelection, 2)));
        double precio = Double.parseDouble(String.valueOf(clientProductList.getValueAt(rowSelection, 3)));
        int newStock = Integer.parseInt(userAmmount.getText());
        if (newStock > 0 && newStock <= stock) {
            auxStock = stock - newStock;
            Product aux = new Product(id, nombre, newStock, precio);
            Product aux2 = new Product(id, nombre, auxStock, precio);
            if (shopList.containsKey(id)) {
                int stockAux = productStockShopList(id) + newStock;
                System.out.println(stockAux);
                Product aux3 = new Product(id, nombre, stockAux, precio);
                shopList.put(aux3.getId(), aux3);
                productList.put(aux2.getId(), aux2);
            } else {
                shopList.put(aux.getId(), aux);
                productList.put(aux2.getId(), aux2);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Unavailable stock");
        }

    }

    private void rowData() {
        String id = String.valueOf(productsTable.getValueAt(rowSelection, 0));
        String name = String.valueOf(productsTable.getValueAt(rowSelection, 1));
        int stock = Integer.parseInt(String.valueOf(productsTable.getValueAt(rowSelection, 2)));
        double price = Double.parseDouble(String.valueOf(productsTable.getValueAt(rowSelection, 3)));
        updateID.setText(id);
        updateName.setText(name);
        updateStock.setText(String.valueOf(stock));
        updatePrice.setText(String.valueOf(price));
    }

    private void updateProduct() {
        String id = updateID.getText();
        if (!id.equals("")) {
            String name = updateName.getText();
            int stock = Integer.parseInt(updateStock.getText());
            Double price = Double.parseDouble(updatePrice.getText());
            Product aux = new Product(id, name, stock, price);
            productList.put(aux.getId(), aux);
        } else {
            JOptionPane.showMessageDialog(null, "Select a product you want to modify");
        }
        cleanLabels();
        listClientProducts();
        listProducts();
    }

    private void cleanLabels() {
        updateID.setText("");
        updateName.setText("");
        updateStock.setText("");
        updatePrice.setText("");
    }

    private void addProduct() {
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

    private void listProducts() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Item ID", "Name", "Stock", "Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Map.Entry<String, Product> entry : productList.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue().getName(), entry.getValue().getStock(), entry.getValue().getPrice()});
        }
        productsTable.setModel(model);
    }

    private void listCart() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Item ID", "Name", "Ammount", "Unit price", "Total price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Map.Entry<String, Product> entry : shopList.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue().getName(), entry.getValue().getStock(), entry.getValue().getPrice(), entry.getValue().getPrice() * entry.getValue().getStock()});
        }

        cartTable.setModel(model);
    }

    private void listClientProducts() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Item ID", "Name", "Stock", "Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Map.Entry<String, Product> entry : productList.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue().getName(), entry.getValue().getStock(), entry.getValue().getPrice()});
        }
        clientProductList.setModel(model);
    }

    private void listaVentas() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Comprobante Nº", "Client Name", "Total Ammount"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (int i = 0; i < listaVentass.size(); i++) {
            model.addRow(new Object[]{listaVentass.get(i).getNumero(), listaVentass.get(i).getNombreCliente(), listaVentass.get(i).getTotalAmmount()});
        }
        ventasTable.setModel(model);
    }
}






