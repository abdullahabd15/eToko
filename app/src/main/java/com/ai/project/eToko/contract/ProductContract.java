package com.ai.project.eToko.contract;

import com.ai.project.eToko.data.ProductData;
import com.ai.project.eToko.database.SqliteDataType;

public class ProductContract extends Contract {
    public static final String TABLE_NAME = "productData";

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public String[] getListColumn() {
        return new String[] {
                Column.PRODUCT_ID,
                Column.PRODUCT_CODE,
                Column.PRODUCT_NAME,
                Column.CAPITAL_PRICE,
                Column.SELLING_PRICE,
                Column.QTY,
                Column.QTY_STOCK
        };
    }

    @Override
    public String[] getListPrimaryKey() {
        return new String[] {
                Column.PRODUCT_ID
        };
    }

    @Override
    public String[] getListType() {
        return new String[] {
                Type.PRODUCT_ID,
                Type.PRODUCT_CODE,
                Type.PRODUCT_NAME,
                Type.CAPITAL_PRICE,
                Type.SELLING_PRICE,
                Type.QTY,
                Type.QTY_STOCK
        };
    }

    public class Column {
        public static final String PRODUCT_ID = "productId";
        public static final String PRODUCT_CODE = "productCode";
        public static final String PRODUCT_NAME = "productName";
        public static final String CAPITAL_PRICE = "capitalPrice";
        public static final String SELLING_PRICE = "sellingPrice";
        public static final String QTY = "qty";
        public static final String QTY_STOCK = "qtyStock";
    }

    public class Type {
        public static final String PRODUCT_ID = SqliteDataType.INTEGER;
        public static final String PRODUCT_CODE = SqliteDataType.TEXT;
        public static final String PRODUCT_NAME = SqliteDataType.TEXT;
        public static final String CAPITAL_PRICE = SqliteDataType.INTEGER;
        public static final String SELLING_PRICE = SqliteDataType.INTEGER;
        public static final String QTY = SqliteDataType.INTEGER;
        public static final String QTY_STOCK = SqliteDataType.INTEGER;
    }

    public static class Query {
        public static String createTable() {
            return "CREATE TABLE " + TABLE_NAME + " (" +
                    Column.PRODUCT_ID + " INTEGER PRIMARY KEY autoincrement, " +
                    Column.PRODUCT_CODE + " TEXT NOT NULL UNIQUE, " +
                    Column.PRODUCT_NAME + " TEXT NOT NULL, " +
                    Column.CAPITAL_PRICE + " INTEGER NOT NULL, " +
                    Column.SELLING_PRICE + " INTEGER NOT NULL, " +
                    Column.QTY_STOCK + " INTEGER NOT NULL, " +
                    Column.QTY + " INTEGER NOT NULL)";
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
            return "UPDATE " + TABLE_NAME + " SET " + Column.PRODUCT_NAME + " = '" + productData.productName + "', "
                    + Column.PRODUCT_CODE + " = '" + productData.productCode + "', "
                    + Column.CAPITAL_PRICE + " = " + productData.capitalPrice + ", "
                    + Column.SELLING_PRICE + " = " + productData.sellingPrice + ", "
                    + Column.QTY + " = " + productData.qty
                    + " WHERE " + Column.PRODUCT_ID + " = " + productData.productId;
        }

        public static String insertProductData(ProductData productData) {
            return "INSERT INTO " + TABLE_NAME + "("
                    + Column.PRODUCT_ID + ", " + Column.PRODUCT_CODE + ", "
                    + Column.PRODUCT_NAME + ", " + Column.CAPITAL_PRICE + ", "
                    + Column.SELLING_PRICE + ", " + Column.QTY_STOCK + ", "
                    + Column.QTY + ")"
                    + " VALUES " + "("
                    + null + ", "
                    + "'" + productData.productCode + "', "
                    + "'" + productData.productName + "', "
                    + productData.capitalPrice + ", "
                    + productData.sellingPrice + ", "
                    + productData.qtyStock + ", "
                    + productData.qty + ")";
        }

        public static String deleteProductDataById(int productId) {
            return "DELETE FROM " + TABLE_NAME + " WHERE " + Column.PRODUCT_ID + " = " + productId;
        }
    }
}
