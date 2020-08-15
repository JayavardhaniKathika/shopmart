package com.project.shopmart.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "product")
public class Product {


    @Id
    @NotNull
    @Column(name="product_id")
    private long productId;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name="product_name")
    private String productName;

    @NotNull
    @Column(name="product_description")
    private String productDescription;

    @NotNull
    @Column(name="product_price")
    private long productPrice;

    @NotNull
    @Column(name="product_quantity")
    private long productQuantity;


    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public long getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(long productPrice) {
        this.productPrice = productPrice;
    }

    public long getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(long productQuantity) {
        this.productQuantity = productQuantity;
    }





}

