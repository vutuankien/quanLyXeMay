package org.example.quanlyxemay;

public class Customer {
    private String name,bikeName,type,cccd, baoHanh,thue;
    private double price;

    public Customer(String name, String bikeName, String type, String cccd, String baoHanh, String thue, double price) {
        this.name = name;
        this.bikeName = bikeName;
        this.type = type;
        this.cccd = cccd;
        this.baoHanh = baoHanh;
        this.thue = thue;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getCccd() {
        return cccd;
    }

    public String getBaoHanh() {
        return baoHanh;
    }

    public String getThue() {
        return thue;
    }

    public double getPrice() {
        return price;
    }

    public String getBikeName() {
        return bikeName;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBikeName(String bikeName) {
        this.bikeName = bikeName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public void setBaoHanh(String baoHanh) {
        this.baoHanh = baoHanh;
    }

    public void setThue(String thue) {
        this.thue = thue;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
