package org.simpleframework.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class ClassUtil {
    private static final String FILE_PROTOCAL = "file";

    /**
     * 根据传入的包名，来提取所有的class对象
     */
    public static Set<Class<?>> extractPackageClass(String packageName) {
        // 获取类加载器classloader
        ClassLoader classLoader = getClassLoader();
        // 根据getResource()方法获取加载的资源的url
        URL url = classLoader.getResource(packageName.replace(".", File.separator));
        if (url == null) {
            log.warn("unable to retrieve anything from package: " + packageName);
            return null;
        }
        Set<Class<?>> classSet = null;
        if (url.getProtocol().equalsIgnoreCase(FILE_PROTOCAL)) {
            classSet = new HashSet<>();
            File packageDirectory = new File(url.getPath());
            extractClassFile(classSet, packageDirectory, packageName);
        }
        return classSet;
    }

    /**
     * @param classSet
     * @param fileSource
     * @param packageName
     */
    private static void extractClassFile(Set<Class<?>> classSet, File fileSource, String packageName) {
        if(fileSource.listFiles().length==0){
            return;
        }
        // 列出当前文件夹中的所有文件和文件夹
        File[] files = fileSource.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                } else {
                    //获取文件的绝对值路径
                    String absoluteFilePath = file.getAbsolutePath();
                    if (absoluteFilePath.endsWith(".class")) {
                        addToClassSet(absoluteFilePath);
                    }
                }
                return false;
            }

            // 这个方法是存在于new FileFilter这个匿名类中的
            private void addToClassSet(String absoluteFilePath) {
                // 从class文件的绝对值路径里面提取包含了package的类名
                absoluteFilePath = absoluteFilePath.replace(File.separator, ".");
                //根据包名截断前面的内容
                String className = absoluteFilePath.substring(absoluteFilePath.indexOf(packageName));
                className = className.substring(0, className.lastIndexOf("."));
                // 通过反射机制获取对应的class对象并加入到classSet里
                Class targetClass = loadClass(className);
                classSet.add(targetClass);

            }
        });
        if (files != null) {
            for (File file : files) {
                extractClassFile(classSet, file, packageName);
            }
        }


    }

    /**
     * 获取classload
     *
     * @return 当前的classloader
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("load class error" + e);
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        extractPackageClass("com.imooc.entity");
    }
}
