package com.test.entity;

import java.io.Serializable;

public class Product implements Serializable {

    private String pid;
    private String productName;

    public Product(String pid, String productName) {
        this.pid = pid;
        this.productName = productName;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
