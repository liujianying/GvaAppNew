package com.ydh.gva.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ydh.gva.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujianying on 15/6/9.
 */
public abstract class SimpleBaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<SimpleBaseRecyclerViewAdapter.ViewHolder>{

    protected Context context;
    protected List<T> data;


    public SimpleBaseRecyclerViewAdapter(Context context, List<T> data) {
        this.context = context;
        this.data = data == null ? new ArrayList<T>() : new ArrayList<>(data);
    }

    @Override
    public SimpleBaseRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(getItemResource(), parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(SimpleBaseRecyclerViewAdapter.ViewHolder holder, int position) {
        onBindViewHolders(holder,  position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private SparseArray<View> views = new SparseArray<>();
        private View convertView;

        public ViewHolder(View convertView) {
            super(convertView);
            this.convertView = convertView;
        }

        @SuppressWarnings("unchecked")
        public <T extends View> T getView(int resId) {
            View v = views.get(resId);
            if (null == v) {
                v = convertView.findViewById(resId);
                views.put(resId, v);
            }
            return (T) v;
        }
    }


    /**
     * @该方法需要子类实现，需要返回item布局的resource id
     *
     * @return
     */
    public abstract int getItemResource();

    /**
     * @使用该getItemView方法替换原来的getView方法，需要子类实现
     * @param holder
     * @param position
     * @return
     */
    public abstract void onBindViewHolders(SimpleBaseRecyclerViewAdapter.ViewHolder holder, int position);

    public void addAll(List<T> elem) {
        data.addAll(elem);
        notifyDataSetChanged();
    }

    public void remove(T elem) {
        data.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        data.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        if(elem == null)elem = new ArrayList<>();
        data.clear();
        data.addAll(elem);
        notifyDataSetChanged();
    }
}
