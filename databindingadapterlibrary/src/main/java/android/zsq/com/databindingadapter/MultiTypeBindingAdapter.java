package android.zsq.com.databindingadapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class MultiTypeBindingAdapter<T> extends BaseDataBindingAdapter<T> {
    public static final int ITEM_VIEW_NORMAL_TYPE = 10000;
    protected ArrayMap<Integer, Integer> multiTypeMap;
    protected ArrayMap<Integer, BindingViewHolder> multiTypeFootHolderMap;
    protected ArrayMap<Integer, BindingViewHolder> multiTypeHeadHolderMap;
    public List<Integer> headKeyList;
    private ArrayList headDataList;
    private ArrayList<Integer> footKeyList;
    private ArrayList footDataList;
    private AdapterTypeConfig footAdapterTypeConfig;

    public MultiTypeBindingAdapter(Context context) {
        super(context);
    }

    public MultiTypeBindingAdapter(Context context, List data) {
        super(context);
        if (data == null) {
            data = new ArrayList();
        }
        init(data);
    }

    /**
     * @doc 单个item的viewType时使用
     */
    public MultiTypeBindingAdapter(Context context, List data, int itemDataRes) {
        super(context);
        if (data == null) {
            data = new ArrayList();
        }
        init(data);
        multiTypeMap.put(ITEM_VIEW_NORMAL_TYPE, itemDataRes);
    }

    private void init(List data) {
        mData = data;
        multiTypeMap = new ArrayMap<>();
        multiTypeFootHolderMap = new ArrayMap<>();
        multiTypeHeadHolderMap = new ArrayMap<>();
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
        this.footAdapterTypeConfig = config;
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
        if (footData != null) {
            footDataList.add(footData);
        }
        footKeyList.add(footKey);
        multiTypeMap.put(footKey, footRes);
    }

    public void removeFootViewForFootKey(int footKey) {
        footKeyList.remove(footKey);
        multiTypeMap.remove(footKey);
    }

    public void setItemViewRes(int itemRes) {
        multiTypeMap.put(ITEM_VIEW_NORMAL_TYPE, itemRes);
    }
    @Override
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
    @Override
    public boolean isFooterView(int position) {
        if (footKeyList == null || footKeyList.size() == 0) {
            return false;
        } else if (footKeyList.size() == 0) {
            return false;
        }
        int count = getHeadAndItemCount();
        return position >= count && position <= getItemCount();
    }
    @Override
    protected int getHeadAndItemCount() {
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
        int count = 0;
        if (mData != null && mData.size() != 0) {
            count = mData.size();
        }
        if (headKeyList != null && headKeyList.size() != 0) {
            count += headKeyList.size();
        }
        if (footKeyList != null && footKeyList.size() != 0) {
            count += footKeyList.size();
        }
        return count;
    }


    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (getLayoutRes(viewType) <= 0)
            throw new NullPointerException("onCreateViewHolder layout res is null");
        return new BindingViewHolder(DataBindingUtil.inflate(mLayoutInflater, getLayoutRes(viewType), parent, false));
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
            if (headDataList == null || headDataList.size() == 0) return;
            data = headDataList.get(position);
            if (headDecorator != null)
                headDecorator.decorator(holder, position, itemViewType, data);
            if (headPresenter != null)
                binding.setVariable(BR.presenter, headPresenter);
            multiTypeHeadHolderMap.put(itemViewType,holder);
        } else if (isFooterView(position)) {
            if (footDataList == null || footDataList.size() == 0) return;
            data = footDataList.get(position - getHeadAndItemCount());
            if (footPresenter != null)
                binding.setVariable(BR.presenter, footPresenter);
            if (footDecorator != null) {
                footDecorator.decorator(holder, position - getHeadAndItemCount(), itemViewType, data);
            }
            multiTypeFootHolderMap.put(itemViewType, holder);
        } else {
            data = mData.get(position - getHeadCount());
            if (itemDecorator != null)
                itemDecorator.decorator(holder, position - getHeadCount(), itemViewType, data);
            if (mPresenter != null) {
                binding.setVariable(BR.presenter, mPresenter);
            }
        }
        if (data == null) {
            throw new NullPointerException("BaseDataBindingAdapter  itemData is null");
        }
        // 分配数据
        binding.setVariable(BR.itemData, data);
        binding.setVariable(BR.itemPosition, position);

        //分配事件
        binding.executePendingBindings();
        if (canLongClick) {
            holder.getBinding().getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemLongPresenter.onItemLongClick(position, mData.get(position));
                    return canLongClick;
                }
            });
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


    public AdapterTypeConfig getFootAdapterTypeConfig() {
        return footAdapterTypeConfig;
    }

    public ArrayMap<Integer, BindingViewHolder> getMultiTypeFootHolderMap() {
        return multiTypeFootHolderMap;
    }
    public <T> T  getMultiTypeFootHolder(int footResKey) {
        if(!multiTypeFootHolderMap.containsKey(footResKey)){
            return null;
        }
        return (T) multiTypeFootHolderMap.get(footResKey);
    }
}
