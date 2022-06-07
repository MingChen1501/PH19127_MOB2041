package com.example.ph19127_mob2041.model;

public class Top {
    String id;
    String category;
    String title;
    String author;
    double cost;
    int amount;

    public Top(String id, String category, String title, String author, double cost, int amount) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.author = author;
        this.cost = cost;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Top top = (Top) o;

        return id.equals(top.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
