package com.abe.project.eToko.Component;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class KasirRecyclerviewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public LayoutInflater inflater;
    public int resource;
    private List<T> items;
    private KasirCardView.CardListener<T> recyclerClickListener;
    private String containerName = "";
    private Activity activity;
    private Object[] params;

    public KasirRecyclerviewAdapter(Activity activity, int resource, List<T> items, Object... params) {
        this.inflater = LayoutInflater.from(activity);
        this.items = items;
        this.resource = resource;
        this.activity = activity;
        this.params = params;
    }

    public void setParameters(Object... params) {
        this.params = params;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        KasirCardView<T> cardView = (KasirCardView<T>) inflater.inflate(this.resource, parent, false);
        cardView.setParameters(params);
        return new RecyclerViewHolders(cardView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RecyclerViewHolders recyclerViewHolders = (RecyclerViewHolders) holder;
        recyclerViewHolders.setData(items.get(position));
    }

    public T GetItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).hashCode();
    }

    public void setRecyclerListener(KasirCardView.CardListener recyclerClickListener) {
        this.recyclerClickListener = recyclerClickListener;
    }

    public void updateSource(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void SetContainer(String containerName) {
        this.containerName = containerName;
    }

    protected Activity getActivity() {
        return activity;
    }
    public class RecyclerViewHolders extends RecyclerView.ViewHolder {
        private KasirCardView<T> ngemartCardView;

        public RecyclerViewHolders(View cardView) {
            super(cardView);
            ngemartCardView = (KasirCardView<T>) cardView;
            if (recyclerClickListener != null) {
                ngemartCardView.setCardListener(new KasirCardView.CardListener<T>() {
                    @Override
                    public void onCardClick(int position, View view, T data) {
                        recyclerClickListener.onCardClick(getAdapterPosition(), view, data);
                    }

                    @Override
                    public void onLongCardClick(int position, View view, T data) {
                        recyclerClickListener.onLongCardClick(getAdapterPosition(), view, data);
                    }
                });
            }
        }

        public void setData(T data) {
            if (containerName.equals("")) {
                ngemartCardView.setData(data);
            } else {
                ngemartCardView.setData(data, containerName);
            }

        }
    }
}
