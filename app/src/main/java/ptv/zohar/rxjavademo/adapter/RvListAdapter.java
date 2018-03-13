package ptv.zohar.rxjavademo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by Zohar on 2018/3/13.
 * desc:
 */
public class RvListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<String> dataList;

    private OnItemClickListener mItemClickListener;

    public RvListAdapter(Context mContext, List<String> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Button btn = new Button(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        btn.setLayoutParams(params);
        btn.setAllCaps(false);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick((Integer) btn.getTag());
                }
            }
        });
        return new RecyclerView.ViewHolder(btn) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Button button = (Button) holder.itemView;
        button.setText(dataList.get(position));
        button.setTag(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
