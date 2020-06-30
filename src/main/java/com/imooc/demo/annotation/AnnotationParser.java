package com.imooc.demo.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnotationParser {
    // parse the type annotation
    public static void parseTypeAnnotation() throws ClassNotFoundException {
        Class clazz = Class.forName("com.imooc.demo.annotation.ImoocCourse");
        // 注意这里获取的是class对象的注解，也就是写在class上面的那个注解，而不是写在其里面的方法和成员变量的注解
        Annotation[] annotations = clazz.getAnnotations();
        for (Annotation annotation : annotations) {
            CourseInfoAnnotation courseInfoAnnotation = (CourseInfoAnnotation) annotation;
            System.out.println("courseName: " + courseInfoAnnotation.courseName() + "\n" + "courseProfile: " + courseInfoAnnotation.courseProfile() + "\n");
        }

    }

    public static void parseFieldAnnotation() throws ClassNotFoundException {
        Class clazz = Class.forName("com.imooc.demo.annotation.ImoocCourse");
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            // 先判断成员变量是否有指定注解类型的注解
            if (f.isAnnotationPresent(PersonInfoAnnotation.class)) {
                PersonInfoAnnotation personInfoAnnotation = f.getAnnotation(PersonInfoAnnotation.class);
                System.out.println("name: " + personInfoAnnotation.name() + "\n" + "gender: " + personInfoAnnotation.gender() + "\n");
                for (String language : personInfoAnnotation.languages()) {
                    System.out.println("language: " + language);
                }
            }
        }
    }
    public static void parseMethodAnnotation() throws ClassNotFoundException{
        Class clazz = Class.forName("com.imooc.demo.annotation.ImoocCourse");
        Method[] methods = clazz.getMethods();
        for(Method m:methods){
            if(m.isAnnotationPresent(CourseInfoAnnotation.class)){
                CourseInfoAnnotation courseInfoAnnotation = m.getAnnotation(CourseInfoAnnotation.class);
                System.out.println("courseName: " + courseInfoAnnotation.courseName() + "\n" + "courseProfile: " + courseInfoAnnotation.courseProfile() + "\n");
            }
        }

    }

    public static void main(String[] args) throws ClassNotFoundException {
        parseTypeAnnotation();
        parseFieldAnnotation();
        parseMethodAnnotation();
    }
}
