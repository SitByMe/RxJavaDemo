package ptv.zohar.rxjavademo.activities.api;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import ptv.zohar.rxjavademo.activities.AppCompatListActivity;
import ptv.zohar.rxjavademo.adapter.RvListAdapter;
import ptv.zohar.rxjavademo.enums.CombinationOperation;
import ptv.zohar.rxjavademo.utils.SnackbarUtils;

/**
 * Created by Zohar on 2018/3/15.
 * desc:组合操作符
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
                    case merge_mergeArray:
                        mergeAndMergeArrayOperation();
                        break;
                    case concatArrayDelayError_mergeArrayDelayError:
                        concatArrayDelayErrorAndMergeArrayDelayErrorOperation();
                        break;
                    case combineLatest:
                        combineLatestOperation();
                        break;
                    case zip:
                        zipOperation();
                        break;
                    case startWith_startWithArray:
                        startWithAndStartWithArrayOperation();
                        break;
                    case join:
//                        joinOperation();
                        SnackbarUtils.showShortSnackbar(recyclerView, "还未完全理解");
                        break;
                    case SWITCH:
                        SnackbarUtils.showShortSnackbar(recyclerView, "还未完全理解");
//                        switchOperation();
                        break;
                }
            }
        };
    }

    /**
     * and/then/when
     */
    private void concatAndConcatArrayOperation() {
        Observable.concat(Observable.just(1, 1, 1),
                Observable.just(2, 2))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "Next：" + integer);
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
        Observable.merge(Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        Thread.sleep(1000);
                        e.onNext(20);

                        Thread.sleep(1000);
                        e.onNext(40);

                        Thread.sleep(1000);
                        e.onNext(60);

                        Thread.sleep(1000);
                        e.onNext(80);

                        Thread.sleep(1000);
                        e.onNext(100);

                        e.onComplete();
                    }
                }).subscribeOn(Schedulers.io()),
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        Thread.sleep(3500);
                        e.onNext(1);

                        Thread.sleep(2000);
                        e.onNext(1);

                        e.onComplete();
                    }
                }).subscribeOn(Schedulers.io()))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "Next：" + integer);
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
     * concatDelayError/mergeDelayError
     */
    private void concatArrayDelayErrorAndMergeArrayDelayErrorOperation() {
        Observable.concatArrayDelayError(
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(1);
                        e.onNext(2);
                        e.onError(new NullPointerException());
                        e.onNext(3);
                    }
                }), Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        e.onNext(4);
                        e.onNext(5);
                        e.onError(new IllegalAccessException());
                        e.onNext(6);
                    }
                }))
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
                        Log.i(TAG, "对Error事件作出响应：" + e.toString());
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
    private void combineLatestOperation() {
        Observable.combineLatest(
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        Thread.sleep(500);
                        Log.i(TAG, "数字：1");
                        e.onNext(1);

                        Thread.sleep(1000);
                        Log.i(TAG, "数字：2");
                        e.onNext(2);

                        Thread.sleep(2000);
                        Log.i(TAG, "数字：3");
                        e.onNext(3);

                        Thread.sleep(500);
                        Log.i(TAG, "数字：4");
                        e.onNext(4);

                        Thread.sleep(1000);
                        Log.i(TAG, "数字：5");
                        e.onNext(5);

                        Log.i(TAG, "数字onComplete()");
                        e.onComplete();
                    }
                }).subscribeOn(Schedulers.io()),
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        Thread.sleep(1000);
                        Log.i(TAG, "字母：A");
                        e.onNext("A");

                        Thread.sleep(800);
                        Log.i(TAG, "字母：B");
                        e.onNext("B");

                        Thread.sleep(1100);
                        Log.i(TAG, "字母：C");
                        e.onNext("C");

                        Thread.sleep(300);
                        Log.i(TAG, "字母：D");
                        e.onNext("D");

                        Log.i(TAG, "字母onComplete()");
                        e.onComplete();
                    }
                }).subscribeOn(Schedulers.io()),
                new BiFunction<Integer, String, String>() {
                    @Override
                    public String apply(Integer integer, String s) throws Exception {
                        return integer + s;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
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
                });
    }

    /**
     * zipOperation
     */
    private void zipOperation() {
        Observable.zip(Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        Thread.sleep(500);
                        Log.i(TAG, "数字：1");
                        emitter.onNext(1);

                        Thread.sleep(1000);
                        Log.i(TAG, "数字：2");
                        emitter.onNext(2);

                        Thread.sleep(2000);
                        Log.i(TAG, "数字：3");
                        emitter.onNext(3);

                        Thread.sleep(500);
                        Log.i(TAG, "数字：4");
                        emitter.onNext(4);

                        Thread.sleep(1000);
                        Log.i(TAG, "数字：5");
                        emitter.onNext(5);

                        Log.i(TAG, "数字onComplete()");
                        emitter.onComplete();
                    }
                }).subscribeOn(Schedulers.io()),
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        Thread.sleep(1000);
                        Log.i(TAG, "字母：A");
                        emitter.onNext("A");

                        Thread.sleep(800);
                        Log.i(TAG, "字母：B");
                        emitter.onNext("B");

                        Thread.sleep(1100);
                        Log.i(TAG, "字母：C");
                        emitter.onNext("C");

                        Thread.sleep(300);
                        Log.i(TAG, "字母：D");
                        emitter.onNext("D");

                        Log.i(TAG, "字母onComplete()");
                        emitter.onComplete();
                    }
                }).subscribeOn(Schedulers.io()),
                new BiFunction<Integer, String, String>() {
                    @Override
                    public String apply(Integer integer, String s) throws Exception {
                        return integer + s;
                    }
                }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(String s) {
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
     * startWith_startWithArray
     */
    private void startWithAndStartWithArrayOperation() {
        Observable.just(2, 3)
                .startWith(Observable.just(1))
                .startWithArray(4, 5, 6)
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

    List<String> houses = new ArrayList<>();//模拟的房源数据，用于测试

    /**
     * join
     */
    private void joinOperation() {
        houses.add("A");
        houses.add("B");
        houses.add("C");
        houses.add("D");
        houses.add("E");

        Observable<String> houseSequence =
                Observable.interval(1, TimeUnit.SECONDS)
                        .map(new Function<Long, String>() {
                            @Override
                            public String apply(Long aLong) throws Exception {
                                return houses.get(aLong.intValue());
                            }
                        }).take(houses.size());

        Observable<Long> tictoc = Observable.interval(1, TimeUnit.SECONDS);

        houseSequence.join(tictoc,
                new Function<String, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(String s) throws Exception {
                        return Observable.timer(2, TimeUnit.SECONDS);
                    }
                },
                new Function<Long, ObservableSource<Long>>() {
                    @Override
                    public ObservableSource<Long> apply(Long aLong) throws Exception {
                        return Observable.timer(0, TimeUnit.SECONDS);
                    }
                },
                new BiFunction<String, Long, String>() {
                    @Override
                    public String apply(String s, Long aLong) throws Exception {
                        return aLong + "-->" + s;
                    }
                }
        ).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(String aLong) {
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
/*        Observable observable = Observable.timer(1, TimeUnit.SECONDS)
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
}
