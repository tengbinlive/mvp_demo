package com.boqii.plant.base;


import android.content.Intent;
import android.os.Bundle;

import com.boqii.plant.R;
import com.boqii.plant.ui.Login.LoginActivity;

/**
 * 启动界面
 */
public class LauncherActivity extends BaseActivity {


    @Override
    public int getContentView() {
        return R.layout.launcher_act;
    }

    @Override
    public void EInit(Bundle savedInstanceState) {
        super.EInit(savedInstanceState);
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        mSwipeBackLayout.setEnableGesture(false);
    }
}
