package org.simpleframework.util;

import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.Set;

@Slf4j
public class ClassUtil {
    /**
     * 根据传入的包名，来提取所有的class对象
     */
    Set<Class<?>> extractPackageClass(String packageName) {
        // 获取类加载器classloader
        ClassLoader classLoader = getClassLoader();
        // 根据getResource()方法获取加载的资源的url
        URL url = classLoader.getResource(packageName.replace(".", "/"));
        if (url == null) {
            log.warn("unable to retrieve anything from package: " + packageName);
            return null;
        }
        return null;
    }

    /**
     * 获取classload
     *
     * @return 当前的classloader
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
