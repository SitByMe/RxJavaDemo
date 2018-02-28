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
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import ptv.zohar.rxjavademo.R;

public class TransformOperatorActivity extends AppCompatActivity {
    private String TAG = "TransformOperatorActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transform_operator);
    }

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
}
