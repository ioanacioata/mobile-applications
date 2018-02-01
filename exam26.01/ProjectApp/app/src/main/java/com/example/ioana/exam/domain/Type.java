package com.example.ioana.exam.domain;

import com.google.gson.annotations.SerializedName;

public enum Type {
    @SerializedName("small")
    SMALL,
    @SerializedName("medium")
    MEDIUM,
    @SerializedName("large")
    LARGE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
