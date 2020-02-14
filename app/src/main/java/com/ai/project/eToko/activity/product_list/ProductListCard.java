package com.ai.project.eToko.activity.product_list;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ai.project.eToko.data.ProductData;
import com.ai.project.eToko.R;
import com.google.android.material.card.MaterialCardView;

public class ProductListCard extends MaterialCardView<ProductData> {

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
            cardListener.onLongCardClick(ProductListCard.this.getVerticalScrollbarPosition(), v, productData);
            return true;
        });
    }

    private void setDataToUII() {
        try {
            tvStockProduct.setText(String.valueOf(productData.qty));
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
