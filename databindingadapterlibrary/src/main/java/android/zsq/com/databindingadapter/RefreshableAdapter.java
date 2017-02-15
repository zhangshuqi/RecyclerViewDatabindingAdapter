package android.zsq.com.databindingadapter;

import java.util.List;

/**
 * Created by Administrator on 2016/5/10 0010.
 */
public interface RefreshableAdapter<T> {
    public void refresh(List<T> newData);

    public void addAll(List<T> newData);

    public void clear();

    public void delete(int position);

    public void add(T object);

    public void add(int position, T data);
}
