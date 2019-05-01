package com.boss.cuncis.bukatoko.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("data")
    private List<Product.Data> products;

    public List<Product.Data> getProducts() {
        return products;
    }

    public class Data {
        @SerializedName("image")
        private String image;

        @SerializedName("product")
        private String product;

        @SerializedName("price")
        private int price;

        @SerializedName("description")
        private String description;

        @SerializedName("id")
        private int id;

        @SerializedName("stock")
        private int stock;

        public String getImage() {
            return image;
        }

        public String getProduct() {
            return product;
        }

        public int getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }

        public int getId() {
            return id;
        }

        public int getStock() {
            return stock;
        }
    }
}