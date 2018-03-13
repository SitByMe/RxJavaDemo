package ptv.zohar.rxjavademo.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zohar on 2018/3/13.
 * desc:
 */
public enum FilterOperation {
    debounce, distinct, elementAt, filter, first, ignoreElements, last, sample, skip, skipLast, take, takeLast, ofType;

    public static List<String> getNames() {
        List<String> nameList = new ArrayList<>();
        for (FilterOperation operation : FilterOperation.values()) {
            nameList.add(operation.name());
        }
        return nameList;
    }
}