package com.boqii.plant.api.service;

import com.boqii.plant.api.helper.Result;
import com.boqii.plant.data.Login.model.User;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface LoginService {

    @POST("myt_parent/parentAction_login.do")
    Observable<Result<User>> login(@Query("parent.phone") String username, @Query("parent.password") String password);

}
