package android.zsq.com.recyclerviewdatabindingadapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.zsq.com.databindingadapter.BaseBindingPresenter;
import android.zsq.com.databindingadapter.SingleTypeBindingAdapter;

import java.util.ArrayList;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     RecyclerView rcContent   = (RecyclerView) findViewById(R.id.rc_content);
        ArrayList data = new ArrayList();
        data.add(new ListViewHolder("1","20"));
        data.add(new ListViewHolder("1","20"));
        data.add(new ListViewHolder("1","20"));
        data.add(new ListViewHolder("1","20"));
        data.add(new ListViewHolder("1","20"));
        data.add(new ListViewHolder("1","20"));
        data.add(new ListViewHolder("1","20"));
        data.add(new ListViewHolder("1","20"));
        data.add(new ListViewHolder("1","20"));
        data.add(new ListViewHolder("1","20"));
        SingleTypeBindingAdapter singleTypeBindingAdapter = new SingleTypeBindingAdapter(this, data, R.layout.item_list);
        singleTypeBindingAdapter.setPresenter(new BaseBindingPresenter<ListViewHolder>() {
            @Override
            public void onItemClick(ListViewHolder itemData) {
                Log.d("---",itemData.getName()+"-----");
            }
        });
        rcContent.setLayoutManager(new LinearLayoutManager(this));
        rcContent.setAdapter(singleTypeBindingAdapter);
    }


}
