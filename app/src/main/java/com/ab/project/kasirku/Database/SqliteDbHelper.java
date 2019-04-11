package com.ab.project.kasirku.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ab.project.kasirku.Data.ProductData;
import com.ab.project.kasirku.Data.UserData;

import java.util.ArrayList;
import java.util.List;

public class SqliteDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tokoManager.db";
    private static final int DATABASE_VERSION = 1;

    public SqliteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserData.Query.createTable());
        db.execSQL(ProductData.Query.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserData.Query.dropTable());
        db.execSQL(ProductData.Query.dropTable());

        onCreate(db);
    }

    public List<ProductData> getAllProductData() {
        List<ProductData> productDataList = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        try {
            Cursor cursor = database.rawQuery(ProductData.Query.selectAllProductData(), null);
            if(cursor.moveToFirst()) {
                do {
                    ProductData productData = new ProductData();
                    productData.productId = cursor.getInt(0);
                    productData.productName = cursor.getString(1);
                    productData.productCode = cursor.getString(2);
                    productData.capitalPrice = cursor.getInt(3);
                    productData.sellingPrice = cursor.getInt(4);
                    productData.stock = cursor.getInt(5);

                    productDataList.add(productData);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return productDataList;
    }

    public void insertProductData(ProductData productData) {
        //ContentValues values = new ContentValues();
        SQLiteDatabase db = getWritableDatabase();
        try {
//            values.put(ProductData.Column.PRODUCT_ID, productData.productId);
//            values.put(ProductData.Column.PRODUCT_NAME, productData.productName);
//            values.put(ProductData.Column.PRODUCT_CODE, productData.productCode);
//            values.put(ProductData.Column.CAPITAL_PRICE, productData.capitalPrice);
//            values.put(ProductData.Column.SELLING_PRICE, productData.sellingPrice);
//            values.put(ProductData.Column.STOCK, productData.stock);

            db.execSQL(ProductData.Query.insertProductData(productData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProductData(ProductData productData) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(ProductData.Query.updateProductData(productData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteProductData(ProductData productData) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(ProductData.Query.deleteProductDataById(productData.productId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
