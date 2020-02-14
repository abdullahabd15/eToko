package com.ai.project.eToko.activity.selling_product;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.ai.project.eToko.activity.add_product.AddProductActivity;
import com.ai.project.eToko.activity.product_list.ProductListActivity;
import com.ai.project.eToko.component.AutoCompleteTextAdapter;
import com.ai.project.eToko.component.DrawerMenu;
import com.ai.project.eToko.component.AiRecyclerView;
import com.ai.project.eToko.component.AiRecyclerviewAdapter;
import com.ai.project.eToko.data.ProductData;
import com.ai.project.eToko.managers.ProductDataManager;
import com.ai.project.eToko.R;
import com.ai.project.libui.AiActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SellingProductActivity extends AiActivity
        implements DrawerMenu.ILeftMenu, AiCardView.CardListener<ProductData> {

    private DrawerMenu drawerMenu;
    private Toolbar toolbar;
    private EditText etPcs;
    private AutoCompleteTextView etProductName;
    private TextView etSellPrice;
    private boolean isPressedTwice = false;
    private CardView cvAddToChart, cvSell;
    private List<ProductData> productDataList = new ArrayList<>();
    private List<ProductData> chartList = new ArrayList<>();
    private ProductDataManager productDataManager;
    private AiRecyclerView rv;
    private AiRecyclerviewAdapter<List<ProductData>> adapter;
    private AutoCompleteTextAdapter arrayAdapter;

    //region override function

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.drawer_selling_product);

            init();

            setupAutoCompleteTV();

        } catch (Exception e) {
            showErrorDialog(e);
        }
    }

    @Override
    public void onCardClick(int position, View view, ProductData data) {
        switch (view.getId()){
            case R.id.btnDeleteProduct:
                showDialogToDeleteProductFromChartList(data);
                break;
        }
    }

    @Override
    public void onLongCardClick(int position, View view, ProductData data) {

    }

    @Override
    public void onHandleBackPressed() {
        closeAppWhenPressedTwice();
    }

    @Override
    public boolean onMenuItemSelected(int itemId) throws Exception {
        switch (itemId) {
            case R.id.nav_add_new_product:
                moveToAddProductActivity();
                break;
            case R.id.nav_list_product:
                moveToListProductActivity();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        drawerMenu.closeDrawer();
    }

    //endregion

    //region init function
    private void init() {
        toolbar = findViewById(R.id.tbMain);
        rv = findViewById(R.id.rvListProduct);
        etSellPrice = findViewById(R.id.etSellingPrice);
        etProductName = findViewById(R.id.etProductName);
        etPcs = findViewById(R.id.etPcs);
        cvAddToChart = findViewById(R.id.btnAddToChart);
        cvSell = findViewById(R.id.btnSellProduct);

        initToolbar();
        initLeftDrawer();
        initObject();
        initRecyclerView();
    }

    private void initRecyclerView() {
        int numColumn = 1;
        adapter = new AiRecyclerviewAdapter(this, R.layout.card_selling_product, chartList);
        adapter.setHasStableIds(true);
        adapter.setRecyclerListener(this);
        rv.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numColumn, GridLayoutManager.VERTICAL, false);
        rv.setHasFixedSize(true);
        rv.SetDefaultDecoration();
        rv.setLayoutManager(gridLayoutManager);
    }

    private void initObject() {
        productDataManager = new ProductDataManager(getApplicationContext());
    }

    private void initLeftDrawer() {
        drawerMenu = new DrawerMenu(this, toolbar);
        drawerMenu.setLeftMenuListener(this);
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.design_default_color_primary));
        setSupportActionBar(toolbar);
    }

    //endregion

    //region other function

    private void setupAutoCompleteTV() {
        try {
            productDataList = productDataManager.getAllProductData();

            arrayAdapter = new AutoCompleteTextAdapter(this, productDataList);
            etProductName.setAdapter(arrayAdapter);

            etProductName.setOnItemClickListener((parent, view, position, id) -> {
                setPriceProductToUI(Objects.requireNonNull(arrayAdapter.getItem(position)));
                cvAddToChart.setOnClickListener(v -> addProductToChartList(arrayAdapter.getItem(position)));
            });
        } catch (Exception e) {
            showErrorDialog(e);
        }
    }

    private void addProductToChartList(ProductData item) {
        final List<ProductData> list = new ArrayList<>(chartList);
        if(validateTextInput()){
            if(chartList.size() != 0) {
                for(ProductData data: list) {
                    if(data.productName.equals(item.productName)) {
                        item.qty = data.qty + Integer.parseInt(etPcs.getText().toString().trim());
                        chartList.remove(data);
                        chartList.add(item);
                        adapter.notifyDataSetChanged();
                        clearTextInput();
                        return;
                    }
                }
                item.qty = Integer.parseInt(etPcs.getText().toString().trim());
                chartList.add(item);
                adapter.notifyDataSetChanged();
                clearTextInput();
            } else {
                item.qty = Integer.parseInt(etPcs.getText().toString().trim());
                chartList.add(item);
                adapter.notifyDataSetChanged();
                clearTextInput();
            }
        }
        hideSoftKeyboard();
    }

    private boolean validateTextInput() {
        if(etProductName.getText().toString().isEmpty()) {
            etProductName.setError(getString(R.string.msg_cannot_empty));
            return false;
        } else if(etPcs.getText().toString().isEmpty()) {
            etPcs.setError(getString(R.string.msg_cannot_empty));
            return false;
        }
        return true;
    }

    private void clearTextInput() {
        etProductName.setText("");
        etSellPrice.setText("");
        etPcs.setText("");
    }

    private void setPriceProductToUI(ProductData item) {
        etSellPrice.setText(String.valueOf(item.sellingPrice));
    }

    private void moveToListProductActivity() {
        Intent intent = new Intent(getApplicationContext(), ProductListActivity.class);
        startActivity(intent);
        finish();
    }

    private void moveToAddProductActivity() {
        Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
        startActivity(intent);
        finish();
    }

    private void closeAppWhenPressedTwice() {
        if(isPressedTwice) {
            moveTaskToBack(true);
        } else {
            showAlertToast(getString(R.string.msg_press_once_again_to_close));
            isPressedTwice = true;
        }

        new Handler().postDelayed(() -> isPressedTwice = false, 2000);
    }

    private void showDialogToDeleteProductFromChartList(ProductData data) {
        showAskDialog(getString(R.string.msg_ask_to_delete_product), (dialog, which) -> {
            deleteProductFromChartList(data);
            dialog.cancel();
        });
    }

    private void deleteProductFromChartList(ProductData data) {
        for(ProductData productData: chartList) {
            if(productData.productName.equals(data.productName) && productData.productCode.equals(data.productCode)) {
                chartList.remove(productData);
                adapter.notifyDataSetChanged();
            }
        }
    }

    //endregion
}
