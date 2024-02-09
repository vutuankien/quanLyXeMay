package org.example.quanlyxemay;

public class Motor {
    private  String id, name, type,status, year,description;
    private double price;

    public Motor(String id, String name, String type, String status, String year, String description, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.year = year;
        this.description = description;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getYear() {
        return year;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }
}
