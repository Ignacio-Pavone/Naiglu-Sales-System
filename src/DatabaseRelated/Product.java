package DatabaseRelated;

import java.util.Objects;

public class Product {
    private String id;
    private String name;
    private String supplierName;
    private int stock;
    private Double price;
    private Double sellPrice;



    public Product(){

    }

    public Product(String id, String supplierName, String name, int stock, Double normalPrice, Double price) {
        this.id = id;
        this.supplierName = supplierName;
        this.name = name;
        this.stock = stock;
        this.price = normalPrice;
        this.sellPrice = price;
    }

    public Product(String id, String name, int stock, Double price) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Override
    public String toString() {
        return  "Product: " + name + " ,Quantity: " + stock + " ,Price: " + sellPrice ;
    }
}
