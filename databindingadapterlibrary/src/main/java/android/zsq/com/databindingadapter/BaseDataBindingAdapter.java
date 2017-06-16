package android.zsq.com.databindingadapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public abstract class BaseDataBindingAdapter<T> extends RecyclerView.Adapter<BindingViewHolder> implements RefreshableAdapter<T> {
    protected BindingViewHolder footHolder;
    protected List<T> mData;
    protected BaseBindingItemPresenter mPresenter;
    protected LayoutInflater mLayoutInflater;
    protected BaseDataBindingDecorator headDecorator;
    protected BaseDataBindingDecorator itemDecorator;
    protected BaseBindingItemPresenter headPresenter;
    protected BaseBindingItemPresenter footPresenter;
    protected BaseDataBindingDecorator footDecorator;

    public BaseDataBindingAdapter(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = new ArrayList<>();
    }


    public interface ItemDecorator<T> {
        void decorator(BindingViewHolder holder, int position, int viewType, List<T> mData);
    }

    public void setItemPresenter(BaseBindingItemPresenter presenter) {
        mPresenter = presenter;
    }

    public BaseBindingItemLongPresenter itemLongPresenter;

    public boolean canLongClick = false;

    public void setItemLongPresenter(BaseBindingItemLongPresenter presenter) {
        itemLongPresenter = presenter;
        canLongClick = true;
    }

    public BaseBindingItemPresenter getBaseBindingItemPresenter() {
        return mPresenter;
    }

    public void refresh(int position) {
        notifyItemChanged(position);
    }

    public void refresh(int position, Object o) {
        notifyItemChanged(position, o);
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

    public void refresh() {
        if (mData != null && mData.size() != 0) {
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
        //notifyItemInserted(position);
    }

    public int getLayoutRes() {
        return -1;
    }

    public int getLayoutRes(int itemViewType) {
        return -1;
    }

    public List<T> getListData() {
        return mData;
    }

    public BindingViewHolder getFootHolder() {
        return footHolder;
    }

    // 解决gridManager 的head 问题
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return isHeaderView(position) || isFooterView(position)
                            ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    protected abstract boolean isFooterView(int position);

    protected abstract boolean isHeaderView(int position);
    protected abstract int getHeadAndItemCount();

    @Override
    public void onViewAttachedToWindow(BindingViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    public BaseDataBindingDecorator getHeadDecorator() {
        return headDecorator;
    }

    public void setHeadDecorator(BaseDataBindingDecorator baseDataBindingDecorator) {
        this.headDecorator = baseDataBindingDecorator;
    }

    public void setHeadPresenter(BaseBindingItemPresenter presenter) {
        headPresenter = presenter;
    }

    public void setFootPresenter(BaseBindingItemPresenter presenter) {
        footPresenter = presenter;
    }

    public void setItemDecorator(BaseDataBindingDecorator decorator) {
        this.itemDecorator = decorator;
    }

    public void setFootDecorator(BaseDataBindingDecorator footDecorator) {
        this.footDecorator = footDecorator;
    }
}
