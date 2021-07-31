package com.ebooker.taxiappcustomer.modals;

public class ride_history_modal {
    private String date, fromAddress, toAddress, status;

    public ride_history_modal(String date, String fromAddress, String toAddress, String status) {
        this.date = date;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getStatus() {
        return status;
    }
}
