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
import android.widget.TextView;

import com.boqii.plant.R;
import com.boqii.plant.base.BaseFragment;
import com.boqii.plant.data.Login.model.User;

import butterknife.BindView;
import butterknife.OnClick;

import static com.facebook.common.internal.Preconditions.checkNotNull;


/**
 * login screen.
 */
public class LoginFragment extends BaseFragment implements LoginContract.View {


    @BindView(R.id.info)
    TextView info;

    private LoginContract.Presenter mPresenter;


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void setPresenter(@NonNull LoginContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public int getLayoutID() {
        return R.layout.login_frag;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @OnClick(R.id.login)
    void login(){
        mPresenter.login("18217612175","111111");
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setUsernameError() {

    }

    @Override
    public void setPasswordError() {

    }

    @Override
    public void navigateToHome(User user) {
        info.setText(user.toString());
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

}
