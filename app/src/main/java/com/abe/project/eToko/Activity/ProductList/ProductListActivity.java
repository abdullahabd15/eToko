package com.abe.project.eToko.Activity.ProductList;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.abe.project.eToko.Activity.AddProduct.AddProductActivity;
import com.abe.project.eToko.Activity.SellingProduct.SellingProductActivity;
import com.abe.project.eToko.Component.CustomAlert;
import com.abe.project.eToko.Component.KasirCardView;
import com.abe.project.eToko.Component.KasirRecyclerView;
import com.abe.project.eToko.Component.KasirRecyclerviewAdapter;
import com.abe.project.eToko.Data.ProductData;
import com.abe.project.eToko.Database.SqliteDbHelper;
import com.abe.project.eToko.R;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity implements KasirCardView.CardListener<ProductData> {
    private Toolbar toolbar;
    private KasirRecyclerView rvProductList;
    private KasirRecyclerviewAdapter<List<ProductData>> adapter;
    private final List<ProductData> productDataList = new ArrayList<>();
    private List<ProductData> dataList = new ArrayList<>();
    private SqliteDbHelper dbHelper;
    private CustomAlert alert;

    private final String menuUpdateProduct = "Ubah data produk";
    private final String menuDeleteProduct = "Hapus produk";
    private final String[] dialogMenuList = {menuUpdateProduct, menuDeleteProduct};
    public static final String PRODUCT_DATA_KEY = "productDataKey";

    //region override function

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_product_list);

            init();
            getAndShowAllProductData();

        } catch (Exception e) {
            alert.showErrorDialog(e);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    @Override
    public void onCardClick(int position, View view, ProductData data) {

    }

    @Override
    public void onLongCardClick(int position, View view, ProductData data) {
        showDialogMenu(data);
    }

    //endregion

    //region init function

    private void init() {
        toolbar = findViewById(R.id.tbAllProductList);
        rvProductList = findViewById(R.id.rvAllProductList);

        initToolbar();
        initObject();
        initRecyclerView();
    }

    private void initObject() {
        dbHelper = new SqliteDbHelper(getApplicationContext());
        alert = new CustomAlert(this);
    }

    private void initRecyclerView() {
        int numColumn = 1;
        adapter = new KasirRecyclerviewAdapter(this, R.layout.card_product_list, productDataList);
        adapter.setHasStableIds(true);
        adapter.setRecyclerListener(this);
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

    //endregion

    //region other function
    private void showDialogMenu(final ProductData data) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        DialogInterface.OnClickListener onItemClick = (dialog1, position) -> {
            switch (dialogMenuList[position]) {
                case menuUpdateProduct:
                    moveToAddProductActivity(data);
                    break;
                case menuDeleteProduct:
                    deleteProductData(data);
                    break;
            }
        };
        dialog.setTitle(getString(R.string.app_name));
        dialog.setItems(dialogMenuList, onItemClick);
        dialog.setNegativeButton(getString(R.string.btn_close), null);
        dialog.create().show();
    }

    private void showDataToUI() {
        productDataList.clear();
        productDataList.addAll(dataList);
        adapter.notifyDataSetChanged();
    }

    private void moveToAddProductActivity(ProductData productData) {
        Intent intent = new Intent(getApplicationContext(), AddProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PRODUCT_DATA_KEY, productData);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void moveToSellingProductActivity() {
        Intent intent = new Intent(getApplicationContext(), SellingProductActivity.class);
        startActivity(intent);
        finish();
    }

    private void getAndShowAllProductData() {
        try {

            dataList = dbHelper.getAllProductData();
            showDataToUI();

        } catch (Exception e) {
            alert.showErrorDialog(e);
        }
    }

    private void deleteProductData(ProductData data) {
        try {
            for(ProductData productData: dataList) {
                if(data.productId == productData.productId) {
                    dbHelper.deleteProductData(productData);
                    dataList.remove(productData);
                    break;
                }
            }
            showDataToUI();
            alert.showAlertToast(data.productName + " berhasil dihapus.");
        } catch (Exception e) {
            alert.showErrorDialog(e);
        }
    }

    //endregion

}
