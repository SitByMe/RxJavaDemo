package ptv.zohar.rxjavademo.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zohar on 2018/3/20.
 * desc:
 */
public enum ErrorOperation {
    onErrorReturn, onErrorResumeNext, onExceptionResumeNext, retry, retryWhen, repeat, repeatWhen;

    public static List<String> getNames() {
        List<String> nameList = new ArrayList<>();
        for (ErrorOperation operation : ErrorOperation.values()) {
            nameList.add(operation.name());
        }
        return nameList;
    }
}