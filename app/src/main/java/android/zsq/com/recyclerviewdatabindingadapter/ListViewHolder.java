package android.zsq.com.recyclerviewdatabindingadapter;

import android.databinding.BaseObservable;

/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class ListViewHolder extends BaseObservable {
    private String name ;
    private String time;

    public ListViewHolder(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
