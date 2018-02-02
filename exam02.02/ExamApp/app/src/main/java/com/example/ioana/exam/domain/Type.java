package com.example.ioana.exam.domain;

import com.google.gson.annotations.SerializedName;

public enum Type {
    @SerializedName("stall")
    STALL,
    @SerializedName("royal circle")
    ROYAL_CIRCLE,
    @SerializedName("grand circle")
    GRAND_CIRCLE,
    @SerializedName("balcony")
    BALCONY;

    @Override
    public String toString() {
        return this.name().toLowerCase().replace("_"," ");
    }
}
