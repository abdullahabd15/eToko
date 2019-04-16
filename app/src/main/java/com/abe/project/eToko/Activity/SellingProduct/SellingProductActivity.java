package com.abe.project.eToko.Activity.SellingProduct;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.abe.project.eToko.Activity.AddProduct.AddProductActivity;
import com.abe.project.eToko.Activity.ProductList.ProductListActivity;
import com.abe.project.eToko.Component.AutoCompleteTextAdapter;
import com.abe.project.eToko.Component.CustomAlert;
import com.abe.project.eToko.Component.DrawerMenu;
import com.abe.project.eToko.Component.KasirCardView;
import com.abe.project.eToko.Component.KasirRecyclerView;
import com.abe.project.eToko.Component.KasirRecyclerviewAdapter;
import com.abe.project.eToko.Data.ProductData;
import com.abe.project.eToko.Database.SqliteDbHelper;
import com.abe.project.eToko.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SellingProductActivity extends AppCompatActivity
        implements DrawerMenu.ILeftMenu, KasirCardView.CardListener<ProductData> {

    private DrawerMenu drawerMenu;
    private Toolbar toolbar;
    private EditText etPcs;
    private AutoCompleteTextView etProductName;
    private TextView etSellPrice;
    private boolean isPressedTwice = false;
    private CustomAlert alert;
    private CardView cvAddToChart, cvSell;
    private List<ProductData> productDataList = new ArrayList<>();
    private List<ProductData> chartList = new ArrayList<>();
    private SqliteDbHelper dbHelper;
    private KasirRecyclerView rv;
    private KasirRecyclerviewAdapter<List<ProductData>> adapter;
    private AutoCompleteTextAdapter arrayAdapter;

    //region override function

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_selling_product);

        init();
        productDataList = dbHelper.getAllProductData();

        arrayAdapter = new AutoCompleteTextAdapter(this, productDataList);
        etProductName.setAdapter(arrayAdapter);

        etProductName.setOnItemClickListener((parent, view, position, id) -> {
            setPriceProductToUI(Objects.requireNonNull(arrayAdapter.getItem(position)));
            cvAddToChart.setOnClickListener(v -> addProductToChartList(arrayAdapter.getItem(position)));
        });
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
        adapter = new KasirRecyclerviewAdapter(this, R.layout.card_selling_product, chartList);
        adapter.setHasStableIds(true);
        adapter.setRecyclerListener(this);
        rv.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numColumn, GridLayoutManager.VERTICAL, false);
        rv.setHasFixedSize(true);
        rv.SetDefaultDecoration();
        rv.setLayoutManager(gridLayoutManager);
    }

    private void initObject() {
        alert = new CustomAlert(this);
        dbHelper = new SqliteDbHelper(this);
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
            alert.showAlertToast(getString(R.string.msg_press_once_again_to_close));
            isPressedTwice = true;
        }

        new Handler().postDelayed(() -> isPressedTwice = false, 2000);
    }

    @Override
    public void onCardClick(int position, View view, ProductData data) {

    }

    @Override
    public void onLongCardClick(int position, View view, ProductData data) {

    }
    //endregion
}
