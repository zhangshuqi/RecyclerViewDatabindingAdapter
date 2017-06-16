package android.zsq.com.databindingadapter;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public interface BaseDataBindingDecorator<T> {
    void decorator(BindingViewHolder holder, int position, int viewType, T mData);
}
