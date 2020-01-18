package com.ai.project.eToko.component;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.ai.project.eToko.data.ProductData;
import com.ai.project.eToko.R;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteTextAdapter extends ArrayAdapter<ProductData> {
    private List<ProductData> productDataList;

    public AutoCompleteTextAdapter(@NonNull Context context, List<ProductData> productDataList) {
        super(context, 0, productDataList);
        this.productDataList = new ArrayList<>(productDataList);
    }

    private Filter productDataFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ProductData> list = new ArrayList<>();
            if(constraint == null || constraint.length() == 0) {
                list.addAll(productDataList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ProductData item : productDataList) {
                    if (item.productName.toLowerCase().contains(filterPattern)) {
                        list.add(item);
                    }
                }
            }

            results.values = list;
            results.count = list.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List) results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((ProductData) resultValue).productName;
        }
    };

    @NonNull
    @Override
    public Filter getFilter() {
        return productDataFilter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.popup_product_name, parent, false);
        }

        TextView tv = convertView.findViewById(R.id.tvProductName);
        ProductData productData = getItem(position);
        if(productData != null) {
            tv.setText(productData.productName);
        }
        return convertView;
    }


}
