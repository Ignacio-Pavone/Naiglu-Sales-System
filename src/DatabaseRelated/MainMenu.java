package DatabaseRelated;

import DatabaseRelated.PDFCreation.PDFTableClass;
import DatabaseRelated.PDFCreation.PDFTextClass;
import Exceptions.FieldCompletionException;
import Exceptions.RowNotSelectedException;
import PersonRelated.Customer;
import PersonRelated.MyBusiness;
import PersonRelated.Supplier;
import UserRelated.Employee;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;


public class MainMenu extends JDialog {
    SalesSystem app = new SalesSystem();

    private JPanel MainMenuPanelMenu;
    private JTabbedPane mainMenuTabPanel;
    private JButton modifyButton;
    private JTable productsTable;
    private JTextField codeField;
    private JTextField nameField;
    private JTextField stockField;
    private JTextField priceField;
    private JButton addButton;
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

    private JTable clientProductList;
    private JButton addToCartButton;
    private JTextField userAmount;
    private JButton confirmPurchaseButton;
    private JScrollPane tabl;
    private JLabel ammountValueLabel;
    private JButton deleteCartElement;
    private JLabel textFinalPrice;
    private JPanel adminPanel;

    private JTable salesTable;
    private JPanel addSales;
    private JButton generateInvoiceButton;
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
    private JLabel workingAreaLabel;
    private JTextField sellPriceField;
    private JLabel sellPriceLabel;
    private JTextField updateSupplier;
    private JTextField updateSellPrice;
    private JButton delElementButton;
    private JPanel supplierTab;
    private JLabel setAmountDay;
    private JButton deskClosing;
    private JTable statisticsTable;
    private JTable customerTable;
    private JTextField CnameCustomer;
    private JTextField CphoeNumberCustomer;
    private JTextField CtaxPayerIDCustomer;
    private JTextField CcategoryCustomer;
    private JButton ADDButton;
    private JLabel CnameLabel;
    private JLabel CtaxLabel;
    private JLabel CphoneLabel;
    private JLabel CcategoryLabel;
    private JComboBox comboBoxCustomers;
    private JLabel customerNameLAbel;
    private JButton xButton;
    private JButton delButton;
    private JLabel employeeName;
    private JPanel customerTab;
    private JPanel statisticsTab;
    private JLabel updateNameLabel;
    private JLabel updateStockLabel;
    private JLabel updatePriceLabel;
    private JTextField businessphoneText;
    private JTextField businesstaxText;
    private JTextField businessNameText;
    private JLabel companyNameLabel;
    private JButton acceptButton;
    private JLabel companyLabel;
    private JLabel taxpayerLabel;
    private JLabel phoneLabelBusiness;
    private JPanel businessTab;
    private JTextField enterProductSearch;
    private JLabel searchProduct;
    private JButton delete;
    private MyBusiness company = new MyBusiness();
    private Employee employee = new Employee();


