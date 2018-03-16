package ptv.zohar.rxjavademo.activities;

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
import ptv.zohar.rxjavademo.R;
import ptv.zohar.rxjavademo.enums.CombinationOperation;
import ptv.zohar.rxjavademo.utils.StartActivityUtils;
import ptv.zohar.rxjavademo.utils.ToastUtils;

public class ApiDocActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api_doc);
    }

    public void gotoCreateOperator(View view) {
        StartActivityUtils.startActivity(this, CreateOperatorActivity.class);
    }

    public void gotoTransformOperator(View view) {
        StartActivityUtils.startActivity(this, TransformOperatorActivity.class);
    }

    public void gotoFilterOperation(View view) {
        StartActivityUtils.startActivity(this, FilterOperationActivity.class);
    }

    public void gotoCombinationOperation(View view) {
        StartActivityUtils.startActivity(this, CombinationOperationActivity.class);
    }
}
