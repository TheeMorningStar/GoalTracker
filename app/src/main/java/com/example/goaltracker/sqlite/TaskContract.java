package com.example.goaltracker.sqlite;

import android.provider.BaseColumns;

/*
This class is created by Yatri Patel
*/
public final class TaskContract {
    private TaskContract() {
    }

    public static class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";

        public static final String COLUMN_NAME_ID = "id";

        public static final String COLUMN_NAME_PRIORITY = "priority";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_START_DATE = "start_date";

        public static final String COLUMN_NAME_END_DATE = "end_date";

        public static final String COLUMN_NAME_END_DESCRIPTION = "description";
    }
}

