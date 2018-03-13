package ptv.zohar.rxjavademo.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ptv.zohar.rxjavademo.R;
import ptv.zohar.rxjavademo.adapter.RvListAdapter;

/**
 * Created by Zohar on 2018/3/13.
 * desc:
 */
public abstract class AppCompatListActivity extends AppCompatActivity {
    public Context mContext = this;

    public RecyclerView recyclerView;
    private RvListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_list);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView = findViewById(R.id.rv_list);
        createUI();
    }

    public void createUI() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        if (adapter == null) {
            adapter = new RvListAdapter(this, getDataList());
            recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(getOnItemClickListener(getDataList()));
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    public abstract List<String> getDataList();

    public abstract RvListAdapter.OnItemClickListener getOnItemClickListener(final List<String> dataList);
}