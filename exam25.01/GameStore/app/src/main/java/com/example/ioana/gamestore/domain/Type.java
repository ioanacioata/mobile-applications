package com.example.ioana.gamestore.domain;

import com.google.gson.annotations.SerializedName;

public enum Type {
    @SerializedName("action")
    ACTION,
    @SerializedName("adventure")
    ADVENTURE,
    @SerializedName("board")
    BOARD;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
