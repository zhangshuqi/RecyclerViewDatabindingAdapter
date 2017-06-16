package android.zsq.com.recyclerviewdatabindingadapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.zsq.com.databindingadapter.BaseBindingItemPresenter;
import android.zsq.com.databindingadapter.SingleTypeBindingAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      RecyclerView rcContent = (RecyclerView) findViewById(R.id.rc_content);
      ArrayList data = new ArrayList();
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      data.add(new ListViewHolder("1", "20"));
      SingleTypeBindingAdapter adapter = new SingleTypeBindingAdapter(this, data, R.layout.item_list);
      adapter.setItemPresenter(new BaseBindingItemPresenter<ListViewHolder>() {
         @Override
         public void onItemClick(ListViewHolder itemData) {
            Log.d("---",itemData.getName()+"-----");
         }
      });
      //  MultiTypeBindingAdapter adapter = new MultiTypeBindingAdapter(this, data, R.layout.item_list);
      // ListViewHolder multiTypeFootHolder = adapter.<ListViewHolder>getMultiTypeFootHolder(1);
      adapter.addSingleHeaderConfig(1, R.layout.head, new ListViewHolder("2", "50"));
      adapter.addSingleFootConfig(2, R.layout.head, new ListViewHolder("2", "50"));
      rcContent.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
      rcContent.setAdapter(adapter);
   }


}
