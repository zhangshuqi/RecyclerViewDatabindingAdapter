package android.zsq.com.databindingadapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class SingleTypeBindingAdapter  extends BaseDataBindingAdapter {

    private   int mLayoutRes;

    public SingleTypeBindingAdapter(Context context, List data, int layoutRes) {
        super(context);
        mData=data;
        mLayoutRes = layoutRes;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BindingViewHolder(DataBindingUtil.inflate(mLayoutInflater,getLayoutRes(),parent,false));
    }
    @Override
    @LayoutRes
    public int getLayoutRes(){
        return mLayoutRes;
    }


}
