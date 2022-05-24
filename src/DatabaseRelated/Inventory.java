package DatabaseRelated;

import PersonRelated.Supplier;
import UserRelated.Employee;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


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
    private JButton GENERARFACTURAButton;
    private JTable supplierTable;
    private JButton addSupplierButton;
    private JTextField supplierNameField;
    private JTextField supplierPhoneField;
    private JTextField supplierIDField;
    private JTextField supplierWorkingArea;
    private JComboBox comboBox1;
    private JLabel supplierText;
    private JLabel nameLabel;
    private JLabel phoneLabel;
    private JLabel taxPayerLabel;
    private JLabel WorkigAreaLabel;
    private JTextField sellPriceField;
    private JLabel sellPriceLabel;
    private JTextField updateSupplier;
    private JTextField updateSellPrice;
    private JButton DELETEELEMENTButton;
    private JPanel supplierTab;
    private JLabel setAmmountDay;
    private JLabel lineLabel1;
    private JLabel lineLabel2;
    private JButton CERRARCAJAButton;
    private int rowSelection;
    private double ammountAcc;

    private Employee employee = new Employee();
    private HashMap<String, Product> productList = new HashMap<>(); // lista Productos
    private HashMap<String, Product> shopList = new HashMap<>(); // lista Carrito
    private ArrayList<Venta> listaVentass = new ArrayList<>(); // lista ventas Concretadas
    private HashSet<Supplier> suppliersList = new HashSet<>(); // lista proveedores
    private Collection<Product> mapTolist;
    private ArrayList<Product> finalProductPDF = new ArrayList<>();


    public Inventory(JFrame parent) {
        super(parent);
        tableStyle();
        setMinimumSize(new Dimension(650, 600));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(products1);
        setModal(true);
        setUndecorated(true);
        setLocationRelativeTo(null);
        listingCollections();

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    sellTable.remove(supplierTab);
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
                setTotalDay();
            }
        });
        addSupplierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSupplier();
            }
        });
        DELETEELEMENTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSupplierFromList();
            }
        });
        GENERARFACTURAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarFactura();
            }
        });
        CERRARCAJAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarCaja();
            }
        });
    }

    private void cerrarCaja() {
        double total = 0;
        int cantFacturas = 0;
        if (allFacturado()) {
            total = totalCaja();
            cantFacturas = listaVentass.size();
            //hacer algo
            listaVentass.clear();
        } else {
            JOptionPane.showMessageDialog(null, "Faltan facturar");
        }
    }

    private void generarFactura() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        rowSelection = ventasTable.getSelectedRow();
        if (rowSelection != -1) {
            dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                createInvoice((Double) ventasTable.getValueAt(rowSelection, 0), (String) ventasTable.getValueAt(rowSelection, 1), (Double) ventasTable.getValueAt(rowSelection, 2), (String) ventasTable.getValueAt(rowSelection, 3));
                isFacturado((Double) ventasTable.getValueAt(rowSelection, 0));
                listaVentas();
            }
        }
    }

    private double totalCaja() {
        double acum = 0;
        for (int i = 0; i < listaVentass.size(); i++) {
            acum += listaVentass.get(i).getTotalAmmount();
        }
        return acum;
    }

    private boolean allFacturado() {
        boolean flag = true;
        for (int i = 0; i < listaVentass.size(); i++) {
            if (!listaVentass.get(i).isFacturado()) {
                flag = false;
            }
        }
        return flag;
    }

    private void isFacturado(Double comprobante) {
        for (int i = 0; i < listaVentass.size(); i++) {
            if (listaVentass.get(i).getNumero().equals(comprobante)) {
                listaVentass.get(i).setFacturado(true);
            }
        }
    }

    private boolean checkSupplierRequirements() {
        return !supplierIDField.getText().equals("") && !supplierNameField.getText().equals("") && !supplierPhoneField.getText().equals("") && !supplierWorkingArea.getText().equals("");
    }

    private void addSupplier() {
        Supplier aux = new Supplier();
        if (!checkSupplierRequirements()) {
            JOptionPane.showMessageDialog(null, "Ingresa todos los datos necesarios");
        } else {
            aux.setName(supplierNameField.getText());
            aux.setTaxpayerID(supplierIDField.getText());
            aux.setPhoneNumber(supplierPhoneField.getText());
            aux.setWorkingArea(supplierWorkingArea.getText());
            suppliersList.add(aux);
            setComboBoxConfig();
            clearSupplierFields();

        }
        listSuppliers();
    }

    private void setComboBoxConfig() {
        comboBox1.removeAllItems();
        Object[] arr = new Object[suppliersList.size()];
        arr = suppliersList.toArray();
        for (Object o : arr) {
            comboBox1.addItem(o);
        }
    }

    private void listSuppliers() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Name", "Taxpayer ID", "Phone", "Area"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Supplier s : suppliersList) {
            model.addRow(new Object[]{s.getName(), s.getTaxpayerID(), s.getPhoneNumber(), s.getWorkingArea()});
        }
        supplierTable.setModel(model);
    }

    private void confirmPruchase() {
        Venta nueva = new Venta();
        if (tableHaveData()) {
            Double ammount = ammountAcc;
            nueva = new Venta(employee.getName(), ammount);
            if (!sellExist(nueva)) {
                listaVentass.add(nueva);
                textFinalPrice.setText("Total Price");
                mapTolist = shopList.values();
                finalProductPDF = new ArrayList<>(mapTolist);
                shopList.clear();
            }
        }
        listCart();
        listaVentas();
    }

    private boolean tableHaveData() {
        boolean flag = false;
        if (shopList.size() > 0 && !flag) {
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
        if (rowSelection != -1 && !ammountValueLabel.getText().equals("")) {
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
            totalprice = totalprice + Double.parseDouble(String.valueOf(cartTable.getValueAt(i, 5)));
        }
        textFinalPrice.setVisible(true);
        textFinalPrice.setText("Final price: $" + totalprice);
        textFinalPrice.setForeground(Color.GREEN);
        ammountAcc = totalprice;
    }

    private void setTotalDay() {
        double totalprice = 0;
        for (int i = 0; i < ventasTable.getRowCount(); i++) {
            totalprice = totalprice + Double.parseDouble(String.valueOf(ventasTable.getValueAt(i, 2)));
        }
        setAmmountDay.setVisible(true);
        setAmmountDay.setText("Today $" + totalprice);
        setAmmountDay.setForeground(Color.GREEN);
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
                String supplier = String.valueOf(cartTable.getValueAt(row, 1));
                String name = String.valueOf(cartTable.getValueAt(row, 2));
                int stock = Integer.parseInt(String.valueOf(cartTable.getValueAt(row, 3)));
                double price = Double.parseDouble(String.valueOf(cartTable.getValueAt(row, 4)));
                double sellPrice = Double.parseDouble(String.valueOf(cartTable.getValueAt(row, 4)));
                newStock = productStock(id) + stock;
                Product aux = new Product(id, supplier, name, newStock, price, sellPrice);
                deleteProductShop(id);
                productList.put(aux.getId(), aux);
                listProducts();
                listClientProducts();
                listCart();
                setTotalPrice();
            }
        } else {

        }
    }

    private void deleteSupplierFromList() {
        int row = 0;
        row = supplierTable.getSelectedRow();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        if (row != -1) {
            dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                String name = String.valueOf(supplierTable.getValueAt(row, 0));
                String taxpayerID = String.valueOf(supplierTable.getValueAt(row, 1));
                String phoneNumber = String.valueOf(supplierTable.getValueAt(row, 2));
                String workingArea = String.valueOf(supplierTable.getValueAt(row, 3));
                Supplier aux = new Supplier(name, taxpayerID, phoneNumber, workingArea);
                deleteSupplier(aux.getName());
                listSuppliers();
                setComboBoxConfig();
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
        sellPriceField.setText("");
    }

    private void clearSupplierFields() {
        supplierNameField.setText("");
        supplierIDField.setText("");
        supplierPhoneField.setText("");
        supplierWorkingArea.setText("");
    }

    private void labelStyle() {
        textFinalPrice.setForeground(Color.WHITE);
        nameText.setForeground(Color.WHITE);
        codetxt.setForeground(Color.WHITE);
        pricetxt.setForeground(Color.WHITE);
        stockLabel.setForeground(Color.WHITE);
        ammountValueLabel.setForeground(Color.WHITE);
        supplierText.setForeground(Color.WHITE);
        nameLabel.setForeground(Color.WHITE);
        taxPayerLabel.setForeground(Color.WHITE);
        phoneLabel.setForeground(Color.WHITE);
        WorkigAreaLabel.setForeground(Color.WHITE);
        sellPriceLabel.setForeground(Color.WHITE);

        setAmmountDay.setForeground(Color.GRAY);
    }

    private void tableStyle() {
        clientProductList.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        cartTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        productsTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        ventasTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        supplierTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        supplierTable.getTableHeader().setBackground(Color.GRAY);
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

    private void deleteSupplier(String name) {
        Supplier aux = searchSupplier(name);
        if (suppliersList.contains(aux)) {
            suppliersList.remove(aux);
        } else {
            JOptionPane.showMessageDialog(null, "Select a product");
        }
    }

    private Supplier searchSupplier(String name) {
        for (Supplier s : suppliersList) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    private void cartProductsData() {
        int auxStock = 0;

        String id = String.valueOf(clientProductList.getValueAt(rowSelection, 0));
        String supplier = String.valueOf(clientProductList.getValueAt(rowSelection, 1));
        String nombre = String.valueOf(clientProductList.getValueAt(rowSelection, 2));
        int stock = Integer.parseInt(String.valueOf(clientProductList.getValueAt(rowSelection, 3))); // 7
        double precio = Double.parseDouble(String.valueOf(clientProductList.getValueAt(rowSelection, 4)));
        double sellPrice = Double.parseDouble(String.valueOf(clientProductList.getValueAt(rowSelection, 5)));
        int newStock = Integer.parseInt(userAmmount.getText()); // 5

        if (newStock > 0 && newStock <= stock) {
            auxStock = stock - newStock;
            Product aux = new Product(id, supplier, nombre, newStock, precio, sellPrice);
            Product aux2 = new Product(id, supplier, nombre, auxStock, precio, sellPrice);
            if (shopList.containsKey(id)) {
                int stockAux = productStockShopList(id) + newStock;
                Product aux3 = new Product(id, supplier, nombre, stockAux, precio, sellPrice);
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
        String supplier = String.valueOf(productsTable.getValueAt(rowSelection, 1));
        String name = String.valueOf(productsTable.getValueAt(rowSelection, 2));
        int stock = Integer.parseInt(String.valueOf(productsTable.getValueAt(rowSelection, 3)));
        double precio = Double.parseDouble(String.valueOf(productsTable.getValueAt(rowSelection, 4)));
        double sellPrice = Double.parseDouble(String.valueOf(productsTable.getValueAt(rowSelection, 5)));
        updateID.setText(id);
        updateSupplier.setText(supplier);
        updateName.setText(name);
        updateStock.setText(String.valueOf(stock));
        updatePrice.setText(String.valueOf(precio));
        updateSellPrice.setText(String.valueOf(sellPrice));
    }

    private void updateProduct() {
        String id = updateID.getText();
        if (!id.equals("")) {
            String name = updateName.getText();
            String supplier = updateSupplier.getText();
            int stock = Integer.parseInt(updateStock.getText());
            Double price = Double.parseDouble(updatePrice.getText());
            Double sellprice = Double.parseDouble(updateSellPrice.getText());
            Product aux = new Product(id, supplier, name, stock, price, sellprice);
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
            Supplier aux = (Supplier) comboBox1.getSelectedItem();
            data.setSupplierName(aux.getName());
            data.setName(nameField.getText());
            data.setStock(Integer.parseInt(stockField.getText()));
            data.setPrice(Double.parseDouble(priceField.getText()));
            data.setSellPrice(Double.parseDouble(sellPriceField.getText()));
            productList.put(data.getId(), data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void listProducts() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Code", "Supplier", "Name", "Stock", "Price", "Sell Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Map.Entry<String, Product> entry : productList.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue().getSupplierName(), entry.getValue().getName(), entry.getValue().getStock(), +entry.getValue().getPrice(), entry.getValue().getSellPrice()});
        }
        productsTable.setModel(model);
    }

    private void listCart() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Code", "Supplier", "Name", "Ammount", "Unity price", "Total price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Map.Entry<String, Product> entry : shopList.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue().getSupplierName(), entry.getValue().getName(), entry.getValue().getStock(), entry.getValue().getSellPrice(), entry.getValue().getSellPrice() * entry.getValue().getStock()});
        }
        cartTable.setModel(model);
    }

    private void listClientProducts() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Code", "Supplier", "Name", "Stock", "Price", "Sell Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Map.Entry<String, Product> entry : productList.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue().getSupplierName(), entry.getValue().getName(), entry.getValue().getStock(), entry.getValue().getPrice(), entry.getValue().getSellPrice()});
        }
        clientProductList.setModel(model);
    }

    private void listaVentas() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Comprobante Nº", "Client Name", "Total Ammount", "Date", "Facturado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (int i = 0; i < listaVentass.size(); i++) {
            model.addRow(new Object[]{listaVentass.get(i).getNumero(), listaVentass.get(i).getNombreCliente(), listaVentass.get(i).getTotalAmmount(), listaVentass.get(i).getFecha(), listaVentass.get(i).isFacturado()});
        }
        ventasTable.setModel(model);
    }

    private void listingCollections() {
        labelStyle();
        tableStyle();
        setLocationRelativeTo(null);
        listProducts();
        listClientProducts();
        listCart();
        listSuppliers();
        listaVentas();
    }

    public void createInvoice(double Comprobante, String cliente, double precio, String fecha) {
        PDDocument doc = new PDDocument();
        PDPage firstPage = new PDPage(PDRectangle.A4);
        doc.addPage(firstPage);
        String name = "Empresa S.A";
        String number = "223456789";
        String time = LocalDateTime.now().toString();
        int pagewidth = (int) firstPage.getTrimBox().getWidth();
        int pageHeight = (int) firstPage.getTrimBox().getHeight();

        try {
            PDPageContentStream contentStream = new PDPageContentStream(doc, firstPage);
            PDFTextClass pdfTextClass = new PDFTextClass(doc, contentStream);
            PDFont font = PDType1Font.COURIER;

            String[] contactInfo = new String[]{"nazarenoorodriguez@gmail.com", "ignaciopavone@gmail.com", "talliercioluis1@gmail.com"};
            //pdfTextClass.addMultiLineText(contactInfo,18,(int)(pagewidth-font.getStringWidth("nazarenorodriguez@gmail.com")/1000*15-10),pageHeight-25,font,15,Color.BLACK);
            pdfTextClass.addLineOfText("EMPRESA S.A", 250, pageHeight - 50, font, 14, Color.GREEN);
            pdfTextClass.addLineOfText("COMPROBANTE: " + Comprobante, 25, pageHeight - 100, font, 10, Color.BLACK);
            pdfTextClass.addLineOfText("CLIENTE: " + cliente, 25, pageHeight - 125, font, 10, Color.BLACK);
            pdfTextClass.addLineOfText("DETALLE: " + finalProductPDF, 25, pageHeight - 150, font, 10, Color.BLACK);
            pdfTextClass.addLineOfText("PRECIO: $" + precio, 25, pageHeight - 175, font, 10, Color.BLACK);
            pdfTextClass.addLineOfText("FECHA: " + fecha, 25, pageHeight - 200, font, 10, Color.BLACK);

            contentStream.close();
            String idConcat = "[" + Comprobante + "]" + employee.getName();
            String namePDF = idConcat.concat(".pdf");
            doc.save(namePDF);
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}






