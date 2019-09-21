package com.example.marketim.model;

import com.google.gson.annotations.SerializedName;



public class jsonList {
    @SerializedName("date")
    private final String date;

    @SerializedName("month")
    private final String month;

    @SerializedName("marketName")
    private final String marketName;

    @SerializedName("orderName")
    private final String orderName;

    @SerializedName("productPrice")
    private final double productPrice;

    @SerializedName("productState")
    private final String productState;

    @SerializedName("productDetail")
    private final ProductDetail productDetail;

    public jsonList(String date, String month, String marketName, String orderName,
                    double productPrice, String productState, ProductDetail productDetail) {
        this.date = date;
        this.month = month;
        this.marketName = marketName;
        this.orderName = orderName;
        this.productPrice = productPrice;
        this.productState = productState;
        this.productDetail = productDetail;
    }

    public String getDate() {
        return date;
    }

    public String getMonth() {
        return month;
    }

    public String getMarketName() {
        return marketName;
    }

    public String getOrderName() {
        return orderName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public String getProductState() {
        return productState;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public static class ProductDetail {
        @SerializedName("orderDetail")
        private final String orderDetail;

        @SerializedName("summaryPrice")
        private final double summaryPrice;

        public ProductDetail(String orderDetail, double summaryPrice) {
            this.orderDetail = orderDetail;
            this.summaryPrice = summaryPrice;
        }

        public String getOrderDetail() {
            return orderDetail;
        }

        public double getSummaryPrice() {
            return summaryPrice;
        }
    }
}
