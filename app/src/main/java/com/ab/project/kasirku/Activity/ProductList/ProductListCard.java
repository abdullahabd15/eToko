package com.ab.project.kasirku.Activity.ProductList;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ab.project.kasirku.Component.KasirCardView;
import com.ab.project.kasirku.Data.ProductData;
import com.ab.project.kasirku.R;

import org.w3c.dom.Text;

public class ProductListCard extends KasirCardView<ProductData> {

    private TextView tvStockProduct, tvProductName, tvBuyPrice, tvSellingPrice;
    private ProductData productData;

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setDataToUII() {
        tvStockProduct.setText(productData.stock);
        tvProductName.setText(productData.productName);
        tvBuyPrice.setText((int) productData.capitalPrice);
        tvSellingPrice.setText((int) productData.sellingPrice);
    }

    private void initComponent() {
        tvStockProduct = findViewById(R.id.tvStockProduct);
        tvProductName = findViewById(R.id.tvProductName);
        tvBuyPrice = findViewById(R.id.tvCapitalPrice);
        tvSellingPrice = findViewById(R.id.tvSellingPrice);
    }
}
