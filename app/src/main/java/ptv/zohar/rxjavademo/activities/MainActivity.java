package ptv.zohar.rxjavademo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ptv.zohar.rxjavademo.R;
import ptv.zohar.rxjavademo.utils.StartActivityUtils;

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

    public void goToUseScenes(View view) {
        StartActivityUtils.startActivity(this, UseScenesActivity.class);
    }
}