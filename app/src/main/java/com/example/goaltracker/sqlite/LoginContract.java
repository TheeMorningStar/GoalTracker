package com.example.goaltracker.sqlite;

import android.provider.BaseColumns;
/*
This class is created by Yatri Patel
*/
public class LoginContract {

    private LoginContract() {
        // Private constructor to prevent instantiation
    }

    public static class LoginEntry implements BaseColumns {
        public static final String TABLE_NAME = "login";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PASSWORD = "password";
    }
}
