package com.abe.project.eToko.Component;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KasirCardView<T> extends CardView {
    protected CardListener cardListener;
    private Context context;
    protected T data;
    protected String containerName = "";
    protected List<Object> params = new ArrayList<>();

    public KasirCardView(Context context) {
        super(context);
        init(context);
    }

    public KasirCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public KasirCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setParameters(Object... params) {
        if (params != null && params.length > 0)
            this.params = Arrays.asList(params);
    }

    private void init(Context context) {
        this.context = context;
    }

    public void setCardListener(CardListener cardListener) {
        this.cardListener = cardListener;

        InitOnClickListener();
        InitOnLongCardClick();
    }

    public void setActivity(Activity activity) {
        this.context = activity;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setData(T data, String containerName) {
        this.data = data;
        this.containerName = containerName;
    }

    private void InitOnClickListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cardListener.onCardClick(getVerticalScrollbarPosition(), v, data);
            }
        });
    }

    private void InitOnLongCardClick() {
        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                cardListener.onLongCardClick(getVerticalScrollbarPosition(), v, data);
                return true;
            }
        });
    }

    public Context getCardContext() { return context;}

    public interface CardListener<T> {
        void onCardClick(int position, View view, T data);
        void onLongCardClick(int position, View view, T data);
    }
}