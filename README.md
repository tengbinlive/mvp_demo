
# build

    debug 调试版
    alpha 测试版
    release 发布版
    
    配置api
    buildConfigField "String", "API_HOST", "\"http://www.mytian.com.cn/\""//API Host 
    配置debug
    buildConfigField "boolean", "CONFIG_DEBUG", "false"// config
    配置日志输出
    buildConfigField "boolean", "LOG_DEBUG", "false"// log
    内存监控&fps监控（默认关闭）
    buildConfigField "boolean", "CANARY_DEBUG", "false"// canary


# 注意事项

1.每次生成新dao时需要提升数据版本schemaVersion

    greendao {
       schemaVersion 2
       daoPackage "com.dao"
       targetGenDir 'src/main/java'
    }

2.三星系列APP需要

    debuggable false