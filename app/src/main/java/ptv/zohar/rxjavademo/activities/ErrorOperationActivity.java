package ptv.zohar.rxjavademo.activities;

import android.util.Log;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import ptv.zohar.rxjavademo.adapter.RvListAdapter;
import ptv.zohar.rxjavademo.enums.ErrorOperation;
import ptv.zohar.rxjavademo.utils.SnackbarUtils;

/**
 * Created by Zohar on 2018/3/20.
 * desc:
 */
public class ErrorOperationActivity extends AppCompatListActivity {
    private String TAG = "ErrorOperationActivity";

    @Override
    public List<String> getDataList() {
        return ErrorOperation.getNames();
    }

    @Override
    public RvListAdapter.OnItemClickListener getOnItemClickListener(final List<String> dataList) {
        return new RvListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (ErrorOperation.valueOf(dataList.get(position))) {
                    case onErrorReturn:
                        onErrorReturnOperation();
                        break;
                    case onErrorResumeNext:
                        onErrorResumeNextOperation();
                        break;
                    case onExceptionResumeNext:
                        onExceptionResumeNextOperation();
                        break;
                    case retry:
                        retryOperation();
                        break;
                    case retryWhen:
//                        retryWhenOperation();
                        SnackbarUtils.showShortSnackbar(recyclerView, "还未完全理解");
                        break;
                    case repeat:
                        repeatOperation();
                        break;
                    case repeatWhen:
                        repeatWhenOperation();
                        break;
                }
            }
        };
    }

    /**
     * onErrorReturn
     */
    private void onErrorReturnOperation() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new NullPointerException());
                e.onNext(3);
            }
        }).onErrorReturn(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(Throwable throwable) throws Exception {
                return -1;
            }
        }).subscribe(new Observer<Integer>() {
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
     * onErrorResumeNext
     */
    private void onErrorResumeNextOperation() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new NullPointerException());
                e.onNext(3);
            }
        }).onErrorResumeNext(Observable.just(4, 5, 6))
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
     * onExceptionResumeNext
     */
    private void onExceptionResumeNextOperation() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new NullPointerException());
//                e.onError(new Throwable("测试Throwable"));
                e.onNext(3);
            }
        }).onExceptionResumeNext(Observable.just(4, 5, 6))
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
                        Log.i(TAG, "对Error事件作出响应：" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "对Complete事件作出响应");
                    }
                });
    }

    /**
     * retry
     */
    private void retryOperation() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
//                e.onError(new NullPointerException());
                e.onError(new Throwable("A"));
                e.onNext(3);
            }
        }).retry(3, new Predicate<Throwable>() {
            @Override
            public boolean test(Throwable throwable) throws Exception {
                Log.i(TAG, throwable.toString());
                return "A".equals(throwable.getMessage());
            }
        }).subscribe(new Observer<Integer>() {
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
     * retry
     */
    private void retryWhenOperation() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onError(new NullPointerException());
                e.onNext(3);
            }
        }).retryWhen(new Function<Observable<Throwable>, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Observable<Throwable> throwableObservable) throws Exception {
                return new ObservableSource<String>() {
                    @Override
                    public void subscribe(Observer<? super String> observer) {
                        observer.onNext("D");
                        observer.onComplete();
                    }
                };
            }
        }).subscribe(new Observer<Integer>() {
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
     * repeat
     */
    private void repeatOperation() {

    }

    /**
     * repeatWhen
     */
    private void repeatWhenOperation() {
    }
}
