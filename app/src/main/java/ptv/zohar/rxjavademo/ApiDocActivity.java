package ptv.zohar.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import ptv.zohar.rxjavademo.utils.ToastUtils;

public class ApiDocActivity extends AppCompatActivity {
    private String TAG = "ApiDocActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_doc);
    }

    /**
     * 基本创建
     *
     * @param view
     */
    public void fullCreate(View view) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter e) throws Exception {

            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        ToastUtils.showLongToastSafe(this, "Observable.create(new ObservableOnSubscribe<Integer>() {\n" +
                "            @Override\n" +
                "            public void subscribe(ObservableEmitter e) throws Exception {}\n" +
                "        })");
    }

    /**
     * 快速创建
     *
     * @param view
     */
    public void quickCreate(View view) {
        Observable.just(1, 2, 3, 4)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        Integer[] items = {1, 2, 3, 4, 5};
        Observable.fromArray(items)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Observable.fromIterable(list)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        ToastUtils.showLongToastSafe(this, "Observable.just(1, 2, 3, 4)\n" +
                "Observable.fromArray(items)\n" +
                "Observable.fromIterable(list)");
    }

    /**
     * 额外的create方法
     *
     * @param view
     */
    public void otherCreate(View view) {
        Observable observable1 = Observable.empty();
        Observable observable2 = Observable.error(new RuntimeException());
        Observable observable3 = Observable.never();
        ToastUtils.showLongToastSafe(this, "Observable observable1 = Observable.empty();\n" +
                "Observable observable2 = Observable.error(new RuntimeException());\n" +
                "Observable observable3 = Observable.never();");
    }


    Integer i = 7;

    /**
     * 延迟创建
     *
     * @param view
     */
    public void delayCreate(View view) {
        Observable<Integer> observable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(i);
            }
        });

        i = 15;
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, "接收到的整数是" + integer);
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
     * 切断被观察者和观察者之间的连接
     *
     * @param view
     */
    public void dispose(View view) {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            // 1. 定义Disposable类变量
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
                Log.i(TAG, "开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, "对Next事件" + integer + "作出响应");
                if (integer == 2) {
                    // 设置在接收到第二个事件后切断观察者和被观察者的连接
                    mDisposable.dispose();
                    Log.i(TAG, "已经切断了连接：" + mDisposable.isDisposed());
                }
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
     * 定时延迟发送事件
     * 本质为：延迟指定时间后，调用一次onNext(0)
     *
     * @param view
     */
    public void timerCreate(View view) {
        // 第一个参数：首次发送事件延迟的时间；
        // 第二个参数：间隔时间单位。
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接");
                    }

                    @Override
                    public void onNext(Long aLong) {
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
     * 指定每隔一段时间就发送一次时间
     * 发送的事件序列：从0开始、无限递增1的的整数序列。
     *
     * @param view
     */
    public void interval(View view) {
        // 第一个参数：首次发送事件延迟的时间；
        // 第二个参数：之后每次发送事件延迟的时间；
        // 第三个参数：间隔时间单位。
        Observable.interval(5, 2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    Disposable mDisposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接。首次发送延迟时间为：5秒;之后每次发送的间隔时间为：2秒；当发送事件为10时，切断连接。");
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.i(TAG, "对Next事件" + aLong + "作出响应");
                        //当发送的事件为10时，切断连接
                        if (aLong == 10) {
                            mDisposable.dispose();
                            Log.i(TAG, "已经切断了连接：" + mDisposable.isDisposed());
                        }
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
     * 指定每隔一段时间发送一次事件，并指定总共发送的事件的数量
     *
     * @param view
     */
    public void intervalRange(View view) {
        // 第一个参数：首次发送的事件；
        // 第二个参数：总共发送的事件的数量；
        // 第三个参数：首次发送事件延迟的时间；
        // 第四个参数：之后每次发送事件的间隔时间；
        // 第五个参数：间隔时间参数。
        Observable.intervalRange(3, 7, 5, 2, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接。首次发送延迟时间为：5秒;之后每次发送的间隔时间为：2秒；首次发送的事件为：3；总共发送的事件的数量为：7。");
                    }

                    @Override
                    public void onNext(Long aLong) {
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
     * 连续发送一个事件序列
     *
     * @param view
     */
    public void range(View view) {
        // 第一个参数：首次发送的事件；
        // 第二个参数：总共发送的事件的数量。
        Observable.range(3, 7)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接。首次发送的事件为：3；总共发送的事件的数量为：7。");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.i(TAG, "对Next事件" + integer + "作出响应");
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
     * 连续发送一个事件序列
     * 和range()的区别在于，rangeLong()可发送Long类型的事件
     *
     * @param view
     */
    public void rangeLong(View view) {
        Observable.rangeLong(3, 7)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "开始采用subscribe连接。首次发送的事件为：3；总共发送的事件的数量为：7。");
                    }

                    @Override
                    public void onNext(Long aLong) {
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
