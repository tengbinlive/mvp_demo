/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.boqii.plant.ui.Login;

import android.support.annotation.NonNull;

import com.boqii.plant.api.ApiHelper;
import com.boqii.plant.api.helper.Result;
import com.boqii.plant.api.service.Api;
import com.boqii.plant.data.Login.model.User;

import rx.Observable;
import rx.Subscriber;

import static com.facebook.common.internal.Preconditions.checkNotNull;


/**
 * Listens to user actions from the UI ({@link LoginFragment}), retrieves the data and updates
 * the UI as required.
 */
public class LoginPresenter implements LoginContract.Presenter {


    private final LoginContract.View mLoginView;

    public LoginPresenter(@NonNull LoginContract.View loginView) {
        mLoginView = checkNotNull(loginView, "StatisticsView cannot be null!");

        mLoginView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void login(String username, String password) {
        mLoginView.showProgress();
        Subscriber<User> subscriber = new Subscriber<User>() {
            @Override
            public void onCompleted() {
                if (!mLoginView.isActive()) {
                    return;
                }
                mLoginView.hideProgress();
            }

            @Override
            public void onError(Throwable e) {
                if (!mLoginView.isActive()) {
                    return;
                }
//                    根据  ((ApiException) e).getResultCode();
//                    mLoginView.onPasswordError();
//                    mLoginView.onUsernameError();
            }

            @Override
            public void onNext(User user) {
                if (!mLoginView.isActive()) {
                    return;
                }
                mLoginView.navigateToHome(user);
            }
        };

        Observable<Result<User>> observable = Api.getInstance().getLoginService().login(username, password);

        ApiHelper.wrap(observable, subscriber);
    }
}
