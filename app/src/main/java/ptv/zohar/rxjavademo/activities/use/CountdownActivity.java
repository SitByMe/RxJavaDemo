package ptv.zohar.rxjavademo.activities.use;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import ptv.zohar.rxjavademo.R;
import ptv.zohar.rxjavademo.activities.MainActivity;

public class CountdownActivity extends AppCompatActivity {

    @BindView(R.id.tv_time)
    AppCompatTextView tvTime;
    @BindView(R.id.btn_begin)
    AppCompatButton btnBegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);
        ButterKnife.bind(this);
    }

    /**
     * 开启倒计时
     *
     * @param view 开始按钮
     */
    public void beginCountDown(final View view) {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        view.setEnabled(false);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (10 - aLong == 0) {
                            disposable.dispose();
                            onComplete();
                        } else {
                            tvTime.setText("当前计时：" + (10 - aLong));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        ((AppCompatButton) view).setText("倒计时出错！");
                        view.setEnabled(true);
                    }

                    @Override
                    public void onComplete() {
                        tvTime.setText("倒计时结束");
                        view.setEnabled(true);
                    }
                });
    }
}
