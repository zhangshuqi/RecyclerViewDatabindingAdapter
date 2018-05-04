package android.zsq.com.databindingadapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class SingleTypeBindingAdapter<T> extends BaseDataBindingAdapter<T> {
    private int mLayoutRes;
    protected Object footSingleFootData;
    protected int footSingleFootRes;
    protected int footSingleKey;
    private Object headSingleData;
    private int headSingleKey;
    private int headSingleFootRes;
    private BindingViewHolder footBindingHolder;


    public SingleTypeBindingAdapter(Context context, List data, int layoutRes) {
        super(context, data);

        mLayoutRes = layoutRes;
    }


    @Override
    public void onBindViewHolder(BindingViewHolder holder, final int position) {
        if (mData == null) {
            throw new NullPointerException("BaseDataBindingAdapter  data is null");
        }
        Object data = null;
        ViewDataBinding binding = holder.getBinding();
        int itemViewType = getItemViewType(position);
        if (isHeaderView(position)) {
            if (headSingleData == null) return;
            data = headSingleData;
            if (headDecorator != null)
                headDecorator.decorator(holder, position, itemViewType, headSingleData);
            if (headPresenter != null)
                binding.setVariable(BR.presenter, headPresenter);
        } else if (isFooterView(position)) {
            if (footSingleFootData == null) return;
            data = footSingleFootData;
            if (footPresenter != null)
                binding.setVariable(BR.presenter, footPresenter);
            if (footDecorator != null) {
                footDecorator.decorator(holder, position - getHeadAndItemCount(), itemViewType, footSingleFootData);
            }
            footBindingHolder = holder;
        } else {
            data = mData.get(position - getHeadCount());
            if (itemDecorator != null)
                itemDecorator.decorator(holder, position - getHeadCount(), itemViewType, data);
            if (mPresenter != null) {
                binding.setVariable(BR.presenter, mPresenter);
            }
        }
        if (canLongClick) {
            holder.getBinding().getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemLongPresenter.onItemLongClick(position, mData.get(position));
                    return canLongClick;
                }
            });
        }
        // 分配数据
        holder.getBinding().setVariable(BR.itemData, data);
        holder.getBinding().setVariable(BR.itemPosition, position);
        holder.getBinding().executePendingBindings();
    }


    @Override
    public int getLayoutRes(int itemViewType) {
        if (itemViewType == headSingleKey)
            return headSingleFootRes;
        if (itemViewType == footSingleKey) {
            return footSingleFootRes;
        }
        return mLayoutRes;
    }

    @Override
    public boolean isFooterView(int position) {
        if (footSingleKey <= 0) {
            return false;
        } else if (footSingleFootRes <= 0) {
            return false;
        }
        int count = mData.size();
        return position >= count && position <= getItemCount();
    }

    @Override
    protected boolean isHeaderView(int position) {
        if (headSingleKey <= 0) {
            return false;
        } else if (footSingleFootRes <= 0) {
            return false;
        } else if (headSingleData == null) {
            return false;
        }
        return position == 0;

    }

    @Override
    protected int getHeadAndItemCount() {
        int count = 0;
        count += getHeadCount();
        count += mData.size();
        return count;
    }

    @Override
    public int getHeadCount() {
        int count = 0;
        if (headSingleData != null && headSingleKey > 0 && headSingleFootRes > 0) {
            count++;
        }
        return count;
    }

    @Override
    public void addSingleFootConfig(int footKey, int footRes, Object footData) {
        footSingleKey = footKey;
        footSingleFootRes = footRes;
        if (footData == null) {
            footData = new Object();
        }
        footSingleFootData = footData;
    }

    @Override
    public void addSingleHeaderConfig(int headKey, int headRes, Object headData) {
        headSingleKey = headKey;
        headSingleFootRes = headRes;
        if (headData == null) {
            headData = new Object();
        }
        headSingleData = headData;
    }

    @Override
    public int getItemCount() {
        int size = mData.size();
        if (footSingleKey > 0) {
            size = size + 1;
        }
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position))
            return headSingleKey;
        if (isFooterView(position)) {
            return footSingleKey;
        }

        return ITEM_VIEW_NORMAL_TYPE;
    }
}
