package com.ai.project.eToko.managers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ai.project.eToko.contract.ProductContract;
import com.ai.project.eToko.data.ProductData;
import com.ai.project.eToko.database.SqliteDbHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductDataManager {
    private SqliteDbHelper dbHelper;

    public ProductDataManager(Context context) {
        dbHelper = new SqliteDbHelper(context);
    }

    public List<ProductData> getAllProductData() throws Exception {
        List<ProductData> productDataList = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.rawQuery(ProductContract.Query.selectAllProductData(), null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ProductData productData = new ProductData();
            productData.productId = cursor.getInt(0);
            productData.productCode = cursor.getString(1);
            productData.productName = cursor.getString(2);
            productData.capitalPrice = cursor.getInt(3);
            productData.sellingPrice = cursor.getInt(4);
            productData.qtyStock = cursor.getInt(5);
            productData.qty = cursor.getInt(6);

            productDataList.add(productData);
            cursor.moveToNext();
        }
        cursor.close();
        database.close();
        return productDataList;
    }

    public void insertProductData(ProductData productData) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(ProductContract.Query.insertProductData(productData));
    }

    public void updateProductData(ProductData productData) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(ProductContract.Query.updateProductData(productData));
    }

    public void deleteProductData(ProductData productData) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(ProductContract.Query.deleteProductDataById(productData.productId));
    }
}
