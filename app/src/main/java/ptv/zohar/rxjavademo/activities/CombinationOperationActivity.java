package ptv.zohar.rxjavademo.activities;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ptv.zohar.rxjavademo.adapter.RvListAdapter;
import ptv.zohar.rxjavademo.enums.CombinationOperation;

/**
 * Created by Zohar on 2018/3/15.
 * desc:
 */
public class CombinationOperationActivity extends AppCompatListActivity {
    private String TAG = "CombinationOperationActivity";

    @Override
    public List<String> getDataList() {
        return CombinationOperation.getNames();
    }

    @Override
    public RvListAdapter.OnItemClickListener getOnItemClickListener(final List<String> dataList) {
        return new RvListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (CombinationOperation.valueOf(dataList.get(position))) {
                    case concat_concatArray:
                        concatAndConcatArrayOperation();
                        break;
                    case combineLatest:
                        combineLatestOperation();
                        break;
                    case join:
//                        joinOperation();
                        break;
                    case merge_mergeArray:
                        mergeAndMergeArrayOperation();
                        break;
                    case startWith:
                        startWithOperation();
                        break;
                    case SWITCH:
                        switchOperation();
                        break;
                    case zip:
                        zipOperation();
                        break;
                }
            }
        };
    }

    /**
     * and/then/when
     */
    private void concatAndConcatArrayOperation() {
        Observable.concat(Observable.just(1, 2, 3),
                Observable.just(4, 5, 6),
                Observable.just(7, 8, 9),
                Observable.just(10, 11, 12))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer aLong) {
                        Log.i(TAG, "Next：" + aLong);
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
     * combineLatest
     */

    Disposable disposable;

    private void combineLatestOperation() {
        final List<String> strList = new ArrayList<>();
        strList.add("O");
        strList.add("A");
        strList.add("B");
        strList.add("C");
        strList.add("D");
        final Observer observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                Log.i(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "Next：" + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "对Error事件作出响应：" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "对Complete事件作出响应");
            }
        };
        Observable.combineLatest(
                Observable.interval(2, TimeUnit.SECONDS).map(new Function<Long, String>() {
                    @Override
                    public String apply(Long aLong) throws Exception {
                        if (aLong >= strList.size()) {
                            disposable.dispose();
                            observer.onComplete();
                        }
                        Log.i(TAG, "字母：" + strList.get(aLong.intValue()));
                        return strList.get(aLong.intValue());
                    }
                }),
                Observable.interval(1200, TimeUnit.MILLISECONDS).map(new Function<Long, Integer>() {
                    @Override
                    public Integer apply(Long aLong) throws Exception {
                        Log.i(TAG, "数字：" + aLong.intValue());
                        return aLong.intValue();
                    }
                }),
                new BiFunction<String, Integer, String>() {

                    @Override
                    public String apply(String s, Integer integer) throws Exception {
                        return integer + s;
                    }
                }).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(observer);
    }

    final List<String> strList = new ArrayList<>();

    /**
     * join
     */
    private void joinOperation() {
        strList.add("A");
        strList.add("B");
        strList.add("C");

        //用来每秒从strList中取出数据并发送出去
        Observable<String> houseSequence =
                Observable.interval(1, TimeUnit.SECONDS)
                        .map(new Function<Long, String>() {
                            @Override
                            public String apply(Long aLong) throws Exception {
                                return strList.get(aLong.intValue());
                            }
                        }).take(strList.size());//这里的take是为了防止strList.get(position.intValue())数组越界

        //用来实现每秒发送一个新的Long型数据
        Observable<Long> tictoc = Observable.interval(1, TimeUnit.SECONDS);

        houseSequence.join(tictoc,
                new Function<String, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(String s) throws Exception {
                        return Observable.timer(1, TimeUnit.SECONDS);
                    }
                },
                new Function<Long, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(Long aLong) throws Exception {
                        return Observable.timer(1, TimeUnit.SECONDS);
                    }
                },
                new BiFunction<String, Long, Object>() {
                    @Override
                    public Object apply(String s, Long aLong) throws Exception {
                        return aLong + "-->" + strList.get(aLong.intValue());
                    }
                }
        ).subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Object s) {
                Log.i(TAG, "Next：" + s);
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
     * merge
     */
    private void mergeAndMergeArrayOperation() {
        Observable.merge(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer aLong) {
                        Log.i(TAG, "Next：" + aLong);
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
     * startWith
     */
    private void startWithOperation() {
        Observable.just(1, 2, 3)
                .startWith(Observable.just(4, 5, 6))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer aLong) {
                        Log.i(TAG, "Next：" + aLong);
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
     * switch
     */
    private void switchOperation() {
       /* Observable observable = Observable.timer(1, TimeUnit.SECONDS)
                .map(new Function<Long, Observable<Long>>() {
                    @Override
                    public Observable<Long> apply(Long aLong) throws Exception {
                        return Observable.interval(2, TimeUnit.SECONDS)
                                .map(new Function<Long, Long>() {
                                    @Override
                                    public Long apply(Long aLong) throws Exception {
                                        return aLong * 10;
                                    }
                                }).take(5);
                    }
                }).take(2);
        Observable.switchOnNext(observable)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.i(TAG, "Next：" + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "对Complete事件作出响应");
                    }
                });*/
    }

    /**
     * zipOperation
     */
    private void zipOperation() {

    }
}
