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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static DatabaseRelated.JsonUtiles.createJSON;


public class MainMenu extends JDialog {

    private MyBusiness placeholderBusiness = new MyBusiness("Name", "123321", "2222222");
    private JPanel products1;
    private JTabbedPane mainMenuTabPanel;
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
    private JTextField userAmount;
    private JButton CONFIRMPURCHASEButton;
    private JScrollPane tabl;
    private JLabel ammountValueLabel;
    private JButton deleteCartElement;
    private JLabel textFinalPrice;
    private JPanel adminPanel;
    private JLabel finalPrice;
    private JButton exitButton2;
    private JTextField cantField;
    private JButton refreshButton;
    private MainMenu productData;
    private JTable salesTable;
    private JPanel addSales;
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
    private JLabel setAmountDay;
    private JLabel lineLabel1;
    private JLabel lineLabel2;
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
    private JButton DELETEButton;
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
    private JButton ACCEPTButton;
    private JLabel companyLabel;
    private JLabel taxpayerLabel;
    private JLabel phoneLabelBusiness;
    private JPanel businesstab;
    private JTextField enterProductSearch;
    private JLabel searchProduct;
    private int rowSelection;
    private double ammountAcc;
    private MyBusiness company = new MyBusiness();
    private Employee employee = new Employee();
    private final GenericHashMap<String,Product> productList = new GenericHashMap<>();
    private final GenericHashMap<String,Product> shopList = new GenericHashMap<>();
    //private final HashMap<String, Product> productList = new HashMap<>(); // lista Productos
    private final ArrayList<Sale> salesList = new ArrayList<>(); // lista ventas Concretadas
    private final HashSet<Supplier> suppliersList = new HashSet<>(); // lista proveedores
    private final ArrayList<Customer> customerList = new ArrayList<>(); // lista clientes
    private Collection<Product> mapTolist;
    private ArrayList<Product> finalProductPDF = new ArrayList<>();


