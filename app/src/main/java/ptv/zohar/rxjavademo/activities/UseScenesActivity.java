package ptv.zohar.rxjavademo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ptv.zohar.rxjavademo.R;
import ptv.zohar.rxjavademo.utils.StartActivityUtils;
import ptv.zohar.rxjavademo.activities.use.CountdownActivity;

public class UseScenesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_scenes);
    }

    public void countDown(View view) {
        StartActivityUtils.startActivity(this, CountdownActivity.class);
    }
}
