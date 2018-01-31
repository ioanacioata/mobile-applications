package com.example.ioana.gamestore.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ioana on 30/01/2018.
 */

public enum Status {
    @SerializedName("available")
    AVAILABLE,
    @SerializedName("sold")
    SOLD,
    @SerializedName("rent")
    RENT;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
