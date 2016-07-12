package com.boqii.plant.api.helper;

import com.boqii.plant.api.ApiCode;
import com.boqii.plant.api.ApiException;

import rx.functions.Func1;

/**
 * 用来统一处理Http的resultCode,并将Result的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */
public class ResultFunc<T> implements Func1<Result<T>, T> {

    @Override
    public T call(Result<T> result) {
        if (ApiCode.SUCCESS_CODE.equals(result.getResultCode())) {
            ApiException apiException = new ApiException(result.getResultCode());
            apiException.setResult(result);
            throw apiException;
        }
        return result.getParent();
    }
}