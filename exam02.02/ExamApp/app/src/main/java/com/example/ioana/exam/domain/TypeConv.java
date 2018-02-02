package com.example.ioana.exam.domain;

import android.arch.persistence.room.TypeConverter;

/**
 * Converter used by Room, to help persisting the Enum Type.
 */
public class TypeConv {
    @TypeConverter
    public static Type toType(String type) {
        type = type.toUpperCase().replace(" ", "_");
        return Type.valueOf(type);
    }

    @TypeConverter
    public static String toString(Type type) {
        return type.toString();
    }
}
