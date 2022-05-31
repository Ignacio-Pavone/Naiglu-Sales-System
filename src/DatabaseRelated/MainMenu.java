package DatabaseRelated;

import Exceptions.FieldCompletionException;
import Exceptions.RowNotSelectedException;
import PersonRelated.MyBusiness;
import UserRelated.Employee;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;


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
    private MyBusiness company = new MyBusiness();
    private Employee employee = new Employee();


    public MainMenu(JFrame parent) {
        super(parent);
        app.supplierReadFile();
        app.customerReadFile();
        app.productReadFile();
        app.setComboBoxConfig(comboBox1);
        app.loadCustomerCombobox(comboBoxCustomers);
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
                app.addProduct(codeField,comboBox1,nameField,stockField,priceField,sellPriceField);
                app.listProducts(productsTable);
                app.listClientProducts(clientProductList);
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
                    app.modifyProduct(productsTable,updateID,updateSupplier,updateName,updateStock,updatePrice,updateSellPrice);
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
                    app.updateProduct(updateID,updateName,updateSupplier,updateStock,updatePrice,updateSellPrice);
                    cleanLabels();
                    app.listClientProducts(clientProductList);
                    app.listProducts(productsTable);
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
                            app.listProducts(productsTable);
                            app.listClientProducts(clientProductList);

                        }
                    }else{
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
                    app.addToCart(clientProductList,cartTable,productsTable,userAmount,ammountValueLabel,textFinalPrice);
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
                app.listProducts(productsTable);
                app.listClientProducts(clientProductList);
                app.listCart(cartTable);
                app.setTotalPrice(cartTable,textFinalPrice);
            }
        });
        confirmPurchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.confirmPruchase(comboBoxCustomers,textFinalPrice);
                app.listCart(cartTable);
                app.salesList(salesTable);
                app.setTotalDay(setAmountDay, salesTable);
            }
        });
        addSupplierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    app.addSupplier(supplierNameField,supplierIDField,supplierPhoneField,supplierWorkingArea);
                    app.setComboBoxConfig(comboBox1);
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
                    app.deleteSupplierFromList(supplierTable, comboBox1);
                } catch (RowNotSelectedException ex) {
                    JOptionPane.showMessageDialog(null, "Select a row");
                    ex.printStackTrace();
                }
            }
        });
        generateInvoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.generateInvoice(salesTable);
            }
        });
        deskClosing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                app.deskClosing(salesTable,statisticsTable,setAmountDay);
            }
        });
        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    app.addCustomer(customerTable,CnameCustomer,CtaxPayerIDCustomer,CphoeNumberCustomer,CcategoryCustomer);
                    clearCustomerFields();
                    app.loadCustomerCombobox(comboBoxCustomers);
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
                    app.deleteCustomerFromList(customerTable,comboBoxCustomers);
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
                app.searchProduct(clientProductList, enterProductSearch);
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

    protected void setEmployee(Employee employee) {
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

    private void listingCollections() {
        labelStyle();
        tableStyle();
        setLocationRelativeTo(null);
        app.listProducts(productsTable);
        app.listClientProducts(clientProductList);
        app.listCart(cartTable);
        app.listSuppliers(supplierTable);
        app.salesList(salesTable);
        app.customerList(customerTable);
        app.createStatisticsTable(statisticsTable);
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






