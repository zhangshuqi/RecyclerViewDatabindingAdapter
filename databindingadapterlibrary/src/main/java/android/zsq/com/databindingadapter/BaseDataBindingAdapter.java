package android.zsq.com.databindingadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;


import java.util.List;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public abstract class BaseDataBindingAdapter<T> extends RecyclerView.Adapter<BindingViewHolder> implements RefreshableAdapter<T> {
    protected List<T> mData;
    protected BaseBindingPresenter mPresenter;
    protected LayoutInflater mLayoutInflater;
    protected Decorator decorator;


    public BaseDataBindingAdapter(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        if (mData == null) {
            throw new NullPointerException("BaseDataBindingAdapter  data is null");
        }
        T data = mData.get(position);
        // 分配数据
        holder.getBinding().setVariable(BR.item, data);
        //分配事件
        if (mPresenter != null)
           holder.getBinding().setVariable(BR.presenter, mPresenter);
        holder.getBinding().setVariable(BR.itemPosition, position);
        holder.getBinding().executePendingBindings();
        if (decorator != null)
            decorator.decorator(holder, position, getItemViewType(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setPresenter(BaseBindingPresenter presenter) {
        mPresenter = presenter;
    }

    public BaseBindingPresenter getBaseBindingPresenter() {
        return mPresenter;
    }

    public interface Decorator {
        void decorator(BindingViewHolder holder, int position, int viewType);
    }

    @Override
    public void refresh(List newData) {
        if (newData == null) {
            mData.clear();
            notifyDataSetChanged();
            return;
        }
        if (mData == null) {
            mData = newData;
            notifyDataSetChanged();
        } else {
            mData.clear();
            mData.addAll(newData);
            notifyDataSetChanged();
        }
    }

    @Override
    public void addAll(List newData) {
        if (newData == null) {
            return;
        }
        if (mData == null) {
            mData = newData;
            notifyDataSetChanged();
        } else {
            mData.addAll(newData);
            notifyDataSetChanged();
        }
    }

    @Override
    public void clear() {
        if (mData != null) {
            mData.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public void delete(int position) {
        if (mData != null && position < mData.size()) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public void add(T data) {
        mData.add(data);
        notifyDataSetChanged();
    }

    @Override
    public void add(int position, T data) {
        mData.add(position, data);
        notifyDataSetChanged();
    }

    public int getLayoutRes() {
        return -1;
    }

    public int getLayoutRes(int itemViewType) {
        return -1;
    }
}
