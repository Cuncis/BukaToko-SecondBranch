package com.boss.cuncis.bukatoko.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Notification {

    @SerializedName("data")
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public class Data {
        @SerializedName("description")
        private String message;

        @SerializedName("created_at")
        private String date;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

}
