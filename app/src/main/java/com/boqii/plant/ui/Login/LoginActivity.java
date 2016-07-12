package com.boqii.plant.ui.Login;


import android.os.Bundle;

import com.boqii.plant.R;
import com.boqii.plant.base.BaseActivity;
import com.boqii.plant.util.ActivityUtils;

/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {

    @Override
    public void EInit(Bundle savedInstanceState) {
        super.EInit(savedInstanceState);
        LoginFragment statisticsFragment = (LoginFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);
        if (statisticsFragment == null) {
            statisticsFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    statisticsFragment, R.id.contentFrame);
        }

        // Create the presenter
        new LoginPresenter(statisticsFragment);

    }

    @Override
    public int getContentView() {
        return R.layout.login_act;
    }


}
