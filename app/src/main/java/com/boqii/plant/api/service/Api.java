package com.boqii.plant.api.service;

import com.boqii.plant.api.helper.RetrofitClient;

public class Api {

    private LoginService loginService;

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final Api INSTANCE = new Api();
    }

    //获取单例
    public static Api getInstance() {
        return SingletonHolder.INSTANCE;
    }


    //构造方法私有
    private Api() {
    }

    /**
     *
     * 登录相关
     *
     * @return
     */
    public LoginService getLoginService() {
        if (null == loginService) {
            loginService = RetrofitClient.getRetrofit().create(LoginService.class);
        }
        return loginService;
    }


}
