package com.abe.project.eToko.Activity.ProductList;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.TextView;

import com.abe.project.eToko.Component.KasirCardView;
import com.abe.project.eToko.Data.ProductData;
import com.abe.project.eToko.R;

public class ProductListCard extends KasirCardView<ProductData> {

    private TextView tvStockProduct, tvProductName, tvBuyPrice, tvSellingPrice;
    private ProductData productData;
    private CheckBox cbProductList;

    public ProductListCard(Context context) {
        super(context);
    }

    public ProductListCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProductListCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setData(ProductData data) {
        try {
            super.setData(data);
            productData = data;

            initComponent();
            setDataToUII();
            setOnCardClick();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setOnCardClick() {
        setOnLongClickListener(v -> {
            cardListener.onLongCardClick(getVerticalScrollbarPosition(), v, productData);
            return true;
        });
    }

    private void setDataToUII() {
        try {
            tvStockProduct.setText(String.valueOf(productData.stock));
            tvProductName.setText(productData.productName);
            tvBuyPrice.setText(String.valueOf(productData.capitalPrice));
            tvSellingPrice.setText(String.valueOf(productData.sellingPrice));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initComponent() {
        tvStockProduct = findViewById(R.id.tvStockProduct);
        tvProductName = findViewById(R.id.tvProductName);
        tvBuyPrice = findViewById(R.id.tvCapitalPrice);
        tvSellingPrice = findViewById(R.id.tvSellingPrice);
        cbProductList = findViewById(R.id.cbSelectProduct);
    }
}
