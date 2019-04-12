package com.abe.project.eToko.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.abe.project.eToko.Data.ProductData;
import com.abe.project.eToko.Data.UserData;

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
        SQLiteDatabase database = getWritableDatabase();
        try {
            Cursor cursor = database.rawQuery(ProductData.Query.selectAllProductData(), null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ProductData productData = new ProductData();
                productData.productId = cursor.getInt(0);
                productData.productCode = cursor.getString(1);
                productData.productName = cursor.getString(2);
                productData.capitalPrice = cursor.getInt(3);
                productData.sellingPrice = cursor.getInt(4);
                productData.stock = cursor.getInt(5);

                productDataList.add(productData);
                cursor.moveToNext();
            }
            cursor.close();
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productDataList;
    }

    public void insertProductData(ProductData productData) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(ProductData.Query.insertProductData(productData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProductData(ProductData productData) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(ProductData.Query.updateProductData(productData));
    }

    public void deleteProductData(ProductData productData) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(ProductData.Query.deleteProductDataById(productData.productId));
    }
}
