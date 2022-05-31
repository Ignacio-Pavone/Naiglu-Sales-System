package DatabaseRelated;

import DatabaseRelated.PDFCreation.PDFTableClass;
import DatabaseRelated.PDFCreation.PDFTextClass;
import Exceptions.FieldCompletionException;
import Exceptions.RowNotSelectedException;
import PersonRelated.Customer;
import PersonRelated.MyBusiness;
import PersonRelated.Supplier;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static DatabaseRelated.JsonUtiles.createJSON;

public class SalesSystem {
    private double ammountAcc;
    private MyBusiness placeholderBusiness = new MyBusiness("Name", "123321", "2222222");
    private final GenericHashMap<String, Product> productList = new GenericHashMap<>();
    private final GenericHashMap<String, Product> shopList = new GenericHashMap<>();
    private final ArrayList<Sale> salesList = new ArrayList<>(); // lista ventas Concretadas
    private final HashSet<Supplier> suppliersList = new HashSet<>(); // lista proveedores
    private final ArrayList<Customer> customerList = new ArrayList<>(); // lista clientes
    private Collection<Product> mapTolist;
    private ArrayList<Product> finalProductPDF = new ArrayList<>();

    public GenericHashMap<String, Product> getProductList() {
        return productList;
    }

    public GenericHashMap<String, Product> getShopList() {
        return shopList;
    }

    public ArrayList<Sale> getSalesList() {
        return salesList;
    }

    public HashSet<Supplier> getSuppliersList() {
        return suppliersList;
    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }

    public Collection<Product> getMapTolist() {
        return mapTolist;
    }

    public ArrayList<Product> getFinalProductPDF() {
        return finalProductPDF;
    }

