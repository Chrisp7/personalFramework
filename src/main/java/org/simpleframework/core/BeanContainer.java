package org.simpleframework.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.annotation.Component;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.core.annotation.Repository;
import org.simpleframework.core.annotation.Service;
import org.simpleframework.util.ClassUtil;
import org.simpleframework.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {
    // 存放所有被配置标记的目标对象的map
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap();
    // 加载bean的注解列表
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION = Arrays.asList(Component.class, Controller.class, Service.class, Repository.class);
    // 判断容器是否已经被加载过
    private boolean loaded = false;

    // 获取bean实例的数量
    public int size() {
        return beanMap.size();
    }

    public boolean isLoaded() {
        return loaded;
    }

    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    private enum ContainerHolder {
        HOLDER;
        private BeanContainer instance;

        ContainerHolder() {
            instance = new BeanContainer();
        }
    }

    // 扫描加载所有bean
    public synchronized void loadBeans(String packageName) {
        if (isLoaded()) {
            log.warn("Bean container has been loaded");
            return;
        }
        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);
        if (ValidationUtil.isEmpty(classSet)) {
            log.warn("extract nothing from packageName: ", packageName);
            return;
        }
        for (Class<?> clazz : classSet) {
            for (Class<? extends Annotation> annotation : BEAN_ANNOTATION) {
                if (clazz.isAnnotationPresent(annotation)) {
                    // 将目标类本身作为键，将目标类的实例作为值，存入beanMap中
                    beanMap.put(clazz, ClassUtil.newInstance(clazz, true));
                }
            }
        }
        loaded = true;
    }

    /**
     * 添加新的class对象和其对应的实例
     *
     * @param clazz
     * @param obj
     * @return
     */
    public Object addBean(Class<?> clazz, Object obj) {
        return beanMap.put(clazz, obj);

    }

    /**
     * 删除class对象
     *
     * @param clazz
     * @return 返回被删除的bean实例，没有则为null
     */
    public Object removeBean(Class<?> clazz) {
        return beanMap.remove(clazz);
    }

    /**
     * 根据class对象获取实例
     *
     * @param clazz
     * @return
     */
    public Object getBean(Class<?> clazz) {
        return beanMap.get(clazz);
    }

    /**
     * 获取所有的class对象
     *
     * @return 一个包含所有class对象的set
     */
    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * 获取所有的bean
     *
     * @return
     */
    public Set<Object> getBeans() {
        return new HashSet<>(beanMap.values());
    }

    /**
     * 返回被注解标记的所有class对象
     * @param annotation
     * @return
     */
    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        Set<Class<?>> classSet = getClasses();
        if (classSet.isEmpty()) {
            log.warn("nothing in the beanMap");
            return null;
        }
        Set<Class<?>> res = classSet.stream().filter(e -> (e.isAnnotationPresent(annotation))).collect(Collectors.toSet());
        return res.size() > 0 ? res : null;
    }

    /**
     * 根据超类获取对应子类的class
     * @param interfaceOrClass
     * @return
     */
    public Set<Class<?>> getClassesBySuper(Class<?> interfaceOrClass) {
        Set<Class<?>> classSet = getClasses();
        if (classSet.isEmpty()) {
            log.warn("nothing in the beanMap");
            return null;
        }
        Set<Class<?>> res = classSet.stream().filter(e -> (interfaceOrClass.isAssignableFrom(e)&&!interfaceOrClass.equals(e))).collect(Collectors.toSet());
        return res.size() > 0 ? res : null;
    }


}
