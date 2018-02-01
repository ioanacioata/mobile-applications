package com.example.ioana.gamestore.config;

/**
 * General interface for actions on the server
 */
public interface MyCallback {
    void showError(String message);

    void clear();

    void showSuccess(String message);
}
