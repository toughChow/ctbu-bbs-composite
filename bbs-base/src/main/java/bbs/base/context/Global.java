package bbs.base.context;


import bbs.base.lang.Consts;
import bbs.base.utils.PropertiesLoader;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Global {

    /**
     * 保存全局属性值
     */
    private static ConcurrentMap<String, String> map = new ConcurrentHashMap<>();

    /**
     * 属性文件加载对象
     */
    private static PropertiesLoader propertiesLoader = new PropertiesLoader(Consts.BBS_CONFIG);

    private static Boolean imageDomain = null;

    private static String imageHost = null;

    /**
     * 获取配置
     */
    public static String getConfig(String key) {
        String value = map.get(key);
        if (value == null) {
            value = propertiesLoader.getProperty(key);
            map.put(key, value);
        }
        return value;
    }

    public static boolean getImageDomain() {
        if (imageDomain == null) {
            imageDomain = propertiesLoader.getBoolean("resource.domain", false);
        }
        return imageDomain;
    }

    public static String getImageHost() {
        if (imageHost == null) {
            imageHost = propertiesLoader.getProperty("resource.host");
        }
        return imageHost;
    }

}