    public MainMenu(JFrame parent) {
        super(parent);
        app.salesReadFile();
        app.supplierReadFile();
        app.customerReadFile();
        app.productReadFile();
        setComboBoxConfig();
        loadCustomerCombobox();
        tableStyle();
        setMinimumSize(new Dimension(900, 700));
        setContentPane(MainMenuPanelMenu);
        setUndecorated(true);
        setLocationRelativeTo(null);
        listingCollections();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
        mainMenuTabPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                adminSettings();
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
                try {
                    app.modifyProduct(productsTable, updateID, updateSupplier, updateName, updateStock, updatePrice, updateSellPrice);
                } catch (RowNotSelectedException ex) {
                    JOptionPane.showMessageDialog(null, "Select a row");
                    ex.printStackTrace();
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateProduct();
                    cleanLabels();
                    listClientProducts();
                    listProducts();
                } catch (RowNotSelectedException ex) {
                    JOptionPane.showMessageDialog(null, "Select a row");
                    ex.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    String id = updateID.getText();
                    if (!id.equals("")) {
                        dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", dialogButton);
                        if (dialogButton == JOptionPane.YES_OPTION) {
                            app.deleteProduct(id);
                            cleanLabels();
                            listProducts();
                            listClientProducts();

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "First Press Modify Button");
                    }
                } catch (RowNotSelectedException ex) {
                    JOptionPane.showMessageDialog(null, "Select a row");
                    ex.printStackTrace();
                }
            }
        });

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addToCart();
                } catch (RowNotSelectedException ex) {
                    JOptionPane.showMessageDialog(null, "Select a row");
                    ex.printStackTrace();
                }
            }
        });
        deleteCartElement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.deleteProductFromCart(cartTable);
                listProducts();
                listClientProducts();
                listCart();
                setTotalPrice();
            }
        });
        confirmPurchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.confirmPruchase(comboBoxCustomers, textFinalPrice);
                listCart();
                salesList();
                setTotalDay();
            }
        });
        addSupplierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    app.addSupplier(supplierNameField.getText(), supplierIDField.getText(), supplierPhoneField.getText(), supplierWorkingArea.getText());
                    setComboBoxConfig();
                    clearSupplierFields();
                    app.listSuppliers(supplierTable);
                } catch (FieldCompletionException ex) {
                    JOptionPane.showMessageDialog(null, "Please fill all the required fields");
                    ex.printStackTrace();
                }
            }
        });
        delElementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteSupplierFromList();
                } catch (RowNotSelectedException ex) {
                    JOptionPane.showMessageDialog(null, "Select a row");
                    ex.printStackTrace();
                }
            }
        });
        generateInvoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateInvoice();
            }
        });
        deskClosing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.salesFile();
                StatisticSale sale = app.deskClosing();
                setAmountDay.setText("Total");
                listStatistics(sale.getDate(), sale.getTotalInvoices(), sale.getInvoiceAmount());
                salesList();
            }
        });
        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    app.addCustomer (CnameCustomer.getText(), CtaxPayerIDCustomer.getText(), CphoeNumberCustomer.getText(), CcategoryCustomer.getText());
                    customerList();
                    clearCustomerFields();
                    loadCustomerCombobox();
                    app.listSuppliers(supplierTable);
                } catch (FieldCompletionException ex) {
                    JOptionPane.showMessageDialog(null, "Please fill all the required fields");
                    ex.printStackTrace();
                }
            }
        });
        xButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                app.supplierFile();
                app.customerFile();
                app.productFile();
            }
        });
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteCustomerFromList();
                } catch (RowNotSelectedException ex) {
                    JOptionPane.showMessageDialog(null, "Select a row");
                    ex.printStackTrace();
                }
            }
        });
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createMyBusiness();
                companyLabelsStyle();
            }
        });
        enterProductSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.searchProduct(enterProductSearch.getText());
            }
        });
    }

    public void setTotalDay() {
        double totalprice = 0;
        for (int i = 0; i < salesTable.getRowCount(); i++) {
            totalprice = totalprice + Double.parseDouble(String.valueOf(salesTable.getValueAt(i, 2)));
        }
        setAmountDay.setVisible(true);
        setAmountDay.setText("" + totalprice);
        setAmountDay.setForeground(Color.GREEN);
    }


    public void deleteCustomerFromList() throws RowNotSelectedException {
        int row = 0;
        row = customerTable.getSelectedRow();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        if (row != -1) {
            dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                String name = String.valueOf(customerTable.getValueAt(row, 0));
                String taxpayerID = String.valueOf(customerTable.getValueAt(row, 1));
                String phoneNumber = String.valueOf(customerTable.getValueAt(row, 2));
                String category = String.valueOf(customerTable.getValueAt(row, 3));
                Customer aux = new Customer(name, taxpayerID, phoneNumber, category);
                app.deleteCustomer(aux);
                loadCustomerCombobox();
                customerList();
            }
        } else {
            throw new RowNotSelectedException("Select a row");
        }
    }
    public void generateInvoice() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int rowSelection = salesTable.getSelectedRow();
        Boolean aux = (Boolean) salesTable.getValueAt(rowSelection, 4);
        if (rowSelection != -1 && aux.equals(false)) {
            dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                app.createInvoice((Double) salesTable.getValueAt(rowSelection, 0), (String) salesTable.getValueAt(rowSelection, 1), (Double) salesTable.getValueAt(rowSelection, 2), (String) salesTable.getValueAt(rowSelection, 3));
                app.isInvoiced((Double) salesTable.getValueAt(rowSelection, 0));
                salesList();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Sale already invoiced");
        }
    }
    public void deleteSupplierFromList() throws RowNotSelectedException {
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
                app.deleteSupplier(aux.getName());
                listSuppliers();
                setComboBoxConfig();
            }
        } else {
            throw new RowNotSelectedException("Select a row");
        }
    }
    public void updateProduct() throws RowNotSelectedException {
        String id = updateID.getText();
        if (!id.equals("")) {
            String name = updateName.getText();
            String supplier = updateSupplier.getText();
            int stock = Integer.parseInt(updateStock.getText());
            Double price = Double.parseDouble(updatePrice.getText());
            Double sellingPrice = Double.parseDouble(updateSellPrice.getText());
            Product aux = new Product(id, supplier, name, stock, price, sellingPrice);
            app.addElementProductList(aux.getId(), aux);
        } else {
            throw new RowNotSelectedException("Select a row");
        }
    }

    public void addProduct() {
        try {
            Product data = new Product();
            if (comboBox1.getSelectedItem() != null) {
                data.setId(codeField.getText());
                Supplier aux = (Supplier) comboBox1.getSelectedItem();
                data.setSupplierName(aux.getName());
                data.setName(nameField.getText());
                data.setStock(Integer.parseInt(stockField.getText()));
                data.setPrice(Double.parseDouble(priceField.getText()));
                data.setSellingPrice(Double.parseDouble(sellPriceField.getText()));
                app.addElementProductList(data.getId(), data);
            } else {
                JOptionPane.showMessageDialog(null, "First we need a Supplier");
            }
        } catch (Exception e) {
            java.lang.System.out.println(e.getMessage());
        }
    }

    public void cartProductsData() {
        int auxStock = 0;
        int rowSelection = clientProductList.getSelectedRow();
        String id = String.valueOf(clientProductList.getValueAt(rowSelection, 0));
        String supplier = String.valueOf(clientProductList.getValueAt(rowSelection, 1));
        String name = String.valueOf(clientProductList.getValueAt(rowSelection, 2));
        int stock = Integer.parseInt(String.valueOf(clientProductList.getValueAt(rowSelection, 3)));
        double price = Double.parseDouble(String.valueOf(clientProductList.getValueAt(rowSelection, 4)));
        double sellingPrice = Double.parseDouble(String.valueOf(clientProductList.getValueAt(rowSelection, 5)));
        int newStock = 0;
        if (!userAmount.getText().isEmpty()) {
            newStock = Integer.parseInt(userAmount.getText()); // 5
            if (newStock > 0 && newStock <= stock) {
                auxStock = stock - newStock;
                Product aux = new Product(id, supplier, name, newStock, price, sellingPrice);
                Product aux2 = new Product(id, supplier, name, auxStock, price, sellingPrice);
                if (app.shopkeyExist(id)) {
                    int stockAux = app.productStockShopList(id) + newStock;
                    Product aux3 = new Product(id, supplier, name, stockAux, price, sellingPrice);
                    app.addElementShopList(aux3.getId(), aux3);
                    app.addElementProductList(aux2.getId(), aux2);
                } else {
                    app.addElementShopList(aux.getId(), aux);
                    app.addElementProductList(aux2.getId(), aux2);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Unavailable stock for this operation");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Enter a valid value");
        }
    }

    public void addToCart() throws RowNotSelectedException {
        int rowSelection = clientProductList.getSelectedRow();
        if (rowSelection != -1 && !ammountValueLabel.getText().equals("")) {
            cartProductsData();
            listCart();
            listProducts();
            listClientProducts();
            userAmount.setText("");
            setTotalPrice();
        } else {
            throw new RowNotSelectedException("Select a row");
        }
    }

    private void companyLabelsStyle() {
        companyNameLabel.setForeground(Color.GREEN);
        companyNameLabel.setText("" + company.getName());
        businessNameText.setText("");
        businesstaxText.setText("");
        businessphoneText.setText("");
    }

    public void searchProduct() {
        String productName = enterProductSearch.getText();
        if (productName.isEmpty()) {
            listClientProducts();
        } else {
            DefaultTableModel model = new DefaultTableModel(
                    new Object[]{"Code", "Supplier", "Name", "Stock", "Price", "Sell Price"}, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            Iterator entries = app.returnIteratorProductList();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                String key = (String) entry.getKey();
                Product value = (Product) entry.getValue();
                if (value.getName().equalsIgnoreCase(productName)) {
                    model.addRow(new Object[]{key, value.getSupplierName(), value.getName(), value.getStock(), value.getPrice(), value.getSellingPrice()});
                }
            }
            clientProductList.setModel(model);
        }
    }

    public void setTotalPrice() {
        double totalprice = 0;
        for (int i = 0; i < cartTable.getRowCount(); i++) {
            totalprice = totalprice + Double.parseDouble(String.valueOf(cartTable.getValueAt(i, 5)));
        }
        textFinalPrice.setVisible(true);
        textFinalPrice.setText("" + totalprice);
        textFinalPrice.setForeground(Color.GREEN);
        app.setAmmountAcc(totalprice);
    }

    void adminSettings() {
        employeeName.setForeground(Color.GREEN);
        employeeName.setText("Logged" + "-[" + employee.getName() + "]" + " Admin Status" + "-[" + employee.isAdmin() + "]");
        if (!employee.isAdmin()) {
            mainMenuTabPanel.remove(adminPanel);
            mainMenuTabPanel.remove(addProducts);
            mainMenuTabPanel.remove(supplierTab);
            mainMenuTabPanel.remove(customerTab);
            mainMenuTabPanel.remove(statisticsTab);
            mainMenuTabPanel.remove(businessTab);
        }
    }

    private boolean checkSupplierRequirements() {
        return !supplierIDField.getText().equals("") && !supplierNameField.getText().equals("") && !supplierPhoneField.getText().equals("") && !supplierWorkingArea.getText().equals("");
    }

    private boolean chekCustomerFields() {
        return !CnameCustomer.getText().equals("") && !CtaxPayerIDCustomer.getText().equals("") && !CphoeNumberCustomer.getText().equals("") && !CcategoryCustomer.getText().equals("");
    }

    private void clearCustomerFields() {
        CnameCustomer.setText("");
        CtaxPayerIDCustomer.setText("");
        CphoeNumberCustomer.setText("");
        CcategoryCustomer.setText("");
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
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
        workingAreaLabel.setForeground(Color.WHITE);
        sellPriceLabel.setForeground(Color.WHITE);
        CnameLabel.setForeground(Color.WHITE);
        CphoneLabel.setForeground(Color.WHITE);
        CcategoryLabel.setForeground(Color.WHITE);
        CtaxLabel.setForeground(Color.WHITE);
        customerNameLAbel.setForeground(Color.WHITE);
        setAmountDay.setForeground(Color.WHITE);
        updateNameLabel.setForeground(Color.WHITE);
        updateStockLabel.setForeground(Color.WHITE);
        updatePriceLabel.setForeground(Color.WHITE);
        companyLabel.setForeground(Color.WHITE);
        taxpayerLabel.setForeground(Color.WHITE);
        phoneLabelBusiness.setForeground(Color.WHITE);
        searchProduct.setForeground(Color.WHITE);
    }

    private void tableStyle() {
        clientProductList.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        cartTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        productsTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        salesTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        supplierTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        customerTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        statisticsTable.getTableHeader().setFont(new Font("Consolas", Font.BOLD, 12));
        statisticsTable.getTableHeader().setBackground(Color.GRAY);
        supplierTable.getTableHeader().setBackground(Color.GRAY);
        customerTable.getTableHeader().setBackground(Color.GRAY);
        salesTable.getTableHeader().setBackground(Color.GRAY);
        productsTable.getTableHeader().setBackground(Color.GRAY);
        cartTable.getTableHeader().setBackground(Color.GRAY);
        clientProductList.getTableHeader().setBackground(Color.GRAY);
    }

    private void cleanLabels() {
        updateID.setText("");
        updateName.setText("");
        updateStock.setText("");
        updatePrice.setText("");
        updateSellPrice.setText("");
    }


    public void listCart() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Code", "Customer", "Name", "Ammount", "Unity price", "Total price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Iterator entries = app.returnIteratorShopList();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            Product value = (Product) entry.getValue();
            model.addRow(new Object[]{key, value.getSupplierName(), value.getName(), value.getStock(), value.getSellingPrice(), value.getSellingPrice() * value.getStock()});
        }
        cartTable.setModel(model);
    }

    public void listSuppliers() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Name", "Taxpayer ID", "Phone", "Area"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Iterator entries = app.returnSuppliersHashSet();
        while (entries.hasNext()) {
            Supplier s = (Supplier) entries.next();
            model.addRow(new Object[]{s.getName(), s.getTaxpayerID(), s.getPhoneNumber(), s.getWorkingArea()});
        }
        supplierTable.setModel(model);
    }

    public void salesList() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Operation Nº", "Client Name", "Total Ammount", "Date", "Facturado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Iterator aux = app.returnIteratorSaleList();
        while (aux.hasNext()){
            Sale sale = (Sale) aux.next();
            model.addRow(new Object[]{sale.getOperationNumber(), sale.getCustomerName(), sale.getTotalAmmount(), sale.getDateFormatted(), sale.isInvoiced()});
        }
        salesTable.setModel(model);
    }

    public void listClientProducts() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Code", "Supplier", "Name", "Stock", "Price", "Sell Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Iterator entries = app.returnIteratorProductList();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            Product value = (Product) entry.getValue();
            model.addRow(new Object[]{key, value.getSupplierName(), value.getName(), value.getStock(), value.getPrice(), value.getSellingPrice()});
        }
        clientProductList.setModel(model);
    }

    public void listProducts() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Code", "Supplier", "Name", "Stock", "Price", "Sell Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Iterator entries = app.returnIteratorProductList();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            Product value = (Product) entry.getValue();
            model.addRow(new Object[]{key, value.getSupplierName(), value.getName(), value.getStock(), value.getPrice(), value.getSellingPrice()});
        }
        productsTable.setModel(model);
    }

    public void customerList() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Name", "ID", "Phone Number", "Category"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Iterator entries = app.returnIteratorCustomerList();
        while (entries.hasNext()) {
            Customer customer = (Customer) entries.next();
            model.addRow(new Object[]{customer.getName(), customer.getTaxpayerID(), customer.getPhoneNumber(), customer.getCategory()});
        }
        customerTable.setModel(model);
    }

    private void listStatistics(String date, double total, int salesAmmount) { //Borra las estadisticas si se genera una nueva factura.
        DefaultTableModel model = (DefaultTableModel) statisticsTable.getModel();
        model.addRow(new Object[]{date, total, salesAmmount});
        statisticsTable.setModel(model);
    }

    public void createStatisticsTable() { //Borra las estadisticas si se genera una nueva factura.
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Date", "Total/Day", "N° Sales"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        statisticsTable.setModel(model);
    }

    private void listingCollections() {
        labelStyle();
        tableStyle();
        setLocationRelativeTo(null);
        listProducts();
        listClientProducts();
        listCart();
        listSuppliers();
        salesList();
        customerList();
        createStatisticsTable();
    }

    public void setComboBoxConfig() {
        comboBox1.removeAllItems();
        Object[] arr = new Object[app.supplierListSize()];
        arr = app.supplierlistArray();
        for (Object o : arr) {
            comboBox1.addItem(o);
        }
    }

    public void loadCustomerCombobox() {
        comboBoxCustomers.removeAllItems();
        Object[] arr = app.customerlistArray();
        for (Object o : arr) {
            comboBoxCustomers.addItem(o);
        }
    }

    private void createMyBusiness() {
        String nameString = businessNameText.getText();
        String taxpayerID = businesstaxText.getText();
        String phoneNumber = businessphoneText.getText();
        company = new MyBusiness(nameString, taxpayerID, phoneNumber);
        Border eBorder = new LineBorder(Color.BLACK, 1, true);

        mainMenuTabPanel.setBorder(BorderFactory.createTitledBorder(eBorder, " " + company.getName() + " ", TitledBorder.CENTER, TitledBorder.CENTER, new Font("Consolas", Font.ITALIC, 12), Color.green));
        UIManager.put("TabbedPane.contentAreaColor", Color.BLACK);
    }
}






