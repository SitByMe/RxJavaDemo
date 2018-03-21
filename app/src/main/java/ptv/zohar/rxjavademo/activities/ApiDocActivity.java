package ptv.zohar.rxjavademo.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ptv.zohar.rxjavademo.R;
import ptv.zohar.rxjavademo.activities.api.CombinationOperationActivity;
import ptv.zohar.rxjavademo.activities.api.CreateOperatorActivity;
import ptv.zohar.rxjavademo.activities.api.ErrorOperationActivity;
import ptv.zohar.rxjavademo.activities.api.FilterOperationActivity;
import ptv.zohar.rxjavademo.activities.api.TransformOperatorActivity;
import ptv.zohar.rxjavademo.utils.StartActivityUtils;

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

    public void gotoErrorOperation(View view) {
        StartActivityUtils.startActivity(this, ErrorOperationActivity.class);
    }
}
