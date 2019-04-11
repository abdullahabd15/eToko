package com.ab.project.kasirku.Activity.ProductList;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.ab.project.kasirku.Component.KasirRecyclerView;
import com.ab.project.kasirku.Component.KasirRecyclerviewAdapter;
import com.ab.project.kasirku.Data.ProductData;
import com.ab.project.kasirku.Database.SqliteDbHelper;
import com.ab.project.kasirku.R;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private KasirRecyclerView rvProductList;
    private KasirRecyclerviewAdapter<List<ProductData>> adapter;
    private List<ProductData> productDataList = new ArrayList<>();
    private SqliteDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_product_list);

            init();
            getAndShowAllProductData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAndShowAllProductData() {
        try {

            List<ProductData> dataList = dbHelper.getAllProductData();
            showDataToUI(dataList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDataToUI(List<ProductData> dataList) {
        productDataList.clear();
        productDataList.addAll(dataList);
        adapter.notifyDataSetChanged();
    }

    private void init() {
        toolbar = findViewById(R.id.tbAllProductList);
        rvProductList = findViewById(R.id.rvAllProductList);

        initToolbar();
        initObject();
        initRecyclerView();
    }

    private void initObject() {
        dbHelper = new SqliteDbHelper(getApplicationContext());
    }

    private void initRecyclerView() {
        int numColumn = 1;
        adapter = new KasirRecyclerviewAdapter(this, R.layout.card_product_list, productDataList);
        adapter.setHasStableIds(true);
        rvProductList.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numColumn, GridLayoutManager.VERTICAL, false);
        rvProductList.setHasFixedSize(true);
        rvProductList.SetDefaultDecoration();
        rvProductList.setLayoutManager(gridLayoutManager);
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.title_list_all_product);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
