package com.example.ioana.exam.domain;

import java.io.Serializable;

/**
 * Created by Ioana on 02/02/2018.
 */

public class Text implements Serializable {
    String text;

    public Text(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
