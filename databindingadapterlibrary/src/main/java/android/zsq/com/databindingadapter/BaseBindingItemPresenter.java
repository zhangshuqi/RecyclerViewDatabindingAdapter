package android.zsq.com.databindingadapter;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public  interface BaseBindingItemPresenter<T> {
     void onItemClick(int position, T itemData);
}
