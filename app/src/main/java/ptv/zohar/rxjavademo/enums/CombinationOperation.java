package ptv.zohar.rxjavademo.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zohar on 2018/3/15.
 * desc:
 */
public enum CombinationOperation {
    concat_concatArray, combineLatest, join, merge_mergeArray, startWith, SWITCH, zip;

    public static List<String> getNames() {
        List<String> nameList = new ArrayList<>();
        for (CombinationOperation operation : CombinationOperation.values()) {
            nameList.add(operation.name());
        }
        return nameList;
    }
}
