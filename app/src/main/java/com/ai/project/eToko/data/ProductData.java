package com.ai.project.eToko.data;

import java.io.Serializable;

public class ProductData implements Serializable {
    public int productId = 0;
    public String productCode = "";
    public String productName = "";
    public long capitalPrice = 0;
    public long sellingPrice = 0;
    public int qty = 0;
    public int qtyStock = 0;
}
