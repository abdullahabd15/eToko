package com.abe.project.eToko.Activity.SellingProduct;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.abe.project.eToko.Activity.AddProduct.AddProductActivity;
import com.abe.project.eToko.Activity.ProductList.ProductListActivity;
import com.abe.project.eToko.Component.CustomAlert;
import com.abe.project.eToko.Component.DrawerMenu;
import com.abe.project.eToko.Component.KasirRecyclerView;
import com.abe.project.eToko.R;

public class SellingProductActivity extends AppCompatActivity implements DrawerMenu.ILeftMenu {
    private DrawerMenu drawerMenu;
    private Toolbar toolbar;
    private KasirRecyclerView rvSellProduct;
    private EditText etProductName, etSellPrice, etPcs;
    private boolean isPressedTwice = false;
    private CustomAlert alert;

    //region override function

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_selling_product);

        init();

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
        rvSellProduct = findViewById(R.id.rvListProduct);
        etSellPrice = findViewById(R.id.etSellingPrice);
        etProductName = findViewById(R.id.etProductName);
        etPcs = findViewById(R.id.etPcs);

        initToolbar();
        initLeftDrawer();
        initObject();
    }

    private void initObject() {
        alert = new CustomAlert(this);
    }

    private void initLeftDrawer() {
        drawerMenu = new DrawerMenu(this, toolbar);
        drawerMenu.setLeftMenuListener(this);
    }

    private void initToolbar() {
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorTextWhite));
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
    //endregion
}
