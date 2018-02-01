package com.example.ioana.exam.domain;

import android.arch.persistence.room.TypeConverter;

/**
 * Converter used by Room, to help persisting the Enum Status.
 */
public class StatusConverter {
    @TypeConverter
    public static Status toType(String status) {
        status = status.toUpperCase();
        return Status.valueOf(status);
    }

    @TypeConverter
    public static String toString(Status status) {
        return status.toString();
    }
}
