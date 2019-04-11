package com.ab.project.kasirku.Data;

import java.io.Serializable;

public class ProductData implements Serializable {
    private static final String TABLE_NAME = "productData";

    public int productId = 0;
    public String productCode = "";
    public String productName = "";
    public long capitalPrice = 0;
    public long sellingPrice = 0;
    public int stock = 0;

    private static class Column {
        private static final String PRODUCT_ID = "productId";
        private static final String PRODUCT_CODE = "userName";
        private static final String PRODUCT_NAME = "userId";
        private static final String CAPITAL_PRICE = "capitalPrice";
        private static final String SELLING_PRICE = "sellingPrice";
        private static final String STOCK = "stock";
    }

    public static class Query {
        public static String createTable() {
            return "CREATE TABLE " + TABLE_NAME + " (" +
                    Column.PRODUCT_ID + " INTEGER PRIMARY KEY autoincrement, " +
                    Column.PRODUCT_CODE + " TEXT NOT NULL UNIQUE, " +
                    Column.PRODUCT_NAME + " TEXT NOT NULL, " +
                    Column.CAPITAL_PRICE + " INTEGER NOT NULL, " +
                    Column.SELLING_PRICE + " INTEGER NOT NULL, " +
                    Column.STOCK + " INTEGER NOT NULL)";
        }

        public static String dropTable() {
            return "DROP TABLE IF EXISTS " + TABLE_NAME;
        }

        public static String selectAllProductData() {
            return "SELECT * FROM " + TABLE_NAME;
        }

        public static String selectProductDataByProductCode(String productCode) {
            return "SELECT * FROM " + TABLE_NAME + "WHERE" + Column.PRODUCT_CODE + " = '" + productCode + "'";
        }

        public static String selectProductDataByProductNameAndId(String productName, String productId) {
            return "SELECT * FROM " + TABLE_NAME + "WHERE" + Column.PRODUCT_NAME + " = '" + productName + "'"
                    + " AND " + Column.PRODUCT_ID + " = " + productId;
        }

        public static String updateProductData(ProductData productData) {
            return "UPDATE " + TABLE_NAME + "SET " + Column.PRODUCT_NAME + " = '" + productData.productName + "', "
                    + Column.PRODUCT_CODE + " = '" + productData.productCode + "', "
                    + Column.CAPITAL_PRICE + " = " + productData.capitalPrice + ", "
                    + Column.SELLING_PRICE + " = " + productData.sellingPrice + ", "
                    + Column.STOCK + " = " + productData.stock
                    + " WHERE " + Column.PRODUCT_ID + " = " + productData.productId;
        }

        public static String insertProductData(ProductData productData) {
            return "INSERT INTO " + TABLE_NAME + " VALUES " + "("
                    + productData.productId + ", "
                    + "'" + productData.productCode + "', "
                    + "'" + productData.productName + "', "
                    + productData.capitalPrice + ", "
                    + productData.sellingPrice + ", "
                    + productData.stock + ")";
        }

        public static String deleteProductDataById(int productId) {
            return "DELETE FROM " + TABLE_NAME + "WHERE " + Column.PRODUCT_ID + " = " + productId;
        }
    }
}
