package com.example.ioana.gamestore.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ioana on 30/01/2018.
 */

enum Status {
    @SerializedName("available")
    AVAILABLE("available"),
    @SerializedName("sold")
    SOLD("sold"),
    @SerializedName("rent")
    RENT("rent") ;

    private String s;

    Status(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
