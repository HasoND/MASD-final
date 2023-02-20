package com.example.tourist;

public class Voucher {

    private int id;
    private String title;
    private String discount;

    public Voucher(int id, String title, String discount) {
        this.id = id;
        this.title = title;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
