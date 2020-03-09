package com.recyclerview_retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataModel {

    @SerializedName("data")
    private Data data;

    @SerializedName("status")
    private String status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }



    public class Data {

        @SerializedName("id")
        private List<AppliedProduct> appliedProduct = null;

        public List<AppliedProduct> getAppliedProduct() {
            return appliedProduct;
        }

        public void setAppliedProduct(List<AppliedProduct> appliedProduct) {
            this.appliedProduct = appliedProduct;
        }
    }

    public class  AppliedProduct{

        @SerializedName("name")
        private String name;

        @SerializedName("content")
        private String content;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }



}
