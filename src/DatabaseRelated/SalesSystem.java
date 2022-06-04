package DatabaseRelated;

//Wrapper Class -------------------------------------------------------------------------------------------------------

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
import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class SalesSystem {
    private double ammountAcc;

    private final GenericHashMap<String, Product> productList = new GenericHashMap<>();
    private final GenericHashMap<String, Product> shopList = new GenericHashMap<>();
    private final ArrayList<Sale> salesList = new ArrayList<>(); // lista ventas Concretadas
    private final HashSet<Supplier> suppliersList = new HashSet<>(); // lista proveedores
    private final ArrayList<Customer> customerList = new ArrayList<>(); // lista clientes
    private ArrayList<Sale> saleListJson = new ArrayList<>(); // historial real de ventas

    private ArrayList<StatisticSale> statisticSales = new ArrayList<>(); // historial de estadisticas
    private Collection<Product> mapTolist;
    private ArrayList<Product> finalProductPDF = new ArrayList<>();
    private GenericHashMap<String, Product> getProductList() {
        return productList;
    }
    private GenericHashMap<String, Product> getShopList() {
        return shopList;
    }
    private ArrayList<Sale> getSalesList() {
        return salesList;
    }
    public ArrayList<Sale> getSaleListJson() {return saleListJson;}

    private HashSet<Supplier> getSuppliersList() {
        return suppliersList;
    }
    private ArrayList<Customer> getCustomerList() {
        return customerList;
    }
    private Collection<Product> getMapTolist() {
        return mapTolist;
    }
    private ArrayList<Product> getFinalProductPDF() {
        return finalProductPDF;
    }


    // Iterators Collections ------------------------------------------------------------------------------------------
    public Iterator returnIteratorShopList() {
        return shopList.getIterator();
    }

    public Iterator returnIteratorProductList() {
        return productList.getIterator();
    }

    public Iterator returnIteratorSaleList() {
        return salesList.iterator();
    }

    public Iterator returnSuppliersHashSet() {
        return suppliersList.iterator();
    }

    public Iterator returnIteratorCustomerList() {
        return customerList.iterator();
    }

    public Iterator returnIteratorSupplierList() {
        return suppliersList.iterator();
    }

    public Iterator returnStatisticsIterator(){
        return statisticSales.iterator();
    }

    public void setAmmountAcc(double ammountAcc) {
        this.ammountAcc = ammountAcc;
    }

    public void deskClosing() {
        double total = 0;
        LocalDateTime fecha;
        String dateFormatted;
        StatisticSale sale = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        int invoiceAmount = 0;
        if (allInvoiced()) {
            total = totalCash();
            fecha = LocalDateTime.now();
            dateFormatted = fecha.format(formatter);
            invoiceAmount = salesList.size();
            JsonUtiles.createJSON(saleListJson);
            salesList.clear();
            sale = new StatisticSale(dateFormatted, total, invoiceAmount);
            statisticSales.add(sale);
        }
    }

    private double totalCash() {
        double acum = 0;
        for (Sale sale : salesList) {
            acum += sale.getTotalAmmount();
        }
        return acum;
    }

    public void deleteProductShop(String id) {
        shopList.deleteElement(id);
    }

    public void deleteProduct(String id) throws RowNotSelectedException {
        if (!productList.deleteElement(id)) {
            throw new RowNotSelectedException("The product doesn't exist");
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

    public boolean clearStatistics(){
        boolean flag = false;
        if (statisticSales.size() > 0){
            flag = true;
            statisticSales.clear();
        }
        return flag;
    }
    public boolean shopkeyExist(String key) {
        return shopList.keyExists(key);
    }

    public void addElementShopList(String key, Product value) {
        shopList.addElement(key, value);
    }

    public void addElementProductList(String key, Product value) {
        productList.addElement(key, value);
    }

    public Object[] supplierlistArray() {
        return suppliersList.toArray();
    }

    public int supplierListSize() {
        return suppliersList.size();
    }

    public Object[] customerlistArray() {
        return customerList.toArray();
    }

    public boolean tableHasData() {
        return shopList.hashmapSize() > 0;
    }

    public double getAmmountAcc() {
        return ammountAcc;
    }

    public Collection<Product> maptoList() {
        return mapTolist = shopList.getHashMap().values();
    }

    public int productStockShopList(String id) {
        int stock = 0;
        stock = shopList.getElementByKey(id).getStock();
        return stock;
    }

    public void addSupplier(String supplierNameField, String supplierIDField, String supplierPhoneField, String supplierWorkingArea) throws FieldCompletionException {
        Supplier aux = new Supplier();
        aux.setName(supplierNameField);
        aux.setTaxpayerID(supplierIDField);
        aux.setPhoneNumber(supplierPhoneField);
        aux.setWorkingArea(supplierWorkingArea);
        suppliersList.add(aux);
    }

    public void addCustomer(String CnameCustomer, String CtaxPayerIDCustomer, String CphoeNumberCustomer, String CcategoryCustomer) throws FieldCompletionException {
        Customer aux = new Customer();
        aux.setName(CnameCustomer);
        aux.setTaxpayerID(CtaxPayerIDCustomer);
        aux.setPhoneNumber(CphoeNumberCustomer);
        aux.setCategory(CcategoryCustomer);
        getCustomerList().add(aux);
    }

    public Product searchProduct(String id) {
        return productList.getElementByKey(id);
    }

    public int productStock(String id) {
        int stock = 0;
        stock = productList.getElementByKey(id).getStock();
        return stock;
    }

    public boolean saletoMap(Sale newSale) {
        boolean flag = false;
        if (!sellExist(newSale)) {
            salesList.add(newSale);
            saleListJson.add(newSale);
            mapTolist = shopList.getHashMap().values(); //TO-DO Arreglar esto
            finalProductPDF = new ArrayList<>(mapTolist);
            shopList.hashmapClear();
            flag = true;
        }
        return flag;
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

    public MyBusiness createCompany(String nameString, String taxpayerID, String phoneNumber) {
        MyBusiness company = new MyBusiness(nameString, taxpayerID, phoneNumber);
        return company;
    }

    public Customer lookForCustomer(String name) {
        for (Customer c : customerList) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
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

    public int searchSale(double operationNumber) {
        int pos = -1;
        for (int i = 0; i < salesList.size(); i++) {
            if (salesList.get(i).getOperationNumber() == operationNumber) {
                pos = i;
            }
        }
        return pos;
    }


    // Files Creation -------------------------------------------------------------------------------------------------
    public void readFiles() {
        statisticsReadFile();
        salesReadFile();
        supplierReadFile();
        customerReadFile();
        productReadFile();
        saleJsonReadFile();
    }
    public void writeFiles() {
        statisticsFile();
        supplierFile();
        customerFile();
        productFile();
        salesFile();
        saleJsonFile();
    }

    public void createFolder(File document) {
        File folder = document.getParentFile();
        folder = document.getParentFile();
        if (!folder.exists()) {
            folder.mkdir();
        }
    }

    public void supplierFile() {
        try {
            File file1 = new File("Data/Supplier.bin");
            createFolder(file1);
            FileOutputStream fileOutputStream = new FileOutputStream(file1);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for (Supplier s : suppliersList) {
                objectOutputStream.writeObject(s);
            }
            objectOutputStream.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void supplierReadFile() {
        try {
            File file1 = new File("Data/Supplier.bin");
            FileInputStream fileInputStream = new FileInputStream(file1);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            int lectura = 1;
            while (lectura == 1) {
                Supplier aux = (Supplier) objectInputStream.readObject();
                suppliersList.add(aux);
            }
            objectInputStream.close();
        } catch (IOException e) {
            e.getMessage();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void saleJsonFile() {
        try {
            File file1 = new File("Data/SaleJson.bin");
            createFolder(file1);
            FileOutputStream fileOutputStream = new FileOutputStream(file1);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for (Sale s : saleListJson) {
                objectOutputStream.writeObject(s);
            }
            objectOutputStream.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void saleJsonReadFile() {
        try {
            File file1 = new File("Data/SaleJson.bin");
            FileInputStream fileInputStream = new FileInputStream(file1);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            int lectura = 1;
            while (lectura == 1) {
                Sale aux = (Sale) objectInputStream.readObject();
                saleListJson.add(aux);
            }
            objectInputStream.close();
        } catch (IOException e) {
            e.getMessage();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void customerFile() {
        try {
            File file1 = new File("Data/Customer.bin");
            createFolder(file1);
            FileOutputStream fileOutputStream = new FileOutputStream(file1);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for (Customer s : customerList) {
                objectOutputStream.writeObject(s);
            }
            objectOutputStream.close();
        } catch (IOException e) {
           e.getMessage();
        }
    }

    public void customerReadFile() {
        try {
            File file1 = new File("Data/Customer.bin");
            FileInputStream fileInputStream = new FileInputStream(file1);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            int lectura = 1;
            while (lectura == 1) {
                Customer aux = (Customer) objectInputStream.readObject();
                customerList.add(aux);
            }
            objectInputStream.close();
        } catch (IOException e) {
            e.getMessage();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void productFile() {
        try {
            File file1 = new File("Data/Product.bin");
            createFolder(file1);
            FileOutputStream fileOutputStream = new FileOutputStream(file1);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            Iterator entries = productList.getIterator();
            while (entries.hasNext()) {
                Map.Entry entry = (Map.Entry) entries.next();
                String key = (String) entry.getKey();
                Product value = (Product) entry.getValue();
                Product aux = new Product(key, value.getSupplierName(), value.getName(), value.getStock(), value.getPrice(), value.getSellingPrice());
                objectOutputStream.writeObject(aux);
            }
            objectOutputStream.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void productReadFile() {
        try {
            File file1 = new File("Data/Product.bin");
            FileInputStream fileInputStream = new FileInputStream(file1);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            int lectura = 1;
            while (lectura == 1) {
                Product aux = (Product) objectInputStream.readObject();
                productList.addElement(aux.getId(), aux);
            }
            objectInputStream.close();
        } catch (IOException e) {
            e.getMessage();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void salesFile() {
        try {
            File file1 = new File("Data/Sales.bin");
            createFolder(file1);
            FileOutputStream fileOutputStream = new FileOutputStream(file1);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for (Sale s : salesList) {
                objectOutputStream.writeObject(s);
            }
            objectOutputStream.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void salesReadFile() {
        try {
            File file1 = new File("Data/Sales.bin");
            FileInputStream fileInputStream = new FileInputStream(file1);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            int lectura = 1;
            while (lectura == 1) {
                Sale aux = (Sale) ois.readObject();
                salesList.add(aux);
            }
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.getMessage();
        }
    }

    public void statisticsFile() {
        try {
            File file1 = new File("Data/Statistics.bin");
            createFolder(file1);
            FileOutputStream fileOutputStream = new FileOutputStream(file1);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for (StatisticSale s : statisticSales) {
                objectOutputStream.writeObject(s);
            }
            objectOutputStream.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void statisticsReadFile() {
        try {
            File file1 = new File("Data/Statistics.bin");
            FileInputStream fileInputStream = new FileInputStream(file1);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            int lectura = 1;
            while (lectura == 1) {
                StatisticSale aux = (StatisticSale) ois.readObject();
                statisticSales.add(aux);
                System.out.println(statisticSales.get(0));
            }
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.getMessage();
        }
    }

    //Invoice PDF creation --------------------------------------------------------------------------------------------
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
    public void createInvoice(double operation, String customer, double price, String formattedDate, MyBusiness myBusiness) {
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
            businessData = myBusiness.generateDataForBills().split(",");

            Customer aux = lookForCustomer(customer);
            String buyerDataString = aux.generateDataForBills();
            String[] buyerDataStringArray = buyerDataString.split(",");

            pdfTextClass.addLineOfText("BUSINESS INFORMATION: ", 25, pageHeight - 25, font, 14, Color.BLACK);
            pdfTextClass.addMultiLineText(businessData, 14.50f, 25, pageHeight - 50, font, 14, Color.BLACK);
            pdfTextClass.addLineOfText("BUYER INFORMATION: ", 25, pageHeight - 125, font, 14, Color.BLACK);
            pdfTextClass.addMultiLineText(buyerDataStringArray, 14.50f, 25, pageHeight - 150, font, 14, Color.BLACK);
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
            contentStream.close();
            String idConcat = "Invoices/Operation N° " + operation + " " + customer;
            String namePDF = idConcat.concat(".pdf");
            File file1 = new File(namePDF);
            createFolder(file1);
            doc.save(namePDF);
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openInvoice(double operation, String customer) {
        String idConcat = "Invoices/Operation N° " + operation + " " + customer;
        String namePDF = idConcat.concat(".pdf");
        File file1 = new File(namePDF);
        try {
            Desktop.getDesktop().open(file1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double increaseBalance(double ammountSale, MyBusiness myBusiness) {
        double ammountBusiness = myBusiness.getBalance();
        myBusiness.setBalance(ammountBusiness + ammountSale);
        return myBusiness.getBalance();
    }

    public double decreaseBalance(double ammountPurchase, MyBusiness myBusiness) {
        double ammountBusiness = myBusiness.getBalance();
        myBusiness.setBalance(ammountBusiness - ammountPurchase);
        return myBusiness.getBalance();
    }
}