    public void listCart(JTable cartTable) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Code", "Customer", "Name", "Ammount", "Unity price", "Total price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Iterator entries = shopList.getIterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            Product value = (Product) entry.getValue();
            model.addRow(new Object[]{key, value.getSupplierName(), value.getName(), value.getStock(), value.getSellingPrice(), value.getSellingPrice() * value.getStock()});
        }
        cartTable.setModel(model);
    }

    public void addElementProductList(String id, Product producto) {
        productList.addElement(id, producto);
    }

    public void listClientProducts(JTable clientProductList) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Code", "Supplier", "Name", "Stock", "Price", "Sell Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Iterator entries = productList.getIterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            Product value = (Product) entry.getValue();
            model.addRow(new Object[]{key, value.getSupplierName(), value.getName(), value.getStock(), value.getPrice(), value.getSellingPrice()});
        }
        clientProductList.setModel(model);
    }

    public void listProducts(JTable productsTable) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Code", "Supplier", "Name", "Stock", "Price", "Sell Price"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Iterator entries = productList.getIterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();
            Product value = (Product) entry.getValue();
            model.addRow(new Object[]{key, value.getSupplierName(), value.getName(), value.getStock(), value.getPrice(), value.getSellingPrice()});
        }
        productsTable.setModel(model);
    }

    public void deleteSupplierFromList(JTable supplierTable, JComboBox comboBox1) throws RowNotSelectedException {
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
                listSuppliers(supplierTable);
                setComboBoxConfig(comboBox1);
            }
        } else {
            throw new RowNotSelectedException("Select a row");
        }
    }

    public void deskClosing(JTable salesTable, JTable statisticsTable, JLabel setAmountDay) {
        double total = 0;
        LocalDateTime fecha;
        String dateFormatted;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        int invoiceAmount = 0;
        if (allInvoiced()) {
            total = totalCash();
            fecha = LocalDateTime.now();
            dateFormatted = fecha.format(formatter);
            invoiceAmount = salesList.size();
            listStatistics(statisticsTable, dateFormatted, total, invoiceAmount);
            salesList.clear();
            salesList(salesTable);
            setAmountDay.setText("Total");
        } else {
            JOptionPane.showMessageDialog(null, "No invoices");
        }
    }

    private void listStatistics(JTable statisticsTable, String date, double total, int salesAmmount) { //Borra las estadisticas si se genera una nueva factura.
        DefaultTableModel model = (DefaultTableModel) statisticsTable.getModel();
        model.addRow(new Object[]{date, total, salesAmmount});
        statisticsTable.setModel(model);
    }

    public void createStatisticsTable(JTable statisticsTable) { //Borra las estadisticas si se genera una nueva factura.
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Date", "Total/Day", "N° Sales"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        statisticsTable.setModel(model);
    }

    public void deleteProductShop(String id) {
        shopList.deleteElement(id);
    }

    public void deleteProduct(String id) throws RowNotSelectedException {
        if (!productList.deleteElement(id)) {
            throw new RowNotSelectedException("The product doesn't exist");
        }
    }

    private double totalCash() {
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

    public void isInvoiced(Double paymentProof) {
        for (Sale sale : salesList) {
            if (sale.getOperationNumber().equals(paymentProof)) {
                sale.setInvoiced(true);
            }
        }
    }

    public void deleteSupplier(String name) throws RowNotSelectedException {
        Supplier aux = searchSupplier(name);
        if (suppliersList.contains(aux)) {
            suppliersList.remove(aux);
        } else {
            throw new RowNotSelectedException("Select a row");
        }
    }

    public Supplier searchSupplier(String name) {
        for (Supplier s : suppliersList) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    public void cartProductsData(JTable clientProductList, JTextField userAmount) {
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

    public void salesList(JTable salesTable) {
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

    public void listSuppliers(JTable supplierTable) {
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

    public void searchProduct(JTable clientProductList, JTextField enterProductSearch) {
        String productName = enterProductSearch.getText();
        if (productName.isEmpty()) {
            listClientProducts(clientProductList);
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

    public void customerList(JTable customerTable) {
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

    public int productStockShopList(String id) {
        int stock = 0;
        stock = shopList.getElementByKey(id).getStock();
        return stock;
    }

    public void hardCode(JComboBox comboBox1, JComboBox comboBoxCustomers) {
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
    }

    public void setComboBoxConfig(JComboBox comboBox1) {
        comboBox1.removeAllItems();
        Object[] arr = new Object[suppliersList.size()];
        arr = suppliersList.toArray();
        for (Object o : arr) {
            comboBox1.addItem(o);
        }
    }

    public void loadCustomerCombobox(JComboBox comboBoxCustomers) {
        comboBoxCustomers.removeAllItems();
        Object[] arr = customerList.toArray();
        for (Object o : arr) {
            comboBoxCustomers.addItem(o);
        }
    }

    public void updateProduct(JTextField updateID, JTextField updateName, JTextField updateSupplier, JTextField updateStock, JTextField updatePrice, JTextField updateSellPrice) throws RowNotSelectedException {
        String id = updateID.getText();
        if (!id.equals("")) {
            String name = updateName.getText();
            String supplier = updateSupplier.getText();
            int stock = Integer.parseInt(updateStock.getText());
            Double price = Double.parseDouble(updatePrice.getText());
            Double sellingPrice = Double.parseDouble(updateSellPrice.getText());
            Product aux = new Product(id, supplier, name, stock, price, sellingPrice);
            addElementProductList(aux.getId(), aux);
        } else {
            throw new RowNotSelectedException("Select a row");
        }
    }

    public void addProduct(JTextField codeField, JComboBox comboBox1, JTextField nameField, JTextField stockField, JTextField priceField, JTextField sellPriceField) {
        try {
            Product data = new Product();
            data.setId(codeField.getText());
            Supplier aux = (Supplier) comboBox1.getSelectedItem();
            data.setSupplierName(aux.getName());
            data.setName(nameField.getText());
            data.setStock(Integer.parseInt(stockField.getText()));
            data.setPrice(Double.parseDouble(priceField.getText()));
            data.setSellingPrice(Double.parseDouble(sellPriceField.getText()));
            addElementProductList(data.getId(), data);
        } catch (Exception e) {
            java.lang.System.out.println(e.getMessage());
        }
    }

    public void deleteCustomerFromList(JTable customerTable, JComboBox comoBoxCustomer) throws RowNotSelectedException {
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
                loadCustomerCombobox(comoBoxCustomer);
                customerList(customerTable);
            }
        } else {
            throw new RowNotSelectedException("Select a row");
        }
    }

    private boolean tableHasData() {
        return shopList.hashmapSize() > 0;
    }

    public void addToCart(JTable clientProductList, JTable cartTable, JTable productsTable, JTextField userAmount, JLabel ammountValueLabel, JLabel textFinalPrice) throws RowNotSelectedException {
        int rowSelection = clientProductList.getSelectedRow();
        if (rowSelection != -1 && !ammountValueLabel.getText().equals("")) {
            cartProductsData(clientProductList, userAmount);
            listCart(cartTable);
            listProducts(productsTable);
            listClientProducts(clientProductList);
            userAmount.setText("");
            setTotalPrice(cartTable, textFinalPrice);
        } else {
            throw new RowNotSelectedException("Select a row");
        }
    }

    public void modifyProduct(JTable productsTable, JTextField updateID, JTextField updateSupplier, JTextField updateName, JTextField updateStock, JTextField updatePrice, JTextField updateSellPrice) throws RowNotSelectedException {
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

    public void deleteProductFromCart(JTable cartTable) {
        int row = 0;
        row = cartTable.getSelectedRow();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int newStock = 0;
        if (row != -1) {
            dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                String id = String.valueOf(cartTable.getValueAt(row, 0));
                int stock = Integer.parseInt(String.valueOf(cartTable.getValueAt(row, 3)));
                newStock = productStock(id) + stock;
                Product exist = searchProduct(id);
                Product aux = new Product(exist.getId(), exist.getSupplierName(), exist.getName(), newStock, exist.getPrice(), exist.getSellingPrice());
                deleteProductShop(id);
                getProductList().addElement(aux.getId(), aux);
            }
        }
    }

    public void addSupplier(JTextField supplierNameField, JTextField supplierIDField, JTextField supplierPhoneField, JTextField supplierWorkingArea) throws FieldCompletionException {
        Supplier aux = new Supplier();
        aux.setName(supplierNameField.getText());
        aux.setTaxpayerID(supplierIDField.getText());
        aux.setPhoneNumber(supplierPhoneField.getText());
        aux.setWorkingArea(supplierWorkingArea.getText());
        suppliersList.add(aux);
    }

    public void addCustomer(JTable customerTable, JTextField CnameCustomer, JTextField CtaxPayerIDCustomer, JTextField CphoeNumberCustomer, JTextField CcategoryCustomer) throws FieldCompletionException {
        Customer aux = new Customer();
        aux.setName(CnameCustomer.getText());
        aux.setTaxpayerID(CtaxPayerIDCustomer.getText());
        aux.setPhoneNumber(CphoeNumberCustomer.getText());
        aux.setCategory(CcategoryCustomer.getText());
        getCustomerList().add(aux);
        customerList(customerTable);
    }

    public Product searchProduct(String id) {
        return productList.getElementByKey(id);
    }

    public int productStock(String id) {
        int stock = 0;
        stock = productList.getElementByKey(id).getStock();
        return stock;
    }

    public void setTotalPrice(JTable cartTable, JLabel textFinalPrice) {
        double totalprice = 0;
        for (int i = 0; i < cartTable.getRowCount(); i++) {
            totalprice = totalprice + Double.parseDouble(String.valueOf(cartTable.getValueAt(i, 5)));
        }
        textFinalPrice.setVisible(true);
        textFinalPrice.setText("" + totalprice);
        textFinalPrice.setForeground(Color.GREEN);
        ammountAcc = totalprice;
    }

    public void setTotalDay(JLabel setAmountDay, JTable salesTable) {
        double totalprice = 0;
        java.lang.System.out.println(salesTable.getRowCount());
        for (int i = 0; i < salesTable.getRowCount(); i++) {
            totalprice = totalprice + Double.parseDouble(String.valueOf(salesTable.getValueAt(i, 2)));
        }
        setAmountDay.setVisible(true);
        setAmountDay.setText("" + totalprice);
        setAmountDay.setForeground(Color.GREEN);
    }

    public void confirmPruchase(JComboBox comboBoxCustomers, JLabel textFinalPrice) {
        Sale newSale = new Sale();
        int dialogButton = JOptionPane.YES_NO_OPTION;
        if (tableHasData()) {
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
            Sale auxSale = new Sale(aux.getName(), price, operationAux);
            createJSON(auxSale, finalProductPDF);
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

    public Customer lookForCustomer(String name) {
        for (Customer c : customerList) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

    public void generateInvoice(JTable salesTable) {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int rowSelection = salesTable.getSelectedRow();
        Boolean aux = (Boolean) salesTable.getValueAt(rowSelection, 4);
        if (rowSelection != -1 && aux.equals(false)) {
            dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure?", "WARNING", dialogButton);
            if (dialogButton == JOptionPane.YES_OPTION) {
                createInvoice((Double) salesTable.getValueAt(rowSelection, 0), (String) salesTable.getValueAt(rowSelection, 1), (Double) salesTable.getValueAt(rowSelection, 2), (String) salesTable.getValueAt(rowSelection, 3));
                isInvoiced((Double) salesTable.getValueAt(rowSelection, 0));
                salesList(salesTable);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Sale already invoiced");
        }
    }

    public void deleteCustomer(Customer aux) {
        int pos = 0;
        boolean flag = false;
        for (int i = 0; i < customerList.size(); i++) {
            if (aux.getTaxpayerID().equals(customerList.get(i).getTaxpayerID()) && !flag) {
                pos = i;
            }
        }
        customerList.remove(pos);
    }
}
