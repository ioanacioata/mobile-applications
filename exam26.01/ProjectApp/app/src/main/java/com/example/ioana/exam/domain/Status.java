package com.example.ioana.exam.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ioana on 30/01/2018.
 */

public enum Status {
    @SerializedName("idea")
    IDEA,
    @SerializedName("pending")
    PENDING,
    @SerializedName("discarded")
    DISCARDED,
    @SerializedName("approved")
    APPROVED;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
