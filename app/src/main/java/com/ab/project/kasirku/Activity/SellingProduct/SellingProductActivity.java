package com.ab.project.kasirku.Activity.SellingProduct;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ab.project.kasirku.Activity.AddProduct.AddProductActivity;
import com.ab.project.kasirku.Activity.ProductList.ProductListActivity;
import com.ab.project.kasirku.Component.DrawerMenu;
import com.ab.project.kasirku.Component.KasirRecyclerView;
import com.ab.project.kasirku.R;

public class SellingProductActivity extends AppCompatActivity implements DrawerMenu.ILeftMenu {
    private DrawerMenu drawerMenu;
    private Toolbar toolbar;
    private KasirRecyclerView rvSellProduct;
    private EditText etProductName, etSellPrice, etPcs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_selling_product);

        init();

    }

    private void init() {
        toolbar = findViewById(R.id.tbMain);
        rvSellProduct = findViewById(R.id.rvListProduct);
        etSellPrice = findViewById(R.id.etSellingPrice);
        etProductName = findViewById(R.id.etProductName);
        etPcs = findViewById(R.id.etPcs);

        initToolbar();
        initLeftDrawer();
    }

    private void initLeftDrawer() {
        drawerMenu = new DrawerMenu(this, toolbar);
        drawerMenu.setLeftMenuListener(this);
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
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

    @Override
    public void onHandleBackPressed() {
        drawerMenu.closeDrawer();
    }
}
