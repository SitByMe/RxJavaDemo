package ptv.zohar.rxjavademo;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Zohar on 2018/2/6.
 * desc:
 */
public class StartActivityUtils {

    public static void startActivity(Context context, Class<?> cls) {
        context.startActivity(new Intent(context, cls));
    }
}
