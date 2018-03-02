package ptv.zohar.rxjavademo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observables.GroupedObservable;
import ptv.zohar.rxjavademo.R;

public class TransformOperatorActivity extends AppCompatActivity {
    private String TAG = "TransformOperatorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transform_operator);
    }

    /**
     * buffer
     *
     * @param view view
     */
    public void bufferOperator(View view) {
        Observable.just(1, 2, 3, 4, 5)
                .buffer(3, 1)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.i(TAG, "接收到的整数列表是：" + integers);
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
     * window
     *
     * @param view view
     */
    public void windowOperator(View view) {
        Observable.just(1, 2, 3, 4, 5)
                .window(3, 1)
                .subscribe(new Consumer<Observable<Integer>>() {
                    @Override
                    public void accept(Observable<Integer> integerObservable) throws Exception {
                        integerObservable.subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.i(TAG, "开始采用subscribe连接");
                            }

                            @Override
                            public void onNext(Integer integer) {
                                Log.i(TAG, "接收到的整数列表是：" + integer);
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
                });
    }

    /**
     * flatMap
     *
     * @param view view
     */
    public void flatMapOperator(View view) {
        Observable.just(1, 2, 3, 4, 5)
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        List<String> list = new ArrayList<>();
                        for (int i = 1; i <= 3; i++) {
                            list.add("事件" + integer + "的子事件" + i);
                        }
                        return Observable.fromIterable(list);
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "接收到的事件是：" + s);
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
            /*    .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "接收到的事件是：" + s);
                    }
                });*/
    }

    /**
     * concatMap
     *
     * @param view view
     */
    public void concatMapOperator(View view) {
        Observable.just(1, 2, 3, 4, 5)
                .concatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        List<String> list = new ArrayList<>();
                        for (int i = 1; i <= 3; i++) {
                            list.add("事件" + integer + "的子事件" + i);
                        }
                        return Observable.fromIterable(list);
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "接收到的事件是：" + s);
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
     * flatMapIterable
     *
     * @param view view
     */
    public void flatMapIterableOperator(View view) {
        Observable.just(1, 2, 3, 4, 5)
                .flatMapIterable(new Function<Integer, Iterable<String>>() {
                    @Override
                    public Iterable<String> apply(Integer integer) throws Exception {
                        List<String> list = new ArrayList<>();
                        for (int i = 1; i <= 3; i++) {
                            list.add("事件" + integer + "的子事件" + i);
                        }
                        return list;
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "接收到的事件是：" + s);
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
     * groupBy
     *
     * @param view view
     */
    public void groupByOperator(View view) {
        Observable.just(1, 2, 3, 4, 5)
                .groupBy(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        //返回值为这个事件所在组的组名
                        return "第" + (integer % 2 == 0 ? 2 : integer % 2) + "组";
                    }
                })
                .subscribe(new Observer<GroupedObservable<String, Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(final GroupedObservable<String, Integer> stringIntegerGroupedObservable) {
                        stringIntegerGroupedObservable.subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.i(TAG, "开始采用subscribe连接2");
                            }

                            @Override
                            public void onNext(Integer integer) {
                                Log.i(TAG, "接收到的事件是：" + stringIntegerGroupedObservable.getKey() + "：" + integer);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "对Error事件作出响应2");
                            }

                            @Override
                            public void onComplete() {
                                Log.i(TAG, "对Complete事件作出响应2");
                            }
                        });
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
     * map
     *
     * @param view view
     */
    public void mapOperator(View view) {
        Observable.just(1, 2, 3, 4, 5)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "将Integer类型的" + integer + "转换成字符串类型的" + String.valueOf(integer);
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "接收到的事件是：" + s);
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
     * cast
     *
     * @param view view
     */
    public void castOperator(View view) {
        Observable.just(1, "2", 3.0, "4", 5)
                .cast(Integer.class)//将不同的事件类型转换为Integer类型
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "接收到的事件是：" + integer);
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
     * scan
     *
     * @param view view
     */
    public void scanOperator(View view) {
        Observable.just(1, 2, 3, 4, 5)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "接收到的事件是：" + integer);
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
}
