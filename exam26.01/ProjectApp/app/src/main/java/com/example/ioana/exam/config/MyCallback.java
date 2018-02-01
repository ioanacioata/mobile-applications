package com.example.ioana.exam.config;

/**
 * General interface for actions on the server
 */
public interface MyCallback {
    void showError(String location, String message);

    void clear();

    void showSuccess(String location, String message);
}
