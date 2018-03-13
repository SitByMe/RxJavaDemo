package ptv.zohar.rxjavademo.activities;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import ptv.zohar.rxjavademo.adapter.RvListAdapter;
import ptv.zohar.rxjavademo.enums.FilterOperation;

public class FilterOperationActivity extends AppCompatListActivity {
    private String TAG = "FilterOperationActivity";

    @Override
    public List<String> getDataList() {
        List<String> dataList = new ArrayList<>();
        dataList.addAll(FilterOperation.getNames());
        return dataList;
    }

    @Override
    public RvListAdapter.OnItemClickListener getOnItemClickListener(final List<String> dataList) {
        return new RvListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (FilterOperation.valueOf(dataList.get(position))) {
                    case debounce:
                        debounceOperation();
                        break;
                    case distinct:
                        break;
                    case elementAt:
                        break;
                    case filter:
                        break;
                    case first:
                        break;
                    case ignoreElements:
                        break;
                    case last:
                        break;
                    case sample:
                        break;
                    case skip:
                        break;
                    case skipLast:
                        break;
                    case take:
                        break;
                    case takeLast:
                        break;
                    case ofType:
                        break;
                }
                Toast.makeText(mContext, dataList.get(position), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void debounceOperation() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                Thread.sleep(500);
                e.onNext(2);
                Thread.sleep(1500);
                e.onNext(3);
                Thread.sleep(500);
                e.onNext(4);
                Thread.sleep(2000);
                e.onNext(5);
                Thread.sleep(2000);
                e.onNext(6);
                Thread.sleep(500);
                e.onNext(7);
                Thread.sleep(500);
                e.onNext(8);
                Thread.sleep(1500);
                e.onNext(9);
            }
        })
                .debounce(1, TimeUnit.SECONDS)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer aLong) {
                        Log.i(TAG, "对Next事件" + aLong + "作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "对Complete事件作出响应");
                    }
                });
    }
}
