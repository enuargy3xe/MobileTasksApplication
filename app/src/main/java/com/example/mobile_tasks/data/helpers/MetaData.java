package com.example.mobile_tasks.data.helpers;

public final class MetaData {
    public static  String user_id;
    public static String user_name;
    public static String user_surname;
    public static String user_image;

    public static String getUser_id() {
        return user_id;
    }

    public static void setUser_id(String user_id) {
        MetaData.user_id = user_id;
    }

    public static String getUser_name() {
        return user_name;
    }

    public static void setUser_name(String user_name) {
        MetaData.user_name = user_name;
    }

    public static void setUser_surname(String user_surname) {
        MetaData.user_surname = user_surname;
    }

    public static String getUser_surname() {
        return user_surname;
    }

    public static String getUser_image() {
        return user_image;
    }

    public static void setUser_image(String user_image) {
        MetaData.user_image = user_image;
    }
}
