package com.ai.project.eToko.activity.add_product;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.ai.project.eToko.activity.product_list.ProductListActivity;
import com.ai.project.eToko.activity.selling_product.SellingProductActivity;
import com.ai.project.eToko.data.ProductData;
import com.ai.project.eToko.managers.ProductDataManager;
import com.ai.project.eToko.R;
import com.ai.project.libui.AiActivity;

public class AddProductActivity extends AiActivity {
    private EditText etProductCode, etProductName, etCapitalPrice, etSellingPrice, etStock;
    private Button btnSaveProductData;
    private Toolbar toolbar;
    private ProductData productData;
    private boolean isUpdateProductData = false;
    private ProductDataManager productDataManager;

    //region override function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_product);

            getDataFromIntent();
            init();
            if(isUpdateProductData) {
                showDataToUI();
            }
        } catch (Exception e) {
            showErrorDialog(e);
        }
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

    @Override
    public void onBackPressed() {
        moveToSellingProductActivity();
    }

    //endregion

    //region init function

    private void init() {
        btnSaveProductData = findViewById(R.id.btnAddProduct);
        etProductCode = findViewById(R.id.etProductCode);
        etProductName = findViewById(R.id.etProductName);
        etCapitalPrice = findViewById(R.id.etBuyPrice);
        etSellingPrice = findViewById(R.id.etSellingPrice);
        etStock = findViewById(R.id.etStock);
        toolbar = findViewById(R.id.tbAddProduct);

        btnSaveProductData.setOnClickListener(v -> {
            if(validateTextInput()) {
                if(isUpdateProductData) {
                    updateProductDataToDb();
                } else {
                    saveProductDataToLocalDb();
                }
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
        productDataManager = new ProductDataManager(getApplicationContext());
    }

    //endregion

    //region other function

    private boolean validateTextInput() {
        if(etProductCode.getText().toString().isEmpty()) {
            etProductCode.setError(getString(R.string.msg_cannot_empty));
            return false;
        } else if(etProductName.getText().toString().isEmpty()) {
            etProductName.setError(getString(R.string.msg_cannot_empty));
            return false;
        } else if(etCapitalPrice.getText().toString().isEmpty()) {
            etCapitalPrice.setError(getString(R.string.msg_cannot_empty));
            return false;
        } else if(etSellingPrice.getText().toString().isEmpty()) {
            etSellingPrice.setError(getString(R.string.msg_cannot_empty));
            return false;
        } else if(etStock.getText().toString().isEmpty()) {
            etStock.setError(getString(R.string.msg_cannot_empty));
            return false;
        }
        return true;
    }

    private void clearUI() {
        etProductCode.setText("");
        etProductName.setText("");
        etCapitalPrice.setText("");
        etSellingPrice.setText("");
        etStock.setText("");
    }

    private void showDataToUI() {
        etProductCode.setText(productData.productCode);
        etProductName.setText(productData.productName);
        etCapitalPrice.setText(String.valueOf(productData.capitalPrice));
        etSellingPrice.setText(String.valueOf(productData.sellingPrice));
        etStock.setText(String.valueOf(productData.qty));

        btnSaveProductData.setText(getString(R.string.btn_save));
    }

    private void getDataFromIntent() {
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            productData = (ProductData) bundle.getSerializable(ProductListActivity.PRODUCT_DATA_KEY);
            isUpdateProductData = true;
        }
    }

    private void moveToSellingProductActivity() {
        Intent intent = new Intent(getApplicationContext(), SellingProductActivity.class);
        startActivity(intent);
        finish();
    }

    private void updateProductDataToDb() {
        ProductData productData = new ProductData();
        try {
            productData.productCode = etProductCode.getText().toString().trim();
            productData.productName = etProductName.getText().toString().trim();
            productData.capitalPrice = Long.parseLong(etCapitalPrice.getText().toString().trim());
            productData.sellingPrice = Long.parseLong(etSellingPrice.getText().toString().trim());
            productData.qty = Integer.parseInt(etStock.getText().toString().trim());
            productData.productId = this.productData.productId;

            productDataManager.updateProductData(productData);

            showAlertToast(getString(R.string.msg_product_has_saved));
            clearUI();
        } catch (Exception e) {
            showErrorDialog(e);
        }
    }

    private void saveProductDataToLocalDb() {
        ProductData productData = new ProductData();
        try {
            productData.productCode = etProductCode.getText().toString().trim();
            productData.productName = etProductName.getText().toString().trim();
            productData.capitalPrice = Long.parseLong(etCapitalPrice.getText().toString().trim());
            productData.sellingPrice = Long.parseLong(etSellingPrice.getText().toString().trim());
            productData.qty = Integer.parseInt(etStock.getText().toString().trim());

            productDataManager.insertProductData(productData);

            showAlertToast(getString(R.string.msg_product_has_saved));
            clearUI();
        } catch (Exception e) {
            showErrorDialog(e);
        }
    }

    //endregion

}
