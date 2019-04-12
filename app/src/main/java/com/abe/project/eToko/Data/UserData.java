package com.abe.project.eToko.Data;

public class UserData {
    private static final String TABLE_NAME = "userData";

    public String userName = "";
    public int userId = 0;

    private static class Column {
        private static final String USER_NAME = "userName";
        private static final String USER_ID = "userId";
    }

    public static class Query {

        public static String createTable() {
            return "CREATE TABLE " + TABLE_NAME + " (" +
                     Column.USER_ID + " INTEGER PRIMARY KEY autoincrement, " +
                     Column.USER_NAME + " TEXT NOT NULL)";
        }

        public static String dropTable() {
            return "DROP TABLE IF EXISTS " + TABLE_NAME;
        }

        public static String selectAllUserData() {
            return "SELECT * FROM " + TABLE_NAME;
        }

        public static String selectUserDataByName(String userName) {
            return "SELECT * FROM " + TABLE_NAME + "WHERE" + Column.USER_NAME + " = '" + userName + "'";
        }

        public static String selectUserDataById(int userId) {
            return "SELECT * FROM " + TABLE_NAME + "WHERE" + Column.USER_ID + " = " + userId;
        }

        public static String updateUserDataName(String newUserName, String oldUserName) {
            return "UPDATE " + TABLE_NAME + "SET " + Column.USER_NAME + " = '" + newUserName
                    + "' WHERE" + Column.USER_NAME + " = '" + oldUserName + "'";
        }

        public static String deleteUserDataByName(String userName) {
            return "DELETE FROM " + TABLE_NAME + "WHERE " + Column.USER_NAME + " = '" + userName + "'";
        }

        public static String deleteUserDataById(int userId) {
            return "DELETE FROM " + TABLE_NAME + "WHERE " + Column.USER_ID + " = " + userId;
        }
    }
}
