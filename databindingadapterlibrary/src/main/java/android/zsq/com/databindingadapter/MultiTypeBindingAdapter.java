package android.zsq.com.databindingadapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class MultiTypeBindingAdapter extends BaseDataBindingAdapter {
    public static final int ITEM_VIEW_NORMAL_TYPE = 10000;
    protected ArrayMap<Integer, Integer> multiTypeMap;
    public List<Integer> headKeyList;
    private ArrayList headDataList;
    private ArrayList<Integer> footKeyList;
    private ArrayList footDataList;

    public MultiTypeBindingAdapter(Context context, List data) {
        super(context);
        mData = data;
        multiTypeMap = new ArrayMap<>();
    }

    /**
     * @doc 单个item的viewType时使用
     */
    public MultiTypeBindingAdapter(Context context, List data, int itemDataRes) {
        super(context);
        mData = data;
        multiTypeMap = new ArrayMap<>();
        multiTypeMap.put(ITEM_VIEW_NORMAL_TYPE, itemDataRes);
    }

    public interface AdapterTypeConfig {
        Map<Integer, Integer> getTypeConfigKeyAndRes();

        List getTypeConfigData();
    }

    public void setMultiHeadConfig(AdapterTypeConfig config) {
        if (config == null) {
            throw new NullPointerException("config is null");
        }
        if (config.getTypeConfigKeyAndRes() == null || config.getTypeConfigKeyAndRes().size() == 0) {
            throw new NullPointerException("config.getHeadKeyAndResMap is null");
        }
        if (config.getTypeConfigData() == null || config.getTypeConfigData().size() == 0) {
            throw new NullPointerException("config.getHeadData() is null");
        }
        multiTypeMap.putAll(config.getTypeConfigKeyAndRes());
        if (headKeyList == null) {
            headKeyList = new ArrayList<>();
        }
        if (headDataList == null) {
            headDataList = new ArrayList();
        }
        Set<Integer> keySet = config.getTypeConfigKeyAndRes().keySet();
        for (Integer key : keySet
                ) {
            this.headKeyList.add(key);
        }
        headDataList.addAll(config.getTypeConfigData());
    }

    public void addMultiTypeMap(Map<Integer, Integer> multiTypeMap) {
        this.multiTypeMap.putAll(multiTypeMap);
    }

    public void addItemViewType(int viewType, int viewRes) {
        this.multiTypeMap.put(viewType, viewRes);
    }

    public void setMultiFootConfig(AdapterTypeConfig config) {
        if (config == null) {
            throw new NullPointerException("config is null");
        }
        if (config.getTypeConfigKeyAndRes() == null || config.getTypeConfigKeyAndRes().size() == 0) {
            throw new NullPointerException("foot config keyAndRes Map is null");
        }
        if (config.getTypeConfigData() == null || config.getTypeConfigData().size() == 0) {
            throw new NullPointerException("foot config data is null");
        }
        multiTypeMap.putAll(config.getTypeConfigKeyAndRes());
        if (footKeyList == null) {
            footKeyList = new ArrayList<>();
        }
        if (footDataList == null) {
            footDataList = new ArrayList();
        }
        Set<Integer> keySet = config.getTypeConfigKeyAndRes().keySet();
        for (Integer key : keySet
                ) {
            this.footKeyList.add(key);
        }
        footDataList.addAll(config.getTypeConfigData());
    }

    public void addSingleHeadConfig(int headKey, int headRes, Object headData) {
        if (headDataList == null) {
            headDataList = new ArrayList();
        }
        if (headKeyList == null) {
            headKeyList = new ArrayList();
        }
        headDataList.add(headData);
        headKeyList.add(headKey);
        multiTypeMap.put(headKey, headRes);
    }

    public void addSingleFootConfig(int footKey, int footRes, Object footData) {
        if (footDataList == null) {
            footDataList = new ArrayList();
        }
        if (footKeyList == null) {
            footKeyList = new ArrayList();
        }
        footDataList.add(footData);
        footKeyList.add(footKey);
        multiTypeMap.put(footKey, footRes);
    }

    public void setItemViewRes(int itemRes) {
        multiTypeMap.put(ITEM_VIEW_NORMAL_TYPE, itemRes);
    }

    public boolean isHeaderView(int position) {
        if (headKeyList == null || headKeyList.size() == 0) {
            return false;
        } else if (headKeyList.size() == 1) {
            return position == 0;
        } else if (headKeyList.size() > 1) {
            return position < headKeyList.size();
        }
        return false;
    }

    public boolean isFooterView(int position) {
        if (footKeyList == null || footKeyList.size() == 0) {
            return false;
        } else if (footKeyList.size() == 0) {
            return false;
        }
        int count = getHeadAndItemCount();
        if (position >= count && position <= getItemCount()) {
            return true;
        }
        return false;
    }

    private int getHeadAndItemCount() {
        int count = getHeadCount();
        if (mData != null || mData.size() != 0) {
            count += mData.size();
        }
        return count;
    }

    public int getHeadCount() {

        return headKeyList != null && headKeyList.size() != 0 ? headKeyList.size() : 0;
    }

    @Override
    public int getItemCount() {
        if (mData == null || mData.size() == 0) {
            return 0;
        }
        int count = mData.size();
        if (headKeyList != null && headKeyList.size() != 0) {
            count += headKeyList.size();
        }
        if (footKeyList != null && footKeyList.size() != 0) {
            count += footKeyList.size();
        }
        return count;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (getLayoutRes(viewType) <= 0)
            throw new NullPointerException("onCreateViewHolder layout res is null");
        return new BindingViewHolder(DataBindingUtil.inflate(mLayoutInflater, getLayoutRes(viewType), parent, false));
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        if (mData == null) {
            throw new NullPointerException("BaseDataBindingAdapter  data is null");
        }
        Object data = null;
        if (isHeaderView(position)) {
            data = headDataList.get(position);
        } else if (isFooterView(position)) {
            data = footDataList.get(position - getHeadAndItemCount());
        } else {
            data = mData.get(position - getHeadCount());
            if (decorator != null)
                decorator.decorator(holder, position - getHeadCount(), getItemViewType(position));
        }
        if (data == null) {
            throw new NullPointerException("BaseDataBindingAdapter  itemData is null");
        }
        // 分配数据
        holder.getBinding().setVariable(BR.item, data);
        holder.getBinding().setVariable(BR.itemPosition, position);
        holder.getBinding().executePendingBindings();
        //分配事件
        if (mPresenter != null) {
            holder.getBinding().setVariable(BR.presenter, mPresenter);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderView(position)) {
            return headKeyList.get(position);
        } else if (isFooterView(position)) {
            return footKeyList.get(position - getHeadAndItemCount());
        }
        return getMyItemViewType(position, multiTypeMap);
    }
    // 解决gridmanager 的head 问题
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

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && holder.getLayoutPosition() == 0) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

    public int getMyItemViewType(int position, ArrayMap<Integer, Integer> multiTypeMap) {
        return ITEM_VIEW_NORMAL_TYPE;
    }

    @Override
    public int getLayoutRes(int itemViewType) {
        return multiTypeMap.get(itemViewType);
    }


    @Override
    public void clear() {
        super.clear();
    }


}
