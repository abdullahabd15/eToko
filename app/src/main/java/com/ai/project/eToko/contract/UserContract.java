package com.ai.project.eToko.contract;

import com.ai.project.eToko.database.SqliteDataType;

public class UserContract extends Contract {
    public static final String TABLE_NAME = "userData";

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getListColumn() {
        return new String[] {
                Column.USER_NAME,
                Column.USER_ID
        };
    }

    @Override
    public String[] getListPrimaryKey() {
        return new String[] {
                Column.USER_ID
        };
    }

    @Override
    public String[] getListType() {
        return new String[] {
                Type.USER_NAME,
                Type.USER_ID
        };
    }

    public class Column {
        public static final String USER_NAME = "userName";
        public static final String USER_ID = "userId";
    }

    public class Type {
        public static final String USER_NAME = SqliteDataType.TEXT;
        public static final String USER_ID = SqliteDataType.INTEGER;
    }
}
