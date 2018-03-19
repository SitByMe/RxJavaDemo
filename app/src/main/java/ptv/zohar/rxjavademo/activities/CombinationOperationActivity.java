package ptv.zohar.rxjavademo.activities;

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
import ptv.zohar.rxjavademo.adapter.RvListAdapter;
import ptv.zohar.rxjavademo.enums.CombinationOperation;
import ptv.zohar.rxjavademo.utils.SnackbarUtils;

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
                    case join:
//                        joinOperation();
                        SnackbarUtils.showShortSnackbar(recyclerView, "还未完全理解");
                        break;
                    case startWith_startWithArray:
                        startWithAndStartWithArrayOperation();
                        break;
                    case SWITCH:
                        switchOperation();
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
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        Thread.sleep(3000);
                        Log.i(TAG, "字母：0");
                        e.onNext("0");

                        Thread.sleep(1000);
                        Log.i(TAG, "字母：A");
                        e.onNext("A");

                        Thread.sleep(2000);
                        Log.i(TAG, "字母：B");
                        e.onNext("B");

                        Thread.sleep(500);
                        Log.i(TAG, "字母：C");
                        e.onNext("C");

                        Thread.sleep(1400);
                        Log.i(TAG, "字母：D");
                        e.onNext("D");

                        Log.i(TAG, "字母onComplete()");
                        e.onComplete();
                    }
                }).subscribeOn(Schedulers.io()),
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        Thread.sleep(1200);
                        Log.i(TAG, "数字：0");
                        e.onNext(0);

                        Thread.sleep(1200);
                        Log.i(TAG, "数字：1");
                        e.onNext(1);

                        Thread.sleep(1200);
                        Log.i(TAG, "数字：2");
                        e.onNext(2);

                        Thread.sleep(1200);
                        Log.i(TAG, "数字：3");
                        e.onNext(3);

                        Thread.sleep(1200);
                        Log.i(TAG, "数字：4");
                        e.onNext(4);

                        Log.i(TAG, "数字onComplete()");
                        e.onComplete();
                    }
                }).subscribeOn(Schedulers.io()),
                new BiFunction<String, Integer, String>() {

                    @Override
                    public String apply(String s, Integer integer) throws Exception {
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
     * zipOperation
     */
    private void zipOperation() {
        Observable.zip(Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        Log.i(TAG, "1");
                        emitter.onNext(1);//0
                        Thread.sleep(1000);

                        Log.i(TAG, "2");
                        emitter.onNext(2);//1
                        Thread.sleep(1000);

                        Log.i(TAG, "3");
                        emitter.onNext(3);//2
                        Thread.sleep(2000);

                        Log.i(TAG, "4");
                        emitter.onNext(4);//4
                        Thread.sleep(1000);

                        Log.i(TAG, "5");
                        emitter.onNext(5);//5
                        Thread.sleep(1000);

                        Log.i(TAG, "数字onComplete()");
                        emitter.onComplete();
                    }
                }).subscribeOn(Schedulers.io()),
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        Thread.sleep(500);
                        Log.i(TAG, "A");
                        emitter.onNext("A");//0.5
                        Thread.sleep(1000);

                        Log.i(TAG, "B");
                        emitter.onNext("B");//1.5
                        Thread.sleep(1000);

                        Log.i(TAG, "C");
                        emitter.onNext("C");//2.5;
                        Thread.sleep(1000);

                        Log.i(TAG, "D");
                        emitter.onNext("D");//3.5
                        Thread.sleep(4000);

                        Log.i(TAG, "E");
                        emitter.onNext("E");//7.5
                        Thread.sleep(1000);

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
        Observable.just(1, 2, 3)
                .startWith(Observable.just(4, 5, 6))
                .startWithArray(7, 8, 9)
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
