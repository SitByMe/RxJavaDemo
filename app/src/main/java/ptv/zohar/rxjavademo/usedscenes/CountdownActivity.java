package ptv.zohar.rxjavademo.usedscenes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import ptv.zohar.rxjavademo.R;

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
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        view.setEnabled(false);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        tvTime.setText("当前计时：" + aLong);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ((AppCompatButton) view).setText("倒计时出错！");
                    }

                    @Override
                    public void onComplete() {
                        view.setEnabled(true);
                    }
                });
    }
}
