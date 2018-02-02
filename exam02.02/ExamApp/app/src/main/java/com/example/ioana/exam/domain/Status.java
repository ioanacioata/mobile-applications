package com.example.ioana.exam.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ioana on 30/01/2018.
 */

public enum Status {
    @SerializedName("available")
    AVAILABLE,
    @SerializedName("reserved")
    RESERVED,
    @SerializedName("confirmed")
    CONFIRMED,
    @SerializedName("taken")
    TAKEN;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
