package ptv.zohar.rxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ptv.zohar.rxjavademo.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 跳转至 基本使用Activity
     *
     * @param view
     */
    public void goToBasicUse(View view) {
        StartActivityUtils.startActivity(this, BasicUseActivity.class);
    }

    /**
     * 跳转至 API解释Activity
     *
     * @param view
     */
    public void goToApiDoc(View view) {
        StartActivityUtils.startActivity(this, ApiDocActivity.class);
    }
}