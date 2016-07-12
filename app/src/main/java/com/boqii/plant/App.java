package com.boqii.plant;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.net.ConnectivityManager;
import android.os.StrictMode;

import com.boqii.plant.base.enums.ConfigKeyEnum;
import com.boqii.plant.base.manager.ActivityManager;
import com.boqii.plant.base.manager.ConfigManager;
import com.boqii.plant.util.ProcessUtil;
import com.dao.DaoMaster;
import com.dao.DaoSession;
import com.debug.StrictModeWrapper;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.utils.FileUtils;
import com.utils.NetworkUtil;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.GINGERBREAD;


/**
 * App运行时上下文.
 * <p/>
 * 约定: 1)Constant类里保存系统安装之后就一直保持不变的常量;
 * 2)App类里保存系统启动后可变的变量,变量的值一般在系统初始化时保存,和状态相关的量在过程中可变;
 * 3)SharedPeferences对象持久化App里部分的变量, 供App初始化时读取, 其他类统一读取App里的变量,
 * 不访问SharedPerferences, 如果以后更换持久化的方式,例如DB,则仅修改App类就可以.
 *
 * @author bin.teng
 */
public class App extends Application {

    private static final String TAG = App.class.getSimpleName();

    public ActivityManager activityManager = null;

    private Activity currentActivity;

    private static App instance;

    private static DaoMaster daoMaster;

    private static DaoSession daoSession;

    private BroadcastReceiver connectionReceiver;

    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Activity currentActivity) {
        this.currentActivity = currentActivity;
    }


    /**
     * 获得本类的一个实例
     */
    public static App getInstance() {
        return instance;
    }


    /**
     * 取得DaoMaster
     */
    public static DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(App.getInstance(), Constants.DB_NAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     */
    public static DaoSession getDaoSession() {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster();
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }


    //---------以下变量存储在APP(内存)中----------//

    /**
     * 当前网络状态
     */
    private static NetworkUtil.NetworkClassEnum currentNetworkStatus = NetworkUtil.NetworkClassEnum.UNKNOWN;


    /**
     * @return 返回当前网络状态枚举类(例如: 未知 / 2G/ 3G / 4G / wifi)
     */
    public static NetworkUtil.NetworkClassEnum getCurrentNetworkStatus() {
        return currentNetworkStatus;
    }

    /**
     * @param currentNetworkStatus 当前网络状态枚举类
     */
    public static void setCurrentNetworkStatus(NetworkUtil.NetworkClassEnum currentNetworkStatus) {
        App.currentNetworkStatus = currentNetworkStatus;
    }

    /**
     * 网络是否有效
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        return !NetworkUtil.NetworkClassEnum.UNKNOWN.equals(currentNetworkStatus);
    }

    //---------以下变量由ConfigManager管理----------//

    /**
     * @return 返回硬件设备编号
     */
    public static String getDeviceId() {
        return ConfigManager.getConfigAsString(ConfigKeyEnum.DEVICE_ID);
    }

    /**
     * @return 返回手机型号
     */
    public static String getMobileType() {
        return ConfigManager.getConfigAsString(ConfigKeyEnum.MOBILE_TYPE);
    }


    /**
     * @return 返回屏幕 宽
     */
    public static int getScreenWidth() {
        return ConfigManager.getConfigAsInt(ConfigKeyEnum.SCREEN_WIDTH);
    }

    /**
     * @return 返回屏幕 高
     */
    public static int getScreenHeight() {
        return ConfigManager.getConfigAsInt(ConfigKeyEnum.SCREEN_HEIGHT);
    }

    /**
     * @return 返回APP版本名称
     */
    public static String getAppVersionName() {
        return ConfigManager.getConfigAsString(ConfigKeyEnum.APP_VERSION_NAME);
    }

    /**
     * @return 返回APP版本code
     */
    public static int getAppVersionCode() {
        return ConfigManager.getConfigAsInt(ConfigKeyEnum.APP_VERSION_CODE);
    }

    /**
     * @return 是否第一次启动(某版本)
     */
    public static boolean isFirstLunch() {
        return ConfigManager.getConfigAsBoolean(ConfigKeyEnum.IS_FIRST_LUNCH);
    }

    private static boolean strictModeAvailable;

    static {
        try {
            StrictModeWrapper.checkAvailable();
            strictModeAvailable = true;
        } catch (Throwable throwable) {
            strictModeAvailable = false;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 多进程情况只初始化一次
        if (ProcessUtil.isCurMainProcess(getApplicationContext())) {

            instance = this;

            //内存监控
            if(BuildConfig.LEAKCANARY_DEBUG) {
                enabledStrictMode();
                LeakCanary.install(this);
            }


            if (strictModeAvailable) {
                int applicationFlags = getApplicationInfo().flags;
                if (BuildConfig.DEBUG && ((applicationFlags & ApplicationInfo.FLAG_DEBUGGABLE) != 0)) {
                    StrictModeWrapper.enableDefaults();
                }
            }

            //初始化自定义Activity管理器
            activityManager = ActivityManager.getScreenManager();

            // 初始化日志类,如果不是调试状态则不输出日志
            LogLevel logLevel = BuildConfig.LOG_DEBUG ? LogLevel.FULL : LogLevel.NONE;

            Logger.init("MAIN")                 // default PRETTYLOGGER or use just init()
                    .methodCount(3)                 // default 2
                    .hideThreadInfo()               // default shown
                    .logLevel(logLevel)        // default LogLevel.FULL
                    .methodOffset(2);                // default 0

            Logger.v(TAG, "成功初始化LOG日志.");

            // 初始化APP相关目录
            FileUtils.Dir.ROOT_DIR = Constants.ROOT_DIR;
            FileUtils.initDirectory();
            Logger.v(TAG, "成功初始化APP相关目录.");

            //本地数据库
            initDAOData();

            // 保存当前网络状态(在每次网络通信时可能需要判断当前网络状态)
            setCurrentNetworkStatus(NetworkUtil.getCurrentNextworkState(this));
            Logger.v(TAG, "保存当前网络状态:" + getCurrentNetworkStatus());
            //注册网络状态监听广播
            newConnectionReceiver();


        }
    }

    private void enabledStrictMode() {
        if (SDK_INT >= GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                    .detectAll() //
                    .penaltyLog() //
                    .penaltyDeath() //
                    .build());
        }
    }

    private void initDAOData() {
        // 系统配置业务.
        ConfigManager.init(this);
    }

    //创建并注册网络状态监听广播
    private void newConnectionReceiver() {
        connectionReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setCurrentNetworkStatus(NetworkUtil.getCurrentNextworkState(context));
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectionReceiver, intentFilter);
    }

    //销毁网络状态监听广播
    private void unConnectionReceiver() {
        if (connectionReceiver != null) {
            unregisterReceiver(connectionReceiver);
            connectionReceiver = null;
        }
    }

    //退出app
    public void exit() {
        activityManager.popAllActivityExceptOne(this.getClass());
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    @Override
    public void onTerminate() {
        unConnectionReceiver();
        super.onTerminate();
    }


}
