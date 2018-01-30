package com.example.ioana.gamestore.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ioana on 30/01/2018.
 */

enum Type {
    @SerializedName("action")
    ACTION("action"),
    @SerializedName("adventure")
    ADVENTURE("adventure"),
    @SerializedName("board")
    BOARD("board");

    private String s;

    Type(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
