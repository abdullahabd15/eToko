package com.ab.project.kasirku.Activity.AddProduct;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ab.project.kasirku.Activity.SellingProduct.SellingProductActivity;
import com.ab.project.kasirku.Data.ProductData;
import com.ab.project.kasirku.Database.SqliteDbHelper;
import com.ab.project.kasirku.R;

public class AddProductActivity extends AppCompatActivity {
    private EditText etProductCode, etProductName, etCapitalPrice, etSellingPrice, etStock;
    private Button btnSaveProductData;
    private Toolbar toolbar;
    private SqliteDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        init();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                moveToSellingProductActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void moveToSellingProductActivity() {
        Intent intent = new Intent(getApplicationContext(), SellingProductActivity.class);
        startActivity(intent);
        finish();
    }

    private void init() {
        btnSaveProductData = findViewById(R.id.btnAddProduct);
        etProductCode = findViewById(R.id.etProductCode);
        etProductName = findViewById(R.id.etProductName);
        etCapitalPrice = findViewById(R.id.etBuyPrice);
        etSellingPrice = findViewById(R.id.etSellingPrice);
        etStock = findViewById(R.id.etStock);
        toolbar = findViewById(R.id.tbAddProduct);

        btnSaveProductData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProductDataToLocalDb();
            }
        });

        initObject();
        initToolbar();
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.title_add_product);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initObject() {
        dbHelper = new SqliteDbHelper(getApplicationContext());
    }

    private void saveProductDataToLocalDb() {
        ProductData productData = new ProductData();
        try {
            productData.productCode = etProductCode.getText().toString().trim();
            productData.productName = etProductName.getText().toString().trim();
            productData.capitalPrice = Long.parseLong(etCapitalPrice.getText().toString().trim());
            productData.sellingPrice = Long.parseLong(etSellingPrice.getText().toString().trim());
            productData.stock = Integer.parseInt(etStock.getText().toString().trim());

            dbHelper.insertProductData(productData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
