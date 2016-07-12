package com.boqii.plant;

/**
 * 常量类.
 * <p>
 * 约定:
 * 1)Constants类里保存系统安装之后就一直保持不变的常量;
 * 2)App类里保存系统启动后可变的变量,变量的值一般在系统初始化时保存,和状态相关的量在过程中可变;
 * 3)SharedPeferences对象持久化App里部分的变量, 供App初始化时读取, 其他类统一读取App里的变量,
 * 不访问SharedPerferences, 如果以后更换持久化的方式,例如DB,则仅修改App类就可以.
 *
 * @author bin.teng
 */
public class Constants {


    public static final String DB_NAME = "app_db";


    public static final String SHARED_PREFERENCES_FILE_NAME = "app_shared";

    /**
     * 存储目录/文件
     **/
    public static final String ROOT_DIR = "/app";


}