    public MainMenu(JFrame parent) {
        super(parent);

        hardCode();
        tableStyle();
        setMinimumSize(new Dimension(900, 700));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(products1);
        setUndecorated(true);
        setLocationRelativeTo(null);
        listingCollections();


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
                    modifyProduct();
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
                    deleteProductFromList();
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
                try {
                    addSupplier();
                } catch (FieldCompletionException ex) {
                    JOptionPane.showMessageDialog(null, "Please fill all the required fields");
                    ex.printStackTrace();
                }
            }
        });
        DELETEELEMENTButton.addActionListener(new ActionListener() {
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
        GENERARFACTURAButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateInvoice();
            }
        });
        deskClosing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deskClosing();
            }
        });
        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addCustomer();
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
            }
        });
        DELETEButton.addActionListener(new ActionListener() {
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
        ACCEPTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createMyBusiness();
                companyLabelsStyle();
            }
        });
        enterProductSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProduct();
            }
        });
    }

    private void companyLabelsStyle() {
        companyNameLabel.setForeground(Color.GREEN);
        companyNameLabel.setText("" + company.getName());
        businessNameText.setText("");
        businesstaxText.setText("");
        businessphoneText.setText("");
    }

    void adminSettings(){
        employeeName.setForeground(Color.GREEN);
        employeeName.setText("Logged" + "-[" + employee.getName() + "]" + " Admin Status" + "-[" + employee.isAdmin() + "]");
        if (!employee.isAdmin()) {
            mainMenuTabPanel.remove(adminPanel);
            mainMenuTabPanel.remove(addProducts);
            mainMenuTabPanel.remove(supplierTab);
            mainMenuTabPanel.remove(customerTab);
            mainMenuTabPanel.remove(statisticsTab);
            mainMenuTabPanel.remove(businesstab);
        }
    }

    private void deskClosing() {
        double total = 0;
        LocalDateTime fecha;
        String dateFormatted;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        int invoiceAmount = 0;
        if (allInvoiced()) {
            total = totalCaja();
            fecha = LocalDateTime.now();
            dateFormatted = fecha.format(formatter);
            invoiceAmount = salesList.size();
            listStadisticTable(dateFormatted, total, invoiceAmount);
            salesList.clear();
            listaVentas();
            setAmountDay.setText("Total");
        } else {
            JOptionPane.showMessageDialog(null, "No invoices");
        }
    }

    private void generateInvoice() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int rowSelection = salesTable.getSelectedRow();
        Boolean aux = (Boolean) salesTable.getValueAt(rowSelection, 4);
        if (rowSelection != -1 && aux.equals(false)) {
            dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                createInvoice((Double) salesTable.getValueAt(rowSelection, 0), (String) salesTable.getValueAt(rowSelection, 1), (Double) salesTable.getValueAt(rowSelection, 2), (String) salesTable.getValueAt(rowSelection, 3));
                isInvoiced((Double) salesTable.getValueAt(rowSelection, 0));
                listaVentas();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Venta ya facturada");
        }
    }


    private double totalCaja() {
        double acum = 0;
        for (Sale sale : salesList) {
            acum += sale.getTotalAmmount();
        }
        return acum;
    }

    private boolean allInvoiced() {
        boolean flag = true;
        for (Sale sale : salesList) {
            if (!sale.isInvoiced()) {
                flag = false;
            }
        }
        return flag;
    }

    private void isInvoiced(Double paymentProof) {
        for (Sale sale : salesList) {
            if (sale.getOperationNumber().equals(paymentProof)) {
                sale.setInvoiced(true);
            }
        }
    }

    private boolean checkSupplierRequirements() {
        return !supplierIDField.getText().equals("") && !supplierNameField.getText().equals("") && !supplierPhoneField.getText().equals("") && !supplierWorkingArea.getText().equals("");
    }

    private void addCustomer() throws FieldCompletionException {
        Customer aux = new Customer();
        if (!chekCustomerFields()) {
            throw new FieldCompletionException("Please fill all the required fields");
        } else {
            aux.setName(CnameCustomer.getText());
            aux.setTaxpayerID(CtaxPayerIDCustomer.getText());
            aux.setPhoneNumber(CphoeNumberCustomer.getText());
            aux.setCategory(CcategoryCustomer.getText());
            customerList.add(aux);
            customerList();
            clearCustomerFields();
        }
        loadCustomerCombobox();
        listSuppliers();
    }

    private void deleteCustomerFromList() throws RowNotSelectedException {
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
                deleteCustomer(aux);
                loadCustomerCombobox();
                customerList();
            }
        } else {
            throw new RowNotSelectedException("Select a row");
        }
    }

    private void deleteCustomer(Customer aux) {
        int pos = 0;
        boolean flag = false;
        for (int i = 0; i < customerList.size(); i++) {
            if (aux.getTaxpayerID().equals(customerList.get(i).getTaxpayerID()) && !flag) {
                pos = i;
            }
        }
        customerList.remove(pos);
    }

    private boolean chekCustomerFields() {
        return !CnameCustomer.getText().equals("") && !CtaxPayerIDCustomer.getText().equals("") && !CphoeNumberCustomer.getText().equals("") && !CcategoryCustomer.getText().equals("");
    }

    private void addSupplier() throws FieldCompletionException {
        Supplier aux = new Supplier();
        if (!checkSupplierRequirements()) {
            throw new FieldCompletionException("Please fill all the required fields");
        } else {
            aux.setName(supplierNameField.getText());
            aux.setTaxpayerID(supplierIDField.getText());
            aux.setPhoneNumber(supplierPhoneField.getText());
            aux.setWorkingArea(supplierWorkingArea.getText());
            suppliersList.add(aux);
            setComboBoxConfig();
            clearCustomerFields();
        }
        listSuppliers();
    }

    private void clearCustomerFields() {
        CnameCustomer.setText("");
        CtaxPayerIDCustomer.setText("");
        CphoeNumberCustomer.setText("");
        CcategoryCustomer.setText("");
    }

    private void setComboBoxConfig() {
        comboBox1.removeAllItems();
        Object[] arr = new Object[suppliersList.size()];
        arr = suppliersList.toArray();
        for (Object o : arr) {
            comboBox1.addItem(o);
        }
    }

    private void loadCustomerCombobox() {
        comboBoxCustomers.removeAllItems();
        Object[] arr = customerList.toArray();
        for (Object o : arr) {
            comboBoxCustomers.addItem(o);
        }
    }

    private void confirmPruchase() {
        Sale newSale = new Sale();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        if (tableHaveData()) {
            dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                double amount = ammountAcc;
                Customer aux = (Customer) comboBoxCustomers.getSelectedItem();
                String id = aux.getTaxpayerID();
                String nameAux = aux.getName();
                newSale = new Sale(nameAux, amount, id);
                if (!sellExist(newSale)) {
                    salesList.add(newSale);
                    textFinalPrice.setText("Total Price");
                    mapTolist = shopList.getHashMap().values();
                    finalProductPDF = new ArrayList<>(mapTolist);
                    shopList.hashmapClear();
                }
            }
        }
        listCart();
        listaVentas();
    }

    private boolean tableHaveData() {
        return shopList.hashmapSize() > 0;
    }

    private boolean sellExist(Sale aux) {
        boolean flag = false;
        for (Sale sale : salesList) {
            if (sale.getOperationNumber().equals(aux.getOperationNumber()) && !flag) {
                flag = true;
            }
        }
        return flag;
    }

    protected void setEmployee(Employee employee) {
        this.employee = employee;
    }

    private void deleteProductFromList() throws RowNotSelectedException {
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
            throw new RowNotSelectedException("Select a row");
        }
    }

    private void addToCart() throws RowNotSelectedException {
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

    private void setTotalPrice() {
        double totalprice = 0;
        for (int i = 0; i < cartTable.getRowCount(); i++) {
            totalprice = totalprice + Double.parseDouble(String.valueOf(cartTable.getValueAt(i, 5)));
        }
        textFinalPrice.setVisible(true);
        textFinalPrice.setText("" + totalprice);
        textFinalPrice.setForeground(Color.GREEN);
        ammountAcc = totalprice;
    }

    private void setTotalDay() {
        double totalprice = 0;
        for (int i = 0; i < salesTable.getRowCount(); i++) {
            totalprice = totalprice + Double.parseDouble(String.valueOf(salesTable.getValueAt(i, 2)));
        }
        setAmountDay.setVisible(true);
        setAmountDay.setText("" + totalprice);
        setAmountDay.setForeground(Color.GREEN);
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
                productList.addElement(aux.getId(), aux);
                listProducts();
                listClientProducts();
                listCart();
                setTotalPrice();
            }
        }
    }

    private void deleteSupplierFromList() throws RowNotSelectedException {
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
            throw new RowNotSelectedException("Select a row");
        }
    }

    private int productStockShopList(String id) {
        int stock = 0;
        stock = shopList.getElementByKey(id).getStock();
        return stock;
    }

    private int productStock(String id) {
        int stock = 0;
        stock = productList.getElementByKey(id).getStock();
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

    private void deleteProductShop(String id) {
        shopList.deleteElement(id);
    }

    private void deleteProduct(String id) throws RowNotSelectedException {
        if(productList.deleteElement(id)){
            cleanLabels();
        } else {
            throw new RowNotSelectedException("Select a row");
        }
    }

    private void deleteSupplier(String name) throws RowNotSelectedException {
        Supplier aux = searchSupplier(name);
        if (suppliersList.contains(aux)) {
            suppliersList.remove(aux);
        } else {
            throw new RowNotSelectedException("Select a row");
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
        int rowSelection = clientProductList.getSelectedRow();
        String id = String.valueOf(clientProductList.getValueAt(rowSelection, 0));
        String supplier = String.valueOf(clientProductList.getValueAt(rowSelection, 1));
        String name = String.valueOf(clientProductList.getValueAt(rowSelection, 2));
        int stock = Integer.parseInt(String.valueOf(clientProductList.getValueAt(rowSelection, 3))); // 7
        double price = Double.parseDouble(String.valueOf(clientProductList.getValueAt(rowSelection, 4)));
        double sellingPrice = Double.parseDouble(String.valueOf(clientProductList.getValueAt(rowSelection, 5)));
        int newStock = 0;
        if (!userAmount.getText().isEmpty()) {
            newStock = Integer.parseInt(userAmount.getText()); // 5
            if (newStock > 0 && newStock <= stock) {
                auxStock = stock - newStock;
                Product aux = new Product(id, supplier, name, newStock, price, sellingPrice);
                Product aux2 = new Product(id, supplier, name, auxStock, price, sellingPrice);
                if (shopList.keyExists(id)) {
                    int stockAux = productStockShopList(id) + newStock;
                    Product aux3 = new Product(id, supplier, name, stockAux, price, sellingPrice);
                    shopList.addElement(aux3.getId(), aux3);
                    productList.addElement(aux2.getId(), aux2);
                } else {
                    shopList.addElement(aux.getId(), aux);
                    productList.addElement(aux2.getId(), aux2);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Unavailable stock for this operation");
            }


        } else {
            JOptionPane.showMessageDialog(null, "Enter a valid value");
        }
    }


    private void modifyProduct() throws RowNotSelectedException {
        int rowSelection = productsTable.getSelectedRow();
        if (rowSelection != -1) {
            String id = String.valueOf(productsTable.getValueAt(rowSelection, 0));
            String supplier = String.valueOf(productsTable.getValueAt(rowSelection, 1));
            String name = String.valueOf(productsTable.getValueAt(rowSelection, 2));
            int stock = Integer.parseInt(String.valueOf(productsTable.getValueAt(rowSelection, 3)));
            double price = Double.parseDouble(String.valueOf(productsTable.getValueAt(rowSelection, 4)));
            double sellingPrice = Double.parseDouble(String.valueOf(productsTable.getValueAt(rowSelection, 5)));
            updateID.setText(id);
            updateSupplier.setText(supplier);
            updateName.setText(name);
            updateStock.setText(String.valueOf(stock));
            updatePrice.setText(String.valueOf(price));
            updateSellPrice.setText(String.valueOf(sellingPrice));
        } else {
            throw new RowNotSelectedException("Select a row");
        }
    }

    private void updateProduct() throws RowNotSelectedException {
        String id = updateID.getText();
        if (!id.equals("")) {
            String name = updateName.getText();
            String supplier = updateSupplier.getText();
            int stock = Integer.parseInt(updateStock.getText());
            Double price = Double.parseDouble(updatePrice.getText());
            Double sellingPrice = Double.parseDouble(updateSellPrice.getText());
            Product aux = new Product(id, supplier, name, stock, price, sellingPrice);
            productList.addElement(aux.getId(), aux);
        } else {
            throw new RowNotSelectedException("Select a row");
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
        updateSellPrice.setText("");
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
            data.setSellingPrice(Double.parseDouble(sellPriceField.getText()));
            productList.addElement(data.getId(), data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    private void listProducts() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Code", "Supplier", "Name", "Stock", "Price", "Sell Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Map.Entry<String, Product> entry : productList.getHashMap().entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue().getSupplierName(), entry.getValue().getName(), entry.getValue().getStock(), +entry.getValue().getPrice(), entry.getValue().getSellingPrice()});
        }
        productsTable.setModel(model);
    }

    private void listStadisticTable(String fecha, double total, int cantVentas) { //Borra las estadisticas si se genera una nueva factura.
        DefaultTableModel model = (DefaultTableModel) statisticsTable.getModel();
        model.addRow(new Object[]{fecha, total, cantVentas});
        statisticsTable.setModel(model);
    }

    private void createStatisticsTable() { //Borra las estadisticas si se genera una nueva factura.
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Date", "Total/Day", "N° Sales"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        statisticsTable.setModel(model);
    }

    private void listCart() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Code", "Customer", "Name", "Ammount", "Unity price", "Total price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Map.Entry<String, Product> entry : shopList.getHashMap().entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue().getSupplierName(), entry.getValue().getName(), entry.getValue().getStock(), entry.getValue().getSellingPrice(), entry.getValue().getSellingPrice() * entry.getValue().getStock()});
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



        for (Map.Entry<String, Product> entry : productList.getHashMap().entrySet()) {
            model.addRow(new Object[]{entry.getKey(), entry.getValue().getSupplierName(), entry.getValue().getName(), entry.getValue().getStock(), entry.getValue().getPrice(), entry.getValue().getSellingPrice()});
        }


        clientProductList.setModel(model);
    }

    private void listaVentas() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Operation Nº", "Client Name", "Total Ammount", "Date", "Facturado"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Sale sale : salesList) {
            model.addRow(new Object[]{sale.getOperationNumber(), sale.getCustomerName(), sale.getTotalAmmount(), sale.getDateFormatted(), sale.isInvoiced()});
        }
        salesTable.setModel(model);
    }

    private void customerList() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Name", "ID", "Phone Number", "Category"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Customer customer : customerList) {
            model.addRow(new Object[]{customer.getName(), customer.getTaxpayerID(), customer.getPhoneNumber(), customer.getCategory()});
        }
        customerTable.setModel(model);
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
        customerList();
        createStatisticsTable();
    }

    public void createInvoice(double operation, String customer, double price, String formattedDate) {
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
            String[] businessData = new String[finalProductPDF.size()];
            businessData = placeholderBusiness.generateDataForBills().split(",");

            Customer aux = lookForCustomer(customer);
            String buyerDataString = aux.generateDataForBills();
            String[] buyerDataStringArray = buyerDataString.split(",");

            pdfTextClass.addLineOfText("BUSINESS INFORMATION: ", 25, pageHeight - 25, font, 14, Color.BLACK);
            pdfTextClass.addMultiLineText(businessData, 14.50f, 25, pageHeight - 50, font, 14, Color.BLACK);
            pdfTextClass.addLineOfText("BUYER INFORMATION: ", 25, pageHeight - 100, font, 14, Color.BLACK);
            pdfTextClass.addMultiLineText(buyerDataStringArray, 14.50f, 25, pageHeight - 125, font, 14, Color.BLACK);
            pdfTextClass.addLineOfText("DATE: " + formattedDate, 25, pageHeight - 220, font, 14, Color.BLACK);
            pdfTextClass.addLineOfText("FINAL PRICE: $" + price, 25, pageHeight - 245, font, 14, Color.BLACK);
            PDFTableClass table = new PDFTableClass(doc, contentStream);

            int[] cellWidth = {130, 130, 130, 130};
            table.setTable(cellWidth, 30, 25, pageHeight - 370);
            table.setTableFont(font, 14, Color.BLACK);
            Color tableBodyColor = new Color(187, 187, 187);
            Color tableHeadColor = new Color(39, 114, 30);

            table.addCell("Item name", tableHeadColor);
            table.addCell("Item ammount", tableHeadColor);
            table.addCell("Unit price", tableHeadColor);
            table.addCell("Total price", tableHeadColor);

            for (Product product : finalProductPDF) {
                table.addCell(product.getName(), tableBodyColor);
                table.addCell(String.valueOf(product.getStock()), tableBodyColor);
                table.addCell(String.valueOf(product.getSellingPrice()), tableBodyColor);
                table.addCell(String.valueOf(product.getSellingPrice() * product.getStock()), tableBodyColor);
            }
            Double doubleAux = operation;
            String operationAux = doubleAux.toString();
            Sale auxSale = new Sale(aux.getName(),price, operationAux);
            createJSON(auxSale,finalProductPDF);
            //createJSON(auxSale,finalProductPDF);
            contentStream.close();
            String idConcat = "Operation N° " + operation + " " + customer;
            String namePDF = idConcat.concat(".pdf");
            doc.save(namePDF);
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchProduct() {
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
            for (Map.Entry<String, Product> entry : productList.getHashMap().entrySet()) {
                if (entry.getValue().getName().equalsIgnoreCase(productName)) {
                    model.addRow(new Object[]{entry.getKey(), entry.getValue().getSupplierName(), entry.getValue().getName(), entry.getValue().getStock(), entry.getValue().getPrice(), entry.getValue().getSellingPrice()});
                }
            }
            clientProductList.setModel(model);
        }
    }


    private Customer lookForCustomer(String name) {
        for (Customer c : customerList) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    private void hardCode() {
        Supplier aux = new Supplier("Fravega", "3333333", "155757575", "IT");
        Supplier aux1 = new Supplier("Compumundo", "6555555", "22333333", "IT");
        Supplier aux2 = new Supplier("Ribeiro", "11111111", "44444444", "IT");
        Supplier aux3 = new Supplier("Delta", "22222222", "2222222", "IT");
        suppliersList.add(aux);
        suppliersList.add(aux1);
        suppliersList.add(aux2);
        suppliersList.add(aux3);
        Product new1 = new Product("1", aux.getName(), "PC", 200, 70000.00, 100000.00);
        Product new2 = new Product("2", aux1.getName(), "KEYBOARD", 150, 5000.00, 5000.00);
        Product new3 = new Product("3", aux2.getName(), "MOUSE", 500, 3000.00, 4000.00);
        Product new4 = new Product("4", aux3.getName(), "HEADPHONES", 100, 6000.00, 8000.00);
        productList.addElement(new1.getId(), new1);
        productList.addElement(new2.getId(), new2);
        productList.addElement(new3.getId(), new3);
        productList.addElement(new4.getId(), new4);
        Customer auxC1 = new Customer("Juan", "22233333", "15550000", "A");
        Customer auxC2 = new Customer("Pedro", "111111111", "222222222", "B");
        Customer auxC3 = new Customer("Ignacio", "555555555", "3333333", "B");
        Customer auxC4 = new Customer("Naza", "66666666", "99999999", "C");
        customerList.add(auxC1);
        customerList.add(auxC2);
        customerList.add(auxC3);
        customerList.add(auxC4);
        setComboBoxConfig();
        loadCustomerCombobox();
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






