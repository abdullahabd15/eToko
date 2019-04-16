package com.abe.project.eToko.Activity.SellingProduct;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.abe.project.eToko.Component.KasirCardView;
import com.abe.project.eToko.Data.ProductData;
import com.abe.project.eToko.R;

public class SellingProductCard extends KasirCardView<ProductData> {
    private TextView tvProductCode, tvProductName, tvPrice, tvQty;
    private ProductData productData;
    private Button btnDelete;

    public SellingProductCard(Context context) {
        super(context);
    }

    public SellingProductCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SellingProductCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setData(ProductData data) {
        super.setData(data);
        productData = data;

        init();
        setDataToUI();
        onCLick();
    }

    private void onCLick() {
        btnDelete.setOnClickListener(v -> cardListener.onCardClick(getVerticalScrollbarPosition(), v, productData));
    }

    private void setDataToUI() {
        tvProductCode.setText(productData.productCode);
        tvProductName.setText(productData.productName);
        tvPrice.setText(String.valueOf(productData.sellingPrice));
        tvQty.setText(String.valueOf(productData.qty));
    }

    private void init() {
        tvProductCode = findViewById(R.id.tvProductCode);
        tvProductName = findViewById(R.id.tvProductName);
        tvPrice = findViewById(R.id.tvPrice);
        tvQty = findViewById(R.id.tvQty);
        btnDelete = findViewById(R.id.btnDeleteProduct);
    }
}
