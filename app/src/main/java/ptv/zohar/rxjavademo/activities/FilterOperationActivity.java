package ptv.zohar.rxjavademo.activities;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import ptv.zohar.rxjavademo.R;
import ptv.zohar.rxjavademo.adapter.RvListAdapter;
import ptv.zohar.rxjavademo.enums.FilterOperation;
import ptv.zohar.rxjavademo.utils.SnackbarUtils;

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
                    case throttleWithTimeout:
                        throttleWithTimeoutOperation();
                        break;
                    case distinct:
                        distinctOperation();
                        break;
                    case elementAt:
                        elementAtOperation();
                        break;
                    case filter:
                        filterOperation();
                        break;
                    case ofType:
                        ofTypeOperation();
                        break;
                    case first:
                        firstOperation();
                        break;
                    case last:
                        lastOperation();
                        break;
                    case ignoreElements:
                        ignoreElementsOperation();
                        break;
                    case throttleFirst:
                        throttleFirstOperation();
                        break;
                    case throttleLast_sample:
                        throttleLastOperation();
                        break;
                    case skip_skipLast:
                        skipAndSkipLastOperation();
                        break;
                    case take:
                        takeOperation();
                        break;
                    case takeLast:
                        takeLastOperation();
                        break;
                }
                SnackbarUtils.showShortSnackbar(recyclerView, dataList.get(position), getResources().getColor(R.color.white), getResources().getColor(R.color.black));
            }
        };
    }

    /**
     * debounce
     */
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
                Thread.sleep(1300);
                e.onComplete();
            }
        }).debounce(1, TimeUnit.SECONDS)
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

    /**
     * throttleWithTimeout
     */
    private void throttleWithTimeoutOperation() {
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
                Thread.sleep(1300);
                e.onComplete();
            }
        }).throttleWithTimeout(1, TimeUnit.SECONDS)
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

    /**
     * distinct
     */
    private void distinctOperation() {
        Observable.just(1, 4, 4, 3, 2, 3, 1, 5)
                .distinct()
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

    /**
     * elementAt
     */
    private void elementAtOperation() {
        Observable.just(0, 1, 2, 3, 4)
                .elementAt(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "对Next事件" + integer + "作出响应");
                    }
                });
    }

    /**
     * filter
     */
    private void filterOperation() {
        Observable.just(1, 2, 3, 4, 5)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer < 4;
                    }
                })
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

    /**
     * ofType
     */
    private void ofTypeOperation() {
        Observable.just(1, "2", 3.0, "4", 5)
                .ofType(Integer.class)
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

    /**
     * first
     */
    private void firstOperation() {
        Observable.just(1, 2, 3, 4, 5)
                .first(3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "对accept事件" + integer + "作出响应");
                    }
                });
    }

    /**
     * last
     */
    private void lastOperation() {
        Observable.just(1, 2, 3, 4, 5)
                .last(3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i(TAG, "对accept事件" + integer + "作出响应");
                    }
                });
    }

    /**
     * ignoreElements
     */
    private void ignoreElementsOperation() {
        Observable.just(1, 2, 3, 4, 5)
                .ignoreElements()
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "对Complete事件作出响应");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "对Error事件作出响应");
                    }
                });
    }

    /**
     * throttleFirst
     */
    private void throttleFirstOperation() {
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
                Thread.sleep(1300);
                e.onComplete();
            }
        }).throttleFirst(1, TimeUnit.SECONDS)
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

    /**
     * throttleLast/sample
     */
    private void throttleLastOperation() {
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
                Thread.sleep(1300);
                e.onComplete();
            }
        }).throttleLast(1, TimeUnit.SECONDS)
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

    /**
     * skip/skipLast
     */
    private void skipAndSkipLastOperation() {
        Observable.just(1, 2, 3, 4, 5)
                .skip(1)
                .skipLast(1)
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

    /**
     * take
     */
    private void takeOperation() {
        Observable.just(1, 2, 3, 4, 5)
                .take(2)
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

    /**
     * takeLast
     */
    private void takeLastOperation() {
        Observable.just(1, 2, 3, 4, 5)
                .takeLast(3)
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
