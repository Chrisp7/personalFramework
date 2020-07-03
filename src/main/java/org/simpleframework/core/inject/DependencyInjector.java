package org.simpleframework.core.inject;

import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.BeanContainer;
import org.simpleframework.core.inject.annotation.Autowired;
import org.simpleframework.util.ClassUtil;
import org.simpleframework.util.ValidationUtil;

import java.lang.reflect.Field;
import java.util.Set;

@Slf4j
public class DependencyInjector {

    private BeanContainer beanContainer;

    public DependencyInjector() {
        beanContainer = BeanContainer.getInstance();
    }

    /**
     * 实现所有的依赖注入操作
     */
    public void doIoc(){
        // 检查一下beancontainer里面是不是空的
        if(beanContainer.size()==0){
            log.warn("no beans inside beanContainer");
            return;
        }
        // 遍历bean容器里的所有class对象
        for(Class<?> clazz:beanContainer.getClasses()){
            Field[] fields = clazz.getDeclaredFields();
            if(fields.length==0){
                continue;
            }
            // 遍历Class对象的所有成员变量
            for(Field field:fields){
                // 找出被autowired标记的成员变量
                if(field.isAnnotationPresent(Autowired.class)){
                    Autowired autowiredInstance = field.getAnnotation(Autowired.class);
                    String autowiredValue = autowiredInstance.value();
                    // 获取这些成员变量的类型
                    Class<?> fieldClass = field.getType(); //**
                    // 获取这些成员变量的类型在容器里的对应实例
                    Object fieldInstance = getFieldInstanceFromFieldClass(fieldClass,autowiredValue);
                    // 通过反射将对应的成员变量实例注入到成员变量所在类的实例里
                    if(fieldInstance==null){
                        throw new RuntimeException("unable to inject relevant type , target fieldClass is: "+fieldClass.getName()+"autowiredValue: "+autowiredValue);
                    }else{
                        Object classBean = beanContainer.getBean(clazz);
                        ClassUtil.setField(field,classBean,fieldInstance,true);
                    }
                }
            }
        }


    }
    private Object getFieldInstanceFromFieldClass(Class<?> fieldClass,String autowiredValue){
        Object fieldInstance = beanContainer.getBean(fieldClass);
        if(fieldInstance!=null){
            return fieldInstance;
        }else{
            Class<?> fieldImplClass = getImplementClass(fieldClass,autowiredValue);
            if(fieldImplClass!=null){
                return beanContainer.getBean(fieldImplClass);
            }else{
                log.error("cannot find implement class of the fieldClass");
                return null;
            }
        }
    }
    private Class<?> getImplementClass(Class<?> fieldClass,String autowiredValue){
        Set<Class<?>> classSet = beanContainer.getClassesBySuper(fieldClass);
        if(!ValidationUtil.isEmpty(classSet)){
            if(ValidationUtil.isEmpty(autowiredValue)){
                if(classSet.size()==1){
                    return classSet.iterator().next();
                }else{
                    throw new RuntimeException("multiple implemented classes for "+fieldClass.getName()+" please specify the implemented class name");
                }
            }else{
                for(Class<?> clazz:classSet){
                    if(autowiredValue.equals(clazz.getSimpleName())){
                        return clazz;
                    }
                }
            }
        }
        return null;
    }



}
