package ptv.zohar.rxjavademo.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zohar on 2018/3/13.
 * desc:
 */
public enum TransformOperator {
    buffer, window, flatMap, concatMap, flatMapIterable, groupBy, map, cast, scan;

    public static List<String> getNames() {
        List<String> nameList = new ArrayList<>();
        for (TransformOperator operation : TransformOperator.values()) {
            nameList.add(operation.name());
        }
        return nameList;
    }
}
